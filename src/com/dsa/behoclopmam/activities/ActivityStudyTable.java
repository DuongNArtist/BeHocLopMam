package com.dsa.behoclopmam.activities;

import java.util.Random;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.dsa.behoclopmam.R;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityStudyTable extends Activity implements OnClickListener {

	private Handler handler;
	private Random random;
	private Button[] btnTableChar;
	private ImageButton btnTableReturn;
	private String[] charUpper;
	private String[] charLower;
	private InterstitialAd interstitialAd;
	private int[] indexs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_table);
		random = new Random();
		handler = new Handler();
		charUpper = getResources().getStringArray(R.array.char_upper);
		charLower = getResources().getStringArray(R.array.char_lower);
		createTable();
		createButton();
		createInterstitialAd();
	}

	private void createInterstitialAd() {
		interstitialAd = new InterstitialAd(this,
				"ca-app-pub-9404075236503358/9401530825");
		interstitialAd.loadAd(new AdRequest());
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadData();
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseData();
	}

	private void createTable() {
		indexs = new int[8];
		for (int i = 0; i < indexs.length; i++) {
			indexs[i] = random.nextInt(29);
		}
	}

	private void createButton() {
		btnTableChar = new Button[8];
		for (int i = 0; i < btnTableChar.length; i++) {
			btnTableChar[i] = (Button) findViewById(R.id.btnTableChar0 + i);
			btnTableChar[i].setOnClickListener(this);
			if (random.nextBoolean()) {
				btnTableChar[i].setText(charUpper[indexs[i]]);
			} else {
				btnTableChar[i].setText(charLower[indexs[i]]);
			}
		}
		btnTableReturn = (ImageButton) findViewById(R.id.btnTableReturn);
		btnTableReturn.setOnClickListener(this);
	}

	private void setEnableButton(boolean enable) {
		btnTableReturn.setEnabled(enable);
		for (int i = 0; i < btnTableChar.length; i++) {
			btnTableChar[i].setEnabled(enable);
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.btnTableReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else {
			int i = id - R.id.btnTableChar0;
			Mfx.playChar(indexs[i]);
			indexs[i] = random.nextInt(29);
			if (random.nextBoolean()) {
				btnTableChar[i].setText(charUpper[indexs[i]]);
			} else {
				btnTableChar[i].setText(charLower[indexs[i]]);
			}
			setEnableButton(false);
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					setEnableButton(true);
				}
			}, 500);
		}
	}

	private void loadData() {
		new AsyncTask<String, Integer, Void>() {

			@Override
			protected void onPreExecute() {
				setEnableButton(false);
			};

			@Override
			protected Void doInBackground(String... params) {
				Mfx.loadChar();
				return null;
			};

			@Override
			protected void onPostExecute(Void result) {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Mfx.playChar(29);
						handler.postDelayed(new Runnable() {

							@Override
							public void run() {
								setEnableButton(true);
							}
						}, 2000);
					}
				}, 1000);
			};
		}.execute();
	}

	private void releaseData() {
		Mfx.releaseChar();
	}
}

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
import com.dsa.behoclopmam.utilities.Dino;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityStudyFind extends Activity implements OnClickListener {

	private Random random;
	private Handler handler;

	private String[] charUpper;
	private String[] charLower;

	private ImageButton btnTableReturn;
	private ImageButton btnTableSpeak;
	private Button[] btnTableChar;

	private InterstitialAd interstitialAd;

	private int[] indexs;
	private int key;
	private int valueKey;
	private int valueOther;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_table);
		random = new Random();
		handler = new Handler();
		createString();
		createButton();
		createQuestion();
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

	private void createString() {
		charUpper = getResources().getStringArray(R.array.char_upper);
		charLower = getResources().getStringArray(R.array.char_lower);
	}

	private void createButton() {
		btnTableSpeak = (ImageButton) findViewById(R.id.btnTableSpeak);
		btnTableSpeak.setOnClickListener(this);
		btnTableSpeak.setVisibility(ImageButton.VISIBLE);
		btnTableReturn = (ImageButton) findViewById(R.id.btnTableReturn);
		btnTableReturn.setOnClickListener(this);
		btnTableChar = new Button[8];
		for (int i = 0; i < btnTableChar.length; i++) {
			btnTableChar[i] = (Button) findViewById(R.id.btnTableChar0 + i);
			btnTableChar[i].setOnClickListener(this);
		}
	}

	private void createQuestion() {
		valueKey = random.nextInt(29);
		indexs = new int[btnTableChar.length];
		for (int i = 0; i < indexs.length; i++) {
			do {
				valueOther = random.nextInt(29);
			} while (valueKey == valueOther);
			indexs[i] = valueOther;
		}
		key = random.nextInt(btnTableChar.length);
		indexs[key] = valueKey;
		for (int i = 0; i < btnTableChar.length; i++) {
			if (random.nextBoolean()) {
				btnTableChar[i].setText(charUpper[indexs[i]]);
			} else {
				btnTableChar[i].setText(charLower[indexs[i]]);
			}
		}
	}

	private void speakQuestion() {
		Mfx.playFind(2);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				Mfx.playChar(valueKey);
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Mfx.playFind(3);
						setEnableButton(true);
					}
				}, 300);
			}
		}, 2000);
	}

	private void setEnableButton(boolean enable) {
		btnTableReturn.setEnabled(enable);
		btnTableSpeak.setEnabled(enable);
		for (int i = 0; i < btnTableChar.length; i++) {
			btnTableChar[i].setEnabled(enable);
		}
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.btnTableReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else if (id == R.id.btnTableSpeak) {
			speakQuestion();
		} else {
			int index = id - R.id.btnTableChar0;
			setEnableButton(false);
			if (index == key) {
				Mfx.playFind(1);
				new Dino(this, Dino.STATE_TRUE, Dino.DURATION_SHORT);
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						createQuestion();
						speakQuestion();
					}
				}, 2000);
			} else {
				Mfx.playFind(0);
				new Dino(this, Dino.STATE_FALSE, Dino.DURATION_SHORT);
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						speakQuestion();
					}
				}, 2000);
			}
		}
	}

	@Override
	public void onBackPressed() {
		return;
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
				Mfx.loadFind();
				Mfx.loadTrueFalse();
				return null;
			};

			@Override
			protected void onPostExecute(Void result) {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						speakQuestion();
					}
				}, 1000);
			};
		}.execute();
	}

	private void releaseData() {
		Mfx.releaseChar();
		Mfx.releaseFind();
		Mfx.releaseTrueFalse();
	}
}

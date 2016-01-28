package com.dsa.behoclopmam.activities;

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

public class ActivityStudyNumber extends Activity implements OnClickListener {

	private Handler handler;
	private Button[] btnNumber;
	private ImageButton btnNumberReturn;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_number);
		handler = new Handler();
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

	private void createButton() {
		btnNumber = new Button[10];
		for (int i = 0; i < btnNumber.length; i++) {
			btnNumber[i] = (Button) findViewById(R.id.btnNumber0 + i);
			btnNumber[i].setOnClickListener(this);
			btnNumber[i].setText(i + "");
		}
		btnNumberReturn = (ImageButton) findViewById(R.id.btnNumberReturn);
		btnNumberReturn.setOnClickListener(this);
	}

	private void setEnableButton(boolean enable) {
		btnNumberReturn.setEnabled(enable);
		for (int i = 0; i < btnNumber.length; i++) {
			btnNumber[i].setEnabled(enable);
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.btnNumberReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else {
			int i = id - R.id.btnNumber0;
			Mfx.playNumber(i);
			setEnableButton(false);
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					setEnableButton(true);
				}
			}, 300);
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
				Mfx.loadNumber();
				return null;
			};

			@Override
			protected void onPostExecute(Void result) {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Mfx.playNumber(10);
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
		Mfx.releaseNumber();
	}
}

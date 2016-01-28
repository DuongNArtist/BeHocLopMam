package com.dsa.behoclopmam.activities;

import java.util.Random;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.dsa.behoclopmam.R;
import com.dsa.behoclopmam.utilities.Dino;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityStudyHigherLower extends Activity implements
		OnClickListener {

	private Random random;
	private Handler handler;

	private ImageButton btnHigherLowerReturn;
	private ImageButton btnHigherLowerSpeak;

	private ImageButton btnHigherLower0;
	private ImageButton btnHigherLower1;

	private InterstitialAd interstitialAd;

	private int index;
	private int key;
	private int key0;
	private int key1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_higher_lower);
		random = new Random();
		handler = new Handler();
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

	private void createButton() {
		btnHigherLowerReturn = (ImageButton) findViewById(R.id.btnHigherLowerReturn);
		btnHigherLowerSpeak = (ImageButton) findViewById(R.id.btnHigherLowerSpeak);
		btnHigherLower0 = (ImageButton) findViewById(R.id.btnHigherLower0);
		btnHigherLower1 = (ImageButton) findViewById(R.id.btnHigherLower1);
		btnHigherLowerReturn.setOnClickListener(this);
		btnHigherLowerSpeak.setOnClickListener(this);
		btnHigherLower0.setOnClickListener(this);
		btnHigherLower1.setOnClickListener(this);
	}

	private void createQuestion() {
		int bound = R.drawable.hl_higher_17 - R.drawable.hl_higher_00;
		index = random.nextInt(bound);
		if (random.nextBoolean()) {
			key0 = R.drawable.hl_higher_00 + index;
			key1 = R.drawable.hl_lower_00 + index;
			key = key0;
		} else {
			key0 = R.drawable.hl_lower_00 + index;
			key1 = R.drawable.hl_higher_00 + index;
			key = key1;
		}
		btnHigherLower0.setImageResource(key0);
		btnHigherLower1.setImageResource(key1);
	}

	private void speakQuestion() {
		Mfx.playHigherLower(0);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setEnableButton(true);
			}
		}, 2000);
	}

	private void setEnableButton(boolean enabled) {
		btnHigherLowerSpeak.setEnabled(enabled);
		btnHigherLower0.setEnabled(enabled);
		btnHigherLower1.setEnabled(enabled);
		btnHigherLowerReturn.setEnabled(enabled);
	}

	private void checkAnswer(int answer) {
		setEnableButton(false);
		if (answer == key) {
			Mfx.playTrueFalse(1);
			Mfx.playTrueFalse(2 + random.nextInt(3));
			new Dino(this, Dino.STATE_TRUE, Dino.DURATION_SHORT);
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					createQuestion();
					speakQuestion();
				}
			}, 2000);
		} else {
			Mfx.playTrueFalse(0);
			new Dino(this, Dino.STATE_FALSE, Dino.DURATION_SHORT);
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					speakQuestion();
				}
			}, 1000);
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnHigherLower0:
			checkAnswer(key0);
			break;
		case R.id.btnHigherLower1:
			checkAnswer(key1);
			break;
		case R.id.btnHigherLowerSpeak:
			speakQuestion();
			break;
		case R.id.btnHigherLowerReturn:
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
			break;
		default:
			break;
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
				Mfx.loadHigherLower();
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
		Mfx.releaseHigherLower();
		Mfx.releaseTrueFalse();
	}
}

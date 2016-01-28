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

public class ActivityStudyTopCenterBottom extends Activity implements
		OnClickListener {

	private Random random;
	private Handler handler;
	private ImageButton btnTopCenterBottomReturn;
	private ImageButton btnTopCenterBottomSpeak;
	private ImageButton[] btnTopCenterBottom;
	private InterstitialAd interstitialAd;

	private int index;
	private int key;
	private int[] keys;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_top_center_bottom);
		random = new Random();
		handler = new Handler();
		keys = new int[3];
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
		btnTopCenterBottomReturn = (ImageButton) findViewById(R.id.btnTopCenterBottomReturn);
		btnTopCenterBottomSpeak = (ImageButton) findViewById(R.id.btnTopCenterBottomSpeak);
		btnTopCenterBottomReturn.setOnClickListener(this);
		btnTopCenterBottomSpeak.setOnClickListener(this);
		btnTopCenterBottom = new ImageButton[3];
		for (int i = 0; i < btnTopCenterBottom.length; i++) {
			btnTopCenterBottom[i] = (ImageButton) findViewById(R.id.btnTopCenterBottom0
					+ i);
			btnTopCenterBottom[i].setOnClickListener(this);
		}
	}

	private void createQuestion() {
		keys[0] = random.nextInt(70);
		do {
			keys[1] = random.nextInt(70);
		} while (keys[1] == keys[0]);
		do {
			keys[2] = random.nextInt(70);
		} while (keys[2] == keys[0] || keys[2] == keys[1]);

		index = random.nextInt(keys.length);
		key = keys[index];

		for (int i = 0; i < keys.length; i++) {
			btnTopCenterBottom[i].setImageResource(R.drawable.image_00
					+ keys[i]);
		}
	}

	private void speakQuestion() {
		Mfx.playTopCenterBottom(index);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setEnableButton(true);
			}
		}, 2000);
	}

	private void setEnableButton(boolean enabled) {
		btnTopCenterBottomReturn.setEnabled(enabled);
		btnTopCenterBottomSpeak.setEnabled(enabled);
		for (ImageButton imageButton : btnTopCenterBottom) {
			imageButton.setEnabled(enabled);
		}
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
		int id = view.getId();
		if (id == R.id.btnTopCenterBottomReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else if (id == R.id.btnTopCenterBottomSpeak) {
			speakQuestion();
		} else {
			checkAnswer(keys[id - R.id.btnTopCenterBottom0]);
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
				Mfx.loadTopCenterBottom();
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
		Mfx.releaseTopCenterBottom();
		Mfx.releaseTrueFalse();
	}
}

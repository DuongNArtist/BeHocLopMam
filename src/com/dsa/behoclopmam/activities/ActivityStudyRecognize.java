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

public class ActivityStudyRecognize extends Activity implements OnClickListener {
	public static final int[] idImg = { R.drawable.recognize_option_0_0,
			R.drawable.recognize_option_1_0, R.drawable.recognize_option_2_0,
			R.drawable.recognize_option_3_0 };
	public static final int[] key = { 0, 3, 1, 2, 2, 3, 1, 0 };

	private Random random;
	private Handler handler;
	private ImageButton btnRecognizeReturn;
	private ImageButton btnRecognizeSpeak;
	private ImageButton[] btnRecognize;
	private InterstitialAd interstitialAd;

	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_recognize);
		random = new Random();
		handler = new Handler();
		index = -1;
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
		btnRecognizeReturn = (ImageButton) findViewById(R.id.btnRecognizeReturn);
		btnRecognizeSpeak = (ImageButton) findViewById(R.id.btnRecognizeSpeak);
		btnRecognizeReturn.setOnClickListener(this);
		btnRecognizeSpeak.setOnClickListener(this);
		int number = R.id.btnRecognize3 - R.id.btnRecognize0 + 1;
		btnRecognize = new ImageButton[number];
		for (int i = 0; i < btnRecognize.length; i++) {
			btnRecognize[i] = (ImageButton) findViewById(R.id.btnRecognize0 + i);
			btnRecognize[i].setOnClickListener(this);
		}
	}

	private void createQuestion() {
		index++;
		if (index == 8) {
			index = 0;
		}
		for (int i = 0; i < btnRecognize.length; i++) {
			btnRecognize[i].setImageResource(idImg[i] + index);
		}
	}

	private void speakQuestion() {
		Mfx.playRecognize(index);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setEnableButton(true);
			}
		}, 2000);
	}

	private void setEnableButton(boolean enabled) {
		btnRecognizeSpeak.setEnabled(enabled);
		btnRecognizeReturn.setEnabled(enabled);
		for (ImageButton button : btnRecognize) {
			button.setEnabled(enabled);
		}
	}

	private void check(int answer) {
		setEnableButton(false);
		if (answer == key[index]) {
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
		if (id == R.id.btnRecognizeReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else if (id == R.id.btnRecognizeSpeak) {
			speakQuestion();
		} else {
			check(id - R.id.btnRecognize0);
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
				Mfx.loadRecognize();
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
		Mfx.releaseRecognize();
		Mfx.releaseTrueFalse();
	}
}

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

public class ActivityStudyPractice extends Activity implements OnClickListener {
	public static final int[] keys = { 2, 2, 2, 1, 0, 1, 1, 2, 1, 0, 2, 0, 1,
			0, 2, 1, 1, 0, 2, 0, 2, 2, 0, 1, 2, 1, 1, 0, };

	private Random random;
	private Handler handler;

	private ImageButton btnPracticeReturn;
	private ImageButton btnPracticeSpeak;
	private ImageButton[] btnPractice;

	private InterstitialAd interstitialAd;

	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_practice);
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
		btnPracticeReturn = (ImageButton) findViewById(R.id.btnPracticeReturn);
		btnPracticeSpeak = (ImageButton) findViewById(R.id.btnPracticeSpeak);
		btnPracticeReturn.setOnClickListener(this);
		btnPracticeSpeak.setOnClickListener(this);
		btnPractice = new ImageButton[3];
		for (int i = 0; i < btnPractice.length; i++) {
			btnPractice[i] = (ImageButton) findViewById(R.id.btnPractice0 + i);
			btnPractice[i].setOnClickListener(this);
		}
	}

	private void createQuestion() {
		index = random.nextInt(keys.length);
		for (int i = 0; i < btnPractice.length; i++) {
			btnPractice[i].setImageResource(R.drawable.practice_000 + i * 28
					+ index);
		}
	}

	private void speakQuestion() {
		Mfx.playPractice(index);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setEnableButton(true);
			}
		}, 2000);
	}

	private void setEnableButton(boolean enabled) {
		btnPracticeSpeak.setEnabled(enabled);
		btnPracticeReturn.setEnabled(enabled);
		for (ImageButton imageButton : btnPractice) {
			imageButton.setEnabled(enabled);
		}
	}

	private void checkAnswer(int answer) {
		setEnableButton(false);
		if (answer == keys[index]) {
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
		if (id == R.id.btnPracticeReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else if (id == R.id.btnPracticeSpeak) {
			speakQuestion();
		} else {
			checkAnswer(id - R.id.btnPractice0);
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
				Mfx.loadPractice();
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
		Mfx.releasePractice();
		Mfx.releaseTrueFalse();
	}
}

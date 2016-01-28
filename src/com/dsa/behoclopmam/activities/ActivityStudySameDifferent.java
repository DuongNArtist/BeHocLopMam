package com.dsa.behoclopmam.activities;

import java.util.Random;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.dsa.behoclopmam.R;
import com.dsa.behoclopmam.utilities.Dino;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityStudySameDifferent extends Activity implements
		OnClickListener {
	private Random random;
	private Handler handler;

	private ImageButton btnSameDifferentReturn;
	private ImageButton btnSameDifferentSpeak;
	private ImageView imgSameDifferent;
	private ImageButton[] btnSameDifferent;
	private InterstitialAd interstitialAd;

	private int type;
	private int index;
	private int key;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_same_different);
		random = new Random();
		handler = new Handler();
		createButton();
		createImage();
		createQuestion();
		createInterstitialAd();
	}

	private void createButton() {
		btnSameDifferentReturn = (ImageButton) findViewById(R.id.btnSameDifferentReturn);
		btnSameDifferentSpeak = (ImageButton) findViewById(R.id.btnSameDifferentSpeak);
		btnSameDifferentReturn.setOnClickListener(this);
		btnSameDifferentSpeak.setOnClickListener(this);
		btnSameDifferent = new ImageButton[4];
		for (int i = 0; i < btnSameDifferent.length; i++) {
			btnSameDifferent[i] = (ImageButton) findViewById(R.id.btnSameDifferent0
					+ i);
			btnSameDifferent[i].setOnClickListener(this);
		}
	}

	private void createImage() {
		imgSameDifferent = (ImageView) findViewById(R.id.imgSameDifferent);
	}

	private void createQuestion() {
		index = random.nextInt(45);
		key = random.nextInt(btnSameDifferent.length);
		if (random.nextBoolean()) {
			type = 0;
			imgSameDifferent.setVisibility(ImageView.VISIBLE);
		} else {
			type = 1;
			imgSameDifferent.setVisibility(ImageView.GONE);
		}
		imgSameDifferent.setImageResource(R.drawable.sd_same_00 + index);
		for (int i = 0; i < btnSameDifferent.length; i++) {
			if (key == i) {
				btnSameDifferent[i].setImageResource(R.drawable.sd_same_00
						+ index);
			} else {
				btnSameDifferent[i].setImageResource(R.drawable.sd_different_00
						+ index);
			}
		}
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

	private void loadData() {
		new AsyncTask<String, Integer, Void>() {

			@Override
			protected void onPreExecute() {
				setEnableButton(false);
			};

			@Override
			protected Void doInBackground(String... params) {
				Mfx.loadSameDifferent();
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

	@Override
	protected void onPause() {
		super.onPause();
		releaseData();
	}

	private void releaseData() {
		Mfx.releaseSameDifferent();
		Mfx.releaseTrueFalse();
	}

	private void speakQuestion() {
		Mfx.playSameDifferent(type);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setEnableButton(true);
			}
		}, 2000);
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

	private void setEnableButton(boolean enabled) {
		btnSameDifferentSpeak.setEnabled(enabled);
		btnSameDifferentReturn.setEnabled(enabled);
		for (int i = 0; i < btnSameDifferent.length; i++) {
			btnSameDifferent[i].setEnabled(enabled);
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.btnSameDifferentReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else {
			if (id == R.id.btnSameDifferentSpeak) {
				speakQuestion();
			} else {
				checkAnswer(id - R.id.btnSameDifferent0);
			}
		}
	}
}

package com.dsa.behoclopmam.activities;

import java.util.Random;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dsa.behoclopmam.R;
import com.dsa.behoclopmam.utilities.Dino;
import com.dsa.behoclopmam.utilities.Gfx;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityStudyMaxMin extends Activity implements OnClickListener {

	private Random random;
	private Handler handler;

	private ImageButton btnMaxMinReturn;
	private ImageButton btnMaxMinSpeak;

	private LinearLayout[] lnrMaxMin;

	private InterstitialAd interstitialAd;

	private int index;
	private int key;
	private int[] options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_max_min);
		random = new Random();
		handler = new Handler();
		options = new int[3];
		createButton();
		createLayout();
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
		btnMaxMinReturn = (ImageButton) findViewById(R.id.btnMaxMinReturn);
		btnMaxMinSpeak = (ImageButton) findViewById(R.id.btnMaxMinSpeak);
		btnMaxMinReturn.setOnClickListener(this);
		btnMaxMinSpeak.setOnClickListener(this);
	}

	private void createLayout() {
		lnrMaxMin = new LinearLayout[3];
		for (int i = 0; i < lnrMaxMin.length; i++) {
			lnrMaxMin[i] = (LinearLayout) findViewById(R.id.lnrMaxMin0 + i);
			lnrMaxMin[i].setOnClickListener(this);
		}
	}

	private void createQuestion() {
		options[0] = 1 + random.nextInt(9);
		do {
			options[1] = 1 + random.nextInt(9);
		} while (options[1] == options[0]);
		do {
			options[2] = 1 + random.nextInt(9);
		} while (options[2] == options[0] || options[2] == options[1]);

		index = random.nextInt(2);
		if (index == 0) {
			key = getMaxOf(options);
		} else {
			key = getMinOf(options);
		}

		for (int i = 0; i < lnrMaxMin.length; i++) {
			lnrMaxMin[i].removeAllViews();
			for (int j = 0; j < options[i]; j++) {
				ImageView imageView = new ImageView(this);
				imageView.setImageResource(R.drawable.image_00
						+ random.nextInt(70));
				int size = Math.round(54 * Gfx.srcDensity);
				imageView.setLayoutParams(new LayoutParams(size, size));
				lnrMaxMin[i].addView(imageView);
			}
		}
	}

	private void speakQuestion() {
		Mfx.playMaxMin(index);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setEnableButton(true);
			}
		}, 2000);
	}

	private void setEnableButton(boolean enabled) {
		btnMaxMinSpeak.setEnabled(enabled);
		btnMaxMinReturn.setEnabled(enabled);
		for (LinearLayout linearLayout : lnrMaxMin) {
			linearLayout.setEnabled(enabled);
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
		if (id == R.id.btnMaxMinReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else if (id == R.id.btnMaxMinSpeak) {
			speakQuestion();
		} else {
			checkAnswer(options[id - R.id.lnrMaxMin0]);
		}
	}

	private int getMaxOf(int[] items) {
		int max = items[0];
		for (int i = 0; i < items.length; i++) {
			if (items[i] > max) {
				max = items[i];
			}
		}
		return max;
	}

	private int getMinOf(int[] items) {
		int min = items[0];
		for (int i = 0; i < items.length; i++) {
			if (items[i] < min) {
				min = items[i];
			}
		}
		return min;
	}

	private void loadData() {
		new AsyncTask<String, Integer, Void>() {

			@Override
			protected void onPreExecute() {
				setEnableButton(false);
			};

			@Override
			protected Void doInBackground(String... params) {
				Mfx.loadMaxMin();
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
		Mfx.releaseMaxMin();
		Mfx.releaseTrueFalse();
	}
}

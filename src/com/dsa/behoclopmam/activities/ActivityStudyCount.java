package com.dsa.behoclopmam.activities;

import java.util.Random;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dsa.behoclopmam.R;
import com.dsa.behoclopmam.utilities.Dino;
import com.dsa.behoclopmam.utilities.Gfx;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityStudyCount extends Activity implements OnClickListener {

	private Random random;
	private Handler handler;

	private ImageButton btnCountReturn;
	private ImageButton btnCountSpeak;
	private LinearLayout lnrCount;
	private Button[] btnCount;

	private InterstitialAd interstitialAd;

	private int index;
	private int key;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_count);
		random = new Random();
		handler = new Handler();
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
		btnCountReturn = (ImageButton) findViewById(R.id.btnCountReturn);
		btnCountSpeak = (ImageButton) findViewById(R.id.btnCountSpeak);
		btnCountReturn.setOnClickListener(this);
		btnCountSpeak.setOnClickListener(this);
		btnCount = new Button[10];
		for (int i = 0; i < btnCount.length; i++) {
			btnCount[i] = (Button) findViewById(R.id.btnCount00 + i);
			btnCount[i].setOnClickListener(this);
		}
	}

	private void createLayout() {
		lnrCount = (LinearLayout) findViewById(R.id.lnrCount);
	}

	private void createQuestion() {
		index = random.nextInt(Mfx.NUMBER_OF_COUNTS);
		key = random.nextInt(btnCount.length);
		lnrCount.removeAllViews();
		int size = Math.round(54 * Gfx.srcDensity);
		for (int i = 0; i < key; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(R.drawable.count_00 + index);
			imageView.setLayoutParams(new LayoutParams(size, size));
			lnrCount.addView(imageView);
		}
	}

	private void speakQuestion() {
		Mfx.playCount(index);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setEnableButton(true);
			}
		}, 2000);
	}

	private void checkAnswer(int myKey) {
		setEnableButton(false);
		if (myKey == key) {
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
		btnCountReturn.setEnabled(enabled);
		btnCountSpeak.setEnabled(enabled);
		for (Button button : btnCount) {
			button.setEnabled(enabled);
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.btnCountReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else if (id == R.id.btnCountSpeak) {
			speakQuestion();
		} else {
			checkAnswer(id - R.id.btnCount00);
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
				Mfx.loadCount();
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
		Mfx.releaseCount();
		Mfx.releaseTrueFalse();
	}
}

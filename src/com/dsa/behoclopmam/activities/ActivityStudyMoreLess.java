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

public class ActivityStudyMoreLess extends Activity implements OnClickListener {

	private Random random;
	private Handler handler;

	private ImageButton btnMoreLessReturn;
	private ImageButton btnMoreLessSpeak;

	private LinearLayout[] lnrMoreLess;

	private InterstitialAd interstitialAd;

	private int index;
	private int key;
	private int[] options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_more_less);
		random = new Random();
		handler = new Handler();
		options = new int[2];
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
		btnMoreLessReturn = (ImageButton) findViewById(R.id.btnMoreLessReturn);
		btnMoreLessSpeak = (ImageButton) findViewById(R.id.btnMoreLessSpeak);
		btnMoreLessReturn.setOnClickListener(this);
		btnMoreLessSpeak.setOnClickListener(this);
	}

	private void createLayout() {
		lnrMoreLess = new LinearLayout[2];
		for (int i = 0; i < lnrMoreLess.length; i++) {
			lnrMoreLess[i] = (LinearLayout) findViewById(R.id.lnrMoreLess0 + i);
			lnrMoreLess[i].setOnClickListener(this);
		}
	}

	private void createQuestion() {
		index = random.nextInt(2);

		options[0] = 1 + random.nextInt(9);
		do {
			options[1] = 1 + random.nextInt(9);
		} while (options[1] == options[0]);

		if (index == 0) {
			if (options[0] > options[1]) {
				key = options[0];
			} else {
				key = options[1];
			}
		} else {
			if (options[0] < options[1]) {
				key = options[0];
			} else {
				key = options[1];
			}
		}

		for (int i = 0; i < lnrMoreLess.length; i++) {
			lnrMoreLess[i].removeAllViews();
			for (int j = 0; j < options[i]; j++) {
				ImageView imageView = new ImageView(this);
				imageView.setImageResource(R.drawable.image_00
						+ random.nextInt(70));
				int size = Math.round(54 * Gfx.srcDensity);
				imageView.setLayoutParams(new LayoutParams(size, size));
				lnrMoreLess[i].addView(imageView);
			}
		}
	}

	private void speakQuestion() {
		Mfx.playMoreLess(index);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setEnableButton(true);
			}
		}, 2000);
	}

	private void setEnableButton(boolean enabled) {
		btnMoreLessReturn.setEnabled(enabled);
		btnMoreLessSpeak.setEnabled(enabled);
		for (LinearLayout linearLayout : lnrMoreLess) {
			linearLayout.setEnabled(enabled);
		}
	}

	private void checkAnswer(int answer) {
		setEnableButton(false);
		if (answer == key) {
			Mfx.playTrueFalse(1);
			Mfx.playTrueFalse(2 + random.nextInt(3));
			new Dino(this, Dino.STATE_TRUE, Dino.DURATION_LONG);
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
		if (id == R.id.btnMoreLessReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else if (id == R.id.btnMoreLessSpeak) {
			speakQuestion();
		} else {
			checkAnswer(options[id - R.id.lnrMoreLess0]);
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
				Mfx.loadMoreLess();
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
		Mfx.releaseMoreLess();
		Mfx.releaseTrueFalse();
	}
}

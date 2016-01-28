package com.dsa.behoclopmam.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.dsa.behoclopmam.R;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityStudyShape extends Activity implements OnClickListener {

	private Handler handler;
	private ImageButton[] btnShape;
	private ImageButton btnShapeReturn;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_shape);
		handler = new Handler();
		createButton();
		createInterstitialAd();
	}

	private void createButton() {
		btnShape = new ImageButton[6];
		for (int i = 0; i < btnShape.length; i++) {
			btnShape[i] = (ImageButton) findViewById(R.id.btnShape0 + i);
			btnShape[i].setOnClickListener(this);
		}
		btnShapeReturn = (ImageButton) findViewById(R.id.btnShapeReturn);
		btnShapeReturn.setOnClickListener(this);
	}

	private void setEnableButton(boolean enable) {
		btnShapeReturn.setEnabled(enable);
		for (int i = 0; i < btnShape.length; i++) {
			btnShape[i].setEnabled(enable);
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

	@Override
	protected void onPause() {
		super.onPause();
		releaseData();
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.btnShapeReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else {
			int i = id - R.id.btnShape0;
			Mfx.playShape(i);
			setEnableButton(false);
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					setEnableButton(true);
				}
			}, 500);
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
				Mfx.loadShape();
				return null;
			};

			@Override
			protected void onPostExecute(Void result) {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Mfx.playShape(6);
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
		Mfx.releaseShape();
	}
}

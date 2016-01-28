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

public class ActivityStudyColor extends Activity implements OnClickListener {

	private Handler handler;
	private Button[] btnColor;
	private ImageButton btnColorReturn;
	private String[] color;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_color);
		handler = new Handler();
		color = getResources().getStringArray(R.array.color_name);
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

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.btnColorReturn) {
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
		} else {
			int i = id - R.id.btnColor0;
			Mfx.playColor(i);
			setEnableButton(false);
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					setEnableButton(true);
				}
			}, 500);
		}
	}

	private void createButton() {
		btnColor = new Button[10];
		for (int i = 0; i < btnColor.length; i++) {
			btnColor[i] = (Button) findViewById(R.id.btnColor0 + i);
			btnColor[i].setOnClickListener(this);
			btnColor[i].setText(color[i]);
		}
		btnColorReturn = (ImageButton) findViewById(R.id.btnColorReturn);
		btnColorReturn.setOnClickListener(this);
	}

	private void setEnableButton(boolean enable) {
		btnColorReturn.setEnabled(enable);
		for (int i = 0; i < btnColor.length; i++) {
			btnColor[i].setEnabled(enable);
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
				Mfx.loadColor();
				return null;
			};

			@Override
			protected void onPostExecute(Void result) {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Mfx.playColor(10);
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
		Mfx.releaseColor();
	}
}

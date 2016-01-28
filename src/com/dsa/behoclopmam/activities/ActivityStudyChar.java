package com.dsa.behoclopmam.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsa.behoclopmam.R;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityStudyChar extends Activity implements OnClickListener {

	private int index;

	private String[] charUpper;
	private String[] charLower;
	private String[] exampleLeft;
	private String[] exampleRight;

	private ImageButton btnCharSpeak;
	private ImageButton btnCharReturn;
	private ImageButton btnCharNext;
	private ImageButton btnCharPrevious;

	private TextView txtCharUpper;
	private TextView txtCharLower;
	private TextView txtCharLeft;
	private TextView txtCharRight;

	private ImageView imgCharLeft;
	private ImageView imgCharRight;

	private Handler handler;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study_char);
		handler = new Handler();
		index = 0;
		createString();
		createButton();
		createText();
		createImage();
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

	private void createString() {
		charUpper = getResources().getStringArray(R.array.char_upper);
		charLower = getResources().getStringArray(R.array.char_lower);
		exampleLeft = getResources().getStringArray(R.array.example_left);
		exampleRight = getResources().getStringArray(R.array.example_right);
	}

	private void createButton() {
		btnCharNext = (ImageButton) findViewById(R.id.btnCharNext);
		btnCharSpeak = (ImageButton) findViewById(R.id.btnCharSpeak);
		btnCharReturn = (ImageButton) findViewById(R.id.btnCharReturn);
		btnCharPrevious = (ImageButton) findViewById(R.id.btnCharPrevious);
		btnCharNext.setOnClickListener(this);
		btnCharSpeak.setOnClickListener(this);
		btnCharReturn.setOnClickListener(this);
		btnCharPrevious.setOnClickListener(this);
	}

	private void createText() {
		txtCharUpper = (TextView) findViewById(R.id.txtCharUpper);
		txtCharLower = (TextView) findViewById(R.id.txtCharLower);
		txtCharLeft = (TextView) findViewById(R.id.txtCharLeft);
		txtCharRight = (TextView) findViewById(R.id.txtCharRight);
	}

	private void createImage() {
		imgCharLeft = (ImageView) findViewById(R.id.imgCharLeft);
		imgCharRight = (ImageView) findViewById(R.id.imgCharRight);
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnCharSpeak:
			speakQuestion();
			break;
		case R.id.btnCharReturn:
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
			break;
		case R.id.btnCharNext:
			index++;
			if (index == Mfx.NUMBER_OF_CHARS - 1) {
				index = 0;
			}
			createQuestion();
			speakQuestion();
			break;
		case R.id.btnCharPrevious:
			index--;
			if (index == -1) {
				index = Mfx.NUMBER_OF_CHARS - 2;
			}
			createQuestion();
			speakQuestion();
			break;
		default:
			break;
		}
	}

	private void createQuestion() {
		txtCharUpper.setText(charUpper[index]);
		txtCharLower.setText(charLower[index]);
		imgCharLeft.setImageResource(R.drawable.example_00_0 + 2 * index + 0);
		imgCharRight.setImageResource(R.drawable.example_00_0 + 2 * index + 1);
		txtCharLeft.setText(exampleLeft[index]);
		txtCharRight.setText(exampleRight[index]);
	}

	private void speakQuestion() {
		Mfx.playChar(index);
		setEnableButton(false);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				setEnableButton(true);
			}
		}, 500);
	}

	private void setEnableButton(boolean enabled) {
		btnCharSpeak.setEnabled(enabled);
		btnCharNext.setEnabled(enabled);
		btnCharPrevious.setEnabled(enabled);
		btnCharReturn.setEnabled(enabled);
	}

	private void loadData() {
		new AsyncTask<String, Integer, Void>() {

			@Override
			protected void onPreExecute() {
				setEnableButton(false);
			};

			@Override
			protected Void doInBackground(String... params) {
				Mfx.loadChar();
				return null;
			};

			@Override
			protected void onPostExecute(Void result) {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Mfx.playChar(29);
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
		Mfx.releaseChar();
	}
}

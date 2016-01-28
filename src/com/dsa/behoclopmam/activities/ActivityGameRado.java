package com.dsa.behoclopmam.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dsa.behoclopmam.R;
import com.dsa.behoclopmam.games.GameRado;
import com.dsa.behoclopmam.games.GameSurface;
import com.dsa.behoclopmam.utilities.Gfx;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityGameRado extends Activity implements OnClickListener {

	private ImageButton btnRadoReturn;
	private ImageButton btnRadoReplay;
	private FrameLayout frmRadoSurface;
	private LinearLayout lnrRadoButton;
	private TextView txtRadoLoading;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_rado);
		createLayout();
		createText();
		createButton();
		createInterstitialAd();
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadGame();
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseGame();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnRadoReturn:
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
			break;
		case R.id.btnRadoReplay:
			lnrRadoButton.setVisibility(LinearLayout.GONE);
			GameSurface.gameRado = new GameRado();
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		return;
	}

	private void createInterstitialAd() {
		interstitialAd = new InterstitialAd(this,
				"ca-app-pub-9404075236503358/9401530825");
		interstitialAd.loadAd(new AdRequest());
	}

	private void createText() {
		txtRadoLoading = (TextView) findViewById(R.id.txtRadoLoading);
	}

	private void createButton() {
		btnRadoReturn = (ImageButton) findViewById(R.id.btnRadoReturn);
		btnRadoReturn.setOnClickListener(this);

		btnRadoReplay = (ImageButton) findViewById(R.id.btnRadoReplay);
		btnRadoReplay.setOnClickListener(this);
	}

	private void createLayout() {
		lnrRadoButton = (LinearLayout) findViewById(R.id.lnrRadoButton);
		frmRadoSurface = (FrameLayout) findViewById(R.id.frmRadoSurface);
	}

	private void loadGame() {
		new AsyncTask<String, Integer, Void>() {
			@Override
			protected void onPreExecute() {
				lnrRadoButton.setVisibility(LinearLayout.GONE);
			};

			@Override
			protected Void doInBackground(String... params) {
				Gfx.loadNumbers();
				Gfx.loadRados();
				Mfx.loadGame();
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				frmRadoSurface.addView(new GameSurface(getBaseContext(),
						new Runnable() {

							@Override
							public void run() {
								if (!GameSurface.gameRado.isPlaying()) {
									lnrRadoButton
											.setVisibility(LinearLayout.VISIBLE);
								}
							}
						}, GameSurface.GAME_RADO));
				txtRadoLoading.setVisibility(TextView.GONE);
				Mfx.playNewMusic();
			};

		}.execute();
	}

	private void releaseGame() {
		frmRadoSurface.removeAllViews();
		Mfx.mediaPlayer.stop();
		Gfx.releaseNumbers();
		Gfx.releaseRados();
		Mfx.releaseGame();
	}
}

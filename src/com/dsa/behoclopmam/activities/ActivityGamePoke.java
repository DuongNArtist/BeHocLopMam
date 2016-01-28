package com.dsa.behoclopmam.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dsa.behoclopmam.R;
import com.dsa.behoclopmam.games.GameSurface;
import com.dsa.behoclopmam.utilities.Gfx;
import com.dsa.behoclopmam.utilities.Mfx;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityGamePoke extends Activity implements OnClickListener {

	private ImageButton btnPokeReturn;
	private FrameLayout frmPokeSurface;
	private TextView txtPokeLoading;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_poke);
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
		case R.id.btnPokeReturn:
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
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
		txtPokeLoading = (TextView) findViewById(R.id.txtPokeLoading);
	}

	private void createLayout() {
		frmPokeSurface = (FrameLayout) findViewById(R.id.frmPokeSurface);
	}

	private void createButton() {
		btnPokeReturn = (ImageButton) findViewById(R.id.btnPokeReturn);
		btnPokeReturn.setOnClickListener(this);
		btnPokeReturn.setVisibility(Button.INVISIBLE);
	}

	private void loadGame() {
		new AsyncTask<String, Integer, Void>() {

			@Override
			protected void onPreExecute() {
				createButton();
				createLayout();
				createText();
			};

			@Override
			protected Void doInBackground(String... params) {
				Gfx.loadBackgrounds();
				Gfx.loadBalls();
				Gfx.loadPokes();
				Gfx.loadNumbers();
				Mfx.loadGame();
				return null;
			};

			@Override
			protected void onPostExecute(Void result) {
				Mfx.playNewMusic();
				txtPokeLoading.setVisibility(TextView.GONE);
				btnPokeReturn.setVisibility(Button.VISIBLE);
				frmPokeSurface.addView(new GameSurface(getBaseContext(), null,
						GameSurface.GAME_POKE));
			}
		}.execute();
	}

	private void releaseGame() {
		frmPokeSurface.removeAllViews();
		Mfx.mediaPlayer.stop();
		Gfx.releaseBackrounds();
		Gfx.releaseBalls();
		Gfx.releaseNumbers();
		Gfx.releasePokes();
		Mfx.releaseGame();
	}

}

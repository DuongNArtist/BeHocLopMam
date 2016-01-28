package com.dsa.behoclopmam.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.dsa.behoclopmam.R;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityGame extends Activity implements OnClickListener {

	private Button btnGamePoke;
	private Button btnGameRado;
	private ImageButton btnGameReturn;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		createInterstitialAd();
		createButton();
	}

	private void createInterstitialAd() {
		interstitialAd = new InterstitialAd(this,
				"ca-app-pub-9404075236503358/9401530825");
		interstitialAd.loadAd(new AdRequest());
	}

	private void createButton() {
		btnGamePoke = (Button) findViewById(R.id.btnGamePoke);
		btnGameRado = (Button) findViewById(R.id.btnGameRado);
		btnGameReturn = (ImageButton) findViewById(R.id.btnGameReturn);
		btnGamePoke.setOnClickListener(this);
		btnGameRado.setOnClickListener(this);
		btnGameReturn.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnGamePoke:
			startActivity(new Intent(this, ActivityGamePoke.class));
			break;
		case R.id.btnGameRado:
			startActivity(new Intent(this, ActivityGameRado.class));
			break;
		case R.id.btnGameReturn:
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
			break;
		default:
			break;
		}
	}
}

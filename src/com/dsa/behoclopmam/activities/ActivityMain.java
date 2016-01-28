package com.dsa.behoclopmam.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.dsa.behoclopmam.R;

public class ActivityMain extends Activity implements OnClickListener {

	private ImageButton btnMainStudy;
	private ImageButton btnMainGame;
	private ImageButton btnMainRate;
	private ImageButton btnMainApp;
	private ImageButton btnMainQuit;
	private ImageView imgMainDino;
	private AnimationDrawable drawable;
	private MediaPlayer mediaPlayer;
	private Context context;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = ActivityMain.this;
		createMedia();
		createImage();
		createButton();
	}

	private void createMedia() {
		mediaPlayer = MediaPlayer.create(this, R.raw.music);
		mediaPlayer.setLooping(true);
	}

	private void createImage() {
		imgMainDino = (ImageView) findViewById(R.id.imgMainDino);
		drawable = (AnimationDrawable) imgMainDino.getDrawable();
		drawable.start();
	}

	private void createButton() {
		btnMainStudy = (ImageButton) findViewById(R.id.btnMainStudy);
		btnMainGame = (ImageButton) findViewById(R.id.btnMainGame);
		btnMainQuit = (ImageButton) findViewById(R.id.btnMainQuit);
		btnMainRate = (ImageButton) findViewById(R.id.btnMainRate);
		btnMainApp = (ImageButton) findViewById(R.id.btnMainApp);
		btnMainStudy.setOnClickListener(this);
		btnMainGame.setOnClickListener(this);
		btnMainRate.setOnClickListener(this);
		btnMainApp.setOnClickListener(this);
		btnMainQuit.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mediaPlayer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mediaPlayer.pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaPlayer.release();
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnMainStudy:
			startActivity(new Intent(context, ActivityStudy.class));
			break;
		case R.id.btnMainGame:
			startActivity(new Intent(context, ActivityGame.class));
			break;
		case R.id.btnMainRate:
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("market://details?id="
					+ getApplicationContext().getPackageName()));
			ActivityMain.this.startActivity(intent);
			break;
		case R.id.btnMainApp:
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri
					.parse("https://play.google.com/store/apps/developer?id=DSA+Inc"));
			startActivity(intent);
			break;
		case R.id.btnMainQuit:
			System.runFinalization();
			System.exit(1);
			break;
		default:
			break;
		}
	}
}

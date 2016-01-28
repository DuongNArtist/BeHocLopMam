package com.dsa.behoclopmam.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.dsa.behoclopmam.R;

public class ActivityLogo extends Activity {

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		context = ActivityLogo.this;

		new CountDownTimer(2000, 2000) {

			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {
				startActivity(new Intent(context, ActivityMain.class));
				finish();
			}
		}.start();
	}

	@Override
	public void onBackPressed() {
		return;
	}
}

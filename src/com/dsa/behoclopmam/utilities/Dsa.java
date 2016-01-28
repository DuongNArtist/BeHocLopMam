package com.dsa.behoclopmam.utilities;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.AudioManager;

public class Dsa extends Application {

	public static Dsa dsa;
	public static AssetManager assetManager;
	public static AudioManager audioManager;
	public static Resources resources;

	@Override
	public void onCreate() {
		super.onCreate();
		dsa = this;
		resources = getResources();
		assetManager = getAssets();
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
				audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 0);
	}

	public static Context getContext() {
		return dsa;
	}

	public static Dsa getDsa() {
		return dsa;
	}
}

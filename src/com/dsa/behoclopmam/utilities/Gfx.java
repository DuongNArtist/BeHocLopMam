package com.dsa.behoclopmam.utilities;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.SparseArray;

public class Gfx {

	public static int srcWidth = (Dsa.resources.getDisplayMetrics().widthPixels > Dsa.resources
			.getDisplayMetrics().heightPixels) ? Dsa.resources
			.getDisplayMetrics().widthPixels : Dsa.resources
			.getDisplayMetrics().heightPixels;
	public static int srcHeight = (Dsa.resources.getDisplayMetrics().heightPixels < Dsa.resources
			.getDisplayMetrics().widthPixels) ? Dsa.resources
			.getDisplayMetrics().heightPixels : Dsa.resources
			.getDisplayMetrics().widthPixels;
	public static float srcDensity = Dsa.resources.getDisplayMetrics().density;
	public static float dstDensity = srcDensity / 1.5f;

	public static final int BACKGROUND_WIDTH = 800;
	public static final int BACKGROUND_HEIGHT = 531;
	public static final int BALL_WIDTH = 48;
	public static final int BALL_HEIGHT = 48;
	public static final int POKEMON_WIDTH = 72;
	public static final int POKEMON_HEIGHT = 72;
	public static final int NUMBER_WIDTH = 30;
	public static final int NUMBER_HEIGHT = 50;
	public static final int RADO_O_WIDTH = 50;
	public static final int RADO_0_HEIGHT = 50;
	public static final int RADO_1_WIDTH = 300;
	public static final int RADO_1_HEIGHT = 300;

	public static SparseArray<Bitmap> saBackground;
	public static SparseArray<Bitmap> saBall;
	public static SparseArray<Bitmap> saPoke;
	public static SparseArray<Bitmap> saNumber;
	public static SparseArray<Bitmap> saRado;

	public static void loadBackgrounds() {
		String path = "gfx/backgrounds/background_";
		saBackground = new SparseArray<Bitmap>();
		for (int i = 0; i < 3; i++) {
			saBackground.put(i, loadBitmapFromAssets(path + i + ".png"));
		}
	}

	public static void releaseBackrounds() {
		saBackground = null;
	}

	public static void loadBalls() {
		String path = "gfx/balls/ball_";
		saBall = new SparseArray<Bitmap>();
		for (int i = 0; i < 27; i++) {
			saBall.put(i, loadBitmapFromAssets(path + i + ".png"));
		}
	}

	public static void releaseBalls() {
		saBall = null;
	}

	public static void loadPokes() {
		String path = "gfx/pokemons/pokemon_";
		saPoke = new SparseArray<Bitmap>();
		for (int i = 0; i < 553; i++) {
			saPoke.put(i, loadBitmapFromAssets(path + i + ".png"));
		}
	}

	public static void releasePokes() {
		saPoke = null;
	}

	public static void loadNumbers() {
		String path = "gfx/numbers/number_";
		saNumber = new SparseArray<Bitmap>();
		for (int i = 0; i < 10; i++) {
			saNumber.put(i, loadBitmapFromAssets(path + i + ".png"));
		}
	}

	public static void releaseNumbers() {
		saNumber = null;
	}

	public static void loadRados() {
		String path = "gfx/rados/rado_";
		saRado = new SparseArray<Bitmap>();
		for (int i = 0; i < 7; i++) {
			saRado.put(i, loadBitmapFromAssets(path + i + ".png"));
		}
	}

	public static void releaseRados() {
		saRado = null;
	}

	public static Bitmap loadBitmapFromAssets(String path) {
		try {
			InputStream inputStream = Dsa.assetManager.open(path);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.RGB_565;
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
					options);
			inputStream.close();
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

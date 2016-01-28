package com.dsa.behoclopmam.games;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.dsa.behoclopmam.utilities.Gfx;
import com.dsa.behoclopmam.utilities.Mfx;

public class GamePoke {

	public static final int NUMBER_OF_POKEMONS_MAX = 10;
	public static final int NUMBER_OF_POKEMONS_MIN = 5;

	private Bitmap background;
	private Rect src;
	private RectF dst;
	private Rect srcNumber;

	private int index;
	private Paint paint;
	private Random random;
	private ActorBall actorBall;
	private ArrayList<ActorPoke> actorPokemons;

	private int counter;

	public GamePoke() {
		counter = 0;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		random = new Random();
		index = random.nextInt(3);
		background = Gfx.saBackground.get(index);
		src = new Rect(0, 0, Gfx.BACKGROUND_WIDTH, Gfx.BACKGROUND_HEIGHT);
		dst = new RectF(0, 0, Gfx.srcWidth, Gfx.srcHeight);

		srcNumber = new Rect(0, 0, Gfx.NUMBER_WIDTH, Gfx.NUMBER_HEIGHT);
		actorBall = new ActorBall();
		actorPokemons = new ArrayList<ActorPoke>();
		for (int i = 0; i < NUMBER_OF_POKEMONS_MAX; i++) {
			actorPokemons.add(new ActorPoke());
		}
	}

	public void onTouchEvent() {
		actorBall.onTouchEvent();
	}

	public void update() {
		counter++;
		if (counter % (30 * GameThread.FPS) == 0) {
			index = random.nextInt(3);
			background = Gfx.saBackground.get(index);
			actorBall.refresh();
		}
		if (counter % (60 * GameThread.FPS) == 0) {
			Mfx.mediaPlayer.stop();
			Mfx.playNewMusic();
		}

		actorBall.check(actorPokemons);
		actorBall.update();

		if (actorPokemons.size() == NUMBER_OF_POKEMONS_MIN) {
			for (int i = 0; i < NUMBER_OF_POKEMONS_MIN; i++) {
				actorPokemons.add(new ActorPoke());
			}
		}
		for (int i = 0; i < actorPokemons.size(); i++) {
			ActorPoke actorPokemon = actorPokemons.get(i);
			if (actorPokemon.isLiving()) {
				actorPokemon.update();
			} else {
				actorPokemons.remove(i);
			}
		}
	}

	public void render(Canvas canvas) {
		drawBackground(canvas);
		actorBall.render(canvas);
		for (ActorPoke actorPokemon : actorPokemons) {
			actorPokemon.render(canvas);
		}
		drawScore(canvas);
	}

	private void drawBackground(Canvas canvas) {
		canvas.drawBitmap(background, src, dst, null);
	}

	private void drawScore(Canvas canvas) {
		String score = actorBall.getScore() + "";
		int length = score.length();
		for (int i = 0; i < length; i++) {
			int index = Integer.parseInt(score.substring(i, i + 1));
			RectF dstNumber = new RectF((i + 1) * Gfx.NUMBER_WIDTH
					* Gfx.dstDensity,
					0.5f * Gfx.NUMBER_HEIGHT * Gfx.dstDensity, (i + 2)
							* Gfx.NUMBER_WIDTH * Gfx.dstDensity, 1.5f
							* Gfx.NUMBER_HEIGHT * Gfx.dstDensity);
			canvas.drawBitmap(Gfx.saNumber.get(index), srcNumber, dstNumber,
					null);
		}
	}
}

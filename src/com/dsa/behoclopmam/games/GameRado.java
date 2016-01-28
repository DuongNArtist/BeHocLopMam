package com.dsa.behoclopmam.games;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.dsa.behoclopmam.utilities.Gfx;

public class GameRado {
	public static int SECONDS = 60;

	private Rect srcNumber;
	private ActorPoly actorPoly;
	private ArrayList<ActorRado> actorRados;
	private boolean playing;
	private long ticker;

	public GameRado() {
		playing = true;
		ticker = System.currentTimeMillis();
		srcNumber = new Rect(0, 0, Gfx.NUMBER_WIDTH, Gfx.NUMBER_HEIGHT);
		actorPoly = new ActorPoly();
		actorRados = new ArrayList<ActorRado>();
		actorRados.add(new ActorRado());
	}

	public void onTouchEvent(PointF position) {
		actorPoly.onTouchEvent(position);
	}

	public void update() {
		if (playing) {
			playing = actorPoly.check(actorRados.get(0));
			actorPoly.update();
			for (int i = 0; i < actorRados.size(); i++) {
				ActorRado actorRado = actorRados.get(i);
				actorRado.update();
				if (!actorRado.isLiving()) {
					actorRados.remove(i);
					actorRados.add(new ActorRado());
				}
			}
		}
	}

	public void render(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		drawScore(canvas);
		actorPoly.render(canvas);
		for (ActorRado actorRado : actorRados) {
			actorRado.render(canvas);
		}
	}

	private void drawScore(Canvas canvas) {
		String score = actorPoly.getScore() + "";
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

	public Rect getSrcNumber() {
		return srcNumber;
	}

	public void setSrcNumber(Rect srcNumber) {
		this.srcNumber = srcNumber;
	}

	public ActorPoly getActorPoly() {
		return actorPoly;
	}

	public void setActorPoly(ActorPoly actorPoly) {
		this.actorPoly = actorPoly;
	}

	public ArrayList<ActorRado> getActorRados() {
		return actorRados;
	}

	public void setActorRados(ArrayList<ActorRado> actorRados) {
		this.actorRados = actorRados;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public long getTicker() {
		return ticker;
	}

	public void setTicker(long ticker) {
		this.ticker = ticker;
	}

}

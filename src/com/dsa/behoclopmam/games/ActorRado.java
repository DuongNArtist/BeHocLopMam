package com.dsa.behoclopmam.games;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.dsa.behoclopmam.utilities.Gfx;
import com.dsa.behoclopmam.utilities.Mfx;

public class ActorRado {

	public static final float POSITION = 10 * Gfx.dstDensity;
	public static final float ACCELERATION = 0.025f * Gfx.dstDensity;
	public static float SPEED = 5 * Gfx.dstDensity;
	public static float speed = SPEED;

	private Random random;
	private RectF bound;
	private Bitmap poly;
	private Rect src;
	private RectF dst;
	private PointF dimension;

	private int type;
	private boolean living;

	private PointF start;
	private PointF stop;

	public ActorRado() {
		Mfx.playGame(0);
		random = new Random();
		living = true;
		type = random.nextInt(6);
		bound = new RectF(0, 0, Gfx.srcWidth, Gfx.srcHeight);
		start = new PointF(Gfx.srcHeight / 2, POSITION);
		stop = new PointF(Gfx.srcHeight / 2, POSITION);
		poly = Gfx.saRado.get(type);
		src = new Rect(0, 0, Gfx.RADO_O_WIDTH, Gfx.RADO_0_HEIGHT);
		dimension = new PointF(Gfx.RADO_O_WIDTH * Gfx.dstDensity,
				Gfx.RADO_0_HEIGHT * Gfx.dstDensity);
		dst = new RectF(stop.x - dimension.x / 2, stop.y - dimension.y / 2,
				stop.x + dimension.x / 2, stop.y + dimension.y / 2);
	}

	public void update() {
		stop.y += speed;
		dst.set(stop.x - dimension.x / 2, stop.y - dimension.y / 2, stop.x
				+ dimension.x / 2, stop.y + dimension.y / 2);
	}

	public void render(Canvas canvas) {
		canvas.drawBitmap(poly, src, dst, null);
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public RectF getBound() {
		return bound;
	}

	public void setBound(RectF bound) {
		this.bound = bound;
	}

	public Rect getSrc() {
		return src;
	}

	public void setSrc(Rect src) {
		this.src = src;
	}

	public RectF getDst() {
		return dst;
	}

	public void setDst(RectF dst) {
		this.dst = dst;
	}

	public PointF getDimension() {
		return dimension;
	}

	public void setDimension(PointF dimension) {
		this.dimension = dimension;
	}

	public PointF getStart() {
		return start;
	}

	public void setStart(PointF start) {
		this.start = start;
	}

	public PointF getStop() {
		return stop;
	}

	public void setStop(PointF stop) {
		this.stop = stop;
	}

	public Bitmap getPoly() {
		return poly;
	}

	public void setPoly(Bitmap poly) {
		this.poly = poly;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isLiving() {
		return living;
	}

	public void setLiving(boolean living) {
		this.living = living;
	}

}

package com.dsa.behoclopmam.games;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.dsa.behoclopmam.utilities.Gfx;
import com.dsa.behoclopmam.utilities.Mfx;

public class ActorPoly {

	public static final float ANGLE = 60;
	public static final float SPEED = 10;

	private Paint paint;
	private Bitmap poly;
	private Rect src;
	private RectF dst;
	private PointF dimension;

	private float angle;
	private float temp;
	private boolean clock;
	private int type;
	private int score;
	private boolean rotate;

	public ActorPoly() {
		replay();
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		poly = Gfx.saRado.get(6);
		src = new Rect(0, 0, Gfx.RADO_1_WIDTH, Gfx.RADO_1_HEIGHT);
		dimension = new PointF(src.right * Gfx.dstDensity, src.bottom
				* Gfx.dstDensity);
		dst = new RectF(Gfx.srcHeight / 2 - dimension.x / 2, Gfx.srcWidth
				- (dimension.y + (Gfx.srcHeight - dimension.y) / 2),
				Gfx.srcHeight / 2 + dimension.x / 2, Gfx.srcWidth
						- (Gfx.srcHeight - dimension.y) / 2);
	}

	public void onTouchEvent(PointF position) {
		if (angle % ANGLE == 0) {
			rotate = true;
			temp = angle;
			if (position.x <= Gfx.srcHeight / 2) {
				clock = false;
				type++;
				if (type == 6) {
					type = 0;
				}
			} else {
				clock = true;
				type--;
				if (type == -1) {
					type = 5;
				}
			}
		}
	}

	public void replay() {
		score = 0;
		type = 0;
		angle = 0;
		temp = 0;
		clock = true;
		rotate = false;
	}

	public boolean check(ActorRado actorRado) {
		if (actorRado.getStop().y >= dst.top) {
			if (actorRado.getType() == type) {
				actorRado.setLiving(false);
				score++;
				ActorRado.speed += ActorRado.ACCELERATION;
				Mfx.playGame(2);
				return true;
			}
			actorRado.setLiving(false);
			Mfx.playGame(1);
			return false;
		}
		return true;
	}

	public void update() {
		if (rotate) {
			if (clock) {
				if (angle < temp + ANGLE) {
					angle += SPEED;
				} else {
					rotate = false;
				}
			} else {
				if (angle > temp - ANGLE) {
					angle -= SPEED;
				} else {
					rotate = false;
				}
			}
		}
	}

	public void render(Canvas canvas) {
		canvas.save();
		canvas.rotate(angle, dst.centerX(), dst.centerY());
		canvas.drawBitmap(poly, src, dst, null);
		canvas.restore();
	}

	public Bitmap getPoly() {
		return poly;
	}

	public void setPoly(Bitmap poly) {
		this.poly = poly;
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

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getTemp() {
		return temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public boolean isClock() {
		return clock;
	}

	public void setClock(boolean clock) {
		this.clock = clock;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public boolean isRotate() {
		return rotate;
	}

	public void setRotate(boolean rotate) {
		this.rotate = rotate;
	}

}

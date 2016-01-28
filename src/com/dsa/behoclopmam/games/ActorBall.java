package com.dsa.behoclopmam.games;

import java.util.ArrayList;
import java.util.Random;

import com.dsa.behoclopmam.utilities.Gfx;
import com.dsa.behoclopmam.utilities.Mfx;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.PointF;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

public class ActorBall {

	public static final float DISTANCE_MIN = 50 * Gfx.dstDensity;
	public static final float DISTANCE_SPEED = 10 * Gfx.dstDensity;
	public static final float DISTANCE_DELAY = 5 * Gfx.dstDensity;

	public static final float START_X = Gfx.srcWidth / 2;
	public static final float START_Y = 40 * Gfx.dstDensity;

	public static final float STROKE_WIDTH = 5 * Gfx.dstDensity;

	public static final float ANGLE_MIN = 10;
	public static final float ANGLE_SPEED = 1;

	public static final int STATE_ROTATE = 0;
	public static final int STATE_SHOOT = 1;
	public static final int STATE_REWIND = 2;

	private int score;
	private int bonus;

	private Random random;
	private RectF bound;
	private Bitmap ball;
	private Rect src;
	private RectF dst;
	private PointF dimension;

	private int state;
	private int index;
	private float distance;
	private float angle;
	private boolean way;
	private boolean empty;

	private PointF start;
	private PointF stop;

	private Paint paint;

	public ActorBall() {
		random = new Random();
		paint = new Paint();
		paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255),
				random.nextInt(255)));
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setStrokeWidth(STROKE_WIDTH);
		paint.setStyle(Style.FILL);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);
		bound = new RectF(0, 0, Gfx.srcWidth, Gfx.srcHeight);
		score = 0;
		bonus = 0;
		index = random.nextInt(27);
		state = STATE_ROTATE;
		way = false;
		empty = true;
		distance = DISTANCE_MIN;
		start = new PointF(START_X, START_Y);
		stop = new PointF(START_X, START_Y + DISTANCE_MIN);
		ball = Gfx.saBall.get(index);
		src = new Rect(0, 0, Gfx.BALL_WIDTH, Gfx.BALL_HEIGHT);
		dimension = new PointF(Gfx.BALL_WIDTH * Gfx.dstDensity, Gfx.BALL_HEIGHT
				* Gfx.dstDensity);
		dst = new RectF(stop.x - dimension.x / 2, stop.y - dimension.y / 2,
				stop.x + dimension.x / 2, stop.y + dimension.y / 2);
	}

	private void updateAngle() {
		if (way) {
			angle -= ANGLE_SPEED;
			if (angle <= ANGLE_MIN) {
				way = false;
			}
		} else {
			angle += ANGLE_SPEED;
			if (angle >= 180 - ANGLE_MIN) {
				way = true;
			}
		}
	}

	private void updateDistance() {
		stop.x = (float) (start.x - distance * Math.cos(Math.toRadians(angle)));
		stop.y = (float) (START_Y + distance * Math.sin(Math.toRadians(angle)));
		dst.set(stop.x - dimension.x / 2, stop.y - dimension.y / 2, stop.x
				+ dimension.x / 2, stop.y + dimension.y / 2);
	}

	public void refresh() {
		paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255),
				random.nextInt(255)));
		index = random.nextInt(27);
		ball = Gfx.saBall.get(index);
	}

	public void onTouchEvent() {
		if (state == STATE_ROTATE && empty) {
			state = STATE_SHOOT;
			Mfx.playGame(0);
		}
	}

	public void check(ArrayList<ActorPoke> actorPokemons) {
		if (state == STATE_SHOOT && empty) {
			for (int i = 0; i < actorPokemons.size(); i++) {
				ActorPoke actorPokemon = actorPokemons.get(i);
				if (actorPokemon.getDst().contains(stop.x, stop.y)) {
					actorPokemon.setAngle(angle);
					actorPokemon.setDistance(distance);
					actorPokemon.setRewind(DISTANCE_SPEED - DISTANCE_DELAY);
					actorPokemon.setMoving(false);
					bonus = (int) (distance / Gfx.dstDensity / 10);
					state = STATE_REWIND;
					empty = false;
					break;
				}
			}
		}
	}

	public void update() {
		switch (state) {
		case STATE_ROTATE:
			updateAngle();
			break;
		case STATE_SHOOT:
			distance += DISTANCE_SPEED;
			if (!bound.contains(stop.x, stop.y)) {
				state = STATE_REWIND;
				if (empty) {
					Mfx.playGame(1);
				}
			}
			break;
		case STATE_REWIND:
			float speed = DISTANCE_SPEED;
			if (!empty) {
				speed = DISTANCE_SPEED - DISTANCE_DELAY;
			}
			distance -= speed;
			if (distance <= DISTANCE_MIN) {
				distance = DISTANCE_MIN;
				state = STATE_ROTATE;
				if (!empty) {
					empty = true;
					Mfx.playGame(2);
				}
				score += bonus;
				bonus = 0;
			}
			break;

		default:
			break;
		}
		updateDistance();
	}

	public void render(Canvas canvas) {
		canvas.drawLine(start.x, start.y, stop.x, stop.y, paint);
		canvas.save();
		canvas.rotate(90 - angle, stop.x, stop.y);
		canvas.drawBitmap(ball, src, dst, null);
		canvas.restore();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
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

	public Bitmap getBall() {
		return ball;
	}

	public void setBall(Bitmap ball) {
		this.ball = ball;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public boolean isWay() {
		return way;
	}

	public void setWay(boolean way) {
		this.way = way;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
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

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

}

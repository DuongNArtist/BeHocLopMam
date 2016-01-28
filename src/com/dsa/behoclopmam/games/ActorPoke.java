package com.dsa.behoclopmam.games;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.dsa.behoclopmam.utilities.Gfx;

public class ActorPoke {

	public static final float PADDING_TOP = 100 * Gfx.dstDensity;
	public static final float SPEED = 3 * Gfx.dstDensity;
	private RectF bound;
	private Rect src;
	private RectF dst;
	private PointF position;
	private PointF dimension;
	private Bitmap pokemon;
	private Random random;
	private int index;
	private boolean living;
	private boolean moving;
	private PointF speed;
	private float rewind;

	private float distance;
	private float angle;

	public ActorPoke() {
		random = new Random();
		index = random.nextInt(553);
		pokemon = Gfx.saPoke.get(index);
		src = new Rect(0, 0, Gfx.POKEMON_WIDTH, Gfx.POKEMON_HEIGHT);
		dimension = new PointF(Gfx.POKEMON_WIDTH * Gfx.dstDensity,
				Gfx.POKEMON_HEIGHT * Gfx.dstDensity);
		bound = new RectF(0, PADDING_TOP, Gfx.srcWidth - dimension.x,
				Gfx.srcHeight - dimension.y);
		position = new PointF(bound.left + random.nextFloat() * bound.right,
				bound.top + random.nextFloat() * bound.bottom);
		dst = new RectF(position.x, position.y, position.x + dimension.x,
				position.y + dimension.y);
		speed = new PointF(random.nextFloat() * SPEED, random.nextFloat()
				* SPEED);
		moving = true;
		living = true;
	}

	public void update() {
		if (moving) {
			position.set(position.x + speed.x, position.y + speed.y);
			dst.set(position.x, position.y, position.x + dimension.x,
					position.y + dimension.y);
			if (position.x >= bound.right) {
				speed.x = -random.nextFloat() * SPEED;
			} else if (position.x <= bound.left) {
				speed.x = random.nextFloat() * SPEED;
			}
			if (position.y >= bound.bottom) {
				speed.y = -random.nextFloat() * SPEED;
			} else if (position.y <= bound.top) {
				speed.y = random.nextFloat() * SPEED;
			}
		} else {
			distance -= rewind;
			position.x = (float) (Gfx.srcWidth / 2 - distance
					* Math.cos(Math.toRadians(angle)));
			position.y = (float) (ActorBall.START_Y + distance
					* Math.sin(Math.toRadians(angle)));
			dst.set(position.x - dimension.x / 2, position.y - dimension.y / 2,
					position.x + dimension.x / 2, position.y + dimension.y / 2);
			if (distance <= ActorBall.DISTANCE_MIN) {
				living = false;
			}
		}
	}

	public void render(Canvas canvas) {
		if (moving) {
			canvas.drawBitmap(pokemon, src, dst, null);
		} else {
			canvas.save();
			canvas.rotate(90 - angle, position.x, position.y);
			canvas.drawBitmap(pokemon, src, dst, null);
			canvas.restore();
		}
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

	public PointF getPosition() {
		return position;
	}

	public void setPosition(PointF position) {
		this.position = position;
	}

	public PointF getDimension() {
		return dimension;
	}

	public void setDimension(PointF dimension) {
		this.dimension = dimension;
	}

	public Bitmap getPokemon() {
		return pokemon;
	}

	public void setPokemon(Bitmap pokemon) {
		this.pokemon = pokemon;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isLiving() {
		return living;
	}

	public void setLiving(boolean living) {
		this.living = living;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public PointF getSpeed() {
		return speed;
	}

	public void setSpeed(PointF speed) {
		this.speed = speed;
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

	public float getRewind() {
		return rewind;
	}

	public void setRewind(float rewind) {
		this.rewind = rewind;
	}

}

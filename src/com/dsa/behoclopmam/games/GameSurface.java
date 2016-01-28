package com.dsa.behoclopmam.games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

	public static GamePoke gamePoke;
	public static GameRado gameRado;

	public static final int GAME_POKE = 0;
	public static final int GAME_RADO = 1;

	private GameThread gameThread;
	private Runnable runnable;
	private Handler handler;
	private int game;

	public GameSurface(Context context, Runnable runnable, int game) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		this.runnable = runnable;
		this.game = game;
	}

	public void update() {
		if (runnable != null) {
			handler.post(runnable);
		}
		switch (game) {
		case GAME_POKE:
			gamePoke.update();
			break;
		case GAME_RADO:
			gameRado.update();
			break;
		default:
			break;
		}
	}

	public void render(Canvas canvas) {
		switch (game) {
		case GAME_POKE:
			gamePoke.render(canvas);
			break;
		case GAME_RADO:
			gameRado.render(canvas);
			break;
		default:
			break;
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		switch (game) {
		case GAME_POKE:
			gamePoke.onTouchEvent();
			break;
		case GAME_RADO:
			gameRado.onTouchEvent(new PointF(motionEvent.getX(), motionEvent
					.getY()));
			break;
		default:
			break;
		}
		return super.onTouchEvent(motionEvent);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		switch (game) {
		case GAME_POKE:
			gamePoke = new GamePoke();
			break;
		case GAME_RADO:
			gameRado = new GameRado();
			break;
		default:
			break;
		}
		handler = new Handler();
		gameThread = new GameThread(this, getHolder());
		gameThread.setRunning(true);
		gameThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				gameThread.setRunning(false);
				gameThread.join();
				retry = false;
			} catch (InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}
		}
	}
}

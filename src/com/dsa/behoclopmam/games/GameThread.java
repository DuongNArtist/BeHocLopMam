package com.dsa.behoclopmam.games;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	public static final String TAG = GameThread.class.getSimpleName();

	public static final int FPS = 60;
	public static final int MAX = 5;

	private long start;
	private long finish;
	private long elapsed;
	private long sleep;
	private int skipped;

	private GameSurface gameSurface;
	private SurfaceHolder surfaceHolder;
	private boolean running;

	public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder) {
		this.gameSurface = gameSurface;
		this.surfaceHolder = surfaceHolder;
		running = false;
	}

	@SuppressLint("WrongCall")
	@Override
	public void run() {
		while (running) {
			Canvas canvas = null;
			try {
				canvas = surfaceHolder.lockCanvas();
				if (canvas != null) {
					synchronized (surfaceHolder) {
						skipped = 0;
						start = System.currentTimeMillis();
						gameSurface.update();
						gameSurface.render(canvas);
						finish = System.currentTimeMillis();
						elapsed = finish - start;
						sleep = (1000 / FPS - elapsed);
						if (sleep > 0) {
							try {
								Thread.sleep(sleep);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						while (sleep < 0 && skipped < MAX) {
							gameSurface.update();
							skipped++;
							sleep += 1000 / FPS;
						}
						Log.i(TAG, "Sleep = " + sleep + ", Skipped = "
								+ skipped);
					}
				}
			} finally {
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getFinish() {
		return finish;
	}

	public void setFinish(long finish) {
		this.finish = finish;
	}

	public long getElapsed() {
		return elapsed;
	}

	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}

	public long getSleep() {
		return sleep;
	}

	public void setSleep(long sleep) {
		this.sleep = sleep;
	}

	public int getSkipped() {
		return skipped;
	}

	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	public GameSurface getGameSurface() {
		return gameSurface;
	}

	public void setGameSurface(GameSurface gameSurface) {
		this.gameSurface = gameSurface;
	}

	public SurfaceHolder getSurfaceHolder() {
		return surfaceHolder;
	}

	public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}

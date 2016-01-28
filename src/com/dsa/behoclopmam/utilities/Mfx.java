package com.dsa.behoclopmam.utilities;

import java.io.IOException;
import java.util.Random;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.SparseArray;

import com.dsa.behoclopmam.R;

@SuppressLint("UseSparseArrays")
public class Mfx {

	public static final int SRC_QUALITY = 0;
	public static final int LEFT_VOLUME = 1;
	public static final int RIGHT_VOLUME = 1;
	public static final int PRIORITY = 1;
	public static final int LOOP = 0;
	public static final int RATE = 1;

	public static final int NUMBER_OF_CHARS = 30;
	public static final int NUMBER_OF_GAMES = 3;
	public static final int NUMBER_OF_FINDS = 4;
	public static final int NUMBER_OF_COUNTS = 36;
	public static final int NUMBER_OF_PRACTICES = 28;

	public static int gameMusic;
	public static MediaPlayer mediaPlayer;

	public static SoundPool spChar;
	public static SparseArray<Integer> saChar;

	public static SoundPool spFind;
	public static SparseArray<Integer> saFind;

	public static SoundPool spGame;
	public static SparseArray<Integer> saGame;

	public static SoundPool spTrueFalse;
	public static SparseArray<Integer> saTrueFalse;

	public static SoundPool spTopCenterBottom;
	public static SparseArray<Integer> saTopCenterBottom;

	public static SoundPool spLeftCenterRight;
	public static SparseArray<Integer> saLeftCenterRight;

	public static SoundPool spHigherLower;
	public static SparseArray<Integer> saHigherLower;

	public static SoundPool spSameDifferent;
	public static SparseArray<Integer> saSameDifferent;

	public static SoundPool spRecognize;
	static SparseArray<Integer> saRecognize;

	public static SoundPool spMoreLess;
	public static SparseArray<Integer> saMoreLess;

	public static SoundPool spMaxMin;
	public static SparseArray<Integer> saMaxMin;

	public static SoundPool spCount;
	public static SparseArray<Integer> saCount;

	public static SoundPool spPractice;
	public static SparseArray<Integer> saPractice;

	public static SoundPool spNumber;
	public static SparseArray<Integer> saNumber;

	public static SoundPool spColor;
	public static SparseArray<Integer> saColor;

	public static SoundPool spShape;
	public static SparseArray<Integer> saShape;

	public static void playNewMusic() {
		mediaPlayer = MediaPlayer.create(Dsa.getContext(), R.raw.game_0
				+ new Random().nextInt(5));
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
	}

	public static void loadChar() {
		String path = "mfx/study/char/voice_";
		spChar = new SoundPool(NUMBER_OF_CHARS, AudioManager.STREAM_MUSIC,
				SRC_QUALITY);
		saChar = new SparseArray<Integer>();
		for (int i = 0; i < NUMBER_OF_CHARS; i++) {
			try {
				saChar.put(i, spChar.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playChar(int index) {
		spChar.play(saChar.get(index), LEFT_VOLUME, RIGHT_VOLUME, PRIORITY,
				LOOP, RATE);
	}

	public static void releaseChar() {
		saChar = null;
		spChar = null;
	}

	public static void loadFind() {
		String path = "mfx/study/find/voice_";
		spFind = new SoundPool(NUMBER_OF_FINDS, AudioManager.STREAM_MUSIC,
				SRC_QUALITY);
		saFind = new SparseArray<Integer>();
		for (int i = 0; i < NUMBER_OF_FINDS; i++) {
			try {
				saFind.put(i, spFind.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playFind(int index) {
		spFind.play(saFind.get(index), LEFT_VOLUME, RIGHT_VOLUME, PRIORITY,
				LOOP, RATE);
	}

	public static void releaseFind() {
		spFind = null;
		saFind = null;
	}

	public static void loadGame() {
		String path = "mfx/games/voice_";
		spGame = new SoundPool(NUMBER_OF_GAMES, AudioManager.STREAM_MUSIC,
				SRC_QUALITY);
		saGame = new SparseArray<Integer>();
		for (int i = 0; i < NUMBER_OF_GAMES; i++) {
			try {
				saGame.put(i, spGame.load(
						Dsa.assetManager.openFd(path + i + ".ogg"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playGame(int index) {
		spGame.play(saGame.get(index), LEFT_VOLUME, RIGHT_VOLUME, PRIORITY,
				LOOP, RATE);
	}

	public static void releaseGame() {
		spGame = null;
		saGame = null;
	}

	public static void loadTrueFalse() {
		String path = "mfx/study/true_false/voice_";
		spTrueFalse = new SoundPool(5, AudioManager.STREAM_MUSIC, SRC_QUALITY);
		saTrueFalse = new SparseArray<Integer>();
		for (int i = 0; i < 5; i++) {
			try {
				saTrueFalse.put(i, spTrueFalse.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void releaseTrueFalse() {
		saTrueFalse = null;
		spTrueFalse = null;
	}

	public static void playTrueFalse(int index) {
		spTrueFalse.play(saTrueFalse.get(index), LEFT_VOLUME, RIGHT_VOLUME,
				PRIORITY, LOOP, RATE);
	}

	public static void loadTopCenterBottom() {
		String path = "mfx/study/top_center_bottom/voice_";
		spTopCenterBottom = new SoundPool(3, AudioManager.STREAM_MUSIC,
				SRC_QUALITY);
		saTopCenterBottom = new SparseArray<Integer>();
		for (int i = 0; i < 3; i++) {
			try {
				saTopCenterBottom.put(i, spTopCenterBottom.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playTopCenterBottom(int index) {
		spTopCenterBottom.play(saTopCenterBottom.get(index), LEFT_VOLUME,
				RIGHT_VOLUME, PRIORITY, LOOP, RATE);
	}

	public static void releaseTopCenterBottom() {
		spTopCenterBottom = null;
		saTopCenterBottom = null;
	}

	public static void loadLeftCenterRight() {
		String path = "mfx/study/left_center_right/voice_";
		spLeftCenterRight = new SoundPool(3, AudioManager.STREAM_MUSIC,
				SRC_QUALITY);
		saLeftCenterRight = new SparseArray<Integer>();
		for (int i = 0; i < 3; i++) {
			try {
				saLeftCenterRight.put(i, spLeftCenterRight.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playLeftCenterRight(int index) {
		spLeftCenterRight.play(saLeftCenterRight.get(index), LEFT_VOLUME,
				RIGHT_VOLUME, PRIORITY, LOOP, RATE);
	}

	public static void releaseLeftCenterRight() {
		spLeftCenterRight = null;
		saLeftCenterRight = null;
	}

	public static void loadHigherLower() {
		String path = "mfx/study/higher_lower/voice_";
		spHigherLower = new SoundPool(1, AudioManager.STREAM_MUSIC, SRC_QUALITY);
		saHigherLower = new SparseArray<Integer>();
		for (int i = 0; i < 1; i++) {
			try {
				saHigherLower.put(i, spHigherLower.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playHigherLower(int index) {
		spHigherLower.play(saHigherLower.get(index), LEFT_VOLUME, RIGHT_VOLUME,
				PRIORITY, LOOP, RATE);
	}

	public static void releaseHigherLower() {
		spHigherLower = null;
		saHigherLower = null;
	}

	public static void loadSameDifferent() {
		String path = "mfx/study/same_different/voice_";
		spSameDifferent = new SoundPool(2, AudioManager.STREAM_MUSIC,
				SRC_QUALITY);
		saSameDifferent = new SparseArray<Integer>();
		for (int i = 0; i < 2; i++) {
			try {
				saSameDifferent.put(i, spSameDifferent.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playSameDifferent(int index) {
		spSameDifferent.play(saSameDifferent.get(index), LEFT_VOLUME,
				RIGHT_VOLUME, PRIORITY, LOOP, RATE);
	}

	public static void releaseSameDifferent() {
		spSameDifferent = null;
		saSameDifferent = null;
	}

	public static void loadRecognize() {
		String path = "mfx/study/recognize/voice_";
		saRecognize = new SparseArray<Integer>();
		spRecognize = new SoundPool(8, AudioManager.STREAM_MUSIC, SRC_QUALITY);
		for (int i = 0; i < 8; i++) {
			try {
				saRecognize.put(i, spRecognize.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void releaseRecognize() {
		saRecognize = null;
		spRecognize = null;
	}

	public static void playRecognize(int index) {
		spRecognize.play(saRecognize.get(index), LEFT_VOLUME, RIGHT_VOLUME,
				PRIORITY, LOOP, RATE);
	}

	public static void loadMoreLess() {
		String path = "mfx/study/more_less/voice_";
		spMoreLess = new SoundPool(2, AudioManager.STREAM_MUSIC, SRC_QUALITY);
		saMoreLess = new SparseArray<Integer>();
		for (int i = 0; i < 2; i++) {
			try {
				saMoreLess.put(i, spMoreLess.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playMoreLess(int index) {
		spMoreLess.play(saMoreLess.get(index), LEFT_VOLUME, RIGHT_VOLUME,
				PRIORITY, LOOP, RATE);
	}

	public static void releaseMoreLess() {
		spMoreLess = null;
		saMoreLess = null;
	}

	public static void loadCount() {
		String path = "mfx/study/count/voice_";
		spCount = new SoundPool(NUMBER_OF_COUNTS, AudioManager.STREAM_MUSIC,
				SRC_QUALITY);
		saCount = new SparseArray<Integer>();
		for (int i = 0; i < NUMBER_OF_COUNTS; i++) {
			try {
				saCount.put(i, spCount.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playCount(int index) {
		spCount.play(saCount.get(index), LEFT_VOLUME, RIGHT_VOLUME, PRIORITY,
				LOOP, RATE);
	}

	public static void releaseCount() {
		spCount = null;
		saCount = null;
	}

	public static void loadMaxMin() {
		String path = "mfx/study/max_min/voice_";
		spMaxMin = new SoundPool(2, AudioManager.STREAM_MUSIC, SRC_QUALITY);
		saMaxMin = new SparseArray<Integer>();
		for (int i = 0; i < 2; i++) {
			try {
				saMaxMin.put(i, spMaxMin.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playMaxMin(int index) {
		spMaxMin.play(saMaxMin.get(index), LEFT_VOLUME, RIGHT_VOLUME, PRIORITY,
				LOOP, RATE);
	}

	public static void releaseMaxMin() {
		spMaxMin = null;
		saMaxMin = null;
	}

	public static void loadPractice() {
		String path = "mfx/study/practice/voice_";
		spPractice = new SoundPool(NUMBER_OF_PRACTICES,
				AudioManager.STREAM_MUSIC, SRC_QUALITY);
		saPractice = new SparseArray<Integer>();
		for (int i = 0; i < NUMBER_OF_PRACTICES; i++) {
			try {
				saPractice.put(i, spPractice.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playPractice(int index) {
		spPractice.play(saPractice.get(index), LEFT_VOLUME, RIGHT_VOLUME,
				PRIORITY, LOOP, RATE);
	}

	public static void releasePractice() {
		spPractice = null;
		saPractice = null;
	}

	public static void loadNumber() {
		String path = "mfx/study/number/voice_";
		spNumber = new SoundPool(11, AudioManager.STREAM_MUSIC, SRC_QUALITY);
		saNumber = new SparseArray<Integer>();
		for (int i = 0; i < 11; i++) {
			try {
				saNumber.put(i, spNumber.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playNumber(int index) {
		spNumber.play(saNumber.get(index), LEFT_VOLUME, RIGHT_VOLUME, PRIORITY,
				LOOP, RATE);
	}

	public static void releaseNumber() {
		spNumber = null;
		saNumber = null;
	}

	public static void loadColor() {
		String path = "mfx/study/color/voice_";
		spColor = new SoundPool(11, AudioManager.STREAM_MUSIC, SRC_QUALITY);
		saColor = new SparseArray<Integer>();
		for (int i = 0; i < 11; i++) {
			try {
				saColor.put(i, spColor.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playColor(int index) {
		spColor.play(saColor.get(index), LEFT_VOLUME, RIGHT_VOLUME, PRIORITY,
				LOOP, RATE);
	}

	public static void releaseColor() {
		spColor = null;
		saColor = null;
	}

	public static void loadShape() {
		String path = "mfx/study/shape/voice_";
		spShape = new SoundPool(7, AudioManager.STREAM_MUSIC, SRC_QUALITY);
		saShape = new SparseArray<Integer>();
		for (int i = 0; i < 7; i++) {
			try {
				saShape.put(i, spShape.load(
						Dsa.assetManager.openFd(path + i + ".mp3"), PRIORITY));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void playShape(int index) {
		spShape.play(saShape.get(index), LEFT_VOLUME, RIGHT_VOLUME, PRIORITY,
				LOOP, RATE);
	}

	public static void releaseShape() {
		spShape = null;
		saShape = null;
	}
}

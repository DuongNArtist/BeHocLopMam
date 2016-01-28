package com.dsa.behoclopmam.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsa.behoclopmam.R;

@SuppressLint("InflateParams")
public class Dino {

	public static final int STATE_DANCE = 0;
	public static final int STATE_FALSE = 1;
	public static final int STATE_TRUE = 2;

	public static final int DURATION_LONG = Toast.LENGTH_LONG;
	public static final int DURATION_SHORT = Toast.LENGTH_SHORT;

	public Dino(Context context, int state, int duration) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.activity_dino, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
		switch (state) {
		case STATE_DANCE:
			imageView.setImageResource(R.drawable.dino_dance);
			break;
		case STATE_FALSE:
			imageView.setImageResource(R.drawable.dino_false);
			break;
		case STATE_TRUE:
			imageView.setImageResource(R.drawable.dino_true);
			break;
		default:
			break;
		}

		AnimationDrawable animationDrawable = (AnimationDrawable) imageView
				.getDrawable();
		animationDrawable.start();

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(duration);
		toast.setView(view);
		toast.show();
	}
}

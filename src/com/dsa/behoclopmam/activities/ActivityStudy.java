package com.dsa.behoclopmam.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.dsa.behoclopmam.R;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

public class ActivityStudy extends Activity implements OnClickListener {

	private Context context;
	private Button btnStudyTopCenterBottom;
	private Button btnStudyLeftCenterRight;
	private Button btnStudyHigherLower;
	private Button btnStudySameDifferent;
	private Button btnStudyRecognize;
	private Button btnStudyMoreLess;
	private Button btnStudyChar;
	private Button btnStudyFindChar;
	private Button btnStudyTableChar;
	private Button btnStudyCount;
	private Button btnStudyMaxMin;
	private Button btnStudyPractice;
	private Button btnStudyNumber;
	private Button btnStudyColor;
	private Button btnStudyShape;
	private ImageButton btnStudyReturn;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_study);
		context = ActivityStudy.this;
		createButton();
		createInterstitialAd();
	}

	private void createInterstitialAd() {
		interstitialAd = new InterstitialAd(this,
				"ca-app-pub-9404075236503358/9401530825");
		interstitialAd.loadAd(new AdRequest());
	}

	private void createButton() {
		btnStudyReturn = (ImageButton) findViewById(R.id.btnStudyReturn);
		btnStudyTopCenterBottom = (Button) findViewById(R.id.btnStudyTopCenterBottom);
		btnStudyLeftCenterRight = (Button) findViewById(R.id.btnStudyLeftCenterRight);
		btnStudyHigherLower = (Button) findViewById(R.id.btnStudyHigherLower);
		btnStudySameDifferent = (Button) findViewById(R.id.btnStudySameDifferent);
		btnStudyRecognize = (Button) findViewById(R.id.btnStudyRecognize);
		btnStudyMoreLess = (Button) findViewById(R.id.btnStudyMoreLess);
		btnStudyChar = (Button) findViewById(R.id.btnStudyChar);
		btnStudyFindChar = (Button) findViewById(R.id.btnStudyFindChar);
		btnStudyTableChar = (Button) findViewById(R.id.btnStudyTableChar);
		btnStudyCount = (Button) findViewById(R.id.btnStudyCount);
		btnStudyMaxMin = (Button) findViewById(R.id.btnStudyMaxMin);
		btnStudyPractice = (Button) findViewById(R.id.btnStudyPractice);
		btnStudyNumber = (Button) findViewById(R.id.btnStudyNumber);
		btnStudyColor = (Button) findViewById(R.id.btnStudyColor);
		btnStudyShape = (Button) findViewById(R.id.btnStudyShape);
		btnStudyReturn.setOnClickListener(this);
		btnStudyTopCenterBottom.setOnClickListener(this);
		btnStudyLeftCenterRight.setOnClickListener(this);
		btnStudyHigherLower.setOnClickListener(this);
		btnStudySameDifferent.setOnClickListener(this);
		btnStudyRecognize.setOnClickListener(this);
		btnStudyMoreLess.setOnClickListener(this);
		btnStudyChar.setOnClickListener(this);
		btnStudyFindChar.setOnClickListener(this);
		btnStudyTableChar.setOnClickListener(this);
		btnStudyCount.setOnClickListener(this);
		btnStudyMaxMin.setOnClickListener(this);
		btnStudyPractice.setOnClickListener(this);
		btnStudyNumber.setOnClickListener(this);
		btnStudyColor.setOnClickListener(this);
		btnStudyShape.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {
		return;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnStudyReturn:
			finish();
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			}
			break;
		case R.id.btnStudyTopCenterBottom:
			startActivity(new Intent(context,
					ActivityStudyTopCenterBottom.class));
			break;
		case R.id.btnStudyLeftCenterRight:
			startActivity(new Intent(context,
					ActivityStudyLeftCenterRight.class));
			break;
		case R.id.btnStudyHigherLower:
			startActivity(new Intent(context, ActivityStudyHigherLower.class));
			break;
		case R.id.btnStudySameDifferent:
			startActivity(new Intent(context, ActivityStudySameDifferent.class));
			break;
		case R.id.btnStudyRecognize:
			startActivity(new Intent(context, ActivityStudyRecognize.class));
			break;
		case R.id.btnStudyMoreLess:
			startActivity(new Intent(context, ActivityStudyMoreLess.class));
			break;
		case R.id.btnStudyChar:
			startActivity(new Intent(context, ActivityStudyChar.class));
			break;
		case R.id.btnStudyFindChar:
			startActivity(new Intent(context, ActivityStudyFind.class));
			break;
		case R.id.btnStudyTableChar:
			startActivity(new Intent(context, ActivityStudyTable.class));
			break;
		case R.id.btnStudyCount:
			startActivity(new Intent(context, ActivityStudyCount.class));
			break;
		case R.id.btnStudyMaxMin:
			startActivity(new Intent(context, ActivityStudyMaxMin.class));
			break;
		case R.id.btnStudyPractice:
			startActivity(new Intent(context, ActivityStudyPractice.class));
			break;
		case R.id.btnStudyNumber:
			startActivity(new Intent(context, ActivityStudyNumber.class));
			break;
		case R.id.btnStudyColor:
			startActivity(new Intent(context, ActivityStudyColor.class));
			break;
		case R.id.btnStudyShape:
			startActivity(new Intent(context, ActivityStudyShape.class));
			break;
		default:
			break;
		}
	}
}

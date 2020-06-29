package com.winter.tosscoin;

import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TossImageView mTossImageView;
    private Button mTossButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTossImageView = findViewById(R.id.toss_image);
        mTossImageView.setImageResource(R.drawable.front);
        mTossButton = findViewById(R.id.toss_start);
        mTossButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toss_start:
                startAnimation();
                break;
            default:
                break;
        }
    }

    private void startAnimation() {
        mTossImageView.cleareOtherAnimation();

        TranslateAnimation translateAnimationUp = new TranslateAnimation(0, 0, 0, -700);
        translateAnimationUp.setDuration(2000);
        translateAnimationUp.setInterpolator(new LinearInterpolator());
        TranslateAnimation translateAnimationDown = new TranslateAnimation(0, 0, 0, 700);
        translateAnimationDown.setDuration(1000);
        translateAnimationDown.setInterpolator(new LinearInterpolator());
        translateAnimationDown.setStartOffset(2000);

        mTossImageView.setInterpolator(new LinearInterpolator());
        mTossImageView.setResult(new Random().nextInt(2) == 0 ? TossImageView.RESULT_FRONT : TossImageView.RESULT_REVERSE);

        mTossImageView.addOtherAnimation(translateAnimationUp);
        mTossImageView.addOtherAnimation(translateAnimationDown);
        mTossImageView.startToss();
    }
}

package com.example.accontbook;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent intent = new Intent(this, MainActivity.class);
        final Intent intent1 = new Intent(this,FirstSetting.class);
        LottieAnimationView lottie = (LottieAnimationView) findViewById(R.id.animation_view);
        lottie.playAnimation();
        lottie.loop(true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("setting",MODE_PRIVATE);
                if(!preferences.getString("key", "").equals("10"))
                {
                    startActivity(intent1);
                }else{
                    startActivity(intent);
                }
                finish();
            }
        }, 4000);

    }
}

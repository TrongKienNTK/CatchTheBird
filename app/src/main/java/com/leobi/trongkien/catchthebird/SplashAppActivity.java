package com.leobi.trongkien.catchthebird;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashAppActivity extends AppCompatActivity {

    public static int SPLASH_TIME_OUT = 2000;
    @Override
    public void onBackPressed() {
        //Do not do anything
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_app);
        ImageView appLogo = (ImageView) findViewById(R.id.appLogo);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.my_transition);
        appLogo.startAnimation(myAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

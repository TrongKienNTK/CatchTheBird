package com.leobi.trongkien.catchthebird;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    public static final String NAME = "Catch_The_Bird";

    @Override
    public void onBackPressed() {
        //Do not thing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLB = (TextView)findViewById(R.id.scoreLB);
        TextView highScoreLB = (TextView)findViewById(R.id.highScoreLB);
//        int score = getIntent().getIntExtra("SCORE", 0);

        SharedPreferences settings = getSharedPreferences(NAME, Context.MODE_PRIVATE);
        int score = settings.getInt("SCORE", 0);
        scoreLB. setText(score + "");
        int highSore = settings.getInt("HIGH_SCORE", 0);
        if(score > highSore){
            Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.my_trans);
            highScoreLB.startAnimation(myAnim);
            highScoreLB.setTextColor(Color.RED);
            highScoreLB.setText("Wow! You got the new best!");

            //Save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();
        } else{
            highScoreLB.setText("High Score: " + highSore);
        }


        Button btClose = findViewById(R.id.exitS);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                builder.setMessage("Do you want exit?")
                        .setCancelable(false)
                        .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }
                        );
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });
                //Creating dialog box
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }
    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

}

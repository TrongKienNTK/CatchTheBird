package com.leobi.trongkien.catchthebird;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final String NAME = "Catch_The_Bird";

    TextView score;
    TextView tapStart;
    TextView tapTap;
    ImageView box;
    ImageView bird;
    ImageView bomb;
    ImageView coin;
    ImageView pinkBird;
    ImageView bomb2;

    //Size
     int frameHeight;
     int boxSize;
     int screenWidth;
     int screenHeight;

     //Speed
    private int boxSpeed;
    private int birdSpeed;
    private int coinSpeed;
    private int bombSpeed;
    private int bomb2Speed;
    private int pinkBirdSpeed;


    //Position
    private int boxY;
    private int birdX;
    private int birdY;
    private int pinkBirdX;
    private int pinkBirdY;
    private int coinX;
    private int coinY;
    private int bombX;
    private int bombY;
    private int bomb2X;
    private int bomb2Y;


    //Score
    int point = 0;

    //Init Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;

    //Status Check
    private boolean action_flg = false;
    private boolean start_flg = false;

    @Override
    public void onBackPressed() {
        //Do Not Thing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = new SoundPlayer(this);

        score = (TextView) findViewById(R.id.score);
        tapStart = (TextView) findViewById(R.id.tapStart);
        tapTap = (TextView) findViewById(R.id.tapTap);
        box = (ImageView) findViewById(R.id.box);
        bird = (ImageView) findViewById(R.id.bird);
        bomb = (ImageView) findViewById(R.id.bomb);
        coin = (ImageView) findViewById(R.id.coin);
        pinkBird = (ImageView) findViewById(R.id.pinkBird);
        bomb2 = (ImageView) findViewById(R.id.bomb2);


        //Get Screen Size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        //Set Speed
        boxSpeed = Math.round(screenHeight / 60F);
        birdSpeed = Math.round(screenWidth / 70F);
        pinkBirdSpeed = Math.round(screenWidth / 45F);
        coinSpeed = Math.round(screenWidth / 36F);
        bombSpeed = Math.round(screenWidth / 40F);
        bomb2Speed = Math.round(screenWidth / 35F);



        //Move out of screen.
        bird.setX(-100);
        bird.setY(-100);
        pinkBird.setX(-100);
        pinkBird.setY(-100);
        bomb.setX(-100);
        bomb.setY(-100);
        bomb2.setX(-100);
        bomb2.setY(-100);
        coin.setX(-100);
        coin.setY(-100);

        //Set INVISIBLE The Box
        box.setVisibility(View.INVISIBLE);


    }

    public void changePos(){

        hitCheck();

        //Bird
        birdX -= birdSpeed;
        if(birdX < 0){
            birdX = screenWidth + 20;
            birdY = (int) Math.floor(Math.random() * (frameHeight - bird.getHeight()));
        }
        bird.setX(birdX);
        bird.setY(birdY);

        //Pink Bird
        pinkBirdX -= pinkBirdSpeed;
        if(pinkBirdX < 0){
            pinkBirdX = screenWidth + 500;
            pinkBirdY = (int) Math.floor(Math.random() * (frameHeight - pinkBird.getHeight()));
        }
        pinkBird.setX(pinkBirdX);
        pinkBird.setY(pinkBirdY);

        //Bomb
        bombX -= bombSpeed;
        if(bombX < 0){
            bombX = screenWidth + 10;
            bombY = (int) Math.floor(Math.random() * (frameHeight - bomb.getHeight()));
        }
        bomb.setX(bombX);
        bomb.setY(bombY);

        //Bomb
        bomb2X -= bomb2Speed;
        if(bomb2X < 0){
            bomb2X = screenWidth + 3000;
            bomb2Y = (int) Math.floor(Math.random() * (frameHeight - bomb2.getHeight()));
        }
        bomb2.setX(bomb2X);
        bomb2.setY(bomb2Y);

        //Coin
        coinX -= coinSpeed;
        if(coinX < 0){
            coinX = screenWidth + 5000;
            coinY = (int) Math.floor(Math.random() * (frameHeight - coin.getHeight()));
        }
        coin.setX(coinX);
        coin.setY(coinY);



        //Move box
        if(action_flg){
            //Touching
            boxY -= boxSpeed;
        }else{
            //Releasing
            boxY += boxSpeed;
        }

        //Check box position.
        if(boxY < 0) boxY = 0;
        if(boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;

        box.setY(boxY);
        score.setText("Score: " + point);
    }

    public void hitCheck(){
        //Bird -- center
        int birdCenterX = birdX + bird.getWidth()/2;
        int birdCenterY = birdY + bird.getHeight()/2;

        // 0 <= birdCenterX <= boxWidth
        // boxY <= birdCenterY <= boxY + boxHeight
        if(0 <= birdCenterX && birdCenterX <= boxSize &&
                boxY <= birdCenterY && birdCenterY <= boxY + boxSize){
            birdX = -100;
            point += 10;
            sound.playHitSound();
        }

        //Pink Bird -- center
        int pinkBirdCenterX = pinkBirdX + pinkBird.getWidth()/2;
        int pinkBirdCenterY = pinkBirdY + pinkBird.getHeight()/2;

        // 0 <= birdCenterX <= boxWidth
        // boxY <= birdCenterY <= boxY + boxHeight
        if(0 <= pinkBirdCenterX && pinkBirdCenterX <= boxSize &&
                boxY <= pinkBirdCenterY && pinkBirdCenterY <= boxY + boxSize){
            pinkBirdY = -100;
            point += 20;
            sound.playHitSound();
        }

        //Coin -- center
        int coinCenterX = coinX + coin.getWidth()/2;
        int coinCenterY = coinY + coin.getHeight()/2;

        // 0 <= coinCenterX <= boxWidth
        // boxY <= coinCenterY <= boxY + boxHeight
        if(0 <= coinCenterX && coinCenterX <= boxSize &&
                boxY <= coinCenterY && coinCenterY <= boxY + boxSize){
            coinX = -100;
            point += 30;
            sound.playHitSound();
        }

        //Bomb2 -- Edge
        int bomb2EdgeX = bomb2X + bomb2.getWidth();
        int bomb2EdgeY = bomb2Y + bomb2.getHeight();

        if(0 <= bomb2EdgeX && bomb2EdgeX <= boxSize &&
                boxY <= bomb2EdgeY && bomb2EdgeY <= boxY + boxSize){
            bomb2X = -100;
            point -= 50;
            sound.playOverSound();
        }

        //Bomb - Edge
        int bombEdgeX = bombX + bomb.getWidth();
        int bombEdgeY = bombY + bomb.getHeight();
        if(0 <= bombEdgeX && bombEdgeX <= boxSize &&
                boxY <= bombEdgeY && bombEdgeY <= boxY + boxSize){
            //Stop Timer
            timer.cancel();
            timer = null;
            sound.playOverSound();

            //Show result
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            SharedPreferences settings = getSharedPreferences(NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("SCORE", point);
            editor.commit();
//            intent.putExtra("SCORE", point);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent me){


        if(!start_flg){
            start_flg = true;

            FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
            frameHeight = frame.getHeight();
            boxY = (int) box.getY();

            boxSize = box.getHeight();
            tapStart.setVisibility(View.GONE);
            tapTap.setVisibility(View.GONE);
            box.setVisibility(View.VISIBLE);


            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        }else{
            if(me.getAction() == MotionEvent.ACTION_DOWN){
                action_flg = true;
            }else if(me.getAction() == MotionEvent.ACTION_UP){
                action_flg = false;
            }
        }


        return true;
    }

}

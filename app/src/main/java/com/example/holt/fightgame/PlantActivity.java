package com.example.holt.fightgame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class PlantActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameView = new GameView(getApplicationContext());
        setContentView(gameView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameView.handTouch(event);
        return super.onTouchEvent(event);
    }
}
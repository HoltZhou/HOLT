package com.example.holt.fightgame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class Second extends AppCompatActivity {

    private Game2View game2View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        game2View = new Game2View(getApplicationContext());
        setContentView(game2View);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game2View.handTouch(event);
        return super.onTouchEvent(event);
    }
}
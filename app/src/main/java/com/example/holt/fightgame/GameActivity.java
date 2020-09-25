package com.example.holt.fightgame;


import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;



public class GameActivity extends AppCompatActivity {

    private int a;
    private String[] data;


    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

       View viewanim = findViewById(R.id.id_recorder_anim);
       viewanim.setBackgroundResource(R.drawable.one);
       AnimationDrawable drawable = (AnimationDrawable) viewanim.getBackground();
       drawable.start();

    }

}

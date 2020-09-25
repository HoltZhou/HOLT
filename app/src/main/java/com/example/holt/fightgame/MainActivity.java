package com.example.holt.fightgame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


public class MainActivity  extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        MediaPlayer bgm = MediaPlayer.create(this, R.raw.a0016);


        boolean a = bgm.isPlaying();
        bgm.start();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ImageButton btn = (ImageButton) findViewById(R.id.imageButton);
        ImageButton btn2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton btn3 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton btn4 = (ImageButton) findViewById(R.id.imageButton4);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
                startActivity(intent);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, FightActivity.class);
                startActivity(intent2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent3);
            }

        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, EndActivity.class);
                startActivity(intent4);
            }

        });


    }



}

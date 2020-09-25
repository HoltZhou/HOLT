package com.example.holt.fightgame;


import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;


public class HelpActivity extends AppCompatActivity{





    private int music;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.avtivity_help);
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView .setTypeface(Typeface.DEFAULT.defaultFromStyle(Typeface.BOLD));
        ImageButton btn8 =(ImageButton)findViewById(R.id.imageButton9);
        btn8.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent6 = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(intent6);
            }
        });

    }

}

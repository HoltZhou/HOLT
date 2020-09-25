package com.example.holt.fightgame;


import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;


public class ChooseActivity extends AppCompatActivity{

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose);

        ImageButton btn5 =(ImageButton)findViewById(R.id.imageButton5);

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 =new Intent(ChooseActivity.this,PlantActivity.class);
                startActivity(intent5);
         }



        });

        ImageButton btn8 =(ImageButton)findViewById(R.id.imageButton10);
        btn8.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Intent intent6 = new Intent(ChooseActivity.this, MainActivity.class);
                startActivity(intent6);
            }
        });

    }

}

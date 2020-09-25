package com.example.holt.fightgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public abstract class Sprite {

        public Bitmap defaultBitmap;
        public Point position;

        public Sprite(Bitmap defaultBitmap, Point position) {
            this.defaultBitmap = defaultBitmap;
            this.position = position;
        }

        public void drawSelf(Canvas canvas){
            canvas.drawBitmap(defaultBitmap, position.x, position.y, null);
        }

    }







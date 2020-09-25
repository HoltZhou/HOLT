package com.example.holt.fightgame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Man extends Sprite{
    public static final int MOVE_LEFT = 0x00;
    public static final int MOVE_RIGHT = 0x01;
    public static final int ATTACK = 0x02;
    public static final int DEFEND = 0x03;
    private int speed = 20;

    public Man(Bitmap defaultBitmap, Point position) {
        super(defaultBitmap, position);
    }

    public void move(int direct) {
        if (direct == MOVE_LEFT) {
            position.x -= speed;
        } else if (direct == MOVE_RIGHT) {
            position.x += speed;

        }
    }

    public void setSpeed(int speed){
        this.speed = 20;
    }

}
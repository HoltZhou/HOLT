package com.example.holt.fightgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class MyButton extends Sprite{


    private boolean isClick = false;
    private Bitmap pressBitmap;

    public MyButton(Bitmap defaultBitmap, Point position, Bitmap pressBitmap) {
        super(defaultBitmap, position);
        this.pressBitmap = pressBitmap;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isClick) {
            canvas.drawBitmap(pressBitmap, position.x, position.y, null);
        }else {
            super.drawSelf(canvas);
        }

    }

    public boolean isClick(Point touchPoint) {
        Rect rect = new Rect(position.x, position.y, position.x + pressBitmap.getWidth(), position.y + pressBitmap.getHeight());
        isClick = rect.contains(touchPoint.x, touchPoint.y);
        return isClick;
    }


    public void setClick(boolean isClick) {

        this.isClick = isClick;
    }

    public void click(){
        if (mListener != null) {
            mListener.click();
        }
    }


    private OnButtonClickListener mListener;
    public interface OnButtonClickListener{
        void click();
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.mListener = listener;
    }






}
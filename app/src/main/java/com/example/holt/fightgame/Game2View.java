package com.example.holt.fightgame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game2View extends SurfaceView implements SurfaceHolder.Callback {

    private SoundPool soundPool;
    private int soundID;
    private boolean isRuning = false;
    private SurfaceTask mTask;
    private SurfaceHolder mHolder;
    private Man mMan, mMan2, mMan3;
    private Man nMan, nMan2, nMan3;
    private Man Bg1, Bg2, Bg3;
    private MyButton mButton;
    private MyButton nButton;
    private MyButton aButton;
    private MyButton bButton;
    private ExecutorService mPool;
    private int a = 0, b = 0, c = 0, d = 0, e = 0;
    private int hp1 = 60, hp2 = 60, score1 = 0, score2 = 0;
    private boolean defend = true;
    private int level=0;


    public Game2View(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPool = Executors.newFixedThreadPool(5);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    public Game2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Game2View(Context context) {
        this(context, null);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap manBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.man);
        Bitmap manBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.man2);
        Bitmap manBitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.man3);
        Bitmap manBitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.enemy);
        Bitmap manBitmap5 = BitmapFactory.decodeResource(getResources(), R.mipmap.enemy2);
        Bitmap manBitmap6 = BitmapFactory.decodeResource(getResources(), R.mipmap.enemy3);


        mMan = new Man(manBitmap, new Point(getWidth() / 2 - 600, getHeight() - 400));
        mMan2 = new Man(manBitmap2, new Point(getWidth() / 2 - 600, getHeight() - 400));
        mMan3 = new Man(manBitmap3, new Point(getWidth() / 2 - 600, getHeight() - 400));


        nMan = new Man(manBitmap4, new Point(getWidth() / 2 + 600, getHeight() - 400));
        nMan2 = new Man(manBitmap5, new Point(getWidth() / 2 + 600, getHeight() - 400));
        nMan3 = new Man(manBitmap6, new Point(getWidth() / 2 + 600, getHeight() - 400));

        Bitmap bgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a001);
        Bitmap bgBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.a002);
        Bitmap bgBitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.a003);
        Bg1 = new Man(bgBitmap, new Point(getWidth() / 2 - 925, getHeight() - 1100));
        Bg2 = new Man(bgBitmap2, new Point(getWidth() / 2 - 925, getHeight() - 1100));
        Bg3 = new Man(bgBitmap3, new Point(getWidth() / 2 - 925, getHeight() - 1100));


        Bitmap btnBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.left);
        Bitmap pressBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.left2);
        nButton = new MyButton(btnBitmap2, new Point(getWidth() / 2 - 800, getHeight() - 140), pressBitmap2);
        nButton.setOnButtonClickListener(listener2);

        Bitmap btnBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.right);
        Bitmap pressBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.right2);
        mButton = new MyButton(btnBitmap, new Point(getWidth() / 2 - 550, getHeight() - 140), pressBitmap);
        mButton.setOnButtonClickListener(listener);

        Bitmap btnBitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.attack);
        Bitmap pressBitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.attack);
        aButton = new MyButton(btnBitmap3, new Point(getWidth() / 2 + 800, getHeight() - 140), pressBitmap3);
        aButton.setOnButtonClickListener(listener3);

        Bitmap btnBitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.defend);
        Bitmap pressBitmap4 = BitmapFactory.decodeResource(getResources(), R.mipmap.defend);
        bButton = new MyButton(btnBitmap4, new Point(getWidth() / 2 + 550, getHeight() - 140), pressBitmap4);
        bButton.setOnButtonClickListener(listener4);

        mTask = new SurfaceTask();
        isRuning = true;
        mPool.execute(mTask);
    }

    private MyButton.OnButtonClickListener listener = new MyButton.OnButtonClickListener() {
        @Override
        public void click() {
            mMan.move(Man.MOVE_RIGHT);
            mMan2.position = mMan.position;
            mMan3.position = mMan.position;
        }
    };
    private MyButton.OnButtonClickListener listener2 = new MyButton.OnButtonClickListener() {
        @Override
        public void click() {
            mMan.move(Man.MOVE_LEFT);
            mMan2.position = mMan.position;
            mMan3.position = mMan.position;
        }
    };
    private MyButton.OnButtonClickListener listener3 = new MyButton.OnButtonClickListener() {
        @Override
        public void click() {
            b = 10;
        }
    };
    private MyButton.OnButtonClickListener listener4 = new MyButton.OnButtonClickListener() {
        @Override
        public void click() {
            b = -10;
        }
    };


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        isRuning = false;
    }


    private class SurfaceTask implements Runnable {
        @Override
        public void run() {
            while (isRuning) {
                try {
                    long start = System.currentTimeMillis();
                    drawUI();
                    long end = System.currentTimeMillis();
                    long dtime = end - start;
                    int fps = (int) (1000 / dtime);
                    System.out.println("fps --" + fps);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                if (mButton.isClick(new Point(x, y))) {
                    d = 0;
                    mMan.move(Man.MOVE_RIGHT);
                    mMan2.position = mMan.position;
                    mMan3.position = mMan.position;
                    mButton.click();
                } else if (nButton.isClick(new Point(x, y))) {
                    d = 0;
                    mMan.move(Man.MOVE_LEFT);
                    mMan2.position = mMan.position;
                    mMan3.position = mMan.position;
                    nButton.click();
                } else if (aButton.isClick(new Point(x, y))) {

                    if (mMan.position.x - nMan.position.x < 100 && mMan.position.x - nMan.position.x > -100) {
                        hp2 = hp2 - 5;
                    }


                    d = 0;
                    b = 10;

                    aButton.click();
                } else if (bButton.isClick(new Point(x, y))) {

                    d = 1;
                    b = -10;
                    bButton.click();
                }
                break;

            case MotionEvent.ACTION_UP:
                mButton.setClick(false);
                nButton.setClick(false);
                break;


            default:
                break;
        }
    }


    public void drawUI() {

        Canvas canvas = mHolder.lockCanvas();
        Paint paint = new Paint();
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        if (a % 30 < 10) {
            Bg1.drawSelf(canvas);
            a++;
        } else if (a % 30 < 20) {
            Bg2.drawSelf(canvas);
            a++;
        } else {
            Bg3.drawSelf(canvas);
            a++;
        }

        if (b == 0) {
            mMan.drawSelf(canvas);
        } else if (b > 0) {
            mMan2.drawSelf(canvas);
            b--;
        } else if (b < 0) {
            mMan3.drawSelf(canvas);
            b++;
        }

        if (nMan.position.x < mMan.position.x) {
            nMan.position.x = nMan.position.x + 70;
            nMan.drawSelf(canvas);
            c++;
        } else if (c % (46-level) < 5) {
            nMan2.position = nMan.position;
            nMan3.position = nMan.position;
            nMan2.drawSelf(canvas);
            if (mMan.position.x - nMan.position.x < 100 && mMan.position.x - nMan.position.x > -100 && d == 0) {
                hp1--;
            }
            c++;
        } else if (c % 38 > 11 && c % 38 < 17) {
            nMan2.position = nMan.position;
            nMan3.position = nMan.position;
            nMan3.drawSelf(canvas);
            c++;
        } else if (c % 33 == 0) {
            nMan.position.x = nMan.position.x - 70;
            nMan.drawSelf(canvas);
            c++;
        } else {
            c++;
            nMan.drawSelf(canvas);
        }

        if (hp2 <= 0) {
            score1++;
            hp1 = 60;
            hp2 = 60;
            mMan.position.x = getWidth() / 2 - 600;
            mMan2.position.x = getWidth() / 2 - 600;
            mMan3.position.x = getWidth() / 2 - 600;
            nMan.position.x = getWidth() / 2 + 600;
            nMan2.position.x = getWidth() / 2 + 600;
            nMan3.position.x = getWidth() / 2 + 600;
            e = e - 30;
            level=level+5;
        }
        if (hp1 <= 0) {
            score2++;
            hp1 = 60;
            hp2 = 60;
            mMan.position.x = getWidth() / 2 - 600;
            mMan2.position.x = getWidth() / 2 - 600;
            mMan3.position.x = getWidth() / 2 - 600;
            nMan.position.x = getWidth() / 2 + 600;
            nMan2.position.x = getWidth() / 2 + 600;
            nMan3.position.x = getWidth() / 2 + 600;
            e = e + 30;
            level=level-5;

        }


        mButton.drawSelf(canvas);
        nButton.drawSelf(canvas);
        aButton.drawSelf(canvas);
        bButton.drawSelf(canvas);
        paint.setTextSize(75);
        paint.setColor(Color.WHITE);
        canvas.drawText("生命值：" + hp1, getWidth() / 2 - 700, getHeight() - 1000, paint);
        canvas.drawText("比分" + score1 + ":" + score2, getWidth() / 2 - 100, getHeight() - 1000, paint);
        canvas.drawText("生命值：" + hp2, getWidth() / 2 + 500, getHeight() - 1000, paint);
        if (e > 0) {

            canvas.drawText("你输了！", getWidth() / 2 - 100, getHeight() - 600, paint);
            canvas.drawText("敌人降级", getWidth() / 2 - 100, getHeight() - 450, paint);
            e--;
        }
        if (e < 0) {

            canvas.drawText("你赢了", getWidth() / 2 - 100, getHeight() - 600, paint);
            canvas.drawText("敌人升级", getWidth() / 2 - 100, getHeight() - 450, paint);



            e++;
        }

        if(score1>=5){
            canvas.drawText("游戏结束", getWidth() / 2 - 100, getHeight() - 600, paint);
            mHolder.lockCanvas();

        }




        mHolder.unlockCanvasAndPost(canvas);
    }


}
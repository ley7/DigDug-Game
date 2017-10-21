package com.example.yorkl.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.example.yorkl.myapplication.controller.GameController;
import com.example.yorkl.myapplication.model.DigDug;
import com.example.yorkl.myapplication.model.GameMap;
import com.example.yorkl.myapplication.model.Monster;
import com.example.yorkl.myapplication.model.Rock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yashaswiniamaresh on 5/10/17.
 */

public class ClockView extends SurfaceView implements SurfaceHolder.Callback {

    private GameController controller;
    private Paint paint;
    public String direction;
    public DigDug digDug = new DigDug();
    private int prevX;
    private int prevY;
    private Monster[] monsters;
    private Rock[] rocks;
    private Bitmap monsterbitmap,firemonsterbitmap,digdugbitmap,dirt;
    int pixel = 60;
    //private GameMap gameMap;
    private boolean[][] gameMap;
    int color;

    public ClockView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        color = 0;
        int pixel = 60;
        int row = pixel/2;
        int column = pixel/2;
        gameMap = new boolean[pixel][pixel];  //remember 60

        gameMap[row-1][column-1]=true;
        gameMap[row-1][column]=true;
        gameMap[row-1][column+1]=true;
        gameMap[row][column-1]=true;
        gameMap[row][column]=true;
        gameMap[row][column+1]=true;
        gameMap[row+1][column-1]=true;
        gameMap[row+1][column]=true;
        gameMap[row+1][column+1]=true;

        gameMap[row/2-1][column/2-1]=true;
        gameMap[row/2-1][column/2]=true;
        gameMap[row/2-1][column/2+1]=true;
        gameMap[row/2][column/2-1]=true;
        gameMap[row/2][column/2]=true;
        gameMap[row/2][column/2+1]=true;
        gameMap[row/2+1][column/2-1]=true;
        gameMap[row/2+1][column/2]=true;
        gameMap[row/2+1][column/2+1]=true;

        digDug.xPos = pixel/2;
        digDug.yPos = pixel/2;


    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //canvas.drawColor(Color.WHITE);
        int width = getWidth();
        int height = getWidth();
        Rect rect = new Rect();
     //   rect.set(0, 0, width/3,height/3);
    //    canvas.drawBitmap(digdugbitmap, null, rect, null);
        int rowHeight = height / pixel;
        int columnWidth = width / pixel;

        for (int i = 0; i < pixel; i++) {   //draw the board
            for (int j = 0; j < pixel; j++) {
                if(!gameMap[i][j]) {
                    rect.set(j * columnWidth, i * rowHeight, (j + 1) * columnWidth, (i + 1) * rowHeight);
                    canvas.drawBitmap(dirt, null, rect, null);
                    // drawDigDug(canvas);
                }
            }
        }

        //  rect.set(10* columnWidth, 16 * rowHeight, 10 * columnWidth, 16 * rowHeight);
        rect.set((digDug.xPos-1)*columnWidth, (digDug.yPos-1)*rowHeight, (digDug.xPos+2)*columnWidth, (digDug.yPos+2)*rowHeight);
        canvas.drawBitmap(digdugbitmap, null, rect, null);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    //    ClockThread clockThread = new ClockThread(this);
     //   clockThread.start();
        setWillNotDraw(false);
        monsterbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_monster);
        firemonsterbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_firemonster);
        digdugbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_digdug);
        dirt = BitmapFactory.decodeResource(getResources(), R.drawable.icon_dirt);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("In onTouchEvent");
        if(color == 3) {
            color = 0;
        }

        switch(color) {
            case 0: paint.setColor(Color.RED);
                    break;
            case 1: paint.setColor(Color.GREEN);
                    break;
            case 2: paint.setColor(Color.BLUE);
                    break;
            default: paint.setColor(Color.BLACK);
        }

        color++;

        return true;
    }

/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("Touch event occured");

        int currX;
        int currY;
        int endRowNum = 0;
        int endColNum = 0;
        int width = getWidth();
        int height = getHeight();

        int rowHeight = height / 3;
        int columnWidth = width / 2;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            prevX = (int) event.getX();
            prevY = (int) event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            currX = (int) event.getX();
            currY = (int) event.getY();
            if(prevX==currX && prevY == currY){
                return false;
            }
            double k;
            k = (currY - prevY) / (currX - prevX);
            double distanceX = currX - prevX;
            double distanceY = currY - prevY;
            if (distanceX > 0 && k <= 1 && k >= -1) {
                direction = "right";
                System.out.println("move right");
            } else if (distanceY > 0 && (k > 1 || k <= -1)) {
                direction = "up";
                System.out.println("move up");
            } else if (distanceX < 0 && k >= -1 && k <= 1) {
                direction = "left";
                System.out.println("move left");
            } else if (distanceY < 0 && (k > 1 || k < -1)) {
                direction = "down";
                System.out.println("move down");
            } else {
                System.out.println("Invalid move");
                direction = "nothing";
                return false;
            }

            invalidate();
        }
        return true;
    }
    */
}


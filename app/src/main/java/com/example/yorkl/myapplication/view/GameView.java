package com.example.yorkl.myapplication.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.example.yorkl.myapplication.R;
import com.example.yorkl.myapplication.controller.GameController;
import com.example.yorkl.myapplication.controller.GameThread;
import com.example.yorkl.myapplication.model.DigDug;
import com.example.yorkl.myapplication.model.FireMonster;
import com.example.yorkl.myapplication.model.GameMap;
import com.example.yorkl.myapplication.model.Monster;
import com.example.yorkl.myapplication.model.Rock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameController controller;
    private Paint paint;
    public String direction;
    public DigDug digDug = new DigDug();
    private int prevX;
    private int prevY;
    public Monster[] monsters = new Monster[3];
    public FireMonster firemonster = new FireMonster();
    public Rock[] rocks = new Rock[3];
    private Bitmap monsterbitmap,firemonsterbitmap,digdugbitmap,dirtbitmap,rockbitmap,backgroundblack,firebitmap,firebitmapleft,firebitmapup,firebitmapdown,inflatebitmapleft,inflatebitmapup,ghostbitmp,ghostbitmp2;
    int pixel = 60;
    public boolean[][] gameMap;
    int color;

    public GameView(Context context) {
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
        rocks[0] = new Rock();
        rocks[1] = new Rock();
        rocks[2] = new Rock();
        monsters[0] = new Monster();
        monsters[1] = new Monster();
        monsters[2] = new Monster();

        gameMap[row-1][column-1]=true;
        gameMap[row-1][column]=true;
        gameMap[row-1][column+1]=true;
        gameMap[row][column-1]=true;
        gameMap[row][column]=true;
        gameMap[row][column+1]=true;
        gameMap[row+1][column-1]=true;
        gameMap[row+1][column]=true;
        gameMap[row+1][column+1]=true;

        digDug.xPos = pixel/2;
        digDug.yPos = pixel/2;

        rocks[0].xPos = column/2-3;
        rocks[0].yPos = row/2-3;
        rocks[1].xPos = 3*column/2;
        rocks[1].yPos = 5*row/7;
        rocks[2].xPos = row/2;
        rocks[2].yPos = 3*column/2;


        for(int i=0;i<3;i++) { //initialize rock
            gameMap[rocks[i].xPos - 1][rocks[i].yPos - 1] = true;
            gameMap[rocks[i].xPos  - 1][rocks[i].yPos] = true;
            gameMap[rocks[i].xPos  - 1][rocks[i].yPos + 1] = true;
            gameMap[rocks[i].xPos ][rocks[i].yPos - 1] = true;
            gameMap[rocks[i].xPos ][rocks[i].yPos] = true;
            gameMap[rocks[i].xPos ][rocks[i].yPos + 1] = true;
            gameMap[rocks[i].xPos + 1][rocks[i].yPos - 1] = true;
            gameMap[rocks[i].xPos  + 1][rocks[i].yPos] = true;
            gameMap[rocks[i].xPos  + 1][rocks[i].yPos + 1] = true;
        }

        monsters[0].xPos = 3*column/2-3;
        monsters[0].yPos = 3*row/2-3;
        monsters[1].xPos = 3*row/2;
        monsters[1].yPos = column/2;

        for(int i=0;i<2;i++){
            for(int j=-5;j<6;j++) {
                gameMap[monsters[i].xPos +j][monsters[i].yPos - 1] = true;
                gameMap[monsters[i].xPos + j][monsters[i].yPos] = true;
                gameMap[monsters[i].xPos + j][monsters[i].yPos + 1] = true;
            }
        }

        monsters[2].xPos = row/2;
        monsters[2].yPos = column;
        for(int j=-5;j<6;j++) {
            gameMap[monsters[2].xPos -1][monsters[2].yPos +j] = true;
            gameMap[monsters[2].xPos ][monsters[2].yPos + j] = true;
            gameMap[monsters[2].xPos + 1][monsters[2].yPos +j] = true;
        }

        firemonster.xPos = column-4;
        firemonster.yPos = row/4;
        for(int j=-5;j<6;j++) {
            gameMap[firemonster.xPos +j][firemonster.yPos - 1] = true;
            gameMap[firemonster.xPos + j][firemonster.yPos] = true;
            gameMap[firemonster.xPos + j][firemonster.yPos + 1] = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //canvas.drawColor(Color.BLACK);
        int width = getWidth();
        int height = getWidth();
        Rect rect = new Rect();

        int rowHeight = height / pixel;
        int columnWidth = width / pixel;
       // System.out.println("before");
        if(!digDug.isalive())  System.exit(0);
       // System.out.println("after");
        if((!monsters[0].isalive())&&(!monsters[1].isalive())&&(!monsters[2].isalive())&&(!firemonster.isalive())) System.exit(0);
        for(int i=0;i<3;i++) {
            if ((rocks[i].xPos - 1 <= digDug.xPos && digDug.xPos <= rocks[i].xPos + 1) && (rocks[i].yPos + 1 >= digDug.yPos && rocks[i].yPos - 1 <= digDug.yPos)) {
               digDug.setalive(false);
            }
        }
        for (int i = 0; i < pixel; i++) {   //draw the board
            for (int j = 0; j < pixel; j++) {
                if(!gameMap[i][j]) {
                   // System.out.println("draw something");
                    rect.set(i * columnWidth, j * rowHeight, (i + 1) * columnWidth, (j + 1) * rowHeight);
                    canvas.drawBitmap(dirtbitmap, null, rect, null);
                }
                else  {
                    rect.set(i * columnWidth, j * rowHeight, (i + 1) * columnWidth, (j + 1) * rowHeight);
                    canvas.drawBitmap(backgroundblack, null, rect, null);
                }
            }
        }

        rect.set((digDug.xPos-1)*columnWidth, (digDug.yPos-1)*rowHeight, (digDug.xPos+2)*columnWidth, (digDug.yPos+2)*rowHeight);
        canvas.drawBitmap(digdugbitmap, null, rect, null);
        if(firemonster.isalive()) {
            if(firemonster.isghost) {
                rect.set((firemonster.xPos - 1) * columnWidth, (firemonster.yPos - 1) * rowHeight, (firemonster.xPos + 2) * columnWidth, (firemonster.yPos + 2) * rowHeight);
                canvas.drawBitmap(ghostbitmp2, null, rect, null);
            }
            else{
                rect.set((firemonster.xPos - 1) * columnWidth, (firemonster.yPos - 1) * rowHeight, (firemonster.xPos + 2) * columnWidth, (firemonster.yPos + 2) * rowHeight);
                canvas.drawBitmap(firemonsterbitmap, null, rect, null);
            }
        }
        for(int i = 0;i < 3;i++) {
            if(rocks[i].isalive()) {
                rect.set((rocks[i].xPos - 1) * columnWidth, (rocks[i].yPos - 1) * rowHeight, (rocks[i].xPos + 2) * columnWidth, (rocks[i].yPos + 2) * rowHeight);
                canvas.drawBitmap(rockbitmap, null, rect, null);
            }
            if(monsters[i].isalive()) {
                if(!monsters[i].isghost) {
                    rect.set((monsters[i].xPos - 1) * columnWidth, (monsters[i].yPos - 1) * rowHeight, (monsters[i].xPos + 2) * columnWidth, (monsters[i].yPos + 2) * rowHeight);
                    canvas.drawBitmap(monsterbitmap, null, rect, null);
                    //System.out.println(monsters[i].isghost);
                }
                else{
                    rect.set((monsters[i].xPos - 1) * columnWidth, (monsters[i].yPos - 1) * rowHeight, (monsters[i].xPos + 2) * columnWidth, (monsters[i].yPos + 2) * rowHeight);
                    canvas.drawBitmap(ghostbitmp, null, rect, null);
                }
            }
        }

        if(digDug.attack_flag) {
            if(digDug.getdirection()=="stand_towards_right") {
                int counter = 0;
                int distance = digDug.xPos;
                int y = digDug.yPos;
                while(hasright(distance,y)){
                    counter++;
                    distance++;
                    if(counter>=11) break;
                }
                counter+=1;
                for(int i=0;i<3;i++){
                    if(monsters[i].isalive()){
                        if(monsters[i].xPos>=digDug.xPos + 2 && monsters[i].xPos<=digDug.xPos +counter  && monsters[i].yPos>=digDug.yPos-2 && monsters[i].yPos<=digDug.yPos+2){
                            if(monsters[i].gethealth() == 0) monsters[i].setalive(false);
                            else {
                                Calendar c = Calendar.getInstance();
                                long currenttime = c.get(Calendar.SECOND);
                                monsters[i].decreasehealth();
                                monsters[i].starttime=currenttime;
                                monsters[i].setdirection("stand");
                                //System.out.println("wocao??:"+monsters[0].gethealth());
                            }
                        }
                    }
                }
                if(firemonster.isalive()){
                    if(firemonster.xPos>=digDug.xPos +2 && firemonster.yPos<=digDug.xPos +counter  && firemonster.yPos>=digDug.yPos-2 && firemonster.yPos<=digDug.yPos+2){
                        if(firemonster.gethealth() == 0) firemonster.setalive(false);
                        else {
                            Calendar c = Calendar.getInstance();
                            long currenttime = c.get(Calendar.SECOND);
                            firemonster.decreasehealth();
                            firemonster.starttime=currenttime;
                            firemonster.setdirection("stand");
                        }
                    }
                }
                rect.set((digDug.xPos + 1) * columnWidth, (digDug.yPos - 1) * rowHeight, (digDug.xPos + counter+1) * columnWidth, (digDug.yPos + 2) * rowHeight);
                canvas.drawBitmap(inflatebitmapleft, null, rect, null);
            }
            if(digDug.getdirection()=="stand_towards_left") {
                int counter = 0;
                int distance = digDug.xPos;
                int y = digDug.yPos;
                while(hasleft(distance,y)){
                    counter++;
                    distance--;
                    if(counter>=11) break;
                }
                counter+=1;
                for(int i=0;i<3;i++){
                    if(monsters[i].isalive()){
                        if(monsters[i].xPos>=digDug.xPos - counter && monsters[i].xPos<=digDug.xPos -2  && monsters[i].yPos>=digDug.yPos-2 && monsters[i].yPos<=digDug.yPos+2){
                            if(monsters[i].gethealth() == 0) monsters[i].setalive(false);
                            else {
                                Calendar c = Calendar.getInstance();
                                long currenttime = c.get(Calendar.SECOND);
                                monsters[i].decreasehealth();
                                monsters[i].starttime=currenttime;
                                monsters[i].setdirection("stand");
                            }
                        }
                    }
                }
                if(firemonster.isalive()){
                    if(firemonster.xPos>=digDug.xPos - counter && firemonster.xPos<=digDug.xPos -2  && firemonster.yPos>=digDug.yPos-2 && firemonster.yPos<=digDug.yPos+2){
                        if(firemonster.gethealth() == 0) firemonster.setalive(false);
                        else {
                            Calendar c = Calendar.getInstance();
                            long currenttime = c.get(Calendar.SECOND);
                            firemonster.decreasehealth();
                            firemonster.starttime=currenttime;
                            firemonster.setdirection("stand");
                        }
                    }
                }
                rect.set((digDug.xPos -counter) * columnWidth, (digDug.yPos - 1) * rowHeight, (digDug.xPos - 1) * columnWidth, (digDug.yPos + 2) * rowHeight);
                canvas.drawBitmap(inflatebitmapleft, null, rect, null);
            }
            if(digDug.getdirection()=="stand_towards_up") {
                int counter = 0;
                int distance = digDug.yPos;
                int x = digDug.xPos;
                while(hasup(x,distance)){
                    counter++;
                    distance--;
                    if(counter>=11) break;
                }
                counter+=1;
                for(int i=0;i<3;i++){
                    if(monsters[i].isalive()){
                        if(monsters[i].yPos>=digDug.yPos - counter && monsters[i].yPos<=digDug.yPos -2  && monsters[i].xPos>=digDug.xPos-2 && monsters[i].xPos<=digDug.xPos+2){
                            if(monsters[i].gethealth() == 0) monsters[i].setalive(false);
                            else {
                                Calendar c = Calendar.getInstance();
                                long currenttime = c.get(Calendar.SECOND);
                                monsters[i].decreasehealth();
                                monsters[i].starttime=currenttime;
                                monsters[i].setdirection("stand");
                            }
                        }
                    }

                }
                if(firemonster.isalive()){
                    if(firemonster.yPos>=digDug.yPos - counter && firemonster.yPos<=digDug.yPos -2  && firemonster.xPos>=digDug.xPos-2 && firemonster.xPos<=digDug.xPos+2){
                        if(firemonster.gethealth() == 0) firemonster.setalive(false);
                        else {
                            Calendar c = Calendar.getInstance();
                            long currenttime = c.get(Calendar.SECOND);
                            firemonster.decreasehealth();
                            firemonster.starttime=currenttime;
                            firemonster.setdirection("stand");
                        }
                    }
                }
                rect.set((digDug.xPos - 1) * columnWidth, (digDug.yPos - counter) * rowHeight, (digDug.xPos + 2) * columnWidth, (digDug.yPos - 1) * rowHeight);
                canvas.drawBitmap(inflatebitmapup, null, rect, null);
            }
            if(digDug.getdirection()=="stand_towards_down") {
                int counter = 0;
                int distance = digDug.yPos;
                int x = digDug.xPos;
                while(hasdown(x,distance)){
                    counter++;
                    distance++;
                    if(counter>=11) break;
                }
                counter+=1;
                for(int i=0;i<3;i++){
                    if(monsters[i].isalive()){
                        if(monsters[i].yPos>=digDug.yPos + 2 && monsters[i].yPos<=digDug.yPos + counter && monsters[i].xPos>=digDug.xPos-2 && monsters[i].xPos<=digDug.xPos+2){
                            if(monsters[i].gethealth() == 0) monsters[i].setalive(false);
                            else {
                                Calendar c = Calendar.getInstance();
                                long currenttime = c.get(Calendar.SECOND);
                                monsters[i].decreasehealth();
                                monsters[i].starttime=currenttime;
                                monsters[i].setdirection("stand");
                            }
                        }
                    }
                }
                if(firemonster.isalive()){
                    if(firemonster.yPos>=digDug.yPos + 2 && firemonster.yPos<=digDug.yPos +counter  && firemonster.xPos>=digDug.xPos-2 && firemonster.xPos<=digDug.xPos+2){
                        if(firemonster.gethealth() == 0) firemonster.setalive(false);
                        else {
                            Calendar c = Calendar.getInstance();
                            long currenttime = c.get(Calendar.SECOND);
                            firemonster.decreasehealth();
                            firemonster.starttime=currenttime;
                            firemonster.setdirection("stand");
                        }
                    }
                }
                rect.set((digDug.xPos - 1) * columnWidth, (digDug.yPos + 1) * rowHeight, (digDug.xPos + 2) * columnWidth, (digDug.yPos + counter+1) * rowHeight);
                canvas.drawBitmap(inflatebitmapup, null, rect, null);
            }
        }

        if(firemonster.attack_flag) {
            if (firemonster.isalive()) {
                System.out.println("attack_double_check");
                if (firemonster.getdirection() == "right") {
                    System.out.println("attack_right_triple_check");
                    int counter = 0;
                    int distance = firemonster.xPos;
                    int y = firemonster.yPos;
                    while(hasright(distance,y)){
                        counter++;
                        distance++;
                        if(counter>=11) break;
                    }
                    counter+=1;
                    if (digDug.xPos >= firemonster.xPos + 2 && digDug.xPos <= firemonster.xPos + counter && digDug.yPos >= firemonster.yPos - 2 && digDug.yPos <= firemonster.yPos + 2) {
                        digDug.setalive(false);
                    }
                    rect.set((firemonster.xPos + 1) * columnWidth, (firemonster.yPos - 1) * rowHeight, (firemonster.xPos + counter+1) * columnWidth, (firemonster.yPos + 2) * rowHeight);
                    canvas.drawBitmap(firebitmap, null, rect, null);
                }
                if (firemonster.getdirection() == "left") {
                    int counter = 0;
                    int distance = firemonster.xPos;
                    int y = firemonster.yPos;
                    while(hasleft(distance,y)){
                        counter++;
                        distance--;
                        if(counter>=11) break;
                    }
                    counter+=1;
                    //System.out.println(counter);
                    if (digDug.xPos >= firemonster.xPos - counter && digDug.xPos <= firemonster.xPos - 2 && digDug.yPos >= firemonster.yPos - 2 && digDug.yPos <= firemonster.yPos + 2) {
                        digDug.setalive(false);
                    }
                    rect.set((firemonster.xPos - counter) * columnWidth, (firemonster.yPos - 1) * rowHeight, (firemonster.xPos - 1) * columnWidth, (firemonster.yPos + 2) * rowHeight);
                    canvas.drawBitmap(firebitmapleft, null, rect, null);
                }
                if (firemonster.getdirection() == "up") {
                    int counter = 0;
                    int distance = firemonster.yPos;
                    int x = firemonster.xPos;
                    while(hasup(x,distance)){
                        counter++;
                        distance--;
                        if(counter>=11) break;
                    }
                    counter+=1;
                    if (digDug.yPos >= firemonster.yPos - counter && digDug.yPos <= firemonster.yPos - 2 && digDug.xPos >= firemonster.xPos - 2 && digDug.xPos <= firemonster.xPos + 2) {
                        digDug.setalive(false);
                    }
                    rect.set((firemonster.xPos - 1) * columnWidth, (firemonster.yPos - counter) * rowHeight, (firemonster.xPos + 1) * columnWidth, (firemonster.yPos - 1) * rowHeight);
                    canvas.drawBitmap(firebitmapup, null, rect, null);
                }
                if (firemonster.getdirection() == "down") {
                    int counter = 0;
                    int distance = firemonster.yPos;
                    int x = firemonster.xPos;
                    while(hasdown(x,distance)){
                        counter++;
                        distance++;
                        if(counter>=11) break;
                    }
                    counter+=1;
                    if (digDug.yPos >= firemonster.yPos + 2 && digDug.yPos <= firemonster.yPos + counter && digDug.xPos >= firemonster.xPos - 2 && digDug.xPos <= firemonster.xPos + 2) {
                        digDug.setalive(false);
                    }
                    rect.set((firemonster.xPos - 1) * columnWidth, (firemonster.yPos + 1) * rowHeight, (firemonster.xPos + 1) * columnWidth, (firemonster.yPos + counter+1) * rowHeight);
                    canvas.drawBitmap(firebitmapdown, null, rect, null);
                }
            }
            System.out.println("attack:"+monsters[0].gethealth());
        }
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //    ClockThread clockThread = new ClockThread(this);
        //   clockThread.start();
       // setWillNotDraw(false);
        monsterbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_monster);
        firemonsterbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_firemonster);
        digdugbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_digdug);
        dirtbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_dirt);
        rockbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_rock);
        ghostbitmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_ghost);
        backgroundblack = BitmapFactory.decodeResource(getResources(), R.drawable.icon_black);
        firebitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_fire);
        firebitmapleft = BitmapFactory.decodeResource(getResources(), R.drawable.icon_fireleft);
        firebitmapup = BitmapFactory.decodeResource(getResources(), R.drawable.icon_fireup);
        firebitmapdown = BitmapFactory.decodeResource(getResources(), R.drawable.icon_firedown);
        inflatebitmapleft = BitmapFactory.decodeResource(getResources(), R.drawable.icon_inflateleft);
        inflatebitmapup = BitmapFactory.decodeResource(getResources(), R.drawable.icon_inflateleft);
        ghostbitmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon_ghost2);
        GameThread gameThread = new GameThread(this);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // System.out.println("Touch event occured");

        int currX;
        int currY;
        int endRowNum = 0;
        int endColNum = 0;
        int width = getWidth();
        int height = getHeight();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            prevX = (int) event.getX();
            prevY = (int) event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            currX = (int) event.getX();
            currY = (int) event.getY();
            if(prevX==currX && prevY == currY){
                if(digDug.getdirection()=="right")digDug.setdirection("stand_towards_right");
                if(digDug.getdirection()=="left")digDug.setdirection("stand_towards_left");
                if(digDug.getdirection()=="up")digDug.setdirection("stand_towards_up");
                if(digDug.getdirection()=="down")digDug.setdirection("stand_towards_down");
                digDug.attack_flag=true;
                return true;
            }
            if(prevX==currX){
               if(prevY<currY)  digDug.setdirection("down");
                if(prevY>currY)  digDug.setdirection("up");
                return true;
            }
            double k;
            k = (currY - prevY) / (currX - prevX);
            digDug.attack_flag=false;
            double distanceX = currX - prevX;
            double distanceY = currY - prevY;
            if (distanceX > 0 && k <= 1 && k >= -1) {
                digDug.setdirection("right");
                //System.out.println("move right");
            } else if (distanceY < 0 && (k > 1 || k <= -1)) {
                digDug.setdirection("up");
                //System.out.println("move up");
            } else if (distanceX < 0 && k >= -1 && k <= 1) {
                digDug.setdirection("left");
                //System.out.println("move left");
            } else if (distanceY > 0 && (k > 1 || k < -1)) {
                digDug.setdirection ("down");
                //System.out.println("move down");
            } else {
                System.out.println("Invalid move");
                digDug.setdirection("stand");
                return false;
            }

            invalidate();
        }
        return true;
    }

    public boolean shouldstand(){
        for(int i=0;i<3;i++) {
            if(rocks[i].isalive()) {
                if (digDug.getdirection() == "left" && digDug.xPos - 2 == rocks[i].xPos + 2 && (digDug.yPos-2<=rocks[i].yPos && rocks[i].yPos <=digDug.yPos+2)) {
                    digDug.setdirection("stand");
                    return true;
                }
                else if(digDug.getdirection() == "right" && digDug.xPos + 2 == rocks[i].xPos - 2 && (digDug.yPos-2<=rocks[i].yPos && rocks[i].yPos <=digDug.yPos+2)) {
                    digDug.setdirection("stand");
                    return true;
                }
                else if(digDug.getdirection() == "up" && digDug.yPos - 2 == rocks[i].yPos + 2 && (digDug.xPos==rocks[i].xPos||digDug.xPos-1==rocks[i].xPos||digDug.xPos+1==rocks[i].xPos)) {
                    digDug.setdirection("stand");
                    return true;
                }
                else if(digDug.getdirection() == "down" && digDug.yPos + 2 == rocks[i].yPos - 2 &&(digDug.xPos==rocks[i].xPos||digDug.xPos-1==rocks[i].xPos||digDug.xPos+1==rocks[i].xPos)) {
                    digDug.setdirection("stand");
                    return true;
                }
            }
        }
        return false;
    }
    public boolean shouldchangedirection(Monster mon) {
        for(int i=0;i<3;i++) {
            if (rocks[i].isalive()) {
                //System.out.println("isalive");
                if(mon.xPos + 2 >= 59 || mon.xPos - 2 <=0 || mon.yPos +2 >=59 || mon.yPos - 2 <= 0) return true;
                if (mon.getdirection() == "left" && ((gameMap[mon.xPos - 2][mon.yPos] == false) || (mon.xPos - 2 == rocks[i].xPos+2 && mon.yPos == rocks[i].yPos && (mon.yPos-2<=rocks[i].yPos && rocks[i].yPos <=mon.yPos+2)))) {
                    return true;
                } else if (mon.getdirection() == "right" && ((gameMap[mon.xPos + 2][mon.yPos] == false) || (mon.xPos + 2 == rocks[i].xPos-2&& mon.yPos == rocks[i].yPos && (mon.yPos-2<=rocks[i].yPos && rocks[i].yPos <=mon.yPos+2)))) {
                    return true;
                } else if (mon.getdirection() == "up" && (gameMap[mon.xPos][mon.yPos-2] == false)) {
                    return true;
                } else if (mon.getdirection() == "down" && (gameMap[mon.xPos][mon.yPos+2] == false)) {
                    return true;
                }
                else if(mon.getdirection() == "stand") {
                    //
                    // System.out.println("isalive");
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasup(int x, int y){
        if(y-2>=0) {
            if (gameMap[x][y - 2] == true) {
                return true;
            }
        }
         return false;
    }
    public boolean hasdown(int x, int y){
        if(y+2<=59) {
            if (gameMap[x][y + 2] == true) {
                return true;
            }
        }
        return false;
    }
    public boolean hasleft(int x, int y){
        if(x-2>=0) {
            if (gameMap[x - 2][y] == true) {
                return true;
            }
        }
        return false;
    }
    public boolean hasright(int x, int y){
        if(x+2<=59) {
            if (gameMap[x + 2][y] == true) {
                return true;
            }
        }
        return false;
    }
}



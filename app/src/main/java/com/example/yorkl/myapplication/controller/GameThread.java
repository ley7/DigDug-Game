package com.example.yorkl.myapplication.controller;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.yorkl.myapplication.view.GameView;

import java.util.Calendar;

public class GameThread extends Thread {
    private GameController controller;
    private GameView gameView;
    private int counter;
    private int ghostcounter;
    public GameThread(GameView gameView) {
        this.gameView = gameView;
    }

    public void run() {
        SurfaceHolder sh = gameView.getHolder();
        while (true) {
            Canvas canvas = sh.lockCanvas();
            if (canvas != null) {
                processInput();
                //System.out.println("0:"+gameView.monsters[0].yPos);
                //System.out.println("thread");
                for(int i=0;i<3;i++) {
                    if(gameView.monsters[i].isalive()) {
                        if (gameView.monsters[i].xPos - 2 <= gameView.digDug.xPos && gameView.digDug.xPos <= gameView.monsters[i].xPos + 2 && gameView.monsters[i].yPos - 2 <= gameView.digDug.yPos && gameView.digDug.yPos <= gameView.monsters[i].yPos + 2) {
                            gameView.digDug.setalive(false);
                        }
                        Calendar c = Calendar.getInstance();
                        long currenttime = c.get(Calendar.SECOND);
                        System.out.println("start:"+gameView.monsters[0].starttime);
                        System.out.println("current"+currenttime);
                        if(gameView.monsters[i].starttime+1 < currenttime&&gameView.monsters[i].gethealth()<3){
                            gameView.monsters[i].increasehealth();
                            //System.out.println("recover:"+gameView.monsters[0].gethealth());
                        }
                    }
                }

                //System.out.println("health::::::::::::::::"+gameView.firemonster.gethealth());
                if(gameView.firemonster.isalive()) {
                    Calendar c = Calendar.getInstance();
                    long currenttime = c.get(Calendar.SECOND);
                    if(gameView.firemonster.starttime+1 < currenttime&&gameView.firemonster.gethealth()<3){
                        gameView.firemonster.increasehealth();
                        System.out.println("recover:"+gameView.monsters[0].gethealth());
                    }
                    if (gameView.firemonster.xPos - 2 <= gameView.digDug.xPos && gameView.digDug.xPos <= gameView.firemonster.xPos + 2 && gameView.firemonster.yPos - 2 <= gameView.digDug.yPos && gameView.digDug.yPos <= gameView.firemonster.yPos + 2) {
                        gameView.digDug.setalive(false);
                    }
                }
                if(gameView.monsters[0].flag) {
                    if(!gameView.monsters[0].isghost)
                    gameView.monsters[0].movenormal(gameView);
                }
                else  gameView.monsters[0].fall(gameView);
                if(gameView.monsters[1].flag) {
                    if(!gameView.monsters[1].isghost)
                    gameView.monsters[1].movenormal(gameView);
                }else gameView.monsters[1].fall(gameView);
                if(gameView.monsters[2].flag) {
                    if(!gameView.monsters[2].isghost)
                    gameView.monsters[2].movenormal(gameView);
                }
                else gameView.monsters[2].fall(gameView);
                if(gameView.firemonster.isalive()) {
                    if (gameView.firemonster.flag) {
                        if (!gameView.firemonster.isghost && counter<=18) {
                            gameView.firemonster.movenormal(gameView);
                        }
                    }
                }
                else {
                    if (gameView.firemonster.isalive())
                        gameView.firemonster.fall(gameView);
                }

                for(int i=0;i<3;i++){
                    gameView.rocks[i].fall(gameView);
                }
                //System.out.println("a:"+gameView.monsters[0].yPos);
                //System.out.println(counter);
                if(counter==23){
                    if(gameView.firemonster.isalive()) {
                        gameView.firemonster.attack_flag = true;
                        System.out.println("attack");
                    }
                }
               // System.out.println("b:"+gameView.monsters[0].yPos);
                for(int i=0;i<3;i++) {
                    if (gameView.monsters[i].ghostcounter >= gameView.monsters[i].threshold) {
                       // System.out.println("hhhhhhhhhhhhhhhhhhhh");
                        gameView.monsters[i].isghost=true;
                        gameView.monsters[i].ghost(gameView);
                    }
                }
                if (gameView.firemonster.ghostcounter >= gameView.firemonster.threshold) {
                    // System.out.println("hhhhhhhhhhhhhhhhhhhh");
                    gameView.firemonster.isghost=true;
                    gameView.firemonster.ghost(gameView);
                }
                //System.out.println("c:"+gameView.monsters[0].yPos);
                //System.out.println(counter);
                if(gameView.firemonster.isalive()) {
                    if (!gameView.firemonster.isghost) {
                        counter++;
                    }
                }
                //System.out.println("counter+++++:"+counter);
                for(int i = 0;i<3;i++){
                    gameView.monsters[i].ghostcounter++;
                }
                gameView.firemonster.ghostcounter++;
                gameView.draw(canvas);
                sh.unlockCanvasAndPost(canvas);
                gameView.digDug.attack_flag=false;
                if(counter>=25){
                    gameView.firemonster.attack_flag=false;
                    counter =0;
                }
                //System.out.println("d:"+gameView.monsters[0].yPos);
            }

            try{
                sleep(250);
            }
            catch(InterruptedException e) {
                System.out.println("Exception occured");
            }
        }
    }

    public void processInput() {
         if (gameView.digDug.getdirection() == "right") {
             if(gameView.shouldstand()) gameView.digDug.setdirection("stand");
             gameView.digDug.moveRight();
             gameView.gameMap[gameView.digDug.xPos+1][gameView.digDug.yPos-1] = true;
             gameView.gameMap[gameView.digDug.xPos+1][gameView.digDug.yPos] = true;
             gameView.gameMap[gameView.digDug.xPos+1][gameView.digDug.yPos+1] = true;

         }
        else if (gameView.digDug.getdirection() == "left") {
             if(gameView.shouldstand()) gameView.digDug.setdirection("stand");
             gameView.digDug.moveLeft();
             gameView.gameMap[gameView.digDug.xPos - 1][gameView.digDug.yPos - 1] = true;
             gameView.gameMap[gameView.digDug.xPos - 1][gameView.digDug.yPos] = true;
             gameView.gameMap[gameView.digDug.xPos - 1][gameView.digDug.yPos + 1] = true;
        }
        else if (gameView.digDug.getdirection() == "up") {
             if(gameView.shouldstand()) gameView.digDug.setdirection("stand");
             gameView.digDug.moveUp();
             gameView.gameMap[gameView.digDug.xPos - 1][gameView.digDug.yPos - 1] = true;
             gameView.gameMap[gameView.digDug.xPos][gameView.digDug.yPos - 1] = true;
             gameView.gameMap[gameView.digDug.xPos + 1][gameView.digDug.yPos - 1] = true;
        }
        else if (gameView.digDug.getdirection() == "down") {
             if(gameView.shouldstand()) gameView.digDug.setdirection("stand");
             gameView.digDug.moveDown();
             gameView.gameMap[gameView.digDug.xPos - 1][gameView.digDug.yPos + 1] = true;
             gameView.gameMap[gameView.digDug.xPos][gameView.digDug.yPos + 1] = true;
             gameView.gameMap[gameView.digDug.xPos + 1][gameView.digDug.yPos + 1] = true;
        }
        // }
        // if (attack) {
        //    digDug.attack();
         }

}

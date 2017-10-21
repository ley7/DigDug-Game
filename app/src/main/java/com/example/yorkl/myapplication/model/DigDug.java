package com.example.yorkl.myapplication.model;

import android.graphics.Canvas;

/**
 * Created by peizhaoo on 5/17/17.
 */
public class DigDug extends MovingGameObject {
    private boolean alive;
    private boolean attacking;
    public boolean attack_flag;

    public DigDug(){
        this.direction = "stand";
        this.alive = true;
        attack_flag = false;
    }
    public void setalive(boolean g){
        this.alive = g;
    }
    public void setdirection(String dir){
        this.direction = dir;
    }

    public String getdirection(){
        return this.direction;
    }

    public boolean isalive(){
        if(alive) return true;
        else return false;
    }
    public void moveLeft() {
        if(this.xPos-1==0){
            this.direction="stand";
        }
        else this.xPos -= 1;
    }

    public void moveRight() {
        if(this.xPos+1==59){
            this.direction="stand";
        }
        else this.xPos += 1;
    }

    public void moveUp() {

        if(this.yPos-1==0){
            this.direction="stand";
        }
        else this.yPos -= 1;
    }

    public void moveDown() {

        if(this.yPos+1==59){
            this.direction="stand";
        }
        else this.yPos += 1;

    }


    public void attack(Canvas canvas) {

    }

    public void stopAttack() {

    }
}

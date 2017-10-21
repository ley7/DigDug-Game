package com.example.yorkl.myapplication.model;

import com.example.yorkl.myapplication.view.GameView;

/**
 * Created by peizhaoo on 5/17/17.
 */
public class Rock extends MovingGameObject {
    private boolean alive;
    private boolean flag;
    public Rock(){
        this.alive = true;
        this.flag = false;
    }
    public boolean isalive(){
        if(alive) return true;
        else return false;
    }
    public boolean shouldFall(GameView g) {
        if(g.hasdown(this.xPos,this.yPos) && g.hasdown(this.xPos - 1,this.yPos) && g.hasdown(this.xPos + 1,this.yPos)){
            return true;
        }
        return false;
    }

    public void fall(GameView gameview) {
        if(shouldFall(gameview)) {
            if (shouldFall(gameview)) {
                this.yPos += 1;
                this.flag = true;
                System.out.println("rock fall");
            }
            for(int i=0;i<3;i++) {
                if ((this.xPos-2 <= gameview.monsters[i].xPos && gameview.monsters[i].xPos<=this.xPos+2) && (this.yPos+3>=gameview.monsters[i].yPos && this.yPos-1 <=gameview.monsters[i].yPos)){
                    gameview.monsters[i].flag = false;
                }
            }

            if ((this.xPos-2 <= gameview.firemonster.xPos && gameview.firemonster.xPos<=this.xPos+2) && (this.yPos+3>=gameview.firemonster.yPos && this.yPos-1 <=gameview.firemonster.yPos)){
                gameview.firemonster.flag = false;
            }

            if(this.flag && !shouldFall(gameview)) {
                System.out.println("set false");
                this.alive=false;
            }
           // this.alive=false;
        }
    }
}

package com.example.yorkl.myapplication.model;

import com.example.yorkl.myapplication.view.GameView;

/**
 * Created by peizhaoo on 5/17/17.
 */
public class FireMonster extends Monster {
    public boolean attack_flag;
    public int threshold = 60;
    public FireMonster(){
        this.alive = true;
        direction = "stand";
        this.speed = 0.5;
        this.flag=true;
        this.attack_flag=false;
        this.isghost=false;
        this.ghostcounter=0;
        this.health = 3;
    }
    public boolean isalive(){
        if(alive) return true;
        else return false;
    }

}

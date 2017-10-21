package com.example.yorkl.myapplication.model;
import com.example.yorkl.myapplication.model.DigDug;
import com.example.yorkl.myapplication.view.GameView;
import java.util.Calendar;
import java.lang.Math;
/**
 * Created by peizhaoo on 5/17/17.
 */
public class Monster extends MovingGameObject {
    public int ghostcounter;
    protected int health=3;
    protected boolean alive;
    public  boolean flag;
    public boolean isghost;
    public boolean enterwallflag;
    public int threshold = 60;
    public long starttime;
    public Monster(){
        this.alive = true;
        direction = "stand";
        this.speed = 0.5;
        this.flag=true;
        this.isghost=false;
        this.ghostcounter=0;

        Calendar c = Calendar.getInstance();
        this.starttime = c.get(Calendar.SECOND);
    }
    public int gethealth(){
        return this.health;
    }
    public void decreasehealth(){
        this.health--;
    }
    public void increasehealth(){
        this.health++;
    }
    public void setalive(boolean f){
        this.alive=f;
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

    public void movenormal(GameView game){
        Calendar c = Calendar.getInstance();
        long currenttime = c.get(Calendar.SECOND);
        if(this.starttime+1 <= currenttime && this.health<3){
           return;
       }
        int distanceX = this.xPos-game.digDug.xPos;
        int distanceY = this.yPos-game.digDug.yPos;
            //System.out.println("shouldchangedirection");
            if (game.shouldchangedirection(this)) {
                //System.out.println("shouldchangedirection");
                if (distanceX>0) {
                    //System.out.println("should move left");
                    if (game.hasleft(this.xPos, this.yPos)&&game.hasleft(this.xPos, this.yPos-1)&&game.hasleft(this.xPos, this.yPos+1)) {
                        //System.out.println("before move left:"+this.xPos);
                        this.moveLeft();
                        //System.out.println("after move left:"+this.xPos);
                        this.direction = "left";
                        return;
                    }
                }
                if(distanceY>0){
                    if(game.hasup(this.xPos, this.yPos)&&game.hasup(this.xPos-1, this.yPos)&&game.hasup(this.xPos+1, this.yPos)){
                        System.out.println("move up");
                        this.moveUp();
                        this.direction = "up";
                        return;
                    }
                }
                if(distanceX<0){
                    if(game.hasright(this.xPos, this.yPos)&&game.hasright(this.xPos, this.yPos-1)&&game.hasright(this.xPos, this.yPos+1)){
                        System.out.println("move right");
                        this.moveRight();
                        this.direction = "right";
                        return;
                    }
                }
                if(distanceY<0){
                    if(game.hasdown(this.xPos, this.yPos)&&game.hasdown(this.xPos-1, this.yPos)&&game.hasdown(this.xPos+1, this.yPos)){
                        System.out.println("move down");
                        this.moveDown();
                        this.direction = "down";
                        return;
                    }
                }
                if(game.hasleft(this.xPos,this.yPos)&&game.hasleft(this.xPos,this.yPos-1)&&game.hasleft(this.xPos,this.yPos+1)) {
                    this.moveLeft();
                    this.direction = "left";
                    return;
                }
                if(game.hasright(this.xPos,this.yPos)&&game.hasright(this.xPos,this.yPos-1)&&game.hasright(this.xPos,this.yPos+1)) {
                    this.moveRight();
                    this.direction = "right";
                    return;
                }
                if(game.hasup(this.xPos,this.yPos)&&game.hasup(this.xPos-1,this.yPos)&&game.hasup(this.xPos+1,this.yPos)) {
                    this.moveUp();
                    this.direction = "up";
                    return;
                }
                if(game.hasdown(this.xPos,this.yPos)&&game.hasdown(this.xPos-1,this.yPos)&&game.hasdown(this.xPos+1,this.yPos)) {
                    this.moveDown();
                    this.direction = "down";
                    return;
                }
            }
            else {
                if(this.getdirection() == "up") {
                    if(distanceX>0 && game.hasleft(this.xPos,this.yPos)&& game.hasleft(this.xPos,this.yPos-1)&& game.hasleft(this.xPos,this.yPos+1)){
                        this.moveLeft();
                        return;
                    }
                    else if(distanceX<0 && game.hasright(this.xPos,this.yPos)&& game.hasright(this.xPos,this.yPos-1)&& game.hasright(this.xPos,this.yPos+1)){
                        this.moveRight();
                        return;
                    }
                    this.moveUp();
                    return;
                }
                if(this.getdirection() == "down") {
                    if(distanceX>0 && game.hasleft(this.xPos,this.yPos)&& game.hasleft(this.xPos,this.yPos-1)&& game.hasleft(this.xPos,this.yPos+1)){
                        this.moveLeft();
                        return;
                    }
                    else if(distanceX<0 && game.hasright(this.xPos,this.yPos)&& game.hasright(this.xPos,this.yPos-1)&& game.hasright(this.xPos,this.yPos+1)){
                        this.moveRight();
                        return;
                    }
                    this.moveDown();
                    return;
                }
                if(this.getdirection() == "right"){
                    if(distanceY>0 && game.hasup(this.xPos,this.yPos)&& game.hasup(this.xPos-1,this.yPos)&& game.hasup(this.xPos+1,this.yPos)){
                        this.moveUp();
                        return;
                    }
                    else if(distanceY<0 && game.hasdown(this.xPos,this.yPos)&& game.hasdown(this.xPos-1,this.yPos)&& game.hasdown(this.xPos+1,this.yPos)){
                        this.moveDown();
                        return;
                    }
                    this.moveRight();
                    return;
                }
                if(this.getdirection() == "left"){
                    if(distanceY>0 && game.hasup(this.xPos,this.yPos)&& game.hasup(this.xPos-1,this.yPos)&& game.hasup(this.xPos+1,this.yPos)){
                        this.moveUp();
                        return;
                    }
                    else if(distanceY<0 && game.hasdown(this.xPos,this.yPos)&&game.hasdown(this.xPos+1,this.yPos)&&game.hasdown(this.xPos-1,this.yPos)){
                        this.moveDown();
                        return;
                    }
                    this.moveLeft();
                    return;
                }
            }

    }
    public boolean shouldFall(GameView g) {
        if(g.hasdown(this.xPos,this.yPos)){
            return true;
        }
        return false;
    }

    public void fall(GameView gameview) {
        if (shouldFall(gameview)) {
            if (shouldFall(gameview)) {
                this.yPos += 1;
                this.flag = true;
                System.out.println("rock fall");
            }
            if (this.flag && !shouldFall(gameview)) {
                System.out.println("set false");
                this.alive = false;
            }
        }
    }
    public void ghost(GameView game){
        int distanceX = this.xPos-game.digDug.xPos;
        int distanceY = this.yPos-game.digDug.yPos;
        //System.out.println("beforemove"+this.yPos);
        int compare = Math.abs(distanceX) - Math.abs(distanceY);
        if((game.gameMap[this.xPos][this.yPos] == true ||game.gameMap[this.xPos-1][this.yPos] == true||game.gameMap[this.xPos+1][this.yPos] == true)&& this.enterwallflag){
            this.enterwallflag=true;
            this.isghost=false;
            this.ghostcounter = 0;
        }
                    if(distanceX>=0 && compare>=0){
                        this.moveLeft();
                        return;
                    }
                    else if(distanceX>=0 && compare<0){
                        if(distanceY>=0){
                            //System.out.println("before"+this.yPos);
                            this.moveUp();
                            //System.out.println("after"+this.yPos);
                            return;
                        }
                        if(distanceY < 0){
                            this.moveDown();
                            return;
                        }
                    }
                    if(distanceX<0 && compare>=0){
                        this.moveRight();
                        return;
                    }
                    else if(distanceX<0 && compare < 0){
                        if(distanceY>=0){
                            this.moveUp();
                            return;
                        }
                        if(distanceY < 0){
                            this.moveDown();
                            return;
                        }
                    }

        //System.out.println("after"+this.yPos);
    }
    public void attack() {

    }
}

package com.example.yorkl.myapplication.controller;

import com.example.yorkl.myapplication.model.DigDug;
import com.example.yorkl.myapplication.model.GameMap;
import com.example.yorkl.myapplication.model.Monster;
import com.example.yorkl.myapplication.model.Rock;
import com.example.yorkl.myapplication.view.GameView;

/**
 * Created by peizhaoo on 5/17/17.
 */
public class GameController {
    private GameView gameView;
    private DigDug digDug;
    private Monster[] monsters;
    private Rock[] rocks;
    private GameMap map;

    private GameThread gameThread;

    public GameController() {
        //gameThread = new GameThread(this, gameView);
       // gameThread.start();
    }

    public void processInput(/** parameter */) {
        // if (moveRight) {
        //    digDug.moveRight();
        // }
        // if (attack) {
        //    digDug.attack();
        // }
    }

    public void update() {
        for (int i = 0; i < monsters.length; i++)
            monsters[i].attack();
        for (int i = 0; i < rocks.length; i++) {
       //     if (rocks[i].shouldFall())
        //        rocks[i].fall();
        }
    }
}

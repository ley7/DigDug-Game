package com.example.yorkl.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yorkl.myapplication.view.GameView;

public class MainActivity extends AppCompatActivity {
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClockView clockView = new ClockView(this);
        setContentView(clockView);
    }
    */
}

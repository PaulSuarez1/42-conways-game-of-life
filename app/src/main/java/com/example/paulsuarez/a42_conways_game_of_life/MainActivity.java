package com.example.paulsuarez.a42_conways_game_of_life;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (GameView) findViewById(R.id.game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.stop();
    }
}

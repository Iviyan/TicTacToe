package com.mc.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    View c11, c12, c13, c21, c22, c23, c31, c32, c33;
    View[] cells;
    TextView tvXScore, tvOScore, tvTieScore, tvStatus;
    Button btnRestart;

    TicTacToeGame game;

    int XScore = 0, OScore = 0, tieScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new TicTacToeGame(randInt(0, 1) == 0 ? Character.X : Character.O);

        c11 = findViewById(R.id.c11);
        c12 = findViewById(R.id.c12);
        c13 = findViewById(R.id.c13);
        c21 = findViewById(R.id.c21);
        c22 = findViewById(R.id.c22);
        c23 = findViewById(R.id.c23);
        c31 = findViewById(R.id.c31);
        c32 = findViewById(R.id.c32);
        c33 = findViewById(R.id.c33);

        cells = new View[] {c11, c12, c13, c21, c22, c23, c31, c32, c33};

        tvXScore = findViewById(R.id.tvXScore);
        tvOScore = findViewById(R.id.tvOScore);
        tvTieScore = findViewById(R.id.tvTieScore);
        tvStatus = findViewById(R.id.tvStatus);

        for (View view: cells) {
            view.setOnClickListener(this::onClick);
        }
        
        btnRestart = findViewById(R.id.btnRestart);

        btnRestart.setOnClickListener(view -> {
            game.reset(randInt(0, 1) == 0 ? Character.X : Character.O);
            setCellsEnable(true);
            for (View v : cells) {
                v.setBackgroundResource(0);
            }
            updateLabel(GameResult.None);
        });

        updateLabel(GameResult.None);
    }

    private void onClick(View view) {
        String[] tag = view.getTag().toString().split("_");
        int y = Integer.parseInt(tag[0]) - 1;
        int x = Integer.parseInt(tag[1]) - 1;

        setBackground(view, game.getCurrentCharacter());
        game.step(y, x);

        GameResult gameResult = game.isGameOver();
        updateLabel(gameResult);

        if (gameResult != GameResult.None) {
            setCellsEnable(false);
            
            switch (gameResult) {
                case Xwin: XScore++; tvXScore.setText(String.valueOf(XScore)); break;
                case Owin: OScore++; tvOScore.setText(String.valueOf(OScore)); break;
                case Tie: tieScore++; tvTieScore.setText(String.valueOf(tieScore)); break;
            }
        }
        
        
    }

    void setBackground(View view, Character character) {
        switch (character) {
            case X: view.setBackgroundResource(R.drawable.cross); break;
            case O: view.setBackgroundResource(R.drawable.ring); break;
        }
    }

    void updateLabel(GameResult result) {
        if (result != GameResult.None) {
            switch (result) {
                case Tie: tvStatus.setText("Ничья"); break;
                case Xwin: tvStatus.setText("Победил X"); break;
                case Owin: tvStatus.setText("Победил O"); break;
            }
        } else {
            switch (game.getCurrentCharacter()) {
                case X: tvStatus.setText("Ходит X"); break;
                case O: tvStatus.setText("Ходит O"); break;
            }
        }
    }

    void setCellsEnable(boolean b) {
        for (View v : cells)
            v.setEnabled(b);
    }

    public static int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
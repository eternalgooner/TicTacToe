package com.davidmackessy.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView onePlayer;
    private TextView twoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onePlayer = (TextView) findViewById(R.id.txt_one_player);
        addClickListener(onePlayer);
        twoPlayer = (TextView) findViewById(R.id.txt_two_player);
        addClickListener(twoPlayer);
    }

    private void addClickListener(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame(v);
            }
        });
    }

    private void startNewGame(View onClickListener) {
        if(((TextView)onClickListener).getText().equals("1 Player")) {
            startOnePlayerGame();
        }else if(((TextView)onClickListener).getText().equals("2 Player")){
            startTwoPlayerGame();
        }
    }

    private void startTwoPlayerGame() {
        Intent intent = new Intent(MainActivity.this, PlayGameActivity.class);
        intent.putExtra("gameType", 2);
        MainActivity.this.startActivity(intent);
    }

    private void startOnePlayerGame() {
        Intent intent = new Intent(MainActivity.this, PlayGameActivity.class);
        intent.putExtra("gameType", 1);
        MainActivity.this.startActivity(intent);
    }
}

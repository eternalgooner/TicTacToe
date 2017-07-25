package com.davidmackessy.tictactoe;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PlayGameActivity extends AppCompatActivity {

    private int gameType;
    private static final int ONE_PLAYER = 1;
    private static final int TWO_PLAYER = 2;
    private ImageView tile1;
    private ImageView tile2;
    private ImageView tile3;
    private ImageView tile4;
    private ImageView tile5;
    private ImageView tile6;
    private ImageView tile7;
    private ImageView tile8;
    private ImageView tile9;
    private Game game;
    private boolean isPlayerOneGo = true;
    private static final String TAG = PlayGameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "entering onCreate() in PlayGameActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        tile1 = (ImageView) findViewById(R.id.tile_1);
        addClickListener(tile1);
        tile2 = (ImageView) findViewById(R.id.tile_2);
        addClickListener(tile2);
        tile3 = (ImageView) findViewById(R.id.tile_3);
        addClickListener(tile3);
        tile4 = (ImageView) findViewById(R.id.tile_4);
        addClickListener(tile4);
        tile5 = (ImageView) findViewById(R.id.tile_5);
        addClickListener(tile5);
        tile6 = (ImageView) findViewById(R.id.tile_6);
        addClickListener(tile6);
        tile7 = (ImageView) findViewById(R.id.tile_7);
        addClickListener(tile7);
        tile8 = (ImageView) findViewById(R.id.tile_8);
        addClickListener(tile8);
        tile9 = (ImageView) findViewById(R.id.tile_9);
        addClickListener(tile9);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        gameType = getIntent().getIntExtra("gameType", 0);
        Toast.makeText(getApplicationContext(), "Game type is: " + gameType, Toast.LENGTH_SHORT).show();

        startGame(gameType);
    }

    private void startGame(int gameType) {
        Log.d(TAG, "entering startGame()");
         if(gameType == ONE_PLAYER){
             startOnePlayerGame();
         }else if(gameType == TWO_PLAYER){
             startTwoPlayerGame();
         }
    }

    private void startOnePlayerGame() {

    }

    private void startTwoPlayerGame() {
        Log.d(TAG, "entering startPlayerTwoGame() in PlayGameActivity");
        game = new Game(TWO_PLAYER);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private void addClickListener(ImageView tile) {
        tile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlayerOneGo && isTileSelectedAlready(v)){
                    game.playerOneGo(getTileChoice(v));
                    isPlayerOneGo = false;
                    setShapeOnTile(v);
                    checkForWinner();
                }else if(!isPlayerOneGo && isTileSelectedAlready(v)){
                    game.playerTwoGo(getTileChoice(v));
                    isPlayerOneGo = true;
                    setShapeOnTile(v);
                    checkForWinner();
                }
            }
        });
    }

    private boolean isTileSelectedAlready(View view) {
        int tileSelected = getTileChoice(view);
        Log.d(TAG, "checking is tile selected. Tile is: " + tileSelected);
        if(game.getGameTilesSelectedSet().contains(tileSelected)){
            Log.d(TAG, "tile already selected");
         return false;
        }
        Log.d(TAG, "tile not selected yet");
        return true;
    }

    private void checkForWinner() {
        Log.d(TAG, "checking for winner");
        String result = game.isThereAWinner();
        if(result.equals("Player 1") || result.equals("Player 2")){
            Toast.makeText(getApplicationContext(), "Winner is: " + result, Toast.LENGTH_SHORT).show();
        }
    }

    private int getTileChoice(View view) {
        ImageView userChoice = (ImageView)view;
        int tileId = userChoice.getId();
        switch (tileId){
            case R.id.tile_1:
                Log.d(TAG, "tile choice is 1");
                return 1;
            case R.id.tile_2:
                Log.d(TAG, "tile choice is 2");
                return 2;
            case R.id.tile_3:
                Log.d(TAG, "tile choice is 3");
                return 3;
            case R.id.tile_4:
                Log.d(TAG, "tile choice is 4");
                return 4;
            case R.id.tile_5:
                Log.d(TAG, "tile choice is 5");
                return 5;
            case R.id.tile_6:
                Log.d(TAG, "tile choice is 6");
                return 6;
            case R.id.tile_7:
                Log.d(TAG, "tile choice is 7");
                return 7;
            case R.id.tile_8:
                Log.d(TAG, "tile choice is 8");
                return 8;
            case R.id.tile_9:
                Log.d(TAG, "tile choice is 9");
                return 9;
        }
        return 0;
    }

    private void setShapeOnTile(View v) {
        Log.d(TAG, "setting shape on tile");
        v.setBackground(getNextShape());
    }

    private Drawable getNextShape(){
        if(game.getJustPlayed() == Game.JustPlayed.PLAYER_ONE.PLAYER_ONE){
            return getDrawable(R.drawable.o);
        }else{
            return getDrawable(R.drawable.x);
        }
    }
}

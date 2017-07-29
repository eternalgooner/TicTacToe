package com.davidmackessy.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PlayGameActivity extends AppCompatActivity{

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
    private CardView cv1;
    private CardView cv2;
    private CardView cv3;
    private CardView cv4;
    private CardView cv5;
    private CardView cv6;
    private CardView cv7;
    private CardView cv8;
    private CardView cv9;
    private Game game;
    private boolean isPlayerOneGo = true;
    private static final String TAG = PlayGameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "entering onCreate() in PlayGameActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        createTilesAndAddClickListener();
        assignCardViews();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //setupSharedPreferences();
        gameType = getIntent().getIntExtra("gameType", 0);
        //Toast.makeText(getApplicationContext(), "Game type is: " + gameType, Toast.LENGTH_SHORT).show();
       if(savedInstanceState == null){
           startGame(gameType);
       }else{
           game = (Game) savedInstanceState.getSerializable("gameState");
           isPlayerOneGo = savedInstanceState.getBoolean("isPlayerOneGo");
           redrawBoard();
       }
    }

    private void assignCardViews() {
        cv1 = (CardView) findViewById(R.id.cv1);
        cv2 = (CardView) findViewById(R.id.cv2);
        cv3 = (CardView) findViewById(R.id.cv3);
        cv4 = (CardView) findViewById(R.id.cv4);
        cv5 = (CardView) findViewById(R.id.cv5);
        cv6 = (CardView) findViewById(R.id.cv6);
        cv7 = (CardView) findViewById(R.id.cv7);
        cv8 = (CardView) findViewById(R.id.cv8);
        cv9 = (CardView) findViewById(R.id.cv9);
    }

    private void redrawBoard() {
        for(Integer tile : game.getPlayerOneTileSet()){
            Log.d(TAG, "in redrawBoard, tile no. is: " + tile);
            redrawPlayerOneTiles(tile);
            highLightWinningTiles();
        }

        for(Integer tile : game.getPlayerTwoTileSet()){
            Log.d(TAG, "in redrawBoard, tile no. is: " + tile);
            redrawPlayerTwoTiles(tile);
            highLightWinningTiles();
        }
    }

    private void redrawPlayerTwoTiles(Integer tile) {
        Log.d(TAG, "setting shape (player 2) on tile from restoreState");
        getCorrectPlayerTile(tile).setBackground(getXs());
    }

    private void redrawPlayerOneTiles(Integer tile) {
        Log.d(TAG, "setting shape (player 1) on tile from restoreState");
        getCorrectPlayerTile(tile).setBackground(getOs());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "in onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putSerializable("gameState", game);
        outState.putBoolean("isPlayerOneGo", isPlayerOneGo);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "in onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void createTilesAndAddClickListener() {
        Log.d(TAG, "entering createTilesAndAddClickListener to instantiate all tiles & add listeners");
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
        //TODO get intent extra which will be the computer level
        //from shared preferences
        //shared pref also dictates who goes first - player 1 or computer or alternate
        //computer level
        //stats show win % v computer
        Log.d(TAG, "entering startPlayerTwoGame() in PlayGameActivity");
        game = new Game(ONE_PLAYER);
    }

    private void startTwoPlayerGame() {
        Log.d(TAG, "entering startPlayerTwoGame() in PlayGameActivity");
        game = new Game(TWO_PLAYER);
    }

//    public boolean onOptionsItemSelected(MenuItem item){
//        int menuItem = item.getItemId();
//        if(menuItem == R.id.menu_pref){
//            showPreferencesScreen();
//            return true;
//        }
//        finish();
//        return super.onOptionsItemSelected(item);
//    }

    private void addClickListener(ImageView tile) {
        tile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlayerOneGo && isTileSelectedAlready(v)){
                    game.playerOneGo(getTileChoice(v));
                    isPlayerOneGo = false;
                    setShapeOnTile(v);
                    checkForWinner();
                    checkIfDraw();
                    if(gameType == 1 && !game.isGameOver()){
                        if(game.computerGo(getApplicationContext())){
                            isPlayerOneGo = true;
                            setShapeOnTile(getCorrectPlayerTile(game.getLastComputerChoice()));
                            checkForWinner();
                            checkIfDraw();
                        }
                    }

                }else if(!isPlayerOneGo && isTileSelectedAlready(v)){
                    game.playerTwoGo(getTileChoice(v));
                    isPlayerOneGo = true;
                    setShapeOnTile(v);
                    checkForWinner();
                    checkIfDraw();
                }
            }
        });
    }

    private void checkIfDraw() {
        Log.d(TAG, "in check if draw");
        if(game.getGameTilesSelectedSet().containsAll(Arrays.asList(1,2,3,4,5,6,7,8,9)) && !game.isGameOver()){
            Log.d(TAG, "draw conditions met, setting draw dialog");
            game.setGameOver(true);
            EndGameDialogFragment dialog = new EndGameDialogFragment();
            dialog.setCancelable(false);
            Bundle bundle = new Bundle();
            bundle.putString("result", "Draw game");
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "endGameDialog");
        }
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
        if(result.equals("Player 1") || result.equals("Player 2") || result.equals("Computer")){
            Log.d(TAG, "winner found - - : " + result);
            //Toast.makeText(getApplicationContext(), "Winner is: " + result, Toast.LENGTH_SHORT).show();
            EndGameDialogFragment dialog = new EndGameDialogFragment();
            dialog.setCancelable(false);
            Bundle bundle = new Bundle();
            if(game.isThereAWinner().equals("Player 1")){
                Log.d(TAG, "setting player 1 as winner in bundle for dialog");
                bundle.putString("result", "Player 1 wins!!");
            }else if(game.isThereAWinner().equals("Player 2")){
                Log.d(TAG, "setting player 2 as winner in bundle for dialog");
                bundle.putString("result", "Player 2 wins!!");
            }else if(game.isThereAWinner().equals("Computer")) {
                Log.d(TAG, "setting computer as winner in bundle for dialog");
                bundle.putString("result", "Computer wins!!");
            }
            highLightWinningTiles();
            Log.d(TAG, "show winner dialog");
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "endGameDialog");
        }else{
            Log.d(TAG, "no winner yet...");
        }
    }

    private void highLightWinningTiles() {
        Log.d(TAG, "entering highlightWinningTiles()");
        if(game.isThereAWinner().equals("Player 1")){
            Log.d(TAG, "highlight player 1 tiles");
            if(game.getPlayerOneTileSet().size() == 3){
                Log.d(TAG, "highlight player 1 tiles  = 3");
                for(int tile : game.getPlayerOneTileSet()){
                    setBackgroundColourOnTile(tile);
                }
            }else{
                highLightTilesIfMoreThan3(1);
            }
        }else if(game.isThereAWinner().equals("Player 2")){
            Log.d(TAG, "highlight player 2 tiles");
            if(game.getPlayerTwoTileSet().size() == 3){
                Log.d(TAG, "highlight player 2 tiles = 3");
                for(int tile : game.getPlayerTwoTileSet()){
                    setBackgroundColourOnTile(tile);
                }
            }else{
                highLightTilesIfMoreThan3(2);
            }
        }else if(game.isThereAWinner().equals("Computer")){
            Log.d(TAG, "highlight computer tiles");
            if(game.getPlayerTwoTileSet().size() == 3){
                Log.d(TAG, "highlight computer tiles = 3");
                for(int tile : game.getPlayerTwoTileSet()){
                    setBackgroundColourOnTile(tile);
                }
            }else{
                highLightTilesIfMoreThan3(2);
            }
        }
    }

    private void highLightTilesIfMoreThan3(int player) {
        Log.d(TAG, "entering highLightTilesIfMoreThan3()");
        Log.d(TAG, "size of sets to check through is: " +  game.getWinningCombinations().size());
        Set<Integer> playerSetToCheck;
        if(player == 1){
            playerSetToCheck = game.getPlayerOneTileSet();
        }else{
            playerSetToCheck = game.getPlayerTwoTileSet();
        }

        for(Set set : game.getWinningCombinations()){
            Log.d(TAG, "entering for loop going through winning combo sets");
            Iterator iterator = set.iterator();
            Log.d(TAG, "player tile set is: " + playerSetToCheck.toString());
            Log.d(TAG, "winnning combo tile set is: " + set.toString());
            if(playerSetToCheck.containsAll(set)){
                Log.d(TAG, "match found, player set contains winning combo in highlight method");
                while(iterator.hasNext()){
                    setBackgroundColourOnTile((int)iterator.next());
                }
                return;
            }
        }
//        if(game.getWinningCombinations()){
//
//        }
    }

    private void setBackgroundColourOnTile(int tile) {
        switch (tile){
            case 1:
                cv1.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                break;
            case 2:
                cv2.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                break;
            case 3:
                cv3.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                break;
            case 4:
                cv4.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                break;
            case 5:
                cv5.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                break;
            case 6:
                cv6.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                break;
            case 7:
                cv7.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                break;
            case 8:
                cv8.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                break;
            case 9:
                cv9.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
                break;
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
        v.setForeground(getNextShape());
    }

//    private void setShapeOnTile(int tile) {
//        Log.d(TAG, "setting shape on tile from restoreState");
//        getCorrectPlayerTile(tile).setBackground(getOs());
//    }

    private Drawable getOs() {
        return getDrawable(R.drawable._o);
    }

    private Drawable getXs() {
        return getDrawable(R.drawable._x);
    }

    private ImageView getCorrectPlayerTile(int tile) {
        switch (tile){
            case 1:
                return tile1;
            case 2:
                return tile2;
            case 3:
                return tile3;
            case 4:
                return tile4;
            case 5:
                return tile5;
            case 6:
                return tile6;
            case 7:
                return tile7;
            case 8:
                return tile8;
            case 9:
                return tile9;
            default:
                Log.d(TAG, "no match found, returning null");
                return null;
        }
    }

    private Drawable getNextShape(){
        if(game.getJustPlayed() == Game.JustPlayed.PLAYER_ONE.PLAYER_ONE){
            return getDrawable(R.drawable._o);
        }else{
            return getDrawable(R.drawable._x);
        }
    }

//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        if(key.equals("computerLevel")){
//            String computerLevel = sharedPreferences.getString(key, "rock hard");
//            //if showing computer level then show here
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_preferences, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int menuItem = item.getItemId();
//        if(menuItem == R.id.menu_pref){
//            showPreferencesScreen();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    private void showPreferencesScreen(){
//        Context context = PlayGameActivity.this;
//        Intent intent = new Intent(context, PreferencesActivity.class);
//        startActivity(intent);
//    }

//    private void setupSharedPreferences() {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//        String computerLevel = sp.getString("computerLevel", "rock hard");
//        //if showing computer level then show here
//
//        sp.registerOnSharedPreferenceChangeListener(this);
//    }
}

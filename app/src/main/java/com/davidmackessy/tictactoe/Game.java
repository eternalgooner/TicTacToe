package com.davidmackessy.tictactoe;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by David on 7/25/2017.
 */

public class Game {

    private Set<Integer> gameTilesSelectedSet;
    //private Set<Integer> fullTileSet = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    private Set<Set<Integer>> winningCombinations;
    private Set<Integer> playerOneTileSet;
    private Set<Integer> playerTwoTileSet;
    private String playerOne = "Player 1";
    private String playerTwo = "Player 2";
    protected enum JustPlayed {PLAYER_ONE, PLAYER_TWO, COMPUTER}
    private JustPlayed justPlayed;
    private boolean isOnePlayerGame;
    private String winner;
    private static final String TAG = Game.class.getSimpleName();

    public Game(int gameType) {
        gameTilesSelectedSet = new HashSet<Integer>();
        winningCombinations = new HashSet<>();
        initWinningCombinations();
        if(gameType == 1){
            isOnePlayerGame = true;
        }

        playerOneTileSet = new HashSet<>();
        playerTwoTileSet = new HashSet<>();
    }

    private void initWinningCombinations() {
        Set<Integer> winningCombination1 = new HashSet<Integer>(Arrays.asList(1,2,3));
        Set<Integer> winningCombination2 = new HashSet<Integer>(Arrays.asList(4,5,6));
        Set<Integer> winningCombination3 = new HashSet<Integer>(Arrays.asList(7,8,9));
        Set<Integer> winningCombination4 = new HashSet<Integer>(Arrays.asList(1,4,7));
        Set<Integer> winningCombination5 = new HashSet<Integer>(Arrays.asList(2,5,8));
        Set<Integer> winningCombination6 = new HashSet<Integer>(Arrays.asList(3,6,9));
        Set<Integer> winningCombination7 = new HashSet<Integer>(Arrays.asList(3,5,7));
        Set<Integer> winningCombination8 = new HashSet<Integer>(Arrays.asList(1,5,9));
        winningCombinations.add(winningCombination1);
        winningCombinations.add(winningCombination2);
        winningCombinations.add(winningCombination3);
        winningCombinations.add(winningCombination4);
        winningCombinations.add(winningCombination5);
        winningCombinations.add(winningCombination6);
        winningCombinations.add(winningCombination7);
        winningCombinations.add(winningCombination8);
    }

    public boolean playerOneGo(int tileChoice){
        justPlayed = JustPlayed.PLAYER_ONE;
        if(playerOneTileSet.contains(tileChoice)){
            Log.d(TAG, "number: " + tileChoice + "already in set, not added. Set contains:" + playerOneTileSet.toString());
            return false;
        }else if(!gameTilesSelectedSet.contains(tileChoice)){
            playerOneTileSet.add(tileChoice);
            gameTilesSelectedSet.add(tileChoice);
            Log.d(TAG, "number: " + tileChoice + " not in set, added now. Set now contains: " + playerOneTileSet.toString());
            checkIfPlayerWins(1);
            return true;
        }
        return false;
    }

    public boolean playerTwoGo(int tileChoice){
        justPlayed = JustPlayed.PLAYER_TWO;
        if(playerTwoTileSet.contains(tileChoice)){
            Log.d(TAG, "number: " + tileChoice + "already in set, not added. Set contains:" + playerTwoTileSet.toString());
            return false;
        }else if(!gameTilesSelectedSet.contains(tileChoice)){
            playerTwoTileSet.add(tileChoice);
            gameTilesSelectedSet.add(tileChoice);
            Log.d(TAG, "number: " + tileChoice + " not in set, added now. Set now contains: " + playerTwoTileSet.toString());
            checkIfPlayerWins(2);
            return true;
        }
        return false;
    }

    private void checkIfPlayerWins(int player) {
        if(player == 1){
            Log.d(TAG, "checking if player 1 won. Player 1 set is: " + playerOneTileSet.toString());
            if(winningCombinations.contains(playerOneTileSet)){
                winner = playerOne;
                Log.d(TAG, "Player set match!! - winner is playerOne");
            }else if(playerOneTileSet.size() > 3){
                if(isWinningCombintationInPlayerSet()){
                    winner = playerOne;
                    Log.d(TAG, "Player set match!! - winner is playerOne");
                }
            }else{
                Log.d(TAG, "no match found");
            }
        }else if(player == 2){
            Log.d(TAG, "checking if player 2 won. Player 2 set is: " + playerTwoTileSet.toString());
            if(winningCombinations.contains(playerTwoTileSet)){
                winner = playerTwo;
                Log.d(TAG, "Player set match!! - winner is playerTwo");
            }else if(playerTwoTileSet.size() > 3){
                if(isWinningCombintationInPlayerSet()){
                    winner = playerTwo;
                    Log.d(TAG, "Player set match!! - winner is playerTwo");
                }else{
                Log.d(TAG, "no match found");

                }
            }
        }
    }

    private boolean isWinningCombintationInPlayerSet() {
        for(Set winningCombination : winningCombinations){
            if(playerOneTileSet.contains(winningCombination)){
                return true;
            }
        }
        return false;
    }

    public String isThereAWinner(){
        if(winner != null){
            return winner;
        }
        return "no winner yet";
    }

    public Set<Integer> getGameTilesSelectedSet() {
        return gameTilesSelectedSet;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public JustPlayed getJustPlayed() {
        return justPlayed;
    }

    public boolean isOnePlayerGame() {
        return isOnePlayerGame;
    }
}

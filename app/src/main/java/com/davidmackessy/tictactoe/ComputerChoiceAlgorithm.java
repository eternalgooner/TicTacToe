package com.davidmackessy.tictactoe;

import android.util.Log;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Created by David on 29-Jul-17.
 */

public class ComputerChoiceAlgorithm {
    private static final String TAG = ComputerChoiceAlgorithm.class.getSimpleName();
    private static final int MEDIUM = 2;
    private static final int HARD = 3;

    public static int getComputerChoiceOnEasyLevel(Set<Integer> numbersLeft){
        Integer[] tilesLeft = numbersLeft.toArray(new Integer[numbersLeft.size()]);
        Random random = new Random();
        int randomTile = random.nextInt(tilesLeft.length);
        return tilesLeft[randomTile];
    }

    public static int getComputerChoiceOnMediumLevel(Set<Integer> numbersLeft, Set<Integer> opponentsTiles, Set<Set<Integer>> gameWinningCombos, int level){
        Log.d(TAG, "in getComputerChoiceOnMediumLevel()");
        Integer[] tilesLeft = numbersLeft.toArray(new Integer[numbersLeft.size()]);

        //if player tiles is less than 3
        if(opponentsTiles.size() < 3){
            Log.d(TAG, "opponents has chosen less than 3 tiles");
            for(Set<Integer> comboSet : gameWinningCombos){
                if(comboSet.containsAll(opponentsTiles)){
                    Log.d(TAG, "opponents tiles have been found in a winning combo. Winning combo is: " + comboSet.toString() + " and opponents tiles are " + opponentsTiles.toString());
                    int returningInt = getUniqueNumberFromSetWhenOpponentHasLessThan3Tiles(comboSet, opponentsTiles, numbersLeft, level);
                    Log.d(TAG, "returning: " + returningInt);
                    return returningInt;
                }
            }
        }else{
            Log.d(TAG, "opponents has chosen 3 or more tiles");
            int choice = randomTileFromWhatsLeft(numbersLeft, null, level);
            Log.d(TAG, "returning random tile from what's left: " + choice);
            return choice;
        }
        Log.e(TAG, "SHOULDN'T HAVE REACHED THIS STATEMENT - RETURNING 1");
        return 1;
    }

    public static int getComputerChoiceOnHardLevel(Set<Integer> numbersLeft, Set<Integer> opponentsTiles, Set<Integer> myTiles, Set<Set<Integer>> gameWinningCombos, int level){
        Log.d(TAG, "in getComputerChoiceOnHardLevel() that takes 5 params");
        Integer[] tilesLeft = numbersLeft.toArray(new Integer[numbersLeft.size()]);

        //if player tiles is less than 3
        if(opponentsTiles.size() < 3){
            Log.d(TAG, "opponents has chosen less than 3 tiles");
            for(Set<Integer> comboSet : gameWinningCombos){
                if(comboSet.containsAll(opponentsTiles)){
                    Log.d(TAG, "opponents tiles have been found in a winning combo. Winning combo is: " + comboSet.toString() + " and opponents tiles are " + opponentsTiles.toString());
                    int returningInt = getUniqueNumberFromSetWhenOpponentHasLessThan3Tiles(comboSet, opponentsTiles, numbersLeft, level);
                    Log.d(TAG, "returning: " + returningInt);
                    return returningInt;
                }
            }
        }else{
            Log.d(TAG, "opponents has chosen 3 or more tiles");
            int choice = randomTileFromWhatsLeft(numbersLeft, level, myTiles, gameWinningCombos);
            Log.d(TAG, "returning random tile from what's left: " + choice);
            return choice;
        }
        Log.e(TAG, "SHOULDN'T HAVE REACHED THIS STATEMENT - RETURNING 1");
        return 1;
    }

    public static int getComputerChoiceOnHardLevel(Game game, int level){
        Log.d(TAG, "in getComputerChoiceOnHardLevel() that takes Game param");
        Integer[] tilesLeft = game.getGameTilesLeft().toArray(new Integer[game.getGameTilesLeft().size()]);

        //if player tiles is less than 3
        if(game.getPlayerOneTileSet().size() < 3){
            Log.d(TAG, "opponents has chosen less than 3 tiles");
            for(Set<Integer> comboSet : game.getWinningCombinations()){
                if(comboSet.containsAll(game.getPlayerOneTileSet())){
                    Log.d(TAG, "opponents tiles have been found in a winning combo. Winning combo is: " + comboSet.toString() + " and opponents tiles are " + game.getPlayerOneTileSet().toString());
                    int returningInt = getUniqueNumberFromSetWhenOpponentHasLessThan3Tiles(game, comboSet, level);
                    Log.d(TAG, "returning: " + returningInt);
                    return returningInt;
                }
                int choice = randomTileFromWhatsLeft(game.getGameTilesLeft(), game, 3);
                Log.d(TAG, "opponent has < 3 tiles chosen, and no winning combo match, will return random: " + choice);
                return choice;
            }
        }else{
            Log.d(TAG, "opponents has chosen 3 or more tiles");
            int choice = randomTileFromWhatsLeft(game.getGameTilesLeft(), level, game.getPlayerTwoTileSet(), game.getWinningCombinations());
            Log.d(TAG, "returning random tile from what's left: " + choice);
            return choice;
        }
        Log.e(TAG, "SHOULDN'T HAVE REACHED THIS STATEMENT - RETURNING 1");
        return 1;
    }

    private static int getUniqueNumberFromSetWhenOpponentHasLessThan3Tiles(Set<Integer> comboSet, Set<Integer> opponentsTiles, Set<Integer> numbersLeft, int level) {
        Integer[] comboTiles = comboSet.toArray(new Integer[comboSet.size()]);
        Integer[] opponentTiles = opponentsTiles.toArray(new Integer[opponentsTiles.size()]);

        Arrays.sort(comboTiles);
        Arrays.sort(opponentTiles);
        for(int i = 0; i < opponentsTiles.size(); ++i){
            if(opponentTiles[i] != comboTiles[i]){
                if(tileHasNotBeenChosenYet(comboTiles[i], numbersLeft)){
                    return comboTiles[i];
                }
            }else {
                continue;
            }
        }
        if(tileHasNotBeenChosenYet(comboTiles[2], numbersLeft)){
            Log.d(TAG, "tile hasn't been chosen & returning element [2] from winning combo");
            return comboTiles[2];
        }
        Log.e(TAG, "returning random tile from tiles left, as only 1 combo matches & 2 choices made already");
        return randomTileFromWhatsLeft(numbersLeft, null, level);
    }

    private static int getUniqueNumberFromSetWhenOpponentHasLessThan3Tiles(Game game, Set<Integer> comboSet, int level) {
        Integer[] comboTiles = comboSet.toArray(new Integer[comboSet.size()]);
        Integer[] opponentTiles = game.getPlayerOneTileSet().toArray(new Integer[game.getPlayerOneTileSet().size()]);

        Arrays.sort(comboTiles);
        Arrays.sort(opponentTiles);
        for(int i = 0; i < game.getPlayerOneTileSet().size(); ++i){
            if(opponentTiles[i] != comboTiles[i]){
                if(tileHasNotBeenChosenYet(comboTiles[i], game.getGameTilesLeft())){
                    return comboTiles[i];
                }
            }else {
                continue;
            }
        }
        if(tileHasNotBeenChosenYet(comboTiles[2], game.getGameTilesLeft())){
            Log.d(TAG, "tile hasn't been chosen & returning element [2] from winning combo");
            return comboTiles[2];
        }
        Log.e(TAG, "returning random tile from tiles left, as only 1 combo matches & 2 choices made already");
        return randomTileFromWhatsLeft(game.getGameTilesLeft(), game, level);
    }

    private static int randomTileFromWhatsLeft(Set<Integer> numbersLeft, Game game, int level) {
        if(level == MEDIUM){
            Integer[] tilesLeft = numbersLeft.toArray(new Integer[numbersLeft.size()]);
            Random random = new Random();
            int choice = random.nextInt(tilesLeft.length);
            Log.d(TAG, "for medium level - returning random tile from what's left: " + tilesLeft[choice]);
            return tilesLeft[choice];
        }else if(level == HARD){
            Log.e(TAG, "SHOULDN'T BE IN HERE!! - CHECK CODE & FIX, HARD LEVEL HAS IT'S OWN METHOD");
            //int choice = ifCanWinSelectTile(game.getPlayerTwoTileSet(), game.getWinningCombinations(), numbersLeft);
            int choice = randomTileFromWhatsLeft(game.getGameTilesLeft(), 3, game.getPlayerTwoTileSet(), game.getWinningCombinations());
            Log.d(TAG, "for hard level - returning tile if can win: " + choice);
            return choice;
        }
        Log.e(TAG, "SHOULDN'T HAVE REACH THIS STATEMENT - ERROR WHEN RETURNING RANDOM INT FROM WHAT'S LEFT - RETURN 0");
        return 0;
    }

    private static int randomTileFromWhatsLeft(Set<Integer> numbersLeft, int level, Set<Integer> myTiles, Set<Set<Integer>> winningCombos) {
        int choice = ifCanWinSelectTile(myTiles, winningCombos, numbersLeft);
        Log.d(TAG, "for hard level - returning tile if can win: " + choice);
        return choice;
    }

    private static int ifCanWinSelectTile(Set<Integer> myTiles, Set<Set<Integer>> winningCombos, Set<Integer> numbersLeft) {
        int winningTile;
        for(Set<Integer> winningCombo : winningCombos){
            if(winningCombo.containsAll(myTiles)){
                Log.d(TAG, "winning combo contains my tiles so will get winning tile number");
                winningTile = getWinningTile(winningCombo, myTiles, numbersLeft);
                Log.d(TAG, "winning tile number is: " + winningTile);
                if(tileHasNotBeenChosenYet(winningTile, numbersLeft)){
                    Log.d(TAG, "winning tile has not been chosen yet, so will return");
                    return winningTile;
                }
               continue;
            }
        }
        int choice = randomTileFromWhatsLeft(numbersLeft, null, 2);
        Log.d(TAG, "on hard level, couldn't find winning tile so returning random from left: " + choice);
        return choice;
    }

    private static int getWinningTile(Set<Integer> comboSet, Set<Integer> myTiles, Set<Integer> numbersLeft) {
        Integer[] comboTiles = comboSet.toArray(new Integer[comboSet.size()]);
        Integer[] compTiles = myTiles.toArray(new Integer[myTiles.size()]);

        Arrays.sort(comboTiles);
        Arrays.sort(compTiles);
        for(int i = 0; i < myTiles.size(); ++i){
            if(compTiles[i] != comboTiles[i]){
                if(tileHasNotBeenChosenYet(comboTiles[i], numbersLeft)){
                    return comboTiles[i];
                }
            }else {
                continue;
            }
        }
        if(tileHasNotBeenChosenYet(comboTiles[2], numbersLeft)){
            Log.d(TAG, "tile hasn't been chosen & returning element [2] from winning combo");
            return comboTiles[2];
        }
        Log.e(TAG, "returning random tile from tiles left, as only 1 combo matches & 2 choices made already");
        return randomTileFromWhatsLeft(numbersLeft, null, 2);
    }

    private static boolean tileHasNotBeenChosenYet(Integer comboTile, Set<Integer> numbersLeft) {
        Log.d(TAG, "checking if tile has been chosen yet");
        if(numbersLeft.contains(comboTile)){
            Log.d(TAG, "tile: " + comboTile + " has not been chosen yet");
            return true;
        }
        Log.d(TAG, "tile HAS been chosen already, returning false");
        return false;
    }
}

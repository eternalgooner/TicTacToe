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

    public static int getComputerChoiceOnEasyLevel(Set<Integer> numbersLeft){
        Integer[] tilesLeft = numbersLeft.toArray(new Integer[numbersLeft.size()]);
        Random random = new Random();
        int randomTile = random.nextInt(tilesLeft.length);
        return tilesLeft[randomTile];
    }

    public static int getComputerChoiceOnMediumLevel(Set<Integer> numbersLeft, Set<Integer> opponentsTiles, Set<Set<Integer>> gameWinningCombos){
        Log.d(TAG, "in getComputerChoiceOnMediumLevel()");
        Integer[] tilesLeft = numbersLeft.toArray(new Integer[numbersLeft.size()]);

        //if player tiles is less than 3
        if(opponentsTiles.size() < 3){
            Log.d(TAG, "opponents has chosen less than 3 tiles");
            for(Set<Integer> comboSet : gameWinningCombos){
                if(comboSet.containsAll(opponentsTiles)){
                    Log.d(TAG, "opponents tiles have been found in a winning combo. Winning combo is: " + comboSet.toString() + " and opponents tiles are " + opponentsTiles.toString());
                    int returningInt = getUniqueNumberFromSetWhenOpponentHasLessThan3Tiles(comboSet, opponentsTiles, numbersLeft);
                    Log.d(TAG, "returning: " + returningInt);
                    return returningInt;
                }
            }
        }else{
            Log.d(TAG, "opponents has chosen 3 or more tiles");
            int choice = randomTileFromWhatsLeft(numbersLeft);
            Log.d(TAG, "returning random tile from what's left: " + choice);
            return choice;
        }
        Log.e(TAG, "SHOULDN'T HAVE REACHED THIS STATEMENT - RETURNING 1");
        return 1;
    }




    private static int getUniqueNumberFromSetWhenOpponentHasLessThan3Tiles(Set<Integer> comboSet, Set<Integer> opponentsTiles, Set<Integer> numbersLeft) {
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
        return randomTileFromWhatsLeft(numbersLeft);
    }

    private static int randomTileFromWhatsLeft(Set<Integer> numbersLeft) {
        Integer[] tilesLeft = numbersLeft.toArray(new Integer[numbersLeft.size()]);
        Random random = new Random();
        int choice = random.nextInt(tilesLeft.length);
        Log.d(TAG, "returning random tile from what's left: " + tilesLeft[choice]);
        return tilesLeft[choice];
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

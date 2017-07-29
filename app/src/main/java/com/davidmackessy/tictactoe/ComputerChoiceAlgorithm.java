package com.davidmackessy.tictactoe;

import java.util.Random;
import java.util.Set;

/**
 * Created by David on 29-Jul-17.
 */

public class ComputerChoiceAlgorithm {

    public static int getComputerChoiceOnEasyLevel(Set<Integer> numbersLeft){
        Integer[] tilesLeft = numbersLeft.toArray(new Integer[numbersLeft.size()]);
        Random random = new Random();
        int randomTile = random.nextInt(tilesLeft.length);
        return tilesLeft[randomTile];
    }

    public static int getComputerChoiceOnMediumLevel(Set<Integer> numbersLeft, Set<Integer> opponentsTiles, Set<Integer> gameWinningCombos){
        Integer[] tilesLeft = numbersLeft.toArray(new Integer[numbersLeft.size()]);
        Random random = new Random();
        int randomTile = random.nextInt(tilesLeft.length);
        return tilesLeft[randomTile];
    }
}

package se329.clue.util;

import java.util.ArrayList;

/**
 * Created by davisbatten on 4/14/16.
 */
public class GameState {

    public static final int NO_INFO = 0;
    public static final int YES = 1;
    public static final int MAYBE = 2;
    public static final int NO = -1;

    private int[][] cards = new int[6][21];
    int[] myCards = new int[21];

    public GameState(){
        //TODO
    }

    public void markCard (int cardId, int playerId, int value) {
        cards[playerId][cardId] = value;
    }

}

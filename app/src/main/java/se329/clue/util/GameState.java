package se329.clue.util;

import android.os.Parcel;
import android.os.Parcelable;

import android.util.Log;
import java.util.ArrayList;

/**
 * Created by davisbatten on 4/14/16.
 */
public class GameState {

    public static final int NO_INFO = 0;
    public static final int YES = 1;
    public static final int MAYBE = 2;
    public static final int NO = -1;

    private int[][] cards;
    ArrayList<Integer> order;   //order of players around the table
    int suspectAnswer;
    int weaponAnswer;
    int roomAnswer;
    int player;

    public GameState(int[] order, int player){
        //initialize fields
        cards = new int[6][21];
        this.order = new ArrayList<Integer>();
        suspectAnswer = -1;
        weaponAnswer = -1;
        roomAnswer = -1;
        //set player
        this.player = player;
        //set order
        for(int i=0; i< 6; i++){
            Log.d("order_i", "" + order[i]);
            this.order.add(order[i]);
        }
    }

    /**
     * Set player's cards
     * @param myCards array of cardIds that player has
     */
    public void setMyCards(ArrayList<Integer> myCards){
        //initialize all cards for player to NO
        for(int i = 0; i < 21; i++){
            markCard(i, player, GameState.NO);
        }
        //set cards to yes if player has them
        for (int card : myCards) {
            markCard(card, player, GameState.YES);
        }
    }

    public void markCard (int cardId, int playerId, int value) {
        cards[playerId][cardId] = value;
    }

    public void updateState(int predictedCards[], int predictor, int disprover, int seenCardType){
        //update predictor's cards
        for(int i = 0; i < 3; i++){
            //only mark maybe if we don't know anything about that card for that player
            if(cards[predictor][predictedCards[i]] == GameState.NO_INFO){
                markCard(predictedCards[i], predictor, GameState.MAYBE);
            }
        }
        //get the first player who can disprove
        int currentPlayer = order.get(order.indexOf(predictor) + 1);
        //mark No for every player who couldn't disprove.
        while(currentPlayer != disprover){
            for(int j = 0; j < 3; j++){
                markCard(predictedCards[j], currentPlayer, GameState.NO);
            }
        }
        //update disprover's cards
        int maybeCount = 0;
        for(int k = 0; k < 3; k++){
            if(cards[disprover][predictedCards[k]] == GameState.NO_INFO){
                markCard(predictedCards[k], disprover, GameState.MAYBE);
                maybeCount++;
            }
        }
        if (maybeCount == 1) {
            //set only maybe to yes
        }
        //update seen card
        if(seenCardType != -1){
            markCard(predictedCards[seenCardType], disprover, GameState.YES);
        }
        updateGameState();
    }

    //update game state to reflect changes (clean data)
    private void updateGameState(){

        for(int i = 0; i < 21; i++){
            boolean inEnvelope = true;
            int maybeId = -1;
            int maybeCount = 0;
            boolean canUpdateMaybe = true;
            for(int j = 0; j < 6; j++){
                if(cards[j][i] != GameState.NO){
                    inEnvelope = false;
                    canUpdateMaybe = false;
                }
                if(cards[j][i] == GameState.MAYBE){
                    maybeId = j;
                    maybeCount++;
                }
            }
            //if all no, card is in envelope
            if(inEnvelope){
                if(i < 6) suspectAnswer = i;
                else if(i < 12) weaponAnswer = i;
                else roomAnswer = i;
            }
            //if all no, but 1 maybe, change maybe to yes
            if(canUpdateMaybe && maybeCount == 1){
                markCard(i, maybeId, GameState.YES);
            }
        }

    }

    //get score for a card, which determines what cards to predict
    private int scoreCard(int cardId){
        int numMaybe = 0;
        int numNoInfo = 0;
        for(int i = 0; i < 6; i++){
            int value = cards[i][cardId];
            if(value == GameState.MAYBE) numMaybe++;
            else if(value == GameState.NO_INFO) numNoInfo++;
        }
        return 2 * numNoInfo + numMaybe ;
    }

    //make a prediction based on best way to find info
    public int[] getPrediction(){
        int i = 0;
        int maxScore = 0;
        int suspectId = 0;
        int weaponId = 6;
        int roomId = 12;
        //get suspect
        while(i<6){
            int score = scoreCard(i);
            if(score > maxScore){
                maxScore = score;
                suspectId = i;
            }
            i++;
        }
        maxScore = 0;
        //get weapon
        while(i<12){
            int score = scoreCard(i);
            if(score > maxScore){
                maxScore = score;
                weaponId = i;
            }
            i++;
        }
        maxScore = 0;
        //get room
        while(i<21){
            int score = scoreCard(i);
            if(score > maxScore){
                maxScore = score;
                roomId = i;
            }
            i++;
        }
        //set prediction
        int[] prediction = new int[3];
        prediction[0] = suspectId;
        prediction[1] = weaponId;
        prediction[2] = roomId;
        return  prediction;
    }

    public ArrayList<Integer> getOrder(){
        return order;
    }

}

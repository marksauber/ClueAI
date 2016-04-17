package se329.clue;

import android.app.Application;

import se329.clue.util.GameState;

/**
 * Created by davisbatten on 4/17/16.
 */
public class MyApp extends Application {

    private GameState gameState;

    public GameState getGameState(){
        return gameState;
    }

    public void setGameState(GameState gs){
        gameState = gs;
    }
}

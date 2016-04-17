package se329.clue;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import se329.clue.R;
import se329.clue.util.GameState;

public class NewGameActivity extends AppCompatActivity {

  int[] order = new int[] {0,1,2,3,4,5};
  int player = 0;
  MyApp appState;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_game);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


    appState = (MyApp) getApplication();

    //TODO set order and player based upon input

    final Button setOrderButton = (Button) findViewById(R.id.orderbutton);
    setOrderButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        //save gamestate
        saveGameState();
        //goto MyCardsActivity
        Intent intent = new Intent(NewGameActivity.this, MyCardsActivity.class);
        NewGameActivity.this.startActivity(intent);
      }
    });










  }

  protected void saveGameState(){
    GameState gameState = new GameState(order, player);
    appState.setGameState(gameState);
  }

}

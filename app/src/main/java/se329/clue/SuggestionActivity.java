package se329.clue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import android.util.Log;

import java.util.Arrays;

import se329.clue.util.CardUtil;
import se329.clue.util.GameState;

public class SuggestionActivity extends AppCompatActivity {

    MyApp appState;
    GameState gameState;
    Spinner suspectSpinner;
    Spinner weaponSpinner;
    Spinner roomSpinner;
    Spinner playerDisprovedSpinner;
    Spinner playerPredictedSpinner;
    Spinner cardKindSpinner;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_turn);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    //Maybe move to Setup method
    suspectSpinner = (Spinner)findViewById(R.id.suspects);
    weaponSpinner = (Spinner)findViewById(R.id.weapons);
    roomSpinner = (Spinner)findViewById(R.id.rooms);
      playerPredictedSpinner = (Spinner) findViewById(R.id.player_turn);
    playerDisprovedSpinner = (Spinner)findViewById(R.id.player_disproved);
    cardKindSpinner = (Spinner)findViewById(R.id.card_kind);
    CardUtil util = new CardUtil(getApplicationContext());
    ArrayAdapter<String> suspect_adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, util.getSuspects());
    ArrayAdapter<String> weapon_adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, util.getWeapons());
    ArrayAdapter<String>room_adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, util.getRooms());
    suspectSpinner.setAdapter(suspect_adapter);
    weaponSpinner.setAdapter(weapon_adapter);
    roomSpinner.setAdapter(room_adapter);
    String[] players = {"None","Scarlet", "Green", "White", "Peacock", "Plum", "Mustard"};
    String[] kindOfCard = {"None","Suspect","Weapon", "Room"};
    ArrayAdapter<String> player_adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, players);
      playerPredictedSpinner.setAdapter(player_adapter);
    playerDisprovedSpinner.setAdapter(player_adapter);
    ArrayAdapter<String> card_adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, kindOfCard);
    cardKindSpinner.setAdapter(card_adapter);

      //setup continue button
      final Button saveButton = (Button) findViewById(R.id.saveButton);
      saveButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              saveTurn();
              Intent intent = new Intent(SuggestionActivity.this, AssistantActivity.class);
              SuggestionActivity.this.startActivity(intent);
          }
      });

      appState = (MyApp) getApplication();
      gameState = appState.getGameState();



  }

    public void saveTurn(){
        int[] prediction = new int[3];
        prediction[0] = suspectSpinner.getSelectedItemPosition();
        prediction[1] = weaponSpinner.getSelectedItemPosition() + 6;
        prediction[2] = roomSpinner.getSelectedItemPosition() + 12;

        Log.d("prediction", Arrays.toString(prediction));

        int predictor = playerPredictedSpinner.getSelectedItemPosition() - 1;

        int disprover = playerDisprovedSpinner.getSelectedItemPosition() - 1;
        int seenCardType = cardKindSpinner.getSelectedItemPosition() - 1;

        Log.d("disprover", "" + disprover);
        Log.d("seenCard", "" + seenCardType);



        gameState.updateState(prediction, predictor, disprover, seenCardType);
        appState.setGameState(gameState);
    }

}

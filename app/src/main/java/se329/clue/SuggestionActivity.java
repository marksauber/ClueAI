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

import se329.clue.util.CardUtil;

public class SuggestionActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_turn);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    //Maybe move to Setup method
    Spinner suspectSpinner = (Spinner)findViewById(R.id.suspects);
    Spinner weaponSpinner = (Spinner)findViewById(R.id.weapons);
    Spinner roomSpinner = (Spinner)findViewById(R.id.rooms);
    Spinner playerDisprovedSpinner = (Spinner)findViewById(R.id.player_disproved);
    Spinner cardKindSpinner = (Spinner)findViewById(R.id.card_kind);
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
    String[] players = {"None","Red","Blue","Purple","Yellow","Green","Black"};
    String[] kindOfCard = {"None","Suspect","Weapon", "Room"};
    ArrayAdapter<String> player_adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, players);
    playerDisprovedSpinner.setAdapter(player_adapter);
    ArrayAdapter<String> card_adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, kindOfCard);
    cardKindSpinner.setAdapter(card_adapter);

      //setup continue button
      final Button saveButton = (Button) findViewById(R.id.saveButton);
      saveButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              Intent intent = new Intent(SuggestionActivity.this, AssistantActivity.class);
              SuggestionActivity.this.startActivity(intent);
          }
      });

  }

}

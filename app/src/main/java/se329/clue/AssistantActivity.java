package se329.clue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import se329.clue.util.CardUtil;
import se329.clue.util.GameState;

public class AssistantActivity extends AppCompatActivity {

    MyApp appState;
    GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appState = (MyApp) getApplication();
        gameState = appState.getGameState();
        Log.d("state", gameState.getGrid());
        Log.d("pred", Arrays.toString(gameState.getPrediction()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssistantActivity.this, SuggestionActivity.class);
                AssistantActivity.this.startActivity(intent);
            }
        });

        CardUtil cardUtil = new CardUtil(getApplicationContext());
        int[] prediction = gameState.getPrediction();
        ArrayList<String> suspects = cardUtil.getSuspects();
        ArrayList<String> weapons = cardUtil.getWeapons();
        ArrayList<String> rooms = cardUtil.getRooms();
        Log.d("suspect", suspects.get(prediction[0]));
        Log.d("weapon", weapons.get(prediction[1] - 6));
        Log.d("room", rooms.get(prediction[2] - 12));

        TextView suspectText = (TextView) findViewById(R.id.predictedSuspect);
        suspectText.setText(suspects.get(prediction[0]));

        TextView weaponText = (TextView) findViewById(R.id.predictedWeapon);
        weaponText.setText(weapons.get(prediction[1] - 6));

        TextView roomText = (TextView) findViewById(R.id.predictedRoom);
        roomText.setText(rooms.get(prediction[2] - 12));




    }

}

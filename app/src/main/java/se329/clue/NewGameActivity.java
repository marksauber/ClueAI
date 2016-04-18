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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.graphics.Typeface;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import se329.clue.R;
import se329.clue.util.CardUtil;
import se329.clue.util.GameState;

public class NewGameActivity extends AppCompatActivity {

  int[] order = new int[] {0,1,2,3,4,5};
  int player = 0;
  MyApp appState;
  private ArrayList<TextView> options;
  private ArrayList<TextView> choices;
  Spinner myPlayerSpinner;
  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_game);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


    appState = (MyApp) getApplication();

    //TODO set order and player based upon input


    myPlayerSpinner = (Spinner)findViewById(R.id.myPlayerSpinner);
    String[] players = {"Miss Scarlet", "Mr. Green", "Mrs. White", "Mrs. Peacock", "Prof. Plum", "Col. Mustard"};
    ArrayAdapter<String> player_adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, players);
    myPlayerSpinner.setAdapter(player_adapter);
    options = new ArrayList<TextView>();
    choices = new ArrayList<TextView>();
    //add option text views
    addOptions();
    //add choices text views
    addChoices();

    //set touch listeners
    setTouchListeners();
    //set drag listeners
    setDragListeners();
    final Button setOrderButton = (Button) findViewById(R.id.orderbutton);
    setOrderButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        //modify order array
        getNewOrder();
        //get player selected
        player = myPlayerSpinner.getSelectedItemPosition();
        //save gamestate
        saveGameState();
        //goto MyCardsActivity
        Intent intent = new Intent(NewGameActivity.this, MyCardsActivity.class);
        NewGameActivity.this.startActivity(intent);
      }
    });



  }
  private void addOptions(){
    options.add((TextView)findViewById(R.id.option_1));
    options.add((TextView)findViewById(R.id.option_2));
    options.add((TextView)findViewById(R.id.option_3));
    options.add((TextView)findViewById(R.id.option_4));
    options.add((TextView)findViewById(R.id.option_5));
    options.add((TextView)findViewById(R.id.option_6));
  }
  private void addChoices(){
    choices.add((TextView)findViewById(R.id.choice_1));
    choices.add((TextView)findViewById(R.id.choice_2));
    choices.add((TextView)findViewById(R.id.choice_3));
    choices.add((TextView)findViewById(R.id.choice_4));
    choices.add((TextView)findViewById(R.id.choice_5));
    choices.add((TextView)findViewById(R.id.choice_6));
  }
  private void setTouchListeners(){
    for(TextView option:options){
      option.setOnTouchListener(new ChoiceTouchListener());
    }
  }
  private void setDragListeners(){
    for(TextView choice:choices){
      choice.setOnDragListener(new ChoiceDragListener());
    }
  }
  //Gets the order based on position of the panels
  //Must call CardUtil to get the ids for each one of the players
  private void getNewOrder(){
    CardUtil util = new CardUtil(getApplicationContext());
    HashMap<String,Integer> cardToId = util.getCardToId();
    int i = 0;
    for(TextView choice:choices){
      order[i]=cardToId.get(choice.getText());
      i++;
    }
  }
  protected void saveGameState(){
    GameState gameState = new GameState(order, player);
    appState.setGameState(gameState);
  }

  private final class ChoiceTouchListener implements OnTouchListener {
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        //setup drag
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        //start dragging the item touched
        view.startDrag(data, shadowBuilder, view, 0);
        return true;
      }
      else {
        return false;
      }
    }
  }
  private class ChoiceDragListener implements OnDragListener {
    @Override
    public boolean onDrag(View v, DragEvent event) {
      //handle drag events
      switch (event.getAction()) {
        case DragEvent.ACTION_DRAG_STARTED:
          //no action necessary
          break;
        case DragEvent.ACTION_DRAG_ENTERED:
          //no action necessary
          break;
        case DragEvent.ACTION_DRAG_EXITED:
          //no action necessary
          break;
        case DragEvent.ACTION_DROP:

          //handle the dragged view being dropped over a drop view
          View view = (View) event.getLocalState();
          //stop displaying the view where it was before it was dragged
          view.setVisibility(View.INVISIBLE);
          //view dragged item is being dropped on
          TextView dropTarget = (TextView) v;
          //view being dragged and dropped
          TextView dropped = (TextView) view;
          //update the text in the target view to reflect the data being dropped
          dropTarget.setText(dropped.getText());
          //make it bold to highlight the fact that an item has been dropped
          dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
          //if an item has already been dropped here, there will be a tag
          Object tag = dropTarget.getTag();
          //if there is already an item here, set it back visible in its original place
          if(tag!=null)
          {
            //the tag is the view id already dropped here
            int existingID = (Integer)tag;
            //set the original view visible again
            findViewById(existingID).setVisibility(View.VISIBLE);
          }
          //set the tag in the target view to the ID of the view being dropped
          dropTarget.setTag(dropped.getId());
          break;
        case DragEvent.ACTION_DRAG_ENDED:
          //no action necessary

          break;
        default:
          break;
      }
      return true;
    }
  }
}

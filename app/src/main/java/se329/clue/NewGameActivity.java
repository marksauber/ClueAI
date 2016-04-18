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
import se329.clue.R;
import se329.clue.util.GameState;

public class NewGameActivity extends AppCompatActivity {

  int[] order = new int[] {0,1,2,3,4,5};
  int player = 0;
  MyApp appState;
  private TextView option1, option2, option3,option4, option5, option6, choice1, choice2, choice3,choice4, choice5, choice6;
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
    Spinner myPlayerSpinner = (Spinner)findViewById(R.id.myPlayerSpinner);
    String[] players = {"Miss Scarlet", "Mr. Green", "Mrs. White", "Mrs. Peacock", "Prof. Plum", "Col. Mustard"};
    ArrayAdapter<String> player_adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, players);
    myPlayerSpinner.setAdapter(player_adapter);
//views to drag
    option1 = (TextView)findViewById(R.id.option_1);
    option2 = (TextView)findViewById(R.id.option_2);
    option3 = (TextView)findViewById(R.id.option_3);
    option4 = (TextView)findViewById(R.id.option_4);
    option5 = (TextView)findViewById(R.id.option_5);
    option6 = (TextView)findViewById(R.id.option_6);

//views to drop onto
    choice1 = (TextView)findViewById(R.id.choice_1);
    choice2 = (TextView)findViewById(R.id.choice_2);
    choice3 = (TextView)findViewById(R.id.choice_3);
    choice4 = (TextView)findViewById(R.id.choice_4);
    choice5 = (TextView)findViewById(R.id.choice_5);
    choice6 = (TextView)findViewById(R.id.choice_6);
//set touch listeners
    option1.setOnTouchListener(new ChoiceTouchListener());
    option2.setOnTouchListener(new ChoiceTouchListener());
    option3.setOnTouchListener(new ChoiceTouchListener());
    option4.setOnTouchListener(new ChoiceTouchListener());
    option5.setOnTouchListener(new ChoiceTouchListener());
    option6.setOnTouchListener(new ChoiceTouchListener());
//set drag listeners
    choice1.setOnDragListener(new ChoiceDragListener());
    choice2.setOnDragListener(new ChoiceDragListener());
    choice3.setOnDragListener(new ChoiceDragListener());
    choice4.setOnDragListener(new ChoiceDragListener());
    choice5.setOnDragListener(new ChoiceDragListener());
    choice6.setOnDragListener(new ChoiceDragListener());




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

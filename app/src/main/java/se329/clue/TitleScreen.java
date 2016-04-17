package se329.clue;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class TitleScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_title_screen);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    final Button notepadButton = (Button) findViewById(R.id.button);
    notepadButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(TitleScreen.this, NotepadActivity.class);
        TitleScreen.this.startActivity(intent);
      }
    });

    final Button assistantButton = (Button) findViewById(R.id.button2);
    assistantButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(TitleScreen.this, NewGameActivity.class);
        TitleScreen.this.startActivity(intent);
      }
    });

  }

}

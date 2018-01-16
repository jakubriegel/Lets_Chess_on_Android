package com.jr.chess;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent. ACTION_PICK_ACTIVITY);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "test");
        sendIntent.setType("text/plain");


        Button startGameButton = findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startGameActivity = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(startGameActivity);
            }
        });

    }
}

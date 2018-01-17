package com.jr.chess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jr.chess.UtilityFragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements SettingsFragment.ISettingsFragment{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout utilityFame = findViewById(R.id.utility_fragment_frame);
        utilityFame.bringToFront();

        Button startGameButton = findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startGameActivity = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(startGameActivity);
            }
        });

        Button settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().add(
                        R.id.utility_fragment_frame, new SettingsFragment()).commit();
            }
        });

    }

    @Override
    public void closeSettings() {
        //getSupportFragmentManager().beginTransaction().remove(R)
    }
}

package com.jr.chess;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jr.chess.views.Board;

public class GameActivity extends AppCompatActivity{

    Game game;

    Board board;
    TextView activeColorText;
    TextView winnerText;
    TextView stateText;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.goFullscreen();

        game = new Game(this);
        game.start();

        board = findViewById(R.id.board_view);
        board.redraw(game.pieces, game.movePointers, game.attackPointers);

        activeColorText = findViewById(R.id.active_color_text);
        winnerText = findViewById(R.id.winner_text);
        stateText = findViewById(R.id.state_text);


        board.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Position p = board.getSquare(new Position((int) event.getX(), (int) event.getY()));
                game.processTouch(event, p);

                board.redraw(game.pieces, game.movePointers, game.attackPointers);
                updateInfo();

                return true;
            }
        });
    }

    private void goFullscreen(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        int newUiOptions = getWindow().getDecorView().getSystemUiVisibility();
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    private void updateInfo(){
        if(game.activeColor == Const.WHITE) activeColorText.setText("Player: WHITE");
        else activeColorText.setText("Player: BLACK");

        if(game.state == Const.STATE_END){
            if(game.winner == Const.WHITE) winnerText.setText("Winner: WHITE");
            else winnerText.setText("Winner: BLACK");
        }

        stateText.setText(game.state + "");
    }

}

package com.jr.chess;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jr.chess.views.Board;

public class GameActivity extends AppCompatActivity implements PromotionFragment.IPromotionFragment{

    Game game;

    Board board;
    TextView activeColorText;
    TextView winnerText;
    FrameLayout fragmentFrame;

    PromotionFragment promotionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setting up the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.goFullscreen();

        fragmentFrame = findViewById(R.id.fragment_frame);
        fragmentFrame.bringToFront();
        fragmentFrame.setVisibility(View.GONE);

        // setting up the game
        game = new Game(this, this);
        game.start();

        board = findViewById(R.id.board_view);
        board.redraw(game.pieces, game.movePointers, game.attackPointers);

        activeColorText = findViewById(R.id.active_color_text);
        winnerText = findViewById(R.id.winner_text);

        board.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Position p = board.getSquare(new Position((int) event.getX(), (int) event.getY()));
                game.processTouch(event, p);

                redrawBoard();
                updateInfo();

                return true;
            }
        });

        promotionFragment = new PromotionFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_frame, promotionFragment).commit();

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

    void redrawBoard(){
        board.redraw(game.pieces, game.movePointers, game.attackPointers);
    }

    void updateInfo(){
        if(game.activeColor == Const.WHITE) activeColorText.setText("Player: WHITE");
        else activeColorText.setText("Player: BLACK");

        if(game.state == Const.STATE_END){
            if(game.winner == Const.WHITE) winnerText.setText("Winner: WHITE");
            else winnerText.setText("Winner: BLACK");
        }
    }

    public void openPromotionFragment(int color, Position position){
        fragmentFrame.bringToFront();
        fragmentFrame.setVisibility(View.VISIBLE);
        promotionFragment = (PromotionFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_frame);

    }

    public void closePromotionFragment(int type){
        game.promotionAddPiece(type);
        fragmentFrame.setVisibility(View.GONE);
        Log.v(Const.DEBUG_TAG, "gameActivity, closePromotionFragment - done");
    }

}

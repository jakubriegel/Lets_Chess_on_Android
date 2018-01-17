package com.jr.chess;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jr.chess.Views.Board;

public class GameActivity extends AppCompatActivity implements PromotionFragment.IPromotionFragment{

    private Game game;

    private Board board;
    private TextView activeColorText;
    private TextView winnerText;
    private FrameLayout fragmentFrame;

    private int displayMode;

    private PromotionFragment promotionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setting up the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fragmentFrame = findViewById(R.id.promotion_fragment_frame);
        fragmentFrame.bringToFront();
        fragmentFrame.setVisibility(View.GONE);

        // setting up the game
        game = new Game(this, this);
        game.start();
        displayMode = Const.CLASSIC_MODE;
        board = findViewById(R.id.board_view);
        board.redraw(game.pieces, game.movePointers, game.attackPointers, displayMode);
        findViewById(R.id.board_layout).bringToFront();

        activeColorText = findViewById(R.id.active_color_text);
        winnerText = findViewById(R.id.winner_text);

        board.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Position p = board.getSquare(new Position((int) event.getX(), (int) event.getY()));
                game.processTouch(event, p);

                redraw();
                updateInfo();

                return true;
            }
        });

    }

    void redraw(){
        board.redraw(game.pieces, game.movePointers, game.attackPointers, displayMode);
    }

    private void updateInfo(){
        if(game.activeColor == Const.WHITE) activeColorText.setText("Player: WHITE");
        else activeColorText.setText("Player: BLACK");

        if(game.state == Const.STATE_END){
            if(game.winner == Const.WHITE) winnerText.setText("Winner: WHITE");
            else winnerText.setText("Winner: BLACK");
        }
    }

    public void openPromotionFragment(int color){
        fragmentFrame.bringToFront();
        if (color == Const.BLACK) fragmentFrame.setRotation(180);
        if (game.activeColor == Const.WHITE) fragmentFrame.setRotation(0);
        fragmentFrame.setVisibility(View.VISIBLE);
        promotionFragment = new PromotionFragment();
        Bundle activeColor = new Bundle();
        activeColor.putInt("color", color);
        promotionFragment.setArguments(activeColor);
        getSupportFragmentManager().beginTransaction().add(R.id.promotion_fragment_frame, promotionFragment).commit();

    }

    public void closePromotionFragment(int type){
        game.promotionAddPiece(type);
        getSupportFragmentManager().beginTransaction().remove(promotionFragment).commit();
        fragmentFrame.setVisibility(View.GONE);
        game.state = Const.STATE_SELECT;
    }

}

package com.jr.chess;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jr.chess.Pieces.Piece;
import com.jr.chess.Views.Board;

import java.util.Objects;

public class GameActivity extends AppCompatActivity implements GameManagement {

    private Game game;

    private Board board;
    private FrameLayout fragmentFrame;
    private PlayerPadFragment whitePad;
    private PlayerPadFragment blackPad;
    private GameEndFragment gameEndFragment;

    private int displayMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setting up the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fragmentFrame = findViewById(R.id.game_fragment_frame);
        fragmentFrame.bringToFront();
        fragmentFrame.setVisibility(View.GONE);

        // setting up the game
        resetGame();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String displayStr = preferences.getString(getResources().getString(R.string.display_key), "");
        if (Objects.equals(displayStr, getResources().getString(R.string.classic_mode))) displayMode = Const.CLASSIC_MODE;
        else  displayMode = Const.MODERN_MODE;

        // setting up board
        board = findViewById(R.id.board_view);
        board.redraw(game.pieces, game.movePointers, game.attackPointers, displayMode);
        findViewById(R.id.board_layout).bringToFront();

        board.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Position p = board.getSquare(new Position((int) event.getX(), (int) event.getY()));
                game.processTouch(event, p);

                redrawBoard();

                return true;
            }
        });

        // players pads
        LinearLayout whitePadLayout = findViewById(R.id.white_pad);
        FrameLayout blackPadFrame = findViewById(R.id.black_pad_frame);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        LinearLayout.LayoutParams padParams = new LinearLayout.LayoutParams(
                (int)(.9 * displayMetrics.widthPixels), (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics()));
        whitePadLayout.setLayoutParams(padParams);
        blackPadFrame.setLayoutParams(padParams);

        whitePad = (PlayerPadFragment) getSupportFragmentManager().findFragmentById(R.id.white_pad);
        blackPad = (PlayerPadFragment) getSupportFragmentManager().findFragmentById(R.id.black_pad);

        whitePad.capturedPad.setDisplayMode(displayMode);
        blackPad.capturedPad.setDisplayMode(displayMode);

        Button menuButton = findViewById(R.id.game_menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (game.state){
                    case Const.STATE_END:
                        fragmentFrame.setVisibility(View.VISIBLE);
                        break;
                    case Const.STATE_MOVE_ATTACK:
                    case Const.STATE_SELECT:
                        openGameMenu();
                        break;
                    case Const.STATE_PAUSE:
                        break;
                }
            }
        });
    }

    public void endOfTheGame(int winner){
        gameEndFragment = new GameEndFragment();
        Bundle winnerCode = new Bundle();
        winnerCode.putInt("winner", winner);
        gameEndFragment.setArguments(winnerCode);
        getSupportFragmentManager().beginTransaction().add(R.id.game_fragment_frame, gameEndFragment).commit();
        fragmentFrame.bringToFront();
        fragmentFrame.setVisibility(View.VISIBLE);
    }

    void redrawBoard(){
        board.redraw(game.pieces, game.movePointers, game.attackPointers, displayMode);
    }

    public void updatePads(Piece capturedPiece){
        switch (capturedPiece.color){
            case Const.WHITE:
                blackPad.capturedPad.addPiece(capturedPiece);
                break;
            case Const.BLACK:
                whitePad.capturedPad.addPiece(capturedPiece);
                break;
        }
    }

    void openGameMenu(){
        game.pause();
        GameMenuFragment gameMenuFragment = new GameMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.game_fragment_frame, gameMenuFragment).commit();
        fragmentFrame.bringToFront();
        fragmentFrame.setVisibility(View.VISIBLE);

    }

    public void openPromotionFragment(int color){
        fragmentFrame.bringToFront();
        if (color == Const.BLACK) fragmentFrame.setRotation(180);
        if (game.activeColor == Const.WHITE) fragmentFrame.setRotation(0);
        PromotionFragment promotionFragment = new PromotionFragment();
        Bundle activeColor = new Bundle();
        activeColor.putInt("color", color);
        promotionFragment.setArguments(activeColor);
        getSupportFragmentManager().beginTransaction().add(R.id.game_fragment_frame, promotionFragment).commit();
        fragmentFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed(); in order not to close activity immediately
        leaveGame();
    }

    // GameManagement interface

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void resetGame(){
        Piece.resetAll();
        game = new Game(this, this);
        game.start();
        if(fragmentFrame.getVisibility() == View.VISIBLE) {
            redrawBoard();
            getSupportFragmentManager().beginTransaction().remove(gameEndFragment).commit();
            fragmentFrame.setVisibility(View.GONE);
        }
    }

    @Override
    public void leaveGame(){
        if (game.state == Const.STATE_PAUSE) return;
        game.pause();
        fragmentFrame.bringToFront();
        fragmentFrame.setRotation(0);
        getSupportFragmentManager().beginTransaction().add(R.id.game_fragment_frame, new GameLeaveFragment()).commit();
        fragmentFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeFragment(Fragment fragmentToClose) {
        getSupportFragmentManager().beginTransaction().remove(fragmentToClose).commit();
        fragmentFrame.setRotation(0);
        fragmentFrame.setVisibility(View.GONE);
        game.unpause();
    }

    @Override
    public void endGame() {
        finish();
    }

    @Override
    public void showBoard() {
        fragmentFrame.setVisibility(View.GONE);
    }

    @Override // TODO: implement board sharing
    public void shareBoard(){
        Toast.makeText(getApplicationContext(), "Available soon...", Toast.LENGTH_SHORT).show();
    }
}

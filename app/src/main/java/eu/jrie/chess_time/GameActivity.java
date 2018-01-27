package eu.jrie.chess_time;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import eu.jrie.chess_time.Pieces.Piece;
import eu.jrie.chess_time.Views.Board;

import java.util.Objects;

public class GameActivity extends AppCompatActivity implements GameManagement {

    private Game game;

    private int drawState;

    private Board board;
    private FrameLayout fragmentFrame;
    private SparseArray<PlayerPadFragment> pads;
    private GameEndFragment gameEndFragment;

    private int displayMode;

    @SuppressLint("ClickableViewAccessibility") // necessary for onTouch event listener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setting up the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // preparing spot for fragments
        fragmentFrame = findViewById(R.id.game_fragment_frame);
        fragmentFrame.bringToFront();
        fragmentFrame.setVisibility(View.GONE);

        // getting settings
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String displayStr = preferences.getString(getResources().getString(R.string.display_key), "");
        if (Objects.equals(displayStr, getResources().getString(R.string.classic_mode))) displayMode = Const.CLASSIC_MODE;
        else  displayMode = Const.MODERN_MODE;

        // players pads
        RelativeLayout whitePadLayout = findViewById(R.id.white_pad);
        FrameLayout blackPadFrame = findViewById(R.id.black_pad_frame); // instead of linLay, because of rotation
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        LinearLayout.LayoutParams padParams = new LinearLayout.LayoutParams(
                (int)(.9 * displayMetrics.widthPixels), (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 80, displayMetrics));
        whitePadLayout.setLayoutParams(padParams);
        blackPadFrame.setLayoutParams(padParams);

        pads = new SparseArray<>();
        pads.put(Const.WHITE, (PlayerPadFragment) getSupportFragmentManager().findFragmentById(R.id.white_pad));
        pads.get(Const.WHITE).setColor(Const.WHITE);
        pads.put(Const.BLACK, (PlayerPadFragment) getSupportFragmentManager().findFragmentById(R.id.black_pad));
        pads.get(Const.BLACK).setColor(Const.BLACK);

        pads.get(Const.WHITE).capturedPad.setDisplayMode(displayMode);
        pads.get(Const.BLACK).capturedPad.setDisplayMode(displayMode);

        // setting up the game
        resetGame();

        drawState = Const.NO_DRAW;

        // setting up board
        board = findViewById(R.id.board_view);
        board.redraw(game.pieces, game.movePointers, game.attackPointers, displayMode);
        findViewById(R.id.board_layout).bringToFront();


        board.setOnTouchListener((view, event) -> {
            Position p = board.getSquare(new Position((int) event.getX(), (int) event.getY()));
            game.processTouch(event, p);

            redrawBoard();

            return true;
        });

        // menu button
        Button menuButton = findViewById(R.id.game_menu_button);
        menuButton.setOnClickListener(view -> {
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
                pads.get(Const.BLACK).capturedPad.addPiece(capturedPiece);
                break;
            case Const.BLACK:
                pads.get(Const.WHITE).capturedPad.addPiece(capturedPiece);
                break;
        }
    }

    private void openGameMenu(){
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
        drawState = Const.NO_DRAW;
        pads.get(Const.WHITE).reset();
        pads.get(Const.BLACK).reset();
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

    @Override
    public void manageDraw(int color) {
        switch (drawState){
            case Const.NO_DRAW:
                drawState = color;
                pads.get(color).setBackground(R.drawable.draw_icon_accepted);
                pads.get(GameManagement.switchColor(color)).setBackground(R.drawable.draw_icon_ready);

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                View toastLayout = getLayoutInflater().inflate(R.layout.toast_draw, findViewById(R.id.toast_layout));
                int pxY = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics());
                if(color == Const.WHITE){
                    toastLayout.setRotation(180);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, pxY);
                } else toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, pxY);
                toast.setView(toastLayout);
                toast.show();

                break;
            case Const.WHITE:
            case Const.BLACK:
                if(color == drawState){
                    drawState = Const.NO_DRAW;
                    pads.get(Const.WHITE).setBackground(R.drawable.draw_icon);
                    pads.get(Const.BLACK).setBackground(R.drawable.draw_icon);
                }
                else{
                    pads.get(color).setBackground(R.drawable.draw_icon_accepted);
                    game.end(Const.DRAW);
                }
                break;
        }
    }

    @Override
    public void proceedSurrendering(int color) {
        game.end(GameManagement.switchColor(color));
    }
}

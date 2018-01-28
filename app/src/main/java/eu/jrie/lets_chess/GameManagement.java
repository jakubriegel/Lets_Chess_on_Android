package eu.jrie.lets_chess;

import android.support.v4.app.Fragment;

interface GameManagement {
    Game getGame();

    void resetGame();
    void leaveGame();
    void endGame();

    void closeFragment(Fragment fragmentToClose);

    void showBoard();
    void shareBoard();

    void manageDraw(final int color);
    void proceedSurrendering(final int color);

    static int switchColor(int c){
        if(c == Const.WHITE) return Const.BLACK;
        return Const.WHITE;
    }

}

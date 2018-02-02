package eu.jrie.lets_chess;

import android.support.v4.app.Fragment;

interface GameManagement {
    Game getGame();

    void initGame(int beginningTime, int addingTime);
    void resetGame();
    void leaveGame();
    void endGame();
    void pauseGame();
    void unpauseGame();

    void closeFragment(Fragment fragmentToClose);

    void showBoard();
    void shareBoard();

    void manageDraw(final byte color);
    void proceedFailure(final byte color);

    static byte switchColor(final byte c){
        if(c == Const.WHITE) return Const.BLACK;
        return Const.WHITE;
    }

}

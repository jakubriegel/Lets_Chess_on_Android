package com.jr.chess;

import android.support.v4.app.Fragment;

interface GameManagement {
    Game getGame();

    void resetGame();
    void leaveGame();
    void endGame();

    void closeFragment(Fragment fragmentToClose);

    void showBoard();
    void shareBoard();


}

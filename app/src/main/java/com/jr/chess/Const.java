package com.jr.chess;

public interface Const {
    int WHITE = 1;
    int BLACK = 2;

    int PAWN = 101;
    int BISHOP = 102;
    int KNIGHT = 103;
    int ROOK = 104;
    int QUEEN = 105;
    int KING = 106;

    int STATE_SELECT = 201;
    int STATE_MOVE_ATTACK = 202;
    int STATE_END = 203;
    int STATE_PAUSE = 204;

    int CLASSIC_MODE = 301;
    int MODERN_MODE = 302;


    String DEBUG_TAG = "debug_jr";

}

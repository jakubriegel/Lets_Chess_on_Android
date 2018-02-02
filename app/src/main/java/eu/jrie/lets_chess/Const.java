package eu.jrie.lets_chess;

public interface Const {
    byte WHITE = 1;
    byte BLACK = 2;
    byte DRAW = 3;

    byte PAWN = 11;
    byte BISHOP = 12;
    byte KNIGHT = 13;
    byte ROOK = 14;
    byte QUEEN = 15;
    byte KING = 16;

    byte STATE_SELECT = 21;
    byte STATE_MOVE_ATTACK = 22;
    byte STATE_END = 23;
    byte STATE_PAUSE = 24;

    byte CLASSIC_MODE = 31;
    byte MODERN_MODE = 32;

    byte NO_DRAW = 41;

    String DEBUG_TAG = "debug_jr";

}

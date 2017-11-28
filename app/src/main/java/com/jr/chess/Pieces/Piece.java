package com.jr.chess.Pieces;

import com.jr.chess.Const;
import com.jr.chess.Position;

public abstract class Piece{

    private static int whitePawns = 0;
    private static int blackPawns = 0;
    private static int whiteBishops = 0;
    private static int blackBishops = 0;
    private static int whiteKnights = 0;
    private static int blackKnights = 0;
    private static int whiteRooks = 0;
    private static int blackRooks = 0;


    public boolean alive;
    public Position position;
    public int color;
    public int type;


    public abstract int test();

    public Piece(int _color, int _type){
        alive = true;
        color = _color;
        type = _type;

        switch (color){
            case Const.WHITE:

                switch (type){
                    case Const.PAWN:
                        position = new Position(whitePawns, 1);
                        whitePawns++;
                        break;
                    case Const.BISHOP:
                        whiteBishops++;
                        break;
                    case Const.KNIGHT:
                        whiteKnights++;
                        break;
                    case Const.ROOK:
                        whiteRooks++;
                        break;
                    case Const.QUEEN:
                        break;
                    case Const.KING:
                        break;
                }

                break;
            case Const.BLACK:

                switch (type) {
                    case Const.PAWN:
                        position = new Position(blackPawns, 6);
                        blackPawns++;
                        break;
                    case Const.BISHOP:
                        blackBishops++;
                        break;
                    case Const.KNIGHT:
                        blackKnights++;
                        break;
                    case Const.ROOK:
                        blackRooks++;
                        break;
                    case Const.QUEEN:
                        break;
                    case Const.KING:
                        break;

                }

                break;

        }
    }
}


package com.jr.chess;

public class Piece{

    private static int whitePawns = 0;
    private static int blackPawns = 0;

    public boolean alive;
    public Position position;
    public int color;
    public int type;

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
                        break;
                    case Const.KNIGHT:
                        break;
                    case Const.ROOK:
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
                }

                break;

        }
    }
}


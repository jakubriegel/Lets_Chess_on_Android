package com.jr.chess.Pieces;

import android.content.Context;

import com.jr.chess.Const;
import com.jr.chess.Position;

import java.util.List;

public abstract class Piece{

    static int whitePawns = 0;
    static int blackPawns = 0;
    static int whiteBishops = 0;
    static int blackBishops = 0;
    static int whiteKnights = 0;
    static int blackKnights = 0;
    static int whiteRooks = 0;
    static int blackRooks = 0;

    public int type;
    public boolean alive;
    public Position position;
    public int color;
    public int strokeColor;

    public boolean firstMove;

    Context context;

    public Piece(Context c, int _color){
        context = c;
        alive = true;
        color = _color;
        firstMove = true;

        position = initialPosition();
    }

    public abstract Position initialPosition();
    public abstract List<Position> moveXY();
    public abstract List<Position> attackXY();

    public void moveTo(Position movePosition) {
        position = movePosition;
        if(firstMove) firstMove = false;
    }


}


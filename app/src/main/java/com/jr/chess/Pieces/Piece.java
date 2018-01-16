package com.jr.chess.Pieces;

import android.content.Context;
import android.graphics.drawable.Drawable;

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
    public Drawable image;
    public boolean enPassant;

    public boolean firstMove;

    Context context; // necessary for processing colors

    Piece(Context c, int _color){
        context = c;
        alive = true;
        color = _color;
        firstMove = true;
        enPassant = false;

        position = initialPosition();
    }

    Piece(Context c, int _color, Position initPos){
        context = c;
        alive = true;
        color = _color;
        firstMove = true;
        enPassant = false;

        position = initPos;
    }

    public abstract Position initialPosition();
    public abstract List<Position> moveXY();
    public abstract List<Position> attackXY();

    public void moveTo(Position movePosition) {
        if(firstMove){
            firstMove = false;
            if(Math.abs(movePosition.y-position.y) > 1) enPassant = true;
        }

        position = movePosition;
    }




}


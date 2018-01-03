package com.jr.chess.Pieces;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.jr.chess.Const;
import com.jr.chess.Position;
import com.jr.chess.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Knight extends Piece {
    public Knight(Context c, int _color) {
        super(c, _color);
        type = Const.KNIGHT;
        strokeColor = ContextCompat.getColor(context, R.color.colorKnight);
    }

    public Knight(Context c, int _color, Position initPos) {
        super(c, _color, initPos);
        type = Const.KNIGHT;
        strokeColor = ContextCompat.getColor(context, R.color.colorKnight);
    }

    @Override
    public Position initialPosition() {
        if(color == Const.WHITE) return new Position(1+(5*whiteKnights++),0);
        return new Position(1+(5*+blackKnights++), 7);
    }

    @Override
    public List<Position> moveXY() {
        List<Position> moves = new ArrayList<>();
        moves.add(new Position(position.x+1, position.y+2));
        moves.add(new Position(position.x+1, position.y-2));
        moves.add(new Position(position.x-1, position.y+2));
        moves.add(new Position(position.x-1, position.y-2));
        moves.add(new Position(position.x+2, position.y+1));
        moves.add(new Position(position.x+2, position.y-1));
        moves.add(new Position(position.x-2, position.y+1));
        moves.add(new Position(position.x-2, position.y-1));

        return moves;
    }

    @Override
    public List<Position> attackXY() {
        List<Position> attacks = new ArrayList<>();
        attacks.add(new Position(position.x+1, position.y+2));
        attacks.add(new Position(position.x+1, position.y-2));
        attacks.add(new Position(position.x-1, position.y+2));
        attacks.add(new Position(position.x-1, position.y-2));
        attacks.add(new Position(position.x+2, position.y+1));
        attacks.add(new Position(position.x+2, position.y-1));
        attacks.add(new Position(position.x-2, position.y+1));
        attacks.add(new Position(position.x-2, position.y-1));

        return attacks;
    }

}

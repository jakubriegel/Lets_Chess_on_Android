package com.jr.chess.Pieces;

import com.jr.chess.Const;
import com.jr.chess.Position;
import com.jr.chess.R;

import java.util.List;

public class Bishop extends Piece {
    public Bishop(int _color) {
        super(_color);

    }

    @Override
    public Position initialPosition() {
        if(color == Const.WHITE) return new Position(2+(3*whiteBishops++),0);
        return new Position(2+(3*+blackBishops++), 7);
    }

    @Override
    public List<Position> moveXY() {
        return null;
    }

    @Override
    public List<Position> attackXY() {
        return null;
    }

    @Override
    public void moveTo(Position movePosition) {
        position = movePosition;
    }
}

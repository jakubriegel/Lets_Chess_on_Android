package com.jr.chess.Pieces;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.jr.chess.Const;
import com.jr.chess.Position;
import com.jr.chess.R;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Context c, int _color) {
        super(c, _color);
        strokeColor = ContextCompat.getColor(context, R.color.colorBishop);
        if (color == Const.WHITE) image = context.getResources().getDrawable(R.drawable.white_bishop);
        else image = context.getResources().getDrawable(R.drawable.black_bishop);
    }

    public Bishop(Context c, int _color, Position initPos) {
        super(c, _color, initPos);
        strokeColor = ContextCompat.getColor(context, R.color.colorBishop);
        if (color == Const.WHITE) image = context.getResources().getDrawable(R.drawable.white_bishop);
        else image = context.getResources().getDrawable(R.drawable.black_bishop);
    }

    @Override
    public Position initialPosition() {
        if(color == Const.WHITE) return new Position(2+(3*whiteBishops++),0);
        return new Position(2+(3*+blackBishops++), 7);
    }

    @Override
    public List<Position> moveXY() {
        List<Position> moves = new ArrayList<>();
        for (int x = position.x+1, y = position.y+1; x < 8 && y < 8; x++, y++ ) moves.add(new Position(x, y));
        for (int x = position.x-1, y = position.y+1; x >= 0 && y < 8; x--, y++ ) moves.add(new Position(x, y));
        for (int x = position.x+1, y = position.y-1; x < 8 && y >= 0; x++, y-- ) moves.add(new Position(x, y));
        for (int x = position.x-1, y = position.y-1; x >= 0 && y >= 0; x--, y-- ) moves.add(new Position(x, y));

        return moves;
    }

    @Override
    public List<Position> attackXY() {
        List<Position> attacks = new ArrayList<>();
        for (int x = position.x+1, y = position.y+1; x < 8 && y < 8; x++, y++ ) attacks.add(new Position(x, y));
        for (int x = position.x-1, y = position.y+1; x >= 0 && y < 8; x--, y++ ) attacks.add(new Position(x, y));
        for (int x = position.x+1, y = position.y-1; x < 8 && y >= 0; x++, y-- ) attacks.add(new Position(x, y));
        for (int x = position.x-1, y = position.y-1; x >= 0 && y >= 0; x--, y-- ) attacks.add(new Position(x, y));

        return attacks;
    }
}

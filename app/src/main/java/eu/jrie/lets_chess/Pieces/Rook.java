package eu.jrie.lets_chess.Pieces;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import eu.jrie.lets_chess.Const;
import eu.jrie.lets_chess.Position;
import eu.jrie.lets_chess.R;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public Rook(Context c, byte _color) {
        super(c, _color);
        type = Const.ROOK;
        strokeColor = ContextCompat.getColor(context, R.color.colorRook);
        if (color == Const.WHITE) image = context.getResources().getDrawable(R.drawable.white_rook);
        else image = context.getResources().getDrawable(R.drawable.black_rook);
    }

    public Rook(Context c, byte _color, Position initPos) {
        super(c, _color, initPos);
        type = Const.ROOK;
        strokeColor = ContextCompat.getColor(context, R.color.colorRook);
        if (color == Const.WHITE) image = context.getResources().getDrawable(R.drawable.white_rook);
        else image = context.getResources().getDrawable(R.drawable.black_rook);
    }

    @Override
    public Position initialPosition() {
        if(color == Const.WHITE) return new Position(7*whiteRooks++,0);
        return new Position(7*blackRooks++, 7);
    }

    @Override
    public List<Position> moveXY() {
        List<Position> moves = new ArrayList<>();
        for (int x = position.x+1; x < 8; x++) moves.add(new Position(x, position.y));
        for (int x = position.x-1; x >= 0; x--) moves.add(new Position(x, position.y));
        for (int y = position.y+1; y < 8; y++) moves.add(new Position(position.x, y));
        for (int y = position.y-1; y >= 0; y--) moves.add(new Position(position.x, y));

        return moves;
    }

    @Override
    public List<Position> attackXY() {
        List<Position> attacks = new ArrayList<>();
        for (int x = position.x+1; x < 8; x++) attacks.add(new Position(x, position.y));
        for (int x = position.x-1; x >= 0; x--) attacks.add(new Position(x, position.y));
        for (int y = position.y+1; y < 8; y++) attacks.add(new Position(position.x, y));
        for (int y = position.y-1; y >= 0; y--) attacks.add(new Position(position.x, y));

        return attacks;
    }

}

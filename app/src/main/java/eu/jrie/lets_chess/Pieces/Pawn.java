package eu.jrie.lets_chess.Pieces;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import eu.jrie.lets_chess.Const;
import eu.jrie.lets_chess.Position;
import eu.jrie.lets_chess.R;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Context c, int _color) {
        super(c, _color);
        type = Const.PAWN;
        strokeColor = ContextCompat.getColor(context, R.color.colorPawn);
        if (color == Const.WHITE) image = context.getResources().getDrawable(R.drawable.white_pawn);
        else image = context.getResources().getDrawable(R.drawable.black_pawn);

    }

    @Override
    public Position initialPosition() {
        if (color == Const.WHITE) return new Position(whitePawns++, 1);
        return new Position(blackPawns++, 6);
    }

    @Override
    public List<Position> moveXY() {
        List<Position> moves = new ArrayList<>();
        if (color == Const.WHITE){
            moves.add(new Position(position.x, position.y+1));
            if (firstMove) moves.add(new Position(position.x, position.y+2));
        }
        else{
            moves.add(new Position(position.x, position.y-1));
            if (firstMove) moves.add(new Position(position.x, position.y-2));
        }

        return moves;
    }

    @Override
    public List<Position> attackXY() {
        List<Position> attacks = new ArrayList<>();
        if(color == Const.WHITE){
            attacks.add(new Position(position.x+1, position.y+1));
            attacks.add(new Position(position.x-1, position.y+1));
        }
        else{
            attacks.add(new Position(position.x+1, position.y-1));
            attacks.add(new Position(position.x-1, position.y-1));
        }
        return attacks;
    }


}

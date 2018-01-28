package eu.jrie.lets_chess.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import eu.jrie.lets_chess.Const;
import eu.jrie.lets_chess.Pieces.Piece;
import eu.jrie.lets_chess.Position;

import java.util.ArrayList;
import java.util.Collections;

public class CapturedPad extends BoardView {

    public CapturedPad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMaxTile(1);
        displayMode = Const.CLASSIC_MODE; // ice
    }

    public void setDisplayMode(int mode){
        displayMode = mode;
    }

    public void addPiece(Piece capturedPiece){
        if(displayMode == Const.CLASSIC_MODE) capturedPiece.color = Const.WHITE; // to avoid drawing upside down
        piecesToDraw.add(capturedPiece);
        updatePiecesToDraw();

        invalidate();
    }

    private void updatePiecesToDraw(){
        // bubble sort
        for(int i = 0; i < piecesToDraw.size()-1; i++)
            if(piecesToDraw.get(i).type > piecesToDraw.get(i+1).type) {
                Collections.swap(piecesToDraw, i, i+1);
                i = 0;
            }

        int x = 0, y = 1;
        for(Piece i : piecesToDraw){
            i.position = new Position(x++,y);
            if(x == 8){ y--; x = 0; }
        }

    }

    public void reset(){
        piecesToDraw = new ArrayList<>();
        invalidate();
    }
}

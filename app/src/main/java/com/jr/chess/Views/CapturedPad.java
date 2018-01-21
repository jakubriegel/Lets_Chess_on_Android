package com.jr.chess.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.jr.chess.Const;
import com.jr.chess.Pieces.Piece;
import com.jr.chess.Position;

public class CapturedPad extends BoardView {

    private int x;
    private int y;

    public CapturedPad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMaxTile(1);
        displayMode = Const.CLASSIC_MODE;
        x = 0;
        y = 1;
    }

    public void setDisplayMode(int mode){
        displayMode = mode;
    }

    public void addPiece(Piece capturedPiece){
        if(displayMode == Const.CLASSIC_MODE) capturedPiece.color = Const.WHITE; // to avoid drawing upside down
        capturedPiece.position = new Position(x++,y);
        if(x == 8){ y--; x = 0; }

        piecesToDraw.add(capturedPiece);
        invalidate();
    }
}

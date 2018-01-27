package eu.jrie.chess_time.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import eu.jrie.chess_time.R;


public class BoardGrid extends View {

    private final Paint whiteTile;
    private final Paint blackTile;

    public BoardGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        whiteTile = new Paint();
        blackTile = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        View parent = (View) this.getParent();
        setMeasuredDimension((int) (parent.getWidth() * .95), (int) (parent.getWidth() * .95));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int boardSize = getWidth();
        int tileSize = boardSize / 8;
        int positionX, positionY;

        whiteTile.setStyle(Paint.Style.FILL);
        whiteTile.setColor(ContextCompat.getColor(getContext(), R.color.colorWhiteTile));
        blackTile.setStyle(Paint.Style.FILL);
        blackTile.setColor(ContextCompat.getColor(getContext(), R.color.colorBlackTile));

        Boolean white = false;
        for(int i = 0; i < 8; i++){
            positionX = 0; positionY = i * tileSize;
            white ^= true;
            for (int j = 0; j < 8; j++) {
                if(white) canvas.drawRect(positionX, positionY, positionX + tileSize, positionY + tileSize, whiteTile);
                else canvas.drawRect(positionX, positionY, positionX + tileSize, positionY + tileSize, blackTile);
                white ^= true;
                positionX += tileSize;
            }
        }


    }



}

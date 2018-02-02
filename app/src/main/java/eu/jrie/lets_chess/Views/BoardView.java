package eu.jrie.lets_chess.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import eu.jrie.lets_chess.Const;
import eu.jrie.lets_chess.Pieces.Piece;
import eu.jrie.lets_chess.Position;
import eu.jrie.lets_chess.R;

import java.util.ArrayList;
import java.util.List;

abstract class BoardView extends View {

    private final Paint fillPaint;
    private final Paint strokePaint;

    List<Piece> piecesToDraw;
    private List<Position> movePointersToDraw;
    private List<Position> attackPointersToDraw;

    int displayMode;
    private int oneTileWidth;
    private int maxY;
    private int maxX;

    private int modernRadius;
    private int pointerRadius;

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        piecesToDraw = new ArrayList<>();
        movePointersToDraw = new ArrayList<>();
        attackPointersToDraw = new ArrayList<>();

        fillPaint = new Paint();
        strokePaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        strokePaint.setStyle(Paint.Style.STROKE);

        modernRadius = 0;
        pointerRadius = 0;

        oneTileWidth = 0;
    }

    void setMaxXY(int x, int y){
        maxX = x;
        maxY = y;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(oneTileWidth == 0){
            oneTileWidth = this.getWidth() / (maxX+1);
            modernRadius = (int)(oneTileWidth * .3);
            pointerRadius = (int)(oneTileWidth * .2);
        }

        switch (displayMode){
            case Const.CLASSIC_MODE:
                drawClassic(canvas);
                break;
            case Const.MODERN_MODE:
                drawModern(canvas);
                break;
        }

        Position temp;

        strokePaint.setStrokeWidth((int)(pointerRadius * .3));
        strokePaint.setColor(Color.GREEN);
        for(Position i : movePointersToDraw){
            temp = toPixels(i);
            temp.x += oneTileWidth / 2;
            temp.y += oneTileWidth / 2;

            canvas.drawCircle(temp.x, temp.y, pointerRadius, strokePaint);
        }
        strokePaint.setColor(Color.RED);
        for(Position i : attackPointersToDraw){
            temp = toPixels(i);
            temp.x += oneTileWidth / 2;
            temp.y += oneTileWidth / 2;
            canvas.drawCircle(temp.x, temp.y, pointerRadius, strokePaint);
        }

    }

    public void redraw(List<Piece> pieces, List<Position> movePointers, List<Position> attackPointers, int mode){
        piecesToDraw = pieces;
        movePointersToDraw = movePointers;
        attackPointersToDraw = attackPointers;
        displayMode = mode;

        invalidate();
    }

    private void drawClassic(Canvas canvas){
        Position temp;
        Drawable pieceImage;

        for(Piece i : piecesToDraw){
            pieceImage = i.image;
            temp = toPixels(i.position);
            if(i.color == Const.BLACK){
                canvas.save();
                canvas.rotate(180, getWidth()/2, getHeight()/2);
                temp.y = getWidth() - temp.y - oneTileWidth;
                temp.x = getWidth() - temp.x - oneTileWidth;
                pieceImage.setBounds(temp.x + oneTileWidth / 10, temp.y + oneTileWidth / 10,
                        temp.x + oneTileWidth - oneTileWidth / 10, temp.y + oneTileWidth - oneTileWidth / 10);
                pieceImage.draw(canvas);
                canvas.restore();
            }
            else {
                pieceImage.setBounds(temp.x + oneTileWidth / 10, temp.y + oneTileWidth / 10,
                        temp.x + oneTileWidth - oneTileWidth / 10, temp.y + oneTileWidth - oneTileWidth / 10);
                pieceImage.draw(canvas);
            }

        }
    }

    private void drawModern(Canvas canvas){
        Position temp;
        strokePaint.setStrokeWidth((int)(modernRadius * .3));
        for(Piece i : piecesToDraw){
            if(i.color == Const.WHITE) fillPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorWhitePlayer));
            else fillPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorBlackPlayer));

            temp = toPixels(i.position);
            temp.x += oneTileWidth / 2;
            temp.y += oneTileWidth / 2;

            strokePaint.setColor(i.strokeColor);

            canvas.drawCircle(temp.x, temp.y, (int)(modernRadius * .9), fillPaint);
            canvas.drawCircle(temp.x, temp.y, modernRadius, strokePaint);
        }
    }

    public Position getSquare(Position p){
        Position squarePosition = new Position(p.x, p.y); // sidestep to avoid reference
        int oneWidth = this.getWidth() / (maxX+1);
        squarePosition.x /= oneWidth;
        squarePosition.y = maxY-(squarePosition.y / oneWidth);

        return squarePosition;
    }

    private Position toPixels(Position p){
        Position pixelPosition = new Position(p.x, p.y); // sidestep to avoid reference
        pixelPosition.x = pixelPosition.x * oneTileWidth;
        pixelPosition.y = (maxY -pixelPosition.y) * oneTileWidth;

        return pixelPosition;
    }

}

package com.jr.chess.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.jr.chess.Const;
import com.jr.chess.Pieces.Piece;
import com.jr.chess.Position;
import com.jr.chess.R;

import java.util.ArrayList;
import java.util.List;


public class Board extends View {
    private Paint fillPaint;
    private Paint strokePaint;

    private List<Piece> piecesToDraw;
    private List<Position> movePointersToDraw;
    private List<Position> attackPointersToDraw;

    private int displayMode;
    private int oneTileWidth;

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        piecesToDraw = new ArrayList<>();
        movePointersToDraw = new ArrayList<>();

        fillPaint = new Paint();
        strokePaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        strokePaint.setStyle(Paint.Style.STROKE);

        oneTileWidth = 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
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

        if(oneTileWidth == 0) oneTileWidth = this.getWidth() / 8;

        switch (displayMode){
            case Const.CLASSIC_MODE:
                drawClassic(canvas);
                break;
            case Const.MODERN_MODE:
                drawModern(canvas);
                break;
        }

        Position temp;
        strokePaint.setStrokeWidth(10);
        strokePaint.setColor(Color.GREEN);
        for(Position i : movePointersToDraw){
            temp = toPixels(i);
            temp.x += oneTileWidth / 2;
            temp.y += oneTileWidth / 2;

            canvas.drawCircle(temp.x, temp.y, 30, strokePaint);
        }
        strokePaint.setColor(Color.RED);
        for(Position i : attackPointersToDraw){
            temp = toPixels(i);
            temp.x += oneTileWidth / 2;
            temp.y += oneTileWidth / 2;
            canvas.drawCircle(temp.x, temp.y, 30, strokePaint);
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

        strokePaint.setStrokeWidth(15);
        for(Piece i : piecesToDraw){
            if(i.color == Const.WHITE) fillPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorWhitePlayer));
            else fillPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorBlackPlayer));

            temp = toPixels(i.position);
            temp.x += oneTileWidth / 2;
            temp.y += oneTileWidth / 2;
            strokePaint.setColor(i.strokeColor);

            canvas.drawCircle(temp.x, temp.y, 46, fillPaint);
            canvas.drawCircle(temp.x, temp.y, 53, strokePaint);
        }
    }

    public Position getSquare(Position p){
        Position squarePosition = new Position(p.x, p.y); // sidestep to avoid reference
        int oneWidth = this.getWidth() / 8;
        squarePosition.x /= oneWidth;
        squarePosition.y = 7-(squarePosition.y / oneWidth);

        return squarePosition;
    }

    private Position toPixels(Position p){
        Position pixelPosition = new Position(p.x, p.y); // sidestep to avoid reference
        pixelPosition.x = pixelPosition.x * oneTileWidth;
        pixelPosition.y = (7-pixelPosition.y) * oneTileWidth;

        return pixelPosition;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


}


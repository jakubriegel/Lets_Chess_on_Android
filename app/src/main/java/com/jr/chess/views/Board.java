package com.jr.chess.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jr.chess.Const;
import com.jr.chess.Pieces.Piece;
import com.jr.chess.Position;
import com.jr.chess.R;

import java.util.ArrayList;
import java.util.List;


public class Board extends View {
    Paint fillPaint = new Paint();
    Paint strokePaint = new Paint();

    List<Piece> piecesToDraw;
    private List<Position> movePointersToDraw;
    private List<Position> attackPointersToDraw;

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        piecesToDraw = new ArrayList<>();
        movePointersToDraw = new ArrayList<>();
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

        Position temp;

        fillPaint.setStyle(Paint.Style.FILL);
        strokePaint.setStyle(Paint.Style.STROKE);

        strokePaint.setStrokeWidth(14);
        for(Piece i : piecesToDraw) if(i.alive){
            if(i.color == Const.WHITE) fillPaint.setColor(Color.WHITE);
            else fillPaint.setColor(Color.BLACK);

            temp = toPixels(i.position);
            strokePaint.setColor(i.strokeColor);

            canvas.drawCircle(temp.x, temp.y, 46, fillPaint);
            canvas.drawCircle(temp.x, temp.y, 53, strokePaint);
        }

        strokePaint.setStrokeWidth(10);
        strokePaint.setColor(Color.GREEN);
        for(Position i : movePointersToDraw){
            temp = toPixels(i);
            canvas.drawCircle(temp.x, temp.y, 30, strokePaint);
        }
        strokePaint.setColor(Color.RED);
        for(Position i : attackPointersToDraw){
            temp = toPixels(i);
            canvas.drawCircle(temp.x, temp.y, 30, strokePaint);
        }

    }

    public void redraw(List<Piece> pieces, List<Position> movePointers, List<Position> attackPointers){
        piecesToDraw = pieces;
        movePointersToDraw = movePointers;
        attackPointersToDraw = attackPointers;

        invalidate();
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
        int oneWidth = this.getWidth() / 8;
        pixelPosition.x = (pixelPosition.x * oneWidth) + (oneWidth / 2);
        pixelPosition.y = ((7-pixelPosition.y)*oneWidth) + (oneWidth / 2);

        return pixelPosition;
    }



}


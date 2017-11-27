package com.jr.chess.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jr.chess.Const;
import com.jr.chess.Piece;
import com.jr.chess.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class Board extends View {
    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        startGame();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        View parent = (View) this.getParent();
        setMeasuredDimension((int) (parent.getWidth() * .95), (int) (parent.getWidth() * .95));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Position touchPosition = getSquare(new Position((int) event.getX(), (int) event.getY()));
                for(Piece i : pieces) if(i.position.x == touchPosition.x && i.position.y == touchPosition.y) i.position.y++;
                /*piece = new Piece(getSquare(new Position((int) event.getX(), (int) event.getY())).x, getSquare(new Position((int) event.getX(), (int) event.getY())).y);
                Log.v("debug_board", "piece.x=" + piece.position.x + " piece.y=" + piece.position.y);*/
                invalidate();
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Position temp;

        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        for(Piece i : pieces) if(i.alive){
            if(i.color == Const.WHITE) p.setColor(Color.WHITE);
            else p.setColor(Color.BLACK);

            /*switch (i.type){
                case Const.PAWN:
            }*/
            temp = toPixels(i.position);
            canvas.drawCircle(temp.x, temp.y, 60, p);

        }


    }

    private Position getSquare(Position p){
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


    List<Piece> pieces = new ArrayList<>();
    private void startGame(){

        for(int i = 0; i < 16; i++) pieces.add(new Piece(Const.WHITE, Const.PAWN));
        for(int i = 0; i < 16; i++) pieces.add(new Piece(Const.BLACK, Const.PAWN));

        invalidate();
    }


}


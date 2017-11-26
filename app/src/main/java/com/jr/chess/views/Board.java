package com.jr.chess.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;


public class Board extends View {
    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
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
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                /*String txt = "x: " + (int)event.getX() + " y: " + (int)event.getY();
                Log.v("motion_test", txt);*/
                getSquare((int)event.getX(), (int)event.getY());
                invalidate();
        }

        return super.onTouchEvent(event);
    }

    private void getSquare(int x, int y){
        int oneWidth = this.getWidth() / 8;
        String txt = "x: " + x/oneWidth + " y: " + (7-(y/oneWidth));
        Log.v("motion_test", txt);
    }

    private class Square{
        public Square(){

        }
    }

    private boolean con = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        if(con){
            con = false;
            p.setColor(Color.BLUE);
            p.setStyle(Paint.Style.FILL);
            canvas.drawCircle(500, 250, 80, p);
        }
        else{
            con = true;
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.FILL);
            canvas.drawCircle(250, 250, 50, p);
        }
        // canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

    }
}

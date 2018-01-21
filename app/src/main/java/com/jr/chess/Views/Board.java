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


public class Board extends BoardView {

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMaxTile(7);
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

    @Override
    public boolean performClick() {
        return super.performClick();
    }


}


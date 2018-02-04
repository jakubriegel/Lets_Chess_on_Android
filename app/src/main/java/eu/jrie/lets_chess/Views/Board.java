package eu.jrie.lets_chess.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;


public class Board extends BoardView {

    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMaxXY(7,7);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        setMeasuredDimension((int) (width * .95), (int) (width * .95));


    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }


}


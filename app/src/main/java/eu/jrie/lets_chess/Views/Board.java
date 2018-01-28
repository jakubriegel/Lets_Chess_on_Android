package eu.jrie.lets_chess.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


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


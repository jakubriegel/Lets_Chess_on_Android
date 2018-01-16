package com.jr.chess.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.jr.chess.R;


public class BoardGrid extends View {

    Paint whiteTilePaint;

    public BoardGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

         whiteTilePaint = new Paint();
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
        int positionX = 0, positionY = 0;

        Drawable tile = getResources().getDrawable(R.drawable.white_tile);

        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tile.setBounds(positionX, positionY, positionX + tileSize, positionY + tileSize);
                tile.draw(canvas);

                positionX += 2 * tileSize;
            }
            positionY += 2 * tileSize;
            positionX = 0;
        }
        positionY = tileSize;
        positionX = tileSize;
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tile.setBounds(positionX, positionY, positionX + tileSize, positionY + tileSize);
                tile.draw(canvas);

                positionX += 2 * tileSize;
            }
            positionY += 2 * tileSize;
            positionX = tileSize;
        }

        positionX = tileSize; positionY = 0;
        tile = getResources().getDrawable(R.drawable.black_tile);

        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tile.setBounds(positionX, positionY, positionX + tileSize, positionY + tileSize);
                tile.draw(canvas);

                positionX += 2 * tileSize;
            }
            positionY += 2 * tileSize;
            positionX = tileSize;
        }
        positionY = tileSize;
        positionX = 0;
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tile.setBounds(positionX, positionY, positionX + tileSize, positionY + tileSize);
                tile.draw(canvas);

                positionX += 2 * tileSize;
            }
            positionY += 2 * tileSize;
            positionX = 0;
        }


    }



}

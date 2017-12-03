package com.jr.chess;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import com.jr.chess.Pieces.Bishop;
import com.jr.chess.Pieces.King;
import com.jr.chess.Pieces.Knight;
import com.jr.chess.Pieces.Pawn;
import com.jr.chess.Pieces.Piece;
import com.jr.chess.Pieces.Queen;
import com.jr.chess.Pieces.Rook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


class Game {
    private int state;
    List<Position> movePointers;
    List<Position> attackPointers;
    private Piece activePiece;
    List<Piece> pieces;

    Context context;

    Game(Context c){
        context = c;
    }

    void startGame(){
        state = Const.STATE_SELECT;
        pieces = new ArrayList<>();
        movePointers = new ArrayList<>();
        attackPointers = new ArrayList<>();

        for (int color = Const.WHITE; color <= Const.BLACK; color++){
            for(int i = 0; i < 16; i++) pieces.add(new Pawn(context, color));
            pieces.add(new Bishop(context, color));
            pieces.add(new Bishop(context, color));
            pieces.add(new Knight(context, color));
            pieces.add(new Knight(context, color));
            pieces.add(new Rook(context, color));
            pieces.add(new Rook(context, color));
            pieces.add(new Queen(context, color));
            pieces.add(new King(context, color));
        }
    }

    void processTouch(MotionEvent event, Position touchPosition){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (state) {
                    case Const.STATE_SELECT:
                        for (Piece i : pieces) if (Position.areEqual(i.position, touchPosition)) {
                                activePiece = i;
                                movePointers = i.moveXY();
                                ListIterator<Position> tempIterator = movePointers.listIterator();
                                int tempX, tempY, sigX, sigY;
                                Position tempMovePointer;
                                while (tempIterator.hasNext()) {
                                    tempMovePointer = tempIterator.next();
                                    if(pieceOnSquare(tempMovePointer)){
                                        tempIterator.remove();
                                        tempX = tempMovePointer.x; tempY = tempMovePointer.y;
                                        sigX = (int) Math.signum(activePiece.position.x - tempX);
                                        sigY = (int) Math.signum(activePiece.position.y - tempY);
                                        while(tempIterator.hasNext()){
                                            tempMovePointer = tempIterator.next();
                                            tempX = tempMovePointer.x; tempY = tempMovePointer.y;
                                            if(sigX == Math.signum(activePiece.position.x - tempX)
                                                && sigY == Math.signum((activePiece.position.y - tempY)))
                                                tempIterator.remove();
                                            else{
                                                tempIterator.previous();
                                                break;
                                            }
                                        }
                                    }
                                }
                                attackPointers = i.attackXY();
                                tempIterator = attackPointers.listIterator();
                                Position tempAttackPointer;
                                while (tempIterator.hasNext()) {
                                    tempAttackPointer = tempIterator.next();
                                    if(!pieceOnSquare(tempAttackPointer)) tempIterator.remove();
                                    else {
                                        if(getPieceOn(tempAttackPointer).color == activePiece.color)
                                            tempIterator.remove();
                                        tempX = tempAttackPointer.x; tempY = tempAttackPointer.y;
                                        sigX = (int) Math.signum(activePiece.position.x - tempX);
                                        sigY = (int) Math.signum(activePiece.position.y - tempY);
                                        while(tempIterator.hasNext()){
                                            tempAttackPointer = tempIterator.next();
                                            tempX = tempAttackPointer.x; tempY = tempAttackPointer.y;
                                            if(sigX == Math.signum(activePiece.position.x - tempX)
                                                    && sigY == Math.signum((activePiece.position.y - tempY)))
                                                tempIterator.remove();
                                            else{
                                                tempIterator.previous();
                                                break;
                                            }
                                        }
                                    }

                                }


                                state = Const.STATE_MOVE_ATTACK;
                                break;
                            }
                        break;

                    case Const.STATE_MOVE_ATTACK:
                        if (Position.areEqual(touchPosition, activePiece.position)) break;
                        for (Position i : movePointers)
                            if (Position.areEqual(i, touchPosition)) {
                                activePiece.moveTo(touchPosition);
                            }
                        for (Position i : attackPointers)
                            if (Position.areEqual(i, touchPosition)){
                                pieces.remove(getPieceOn(touchPosition));
                                activePiece.moveTo(touchPosition);
                            }
                        state = Const.STATE_SELECT;
                        movePointers = new ArrayList<>();
                        attackPointers = new ArrayList<>();
                        break;
                }
        }
    }

    private boolean pieceOnSquare(Position square){
        for(Piece p : pieces) if (Position.areEqual(p.position, square)) return true;
        return false;
    }

    private Piece getPieceOn(Position p){
        for(Piece i : pieces) if(Position.areEqual(p, i.position)) return i;
        return null;
    }


}

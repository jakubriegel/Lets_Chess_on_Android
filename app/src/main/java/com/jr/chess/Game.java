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

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


class Game {
    private int state;
    List<Position> movePointers;
    List<Position> attackPointers;
    private Piece activePiece;
    int activeColor;
    List<Piece> pieces;

    private Context context;

    Game(Context c){
        context = c;
    }

    void startGame(){
        state = Const.STATE_SELECT;
        pieces = new ArrayList<>();
        movePointers = new ArrayList<>();
        attackPointers = new ArrayList<>();

        activeColor = Const.WHITE;

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
                        for (Piece i : pieces) if(i.color == activeColor)
                            if (Position.areEqual(i.position, touchPosition)) {
                                activePiece = i;
                                movePointers = getMovePointers(activePiece);
                                attackPointers = getAttackPointers(activePiece);
                                if(activePiece instanceof King){
                                    movePointers = removeAttacked(movePointers, activePiece);
                                    attackPointers = removeAttacked(attackPointers, activePiece);
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
                                changeActiveColor();
                                break;
                            }
                        for (Position i : attackPointers)
                            if (Position.areEqual(i, touchPosition)){
                                pieces.remove(getPieceOn(touchPosition));
                                activePiece.moveTo(touchPosition);
                                changeActiveColor();
                                break;
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

    private void changeActiveColor(){
        if(activeColor == Const.WHITE) activeColor = Const.BLACK;
        else activeColor = Const.WHITE;
    }

    private List<Position> getMovePointers(Piece p){
        List<Position> tempMovePointers = p.moveXY();
        ListIterator<Position> tempIterator = tempMovePointers.listIterator();
        int tempX, tempY, sigX, sigY;
        Position tempMovePointer;
        while (tempIterator.hasNext()) {
            tempMovePointer = tempIterator.next();
            if(pieceOnSquare(tempMovePointer)){
                tempIterator.remove();
                tempX = tempMovePointer.x; tempY = tempMovePointer.y;
                sigX = (int) Math.signum(p.position.x - tempX);
                sigY = (int) Math.signum(p.position.y - tempY);
                while(tempIterator.hasNext()){
                    tempMovePointer = tempIterator.next();
                    tempX = tempMovePointer.x; tempY = tempMovePointer.y;
                    if(sigX == Math.signum(p.position.x - tempX)
                            && sigY == Math.signum((p.position.y - tempY)))
                        tempIterator.remove();
                    else{
                        tempIterator.previous();
                        break;
                    }
                }
            }
        }
        return tempMovePointers;
    }

    private List<Position> getAttackPointers(Piece p){
        List<Position> tempAttackPointers = p.attackXY();
        ListIterator<Position> tempIterator = tempAttackPointers.listIterator();
        int tempX, tempY, sigX, sigY;

        Position tempAttackPointer;
        while (tempIterator.hasNext()) {
            tempAttackPointer = tempIterator.next();
            if (!pieceOnSquare(tempAttackPointer)) tempIterator.remove();
            else {
                if (getPieceOn(tempAttackPointer).color == p.color)
                    tempIterator.remove();
                tempX = tempAttackPointer.x;
                tempY = tempAttackPointer.y;
                sigX = (int) Math.signum(p.position.x - tempX);
                sigY = (int) Math.signum(p.position.y - tempY);
                while (tempIterator.hasNext()) {
                    tempAttackPointer = tempIterator.next();
                    tempX = tempAttackPointer.x;
                    tempY = tempAttackPointer.y;
                    if (sigX == Math.signum(p.position.x - tempX)
                            && sigY == Math.signum((p.position.y - tempY)))
                        tempIterator.remove();
                    else {
                        tempIterator.previous();
                        break;
                    }
                }
            }
        }
            return tempAttackPointers;
    }

    private boolean isSquareAttacked(Position square, Piece protectedPiece){
        Position protectedPiecePosition = protectedPiece.position;
        protectedPiece.position = square;
        for(Piece i : pieces) if(i.color != protectedPiece.color) {
            for (Position attackedSquare : getAttackPointers(i))
                if (Position.areEqual(square, attackedSquare)) {
                    Log.v(Const.DEBUG_TAG, i.getClass().getName());
                    for (Position attackedSquarer : getAttackPointers(i)) Log.v(Const.DEBUG_TAG, attackedSquarer.x + " " + attackedSquarer.y);
                    protectedPiece.position = protectedPiecePosition;
                    return true;
                }
        }
        protectedPiece.position = protectedPiecePosition;
        return false;
    }

    private List<Position> removeAttacked(List<Position> squares, Piece protectedPiece){
        ListIterator<Position> squaresIterator = squares.listIterator();
        Position tempSquare;
        while(squaresIterator.hasNext()) {
            tempSquare = squaresIterator.next();
            if(isSquareAttacked(tempSquare, protectedPiece)) squaresIterator.remove();
        }
        return squares;
    }

}

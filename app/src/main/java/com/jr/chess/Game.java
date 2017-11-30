package com.jr.chess;

import android.view.MotionEvent;

import com.jr.chess.Pieces.Bishop;
import com.jr.chess.Pieces.Pawn;
import com.jr.chess.Pieces.Piece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Game {
    private int state;
    List<Position> movePointers;
    List<Position> attackPointers;
    private Piece activePiece;
    List<Piece> pieces;

    void startGame(){
        state = Const.STATE_SELECT;
        pieces = new ArrayList<>();
        movePointers = new ArrayList<>();
        attackPointers = new ArrayList<>();

        for (int color = Const.WHITE; color <= Const.BLACK; color++){
            for(int i = 0; i < 16; i++) pieces.add(new Pawn(color));
            pieces.add(new Bishop(color));
            pieces.add(new Bishop(color));
            /*pieces.add(new Piece(color, Const.BISHOP));
            pieces.add(new Piece(color, Const.BISHOP));
            pieces.add(new Piece(color, Const.KNIGHT));
            pieces.add(new Piece(color, Const.KNIGHT));
            pieces.add(new Piece(color, Const.ROOK));
            pieces.add(new Piece(color, Const.ROOK));
            pieces.add(new Piece(color, Const.QUEEN));
            pieces.add(new Piece(color, Const.KING));
            pieces.add(new Pawn(color, Const.PAWN));*/
        }
    }

    void processTouch(MotionEvent event, Position touchPosition){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (state) {
                    case Const.STATE_SELECT:
                        for (Piece i : pieces)
                            if (Position.areEqual(i.position, touchPosition)) {
                                movePointers = i.moveXY();
                                Iterator<Position> tempIterator = movePointers.iterator();
                                while (tempIterator.hasNext()) {
                                    Position tempMovePointer = tempIterator.next();
                                    if(pieceOnSquare(tempMovePointer)) tempIterator.remove();
                                }
                                attackPointers = i.attackXY();
                                tempIterator = attackPointers.iterator();
                                while (tempIterator.hasNext()) {
                                    Position tempMovePointer = tempIterator.next();
                                    if(!pieceOnSquare(tempMovePointer) || getPieceOn(tempMovePointer).color == activePiece.color) tempIterator.remove();
                                }

                                activePiece = i;
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

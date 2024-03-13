package com.example.android16.Model;

import java.io.Serializable;

public class King extends ChessPiece {

    public final String id;
    public static final long serialVersionUID = 1L;

    public King(String color, String position) {
        super(color, position);
        if (super.color.equals("white"))
            this.id = "wK";
        else if (super.color.equals("black"))
            this.id = "bK";
        else
            throw new IllegalArgumentException();
    }

    public void movePiece(String newPosition) {
        hasMoved = true;
        this.position = newPosition;
    }

    public boolean islegalMove(String newPosition, ChessBoard board) {
        if(this.getID().charAt(0) == 'w'){
            if(board.squareUnderAttack(newPosition, 'b')){
                return false;
            }
        }
        if(this.getID().charAt(0) == 'b'){
            if(board.squareUnderAttack(newPosition, 'w')){
                return false;
            }
        }
        int newj = newPosition.charAt(0) - 97;
        int newi = newPosition.charAt(1) - 42;
        newi += 2 * (7 - newi);
        if (newPosition.charAt(0) > 'h' || newPosition.charAt(0) < 'a' || newPosition.charAt(1) > '8'
                || newPosition.charAt(1) < '1') {
            return false;
        }
        if (super.position.equals(newPosition))
            return false;

        String position = super.position;

        if (this.getID().charAt(0) == 'w') {
            if (newi == 7 && newj == 6) {
                if(board.boardArray[7][7] != null ){
                    if(board.boardArray[7][7].getID().charAt(1) == 'R'){
                        if (this.hasMoved == false && board.boardArray[7][7].hasMoved == false) {
                            if (!board.squareUnderAttack("f1", 'b') && !board.squareUnderAttack("g1", 'b')) {
                                if (board.boardArray[7][5] == null && board.boardArray[7][6] == null) {
                                    board.boardArray[7][7].movePiece("f1");
                                    board.changePos("h1", "f1");
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            if (newi == 7 && newj == 2) {
                if(board.boardArray[7][0] != null ){
                    if(board.boardArray[7][0].getID().charAt(1) == 'R'){
                        if (this.hasMoved == false && board.boardArray[7][0].hasMoved == false) {
                            if (!board.squareUnderAttack("d1", 'b') && !board.squareUnderAttack("c1", 'b')
                                    && !board.squareUnderAttack("b1", 'b')) {
                                if (board.boardArray[7][1] == null && board.boardArray[7][2] == null
                                        && board.boardArray[7][3] == null) {
                                    board.boardArray[7][0].movePiece("d1");
                                    board.changePos("a1", "d1");
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (this.getID().charAt(0) == 'b') {
            if (newi == 0 && newj == 6) {
                if(board.boardArray[0][7] != null ){
                    if(board.boardArray[0][7].getID().charAt(1) == 'R'){
                        if (this.hasMoved == false && board.boardArray[0][7].hasMoved == false) {
                            if (!board.squareUnderAttack("f8", 'w') && !board.squareUnderAttack("g8", 'w')) {
                                if (board.boardArray[0][5] == null && board.boardArray[0][6] == null) {
                                    board.boardArray[0][7].movePiece("f8");
                                    board.changePos("h8", "f8");
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            if (newi == 0 && newj == 2) {
                if(board.boardArray[0][0] != null ){
                    if(board.boardArray[0][0].getID().charAt(1) == 'R'){
                        if (this.hasMoved == false && board.boardArray[0][0].hasMoved == false) {
                            if (!board.squareUnderAttack("d8", 'w') && !board.squareUnderAttack("c8", 'w')
                                    && !board.squareUnderAttack("b8", 'w')) {
                                if (board.boardArray[7][1] == null && board.boardArray[7][2] == null
                                        && board.boardArray[7][3] == null) {
                                 board.boardArray[7][0].movePiece("d8");
                                    board.changePos("a8", "d8");
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        if ((position.charAt(0) == newPosition.charAt(0) || position.charAt(0) == newPosition.charAt(0) + 1
                || position.charAt(0) == newPosition.charAt(0) - 1) &&
                (position.charAt(1) == newPosition.charAt(1) || position.charAt(1) == newPosition.charAt(1) + 1
                        || position.charAt(1) == newPosition.charAt(1) - 1)) {
            if (board.boardArray[newi][newj] != null) {
                if (board.boardArray[newi][newj].getID().charAt(0) == this.getID().charAt(0))
                    return false;
                else
                    return true;
            }
            return true;
        }
        return false;
    }

    public String getID() {
        return this.id;
    }
}

package com.example.android16.Model;

import java.io.Serializable;

public class Rook extends ChessPiece {

    public final String id;
    public static final long serialVersionUID = 1L;

    public Rook(String color, String position) {
        super(color, position);
        if(super.color.equals("white")) this.id = "wR";
        else if(super.color.equals("black")) this.id = "bR";
        else throw new IllegalArgumentException();
    }

    public void movePiece(String newPosition) {
        hasMoved = true;
        this.position = newPosition;
    }

    public boolean islegalMove(String newPosition, ChessBoard board) {
        if(super.position.equals(newPosition)) return false;
        if(super.position.charAt(0) == newPosition.charAt(0) || super.position.charAt(1) == newPosition.charAt(1)) {
            if(newPosition.charAt(0) > 'h' || newPosition.charAt(0) < 'a' || newPosition.charAt(1) > '8' || newPosition.charAt(1) < '1') {return false;}
            int j = position.charAt(0) - 97;
            int i = position.charAt(1) - 42;
            i += 2*(7-i);

            int newj = newPosition.charAt(0) - 97;
            int newi = newPosition.charAt(1) - 42;
            newi += 2*(7-newi);

            if (newPosition.charAt(1) != this.getPosition().charAt(1)) {
                int iterator;
                if(newi-i >= 0) iterator = 1; else iterator = -1;
                for(int row=i+iterator; iterator == 1 ? row < newi : row > newi; row+=iterator) {
                    if(row != newi && board.boardArray[row][j] != null) return false;
                }
            }
            if (newPosition.charAt(0) != this.getPosition().charAt(0)) {
                int iterator;
                if(newj-j >= 0) iterator = 1; else iterator = -1;
                for(int col=j+iterator; iterator == 1 ? col < newj : col > newj; col+=iterator) {
                    if(col != newj && board.boardArray[i][col] != null) return false;
                }
            }
            if(board.boardArray[newi][newj] != null) {
                if(board.boardArray[newi][newj].getID().charAt(0) == this.getID().charAt(0)) return false;
            }
            return true;
        }
        return false;
    }

    public String getID() {return this.id;}
}

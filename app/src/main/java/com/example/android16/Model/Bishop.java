package com.example.android16.Model;

import java.io.Serializable;

public class Bishop extends ChessPiece {

    public static final long serialVersionUID = 1L;

    public final String id;

    public Bishop(String color, String position) {
        super(color, position);
        if(super.color.equals("white")) this.id = "wB";
        else if(super.color.equals("black")) this.id = "bB";
        else throw new IllegalArgumentException();
    }

    public void movePiece(String newPosition) {
        super.position = newPosition;
    }

    public boolean islegalMove(String newPosition, ChessBoard board) {
        boolean legal = false;
        if(newPosition.charAt(0) > 'h' || newPosition.charAt(0) < 'a' || newPosition.charAt(1) > '8' || newPosition.charAt(1) < '1') {return false;}
        if(super.position.equals(newPosition)) return false;
        for(int i=0; i<8; i++) {
            if(newPosition.charAt(0) == super.position.charAt(0)+i && newPosition.charAt(1) == super.position.charAt(1)+i) legal = true;
            if(newPosition.charAt(0) == super.position.charAt(0)-i && newPosition.charAt(1) == super.position.charAt(1)-i) legal = true;
            if(newPosition.charAt(0) == super.position.charAt(0)+i && newPosition.charAt(1) == super.position.charAt(1)-i) legal = true;
            if(newPosition.charAt(0) == super.position.charAt(0)-i && newPosition.charAt(1) == super.position.charAt(1)+i) legal = true;
        }
        if(legal) {
            int rowIterator, colIterator;
            int j = position.charAt(0) - 97;
            int i = position.charAt(1) - 42;
            i += 2*(7-i);

            int newj = newPosition.charAt(0) - 97;
            int newi = newPosition.charAt(1) - 42;
            newi += 2*(7-newi);

            if(newi-i>=0) rowIterator = 1; else rowIterator = -1;
            if(newj-j>=0) colIterator = 1; else colIterator = -1;

            for(int row = i+rowIterator, col = j+colIterator; rowIterator == 1 ? row < newi : row > newi && colIterator == 1 ? col < newj : col > newj; row+=rowIterator, col+=colIterator) {
                //System.out.println(row + " " + col);
                if(board.boardArray[row][col] != null) return false;
            }
            if(board.boardArray[newi][newj] != null) {
                if(board.boardArray[newi][newj].getID().charAt(0) == this.getID().charAt(0)) return false;
            }
            return true;
        }
        else return false;
    }

    public String getID() {return this.id;}
}

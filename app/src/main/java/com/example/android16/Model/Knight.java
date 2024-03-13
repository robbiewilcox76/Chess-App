package com.example.android16.Model;

import java.io.Serializable;

public class Knight extends ChessPiece {

    public final String id;
    public static final long serialVersionUID = 1L;

    public Knight(String color, String position) {
        super(color, position);
        if(super.color.equals("white")) this.id = "wN";
        else if(super.color.equals("black")) this.id = "bN";
        else throw new IllegalArgumentException();
    }

    public boolean islegalMove(String newPosition, ChessBoard board) {
        String position = super.getPosition();
        if(position.equals(newPosition)) {return false;}
        if(newPosition.charAt(0) > 'h' || newPosition.charAt(0) < 'a' || newPosition.charAt(1) > '8' || newPosition.charAt(1) < '1') {return false;}
        int j = position.charAt(0) - 97;
        int i = position.charAt(1) - 42;
        i += 2*(7-i);

        int newj = newPosition.charAt(0) - 97;
        int newi = newPosition.charAt(1) - 42;
        newi += 2*(7-newi);

        if(newi == i-1 || newi == i+1){
            if(newj == j+2 || newj == j-2){
                if(board.boardArray[newi][newj] == null){
                    return true;
                }
                else{
                    if(board.boardArray[newi][newj].color.equals(this.color)){
                        return false;
                    }
                    return true;
                }
            }
        }
        if(newj == j-1 || newj == j+1){
            if(newi == i+2 || newi == i-2){
                if(board.boardArray[newi][newj] == null){
                    return true;
                }
                else{
                    if(board.boardArray[newi][newj].color.equals(this.color)){
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void movePiece(String newPosition) {
        super.position = newPosition;
    }

    public String getID() {return this.id;}
}

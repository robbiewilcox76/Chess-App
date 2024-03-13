package com.example.android16.Model;

import java.io.Serializable;

public class Pawn extends ChessPiece {

    public final String id;
    public static final long serialVersionUID = 1L;

    public Pawn(String color, String position) {
        super(color, position);
        if(super.color.equals("white")) this.id = "wp";
        else if(super.color.equals("black")) this.id = "bp";
        else throw new IllegalArgumentException();
    }

    public void movePiece(String newPosition) {
        this.position = newPosition;
    }

    public boolean islegalMove(String newPosition, ChessBoard board) {
        if(this.getID().charAt(0) == 'w') {
            String position = super.getPosition();
            if(position.charAt(1) > newPosition.charAt(1)) {return false;}
            if(position.equals(newPosition)) {return false;}
            if(newPosition.charAt(0) > 'h' || newPosition.charAt(0) < 'a' || newPosition.charAt(1) > '8' || newPosition.charAt(1) < '1') {return false;}
            int j = position.charAt(0) - 97;
            int i = position.charAt(1) - 42;
            i += 2*(7-i);

            int newj = newPosition.charAt(0) - 97;
            int newi = newPosition.charAt(1) - 42;
            newi += 2*(7-newi);

            if((newj == j+1 && newi == i-1) || (newj == j-1 && newi == i-1)) {
                if(board.boardArray[newi][newj] == null && board.boardArray[i][newj] != null && board.boardArray[i][newj].enpassant == true){
                    if(board.boardArray[i][newj].getID().charAt(0) == this.getID().charAt(0)) return false;
                    else{
                        board.boardArray[i][newj] = null;
                        return true;
                    }
                }
                if(board.boardArray[newi][newj] != null) {
                    if(board.boardArray[newi][newj].getID().charAt(0) == this.getID().charAt(0)) return false;
                    else return true;
                }
                return false;
            }
            if(position.charAt(0) != newPosition.charAt(0)) return false;
            if(super.originalPosition.equals(position)) {
                if(super.position.charAt(1) + 2 < newPosition.charAt(1)) {return false;}

                if(super.position.charAt(1) == newPosition.charAt(1)-2){
                    if(board.boardArray[i-1][j] != null || board.boardArray[i-2][j] != null){
                        return false;
                    }
                    enpassant = true;
                    return true;
                }
                if(super.position.charAt(1) == newPosition.charAt(1)-1){
                    if(board.boardArray[i-1][j] != null){
                        return false;
                    }
                    return true;
                }
            }
            if(super.position.charAt(1) == newPosition.charAt(1)-1){
                if(board.boardArray[i-1][j] != null){
                    return false;
                }
                return true;
            }
            if(position.charAt(1)+1 != newPosition.charAt(1) || position.charAt(1) > newPosition.charAt(1)) {return false;}
            return true;}

        if(this.getID().charAt(0) == 'b') {
            String position = super.getPosition();
            if(position.charAt(1) < newPosition.charAt(1)) {return false;}
            if(position.equals(newPosition)) {return false;}
            if(newPosition.charAt(0) > 'h' || newPosition.charAt(0) < 'a' || newPosition.charAt(1) > '8' || newPosition.charAt(1) < '1') {return false;}
            int j = position.charAt(0) - 97;
            int i = position.charAt(1) - 42;
            i += 2*(7-i);

            int newj = newPosition.charAt(0) - 97;
            int newi = newPosition.charAt(1) - 42;
            newi += 2*(7-newi);

            if((newj == j+1 && newi == i+1) || (newj == j-1 && newi == i+1)) {
                if(board.boardArray[newi][newj] == null && board.boardArray[i][newj] != null && board.boardArray[i][newj].enpassant == true){
                    if(board.boardArray[i][newj].getID().charAt(0) == this.getID().charAt(0)) return false;
                    else{
                        board.boardArray[i][newj] = null;
                        return true;
                    }
                }
                if(board.boardArray[newi][newj] != null) {
                    if(board.boardArray[newi][newj].getID().charAt(0) == this.getID().charAt(0)) return false;
                    else return true;
                }
                return false;
            }
            if(position.charAt(0) != newPosition.charAt(0)) return false;
            if(super.originalPosition.equals(position)) {
                if(super.position.charAt(1) - 2 > newPosition.charAt(1)) {return false;}
                if(super.position.charAt(1) == newPosition.charAt(1)+2){
                    if(board.boardArray[i+1][j] != null || board.boardArray[i+2][j] != null){
                        return false;
                    }
                    enpassant = true;
                    return true;
                }
                if(super.position.charAt(1) == newPosition.charAt(1)+1){
                    if(board.boardArray[i+1][j] != null){
                        return false;
                    }
                    return true;
                }
            }
            if(super.position.charAt(1) == newPosition.charAt(1)+1){
                if(board.boardArray[i+1][j] != null){
                    return false;
                }
                return true;
            }
            if(position.charAt(1) - 1 > newPosition.charAt(1) || position.charAt(1) < newPosition.charAt(1)) {return false;}
            return true;}
        return false;
    }

    public String getID() {return this.id;}
}

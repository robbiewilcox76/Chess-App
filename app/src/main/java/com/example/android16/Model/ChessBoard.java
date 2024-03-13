package com.example.android16.Model;

import java.io.Serializable;

public class ChessBoard implements Serializable {

    public static final long serialVersionUID = 1L;

    public String wKingPos;

    public String bKingPos;

    public ChessPiece[][] boardArray;

    public final int boardSize = 8;

    public ChessBoard() {
        this.wKingPos = "e1";
        this.bKingPos = "e8";
        boardArray = new ChessPiece[8][8];
    }

    public void LoadPieces() {
        int col = (int)'a';
        for(int i=0; i<8; i++) {
            ChessPiece piece = new Pawn("white", (char)col+"2");
            boardArray[6][i] = piece;
            col++;
        }
        col = (int)'a';
        for(int i=0; i<8; i++) {
            ChessPiece piece = new Pawn("black", (char)col+"7");
            boardArray[1][i] = piece;
            col++;
        }


        boardArray[0][0] = new Rook("black", "a8");
        boardArray[0][7] = new Rook("black", "h8");
        boardArray[7][0] = new Rook("white", "a1");
        boardArray[7][7] = new Rook("white", "h1");

        boardArray[0][2] = new Bishop("black", "c8");
        boardArray[0][5] = new Bishop("black", "f8");
        boardArray[7][2] = new Bishop("white", "c1");
        boardArray[7][5] = new Bishop("white", "f1");

        boardArray[0][1] = new Knight("black", "b8");
        boardArray[0][6] = new Knight("black", "g8");
        boardArray[7][1] = new Knight("white", "b1");
        boardArray[7][6] = new Knight("white", "g1");

        boardArray[0][3] = new Queen("black", "d8");
        boardArray[7][3] = new Queen("white", "d1");


        boardArray[0][4] = new King("black", "e8");
        boardArray[7][4] = new King("white", "e1");
    }

    public void drawBoard() {
        System.out.println("\n\n");
        int hash = 1;
        int row = 8;
        for(int i=0; i<8; i++) {
            System.out.print(" ");
            for(int j=0; j<8; j++) {
                if(boardArray[i][j] != null) System.out.print(boardArray[i][j].getID());
                else if (hash % 2 == 0) System.out.print("##"); else System.out.print("  ");
                System.out.print(" ");
                hash++;
            }
            System.out.print(row + "\n");
            row--;
            hash++;
        }
        System.out.print("  a  b  c  d  e  f  g  h");
    }

    public ChessPiece findPiece(String pos) {
        int j = pos.charAt(0) - 97;
        int i = pos.charAt(1) - 42;
        i += 2*(7-i);
        if(i<8 && j<8 && boardArray[i][j] != null) return boardArray[i][j];
        return null;
    }

    public void changePos(String pos, String newPos) {
        int j = pos.charAt(0) - 97;
        int i = pos.charAt(1) - 42;
        i += 2*(7-i);

        int newj = newPos.charAt(0) - 97;
        int newi = newPos.charAt(1) - 42;
        newi += 2*(7-newi);

        if(pos.equals(wKingPos)) {
            wKingPos = newPos;
        }
        if(pos.equals(bKingPos)) {
            bKingPos = newPos;
        }

        if(boardArray[newi][newj] != null) {this.capturePiece(newPos);}
        boardArray[newi][newj] = boardArray[i][j];
        boardArray[i][j] = null;
    }

    public void clearBlackEnpass(){
        for(int r = 0; r<boardSize; r++){
            for(int c = 0; c<boardSize; c++){
                if(boardArray[r][c] != null && boardArray[r][c].getID().charAt(0)=='b'){
                    boardArray[r][c].enpassant = false;
                }
            }
        }
    }

    public void clearWhiteEnpass(){
        for(int r = 0; r<boardSize; r++){
            for(int c = 0; c<boardSize; c++){
                if(boardArray[r][c] != null && boardArray[r][c].getID().charAt(0)=='w'){
                    boardArray[r][c].enpassant = false;
                }
            }
        }
    }

    public String findKing(char color){
        String x = "";
        for(int r = 0; r<boardSize; r++){
            for(int c = 0; c<boardSize; c++){
                if(boardArray[r][c] != null && boardArray[r][c].getID().charAt(0)==color && boardArray[r][c] instanceof King){
                    x = boardArray[r][c].getPosition();
                }
            }
        }
        return x;
    }

    public boolean whiteCheck(){
        return squareUnderAttack(findKing('w'), 'b');
    }

    public boolean blackCheck(){
        return squareUnderAttack(findKing('b'), 'w');
    }

    public boolean squareUnderAttack(String pos, char color){
        for(int r = 0; r<boardSize; r++){
            for(int c = 0; c<boardSize; c++){
                if(boardArray[r][c] != null && !(boardArray[r][c] instanceof King) && boardArray[r][c].getID().charAt(0)==color && boardArray[r][c].islegalMove(pos, this)){
                    return true;
                }
            }
        }
        return false;
    }

    public void capturePiece(String pos) {
        int j = pos.charAt(0) - 97;
        int i = pos.charAt(1) - 42;
        i += 2*(7-i);

        boardArray[i][j].capture();
        boardArray[i][j] = null;
    }

    /*public ChessPiece promotePawn(ChessPiece pawn, String position, String newPieces) {
        int j = position.charAt(0) - 97;
        int i = position.charAt(1) - 42;
        i += 2*(7-i);
        char[] newPiece = newPieces.toCharArray();
        String color;
        if(pawn.getID().charAt(0) == 'w') color = "white";
        else color = "black";
        switch(newPiece[0]) {
            case 'N': return new Knight(color, position);
            case 'Q': return new Queen(color, position);
            case 'R': return new Rook(color, position);
            case 'B': return new Bishop(color, position);
        }
        return null;
    }

    public ChessPiece promotePawn(ChessPiece pawn, String position) {
        int j = position.charAt(0) - 97;
        int i = position.charAt(1) - 42;
        i += 2*(7-i);
        String color;
        if(pawn.getID().charAt(0) == 'w') color = "white";
        else color = "black";
        return new Queen(color, position);
    }

    public void illegalMove() {System.out.println("\n\nIllegal move, try again.");}*/

}


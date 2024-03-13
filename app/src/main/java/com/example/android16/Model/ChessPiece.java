package com.example.android16.Model;

import java.io.Serializable;

public abstract class ChessPiece implements Serializable {

    public static final long serialVersionUID = 1L;

    public final String originalPosition;

    public String color;

    public String position;

    public boolean isCaptured = false;

    public boolean enpassant = false;

    public boolean hasMoved = false;

    public ChessPiece(String color, String position) {
        this.color = color;
        this.position = position;
        this.originalPosition = position;
    }

    public void setPosition(String position) {this.position = position;}

    public String getPosition() {return this.position;}

    public void capture() {
        this.isCaptured = true;
        this.position = "captured";
    }

    public abstract String getID();

    public abstract void movePiece(String newPosition);

    public abstract boolean islegalMove(String newPosition, ChessBoard board);
}

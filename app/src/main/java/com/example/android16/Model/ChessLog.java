package com.example.android16.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class ChessLog implements Serializable {

    public static final long serialVersionUID = 1L;

    public ArrayList<ChessBoard> moves;
    public Date date;
    public String name;
    public String result;

    public ChessLog(String name) {
        this.moves = new ArrayList<ChessBoard>();
        this.name = name;
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MILLISECOND, 0);
        this.date = calendar.getTime();
    }

    public void setResult(String result){
        this.result = result;
    }

    public void changeName(String name) {this.name = name;}

    public void addMove(ChessBoard move) {
        moves.add(move);
    }

    public String toString() {
        return this.name + "\nDate Played: " + this.date + "\nResult: " + this.result;
    }
}

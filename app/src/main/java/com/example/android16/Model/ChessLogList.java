package com.example.android16.Model;

import android.content.Context;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChessLogList implements Serializable {

    public static final long serialVersionUID = 1L;

    public List<ChessLog> loglist;

    public ChessLogList(){
        loglist = new ArrayList<ChessLog>();
    }

    public void save(ChessLogList list, Context context) throws FileNotFoundException, IOException {
        FileOutputStream f = context.openFileOutput("userinfo.dat", Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(f);
        oos.writeObject(list);
        f.close();
        oos.close();
    }

    public static ChessLogList load(Context context) throws IOException, ClassNotFoundException {
        File f = new File(context.getFilesDir(), "userinfo.dat");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fis = context.openFileInput("userinfo.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ChessLogList logList = (ChessLogList) ois.readObject();
            ois.close();
            fis.close();
            return logList;
        }
        catch(EOFException e) {
            return new ChessLogList();
        }
    }

    public void addGame(ChessLog game) throws IOException {
        this.loglist.add(game);
    }

    public ChessLog getGame(String name) {
        for(ChessLog game: this.loglist) {
            if(game.name.equals(name)) {return game;}
        }
        return null;
    }
}

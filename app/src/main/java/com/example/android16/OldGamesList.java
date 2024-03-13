package com.example.android16;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android16.Model.ChessLog;
import com.example.android16.Model.Bishop;
import com.example.android16.Model.ChessBoard;
import com.example.android16.Model.ChessLog;
import com.example.android16.Model.ChessLogList;
import com.example.android16.Model.ChessPiece;
import com.example.android16.Model.King;
import com.example.android16.Model.Knight;
import com.example.android16.Model.Pawn;
import com.example.android16.Model.Queen;
import com.example.android16.Model.Rook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

public class OldGamesList extends AppCompatActivity {

    private ListView gameList;
    private ArrayAdapter adapter;
    private List<String> list;
    private List<ChessLog> logList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_games);
        setTitle("Click a Game To Replay");
        try {
            ChessLogList Loglist = ChessLogList.load(this);
            logList = Loglist.loglist;
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, logList);
            gameList = findViewById(R.id.GameList);
            gameList.setAdapter(adapter);
            logList.sort((s1, s2) -> s2.date.compareTo(s1.date));
            gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectName = logList.get(i).name;
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(OldGamesList.this, ReplayGame.class);
                    intent.putExtra("name", selectName);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void sortByName(View view) {
        logList.sort((s1, s2) -> s1.name.compareToIgnoreCase(s2.name));
        list = new ArrayList<String>();
        for(int i=0; i<logList.size(); i++) {
            list.add(logList.get(i).toString());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        gameList.setAdapter(adapter);
        return;
    }

    public void sortByDate(View view) {
        logList.sort((s1, s2) -> s2.date.compareTo(s1.date));
        list = new ArrayList<String>();
        for(int i=0; i<logList.size(); i++) {
            list.add(logList.get(i).toString());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        gameList.setAdapter(adapter);
        return;
    }

}

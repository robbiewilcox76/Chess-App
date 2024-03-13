package com.example.android16;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class ReplayGame extends AppCompatActivity{
    private List<ChessLog> logList;
    static Context context;
    private ChessLog playGame;
    private int counter;
    private int numMoves;
    static ChessBoard board;
    static TextView[][] squares, squares2;


    protected void onCreate(Bundle savedInstanceState) {
        counter = 1;
        String playName;
        playName = getIntent().getStringExtra("name");
        try {
            ChessLogList Loglist = ChessLogList.load(this);
            logList = Loglist.loglist;
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(ChessLog log: logList) {
            if (log.name.equals(playName)) {
                playGame = log;
                numMoves = playGame.moves.size();
                break;
            }
        }
        squares = new TextView[8][8];
        squares2 = new TextView[8][8];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay_games);
        setTitle("Replay " + playName);
        context = ReplayGame.this;

        squares[0][0] = findViewById(R.id.a8view);
        squares[1][0] = findViewById(R.id.a7view);
        squares[2][0] = findViewById(R.id.a6view);
        squares[3][0] = findViewById(R.id.a5view);
        squares[4][0] = findViewById(R.id.a4view);
        squares[5][0] = findViewById(R.id.a3view);
        squares[6][0] = findViewById(R.id.a2view);
        squares[7][0] = findViewById(R.id.a1view);

        squares[0][1] = findViewById(R.id.b8view);
        squares[1][1] = findViewById(R.id.b7view);
        squares[2][1] = findViewById(R.id.b6view);
        squares[3][1] = findViewById(R.id.b5view);
        squares[4][1] = findViewById(R.id.b4view);
        squares[5][1] = findViewById(R.id.b3view);
        squares[6][1] = findViewById(R.id.b2view);
        squares[7][1] = findViewById(R.id.b1view);

        squares[0][2] = findViewById(R.id.c8view);
        squares[1][2] = findViewById(R.id.c7view);
        squares[2][2] = findViewById(R.id.c6view);
        squares[3][2] = findViewById(R.id.c5view);
        squares[4][2] = findViewById(R.id.c4view);
        squares[5][2] = findViewById(R.id.c3view);
        squares[6][2] = findViewById(R.id.c2view);
        squares[7][2] = findViewById(R.id.c1view);

        squares[0][3] = findViewById(R.id.d8view);
        squares[1][3] = findViewById(R.id.d7view);
        squares[2][3] = findViewById(R.id.d6view);
        squares[3][3] = findViewById(R.id.d5vie);
        squares[4][3] = findViewById(R.id.d4view);
        squares[5][3] = findViewById(R.id.d3view);
        squares[6][3] = findViewById(R.id.d2view);
        squares[7][3] = findViewById(R.id.d1view);

        squares[0][4] = findViewById(R.id.e8view);
        squares[1][4] = findViewById(R.id.e7view);
        squares[2][4] = findViewById(R.id.e6view);
        squares[3][4] = findViewById(R.id.e5view);
        squares[4][4] = findViewById(R.id.e4view);
        squares[5][4] = findViewById(R.id.e3view);
        squares[6][4] = findViewById(R.id.e2view);
        squares[7][4] = findViewById(R.id.e1view);

        squares[0][5] = findViewById(R.id.f8view);
        squares[1][5] = findViewById(R.id.f7view);
        squares[2][5] = findViewById(R.id.f6view);
        squares[3][5] = findViewById(R.id.f5view);
        squares[4][5] = findViewById(R.id.f4view);
        squares[5][5] = findViewById(R.id.f3view);
        squares[6][5] = findViewById(R.id.f2view);
        squares[7][5] = findViewById(R.id.f1view);

        squares[0][6] = findViewById(R.id.g8view);
        squares[1][6] = findViewById(R.id.g7view);
        squares[2][6] = findViewById(R.id.g6view);
        squares[3][6] = findViewById(R.id.g5view);
        squares[4][6] = findViewById(R.id.g4view);
        squares[5][6] = findViewById(R.id.g3view);
        squares[6][6] = findViewById(R.id.g2view);
        squares[7][6] = findViewById(R.id.g1view);

        squares[0][7] = findViewById(R.id.h8view);
        squares[1][7] = findViewById(R.id.h7view);
        squares[2][7] = findViewById(R.id.h6view);
        squares[3][7] = findViewById(R.id.h5view);
        squares[4][7] = findViewById(R.id.h4view);
        squares[5][7] = findViewById(R.id.h3view);
        squares[6][7] = findViewById(R.id.h2view);
        squares[7][7] = findViewById(R.id.h1view);

        board = new ChessBoard();
        board.LoadPieces();
        try {
            drawBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void nextMove(View view)throws IOException, ClassNotFoundException {
        if (counter < numMoves) {
            drawBoard(playGame.moves.get(counter));
            counter++;
        }
        else{
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Replay Finished");
            alert.setMessage("Return to main menu?");
            alert.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(this, MainMenu.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
            alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }
    }


    public void drawBoard(ChessBoard board) throws IOException, ClassNotFoundException {
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                if(board.boardArray[i][j] != null) {
                    if(board.boardArray[i][j] instanceof King) {
                        if(((King) board.boardArray[i][j]).id.charAt(0) == 'w') squares[i][j].setBackgroundResource(R.drawable.whire_king);
                        else squares[i][j].setBackgroundResource(R.drawable.black_king);
                    }
                    if(board.boardArray[i][j] instanceof Queen) {
                        if(((Queen) board.boardArray[i][j]).id.charAt(0) == 'w') squares[i][j].setBackgroundResource(R.drawable.white_queen);
                        else squares[i][j].setBackgroundResource(R.drawable.black_queen);
                    }
                    if(board.boardArray[i][j] instanceof Pawn) {
                        if(((Pawn) board.boardArray[i][j]).id.charAt(0) == 'w') squares[i][j].setBackgroundResource(R.drawable.white_pawn);
                        else squares[i][j].setBackgroundResource(R.drawable.black_pawn);
                    }
                    if(board.boardArray[i][j] instanceof Bishop) {
                        if(((Bishop) board.boardArray[i][j]).id.charAt(0) == 'w') squares[i][j].setBackgroundResource(R.drawable.white_bishop);
                        else squares[i][j].setBackgroundResource(R.drawable.black_bishop);
                    }
                    if(board.boardArray[i][j] instanceof Rook) {
                        if(((Rook) board.boardArray[i][j]).id.charAt(0) == 'w') squares[i][j].setBackgroundResource(R.drawable.white_rook);
                        else squares[i][j].setBackgroundResource(R.drawable.black_rook);
                    }
                    if(board.boardArray[i][j] instanceof Knight) {
                        if(((Knight) board.boardArray[i][j]).id.charAt(0) == 'w') squares[i][j].setBackgroundResource(R.drawable.white_knight);
                        else squares[i][j].setBackgroundResource(R.drawable.black_knight);
                    }
                }
                else squares[i][j].setBackgroundResource(0);
            }
        }
        return;
    }



}

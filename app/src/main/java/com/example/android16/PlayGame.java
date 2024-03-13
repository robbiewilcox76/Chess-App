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

public class PlayGame extends AppCompatActivity {

    public static ChessLog log;
    static Context context;
    static LinearLayout pawnPromoter;
    static boolean legal = false;
    static boolean resign = false;
    static ChessBoard board;
    static ChessBoard prevBoard = null;
    static boolean hasMoved = true;
    static TextView[][] squares, squares2;
    static int counter;
    static TextView firstSelected = null;
    static TextView secondSelected = null;
    static int i;
    static int j;
    static int newi;
    static int newj;
    public static int pawnSelectionInt;
    public String title;
    public boolean Draw;
    public boolean ai;
    public boolean undo;

    protected void onCreate(Bundle savedInstanceState) {
        log = new ChessLog("Chess Game");
        squares = new TextView[8][8];
        squares2 = new TextView[8][8];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);
        setTitle("May The Best Player Win");
        context = PlayGame.this;
        pawnPromoter = findViewById(R.id.pawnPromoter);

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
        counter = 2;
    }

    public void onClick(View view) throws IOException, ClassNotFoundException {
        if(firstSelected == null) {
            if(containsPiece(view)) {
            firstSelected = (TextView)view;
            return;
            }
            return;
        }
        if(secondSelected == null) {
            if(containsPiece(view)) {}
            secondSelected = (TextView)view;
        }
        if(firstSelected == secondSelected) {illegalMove(); return;}
        if(!board.wKingPos.equals("captured") && !board.bKingPos.equals("captured")) {
                if(counter % 2 == 0){
                    board.clearWhiteEnpass();
                    whiteMove(board);
                    updatePrevBoard(board);
                    if(board.blackCheck()){
                        if(blackCheckMate(board)){
                            whiteMove(board);
                            drawBoard(board);
                            counter++;
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {resignorDraw(alert.create().getButton(1));});
                            alert.setTitle("Checkmate");
                            alert.show();
                            board.wKingPos = "captured";
                            return;
                        }
                        else{
                        Toast toast = Toast.makeText(context, "Check", Toast.LENGTH_SHORT);
                        toast.setMargin(0, (float)0.6);
                        toast.show();}
                    }
                }
                else{
                    board.clearBlackEnpass();
                    blackMove(board);
                    updatePrevBoard(board);
                    if(board.whiteCheck()){
                        if(whiteCheckMate(board)){
                            blackMove(board);
                            drawBoard(board);
                            counter++;
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {resignorDraw(alert.create().getButton(1));});
                            alert.setTitle("Checkmate");
                            alert.show();
                            board.bKingPos = "captured";
                            return;
                        }
                        else{
                            Toast toast = Toast.makeText(context, "Check", Toast.LENGTH_SHORT);
                            toast.setMargin(0, (float)0.6);
                            toast.show();}
                    }
                    updatePrevBoard(board);
                }
            firstSelected = null;
            secondSelected = null;
            drawBoard(board);
            if(legal) counter++;
            legal = false;
            return;
        }
    }

    public void endGame(){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainMenu.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void resignorDraw(View view) {
        if(view == null) {}
        else if(view.getId() == R.id.draw) Draw = true;
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                squares[i][j].setClickable(false);
            }
        }
        findViewById(R.id.resign).setClickable(false);
        findViewById(R.id.draw).setClickable(false);
        findViewById(R.id.undo).setClickable(false);
        findViewById(R.id.aimove).setClickable(false);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            try {
                LogGame();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        alert.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {endGame();});
        String color;
        if(counter%2==0) color = "Black"; else color = "White";
        alert.setTitle(color+ " Wins!  Log Game?");
        alert.show();
        return;
    }

    public void LogGame() throws IOException, ClassNotFoundException {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Title");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                title = input.getText().toString();
                ChessLogList list = null;
                try {
                    list = ChessLogList.load(context);
                    log.name = title;
                    if(Draw) log.result = "Draw";
                    else if(counter%2==0) log.result = "Black wins";
                    else log.result = "White wins";
                    list.loglist.add(log);
                    list.save(list, context);
                    endGame();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                endGame();
            }
        });
        builder.show();
    }

    public void aiMove(View view) throws IOException, ClassNotFoundException {
        ai = true;
        if(counter % 2 == 0){
            for(int r = 0; r<8; r++){
                for(int c = 0; c<8; c++) {
                    if (board.boardArray[r][c] != null && board.boardArray[r][c].color.charAt(0) == 'w' && !(board.boardArray[r][c] instanceof King)) {
                        for (int ii = 0; ii < 8; ii++) {
                            for (int jj = 1; jj < 8; jj++) {
                                String newpos = toStr(ii, jj);
                                if (board.boardArray[r][c].islegalMove(newpos, board)) {
                                    newi = ii;
                                    newj = jj;
                                    i = r;
                                    j = c;
                                    whiteMove(board);
                                    updatePrevBoard(board);
                                    drawBoard(board);
                                    counter++;
                                    if(board.blackCheck()){
                                        if(blackCheckMate(board)){
                                            whiteMove(board);
                                            drawBoard(board);
                                            updatePrevBoard(board);
                                            counter++;
                                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                            alert.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {resignorDraw(alert.create().getButton(1));});
                                            alert.setTitle("Checkmate");
                                            alert.show();
                                            board.wKingPos = "captured";
                                            ai = false;
                                            return;
                                        }
                                        else{
                                            whiteMove(board);
                                            drawBoard(board);
                                            updatePrevBoard(board);
                                            Toast toast = Toast.makeText(context, "Check", Toast.LENGTH_SHORT);
                                            toast.setMargin(0, (float)0.6);
                                            toast.show();}
                                    }
                                    ai = false;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        else{
            for(int r = 0; r<8; r++){
                for(int c = 0; c<8; c++) {
                    if (board.boardArray[r][c] != null && board.boardArray[r][c].color.charAt(0) == 'b' && !(board.boardArray[r][c] instanceof King)) {
                        for (int ii = 0; ii < 8; ii++) {
                            for (int jj = 1; jj < 8; jj++) {
                                String newPos = toStr(ii, jj);
                                if (board.boardArray[r][c].islegalMove(newPos, board)) {
                                    newi = ii;
                                    newj = jj;
                                    i = r;
                                    j = c;
                                    blackMove(board);
                                    updatePrevBoard(board);
                                    drawBoard(board);
                                    counter++;
                                    if(board.whiteCheck()){
                                        if(whiteCheckMate(board)){
                                            blackMove(board);
                                            updatePrevBoard(board);
                                            drawBoard(board);
                                            counter++;
                                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                            alert.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {resignorDraw(alert.create().getButton(1));});
                                            alert.setTitle("Checkmate");
                                            alert.show();
                                            board.bKingPos = "captured";
                                            ai = false;
                                            return;
                                        }
                                        else{
                                            blackMove(board);
                                            updatePrevBoard(board);
                                            drawBoard(board);
                                            Toast toast = Toast.makeText(context, "Check", Toast.LENGTH_SHORT);
                                            toast.setMargin(0, (float)0.6);
                                            toast.show();}
                                    }
                                    ai = false;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        ai = false;
        return;
    }

    public void undoMove(View view) throws IOException, ClassNotFoundException {
        if(undo) {
            Toast toast = Toast.makeText(context, "Cannot undo consecutive moves", Toast.LENGTH_SHORT);
            toast.setMargin(0, (float)0.6);
            toast.show();
            return;
        }
        if(counter == 2) return;
        undo = true;
        counter--;
        board = copyBoard(log.moves.get(log.moves.size()-2));
        drawBoard(board);
        log.moves.remove(log.moves.size()-1);
        log.moves.remove(log.moves.size()-1);
        return;
    }

    public static void undo(ChessBoard board){
        for(int r = 0; r<8; r++){
            for(int c = 0; c<8; c++){
                if(prevBoard.boardArray[r][c]!= null){
                    if(prevBoard.boardArray[r][c] instanceof King){
                        board.boardArray[r][c] = new King(prevBoard.boardArray[r][c].color, prevBoard.boardArray[r][c].position);
                        board.boardArray[r][c].hasMoved = prevBoard.boardArray[r][c].hasMoved;
                    }
                    else if(prevBoard.boardArray[r][c] instanceof Queen){
                        board.boardArray[r][c] = new Queen(prevBoard.boardArray[r][c].color, prevBoard.boardArray[r][c].position);
                    }
                    else if(prevBoard.boardArray[r][c] instanceof Bishop){
                        board.boardArray[r][c] = new Bishop(prevBoard.boardArray[r][c].color, prevBoard.boardArray[r][c].position);
                    }
                    else if(prevBoard.boardArray[r][c] instanceof Rook){
                        board.boardArray[r][c] = new Rook(prevBoard.boardArray[r][c].color, prevBoard.boardArray[r][c].position);
                        board.boardArray[r][c].hasMoved = prevBoard.boardArray[r][c].hasMoved;
                    }
                    else if(prevBoard.boardArray[r][c] instanceof Knight){
                        board.boardArray[r][c] = new Knight(prevBoard.boardArray[r][c].color, prevBoard.boardArray[r][c].position);
                    }
                    else if(prevBoard.boardArray[r][c] instanceof Pawn){
                        board.boardArray[r][c] = new Pawn(prevBoard.boardArray[r][c].color, prevBoard.boardArray[r][c].position);
                        board.boardArray[r][c].hasMoved = prevBoard.boardArray[r][c].hasMoved;
                        board.boardArray[r][c].enpassant = prevBoard.boardArray[r][c].enpassant;
                    }
                }
                else{
                    board.boardArray[r][c] = null;
                }
            }
        }
    }

    public static boolean blackCheckMate(ChessBoard board){
        boolean mate = true;
        for(int r = 0; r<8; r++){
            for(int c = 0; c<8; c++){
                if(board.boardArray[r][c] != null && board.boardArray[r][c].color.charAt(0) == 'b'){
                    for(char i = 'a'; i<='h'; i++){
                        for(int j = 1; j<=8; j++){
                            String pos = Character.toString(i)+Integer.toString(j);
                            if(board.boardArray[r][c].islegalMove(pos, board)){
                                board.changePos(board.boardArray[r][c].position, pos);
                                board.findPiece(pos).setPosition(pos);
                                if(board.blackCheck() == false){
                                    undo(board);
                                    return false;
                                }
                                undo(board);
                            }
                        }
                    }
                }
            }
        }
        return mate;
    }

    public static boolean whiteCheckMate(ChessBoard board){
        boolean mate = true;
        for(int r = 0; r<8; r++){
            for(int c = 0; c<8; c++){
                if(board.boardArray[r][c] != null && board.boardArray[r][c].color.charAt(0) == 'w'){
                    for(char i = 'a'; i<='h'; i++){
                        for(int j = 1; j<=8; j++){
                            String pos = Character.toString(i)+Integer.toString(j);
                            if(board.boardArray[r][c].islegalMove(pos, board)){
                                board.changePos(board.boardArray[r][c].position, pos);
                                board.findPiece(pos).setPosition(pos);
                                if(board.whiteCheck() == false){
                                    undo(board);
                                    return false;
                                }
                                undo(board);
                            }
                        }
                    }
                }
            }
        }
        return mate;
    }

    public static String toStr(int i, int j) {
        String str = "";
        str +=  (char)(j+97);
        switch(i){
            case(0):{i=8;break;}
            case(1):{i=7;break;}
            case(2):{i=6;break;}
            case(3):{i=5;break;}
            case(4):{i=4;break;}
            case(5):{i=3;break;}
            case(6):{i=2;break;}
            case(7):{i=1;break;}
        }
        str+=i;
        return str;
    }

    public void whiteMove(ChessBoard board) throws IOException, ClassNotFoundException {
        String currentPos = toStr(i, j);
        String newPos = toStr(newi, newj);

        System.out.println(newi + " " +newj + " " +currentPos+ " " + newPos);

        if(board.boardArray[i][j] == null) {; return;}
        if(board.boardArray[i][j] != null) {if(board.boardArray[i][j].getID().charAt(0) != 'w') {wrongMove("White"); return;}}
        if(board.boardArray[i][j].islegalMove(newPos, board) == false) {illegalMove(); return;}
        board.boardArray[i][j].movePiece(newPos);
        if(newi == 0 && board.boardArray[i][j].getID().equals("wp")) {
            promotePawn(board.boardArray[i][j], newPos);
        }
        board.changePos(currentPos, newPos);
        if(board.whiteCheck()){
            undo(board);
            illegalMove();
            return;
        }
        if(ai && newi == 0) board.boardArray[newi][newj] = new Queen("white", newPos);
        System.out.println(board.boardArray[newi][newj]);
        board.drawBoard();
        undo = false;
        hasMoved = true;
        legal = true;
        return;
    }

    public void blackMove(ChessBoard board) throws IOException, ClassNotFoundException {
        String currentPos = toStr(i, j);
        String newPos = toStr(newi, newj);

        System.out.println(newi + " " +newj + " " +currentPos+ " " + newPos);

        if(board.boardArray[i][j] == null) {illegalMove(); return;}
        if(board.boardArray[i][j] != null) {if(board.boardArray[i][j].getID().charAt(0) != 'b') {wrongMove("Black"); return;}}
        if(board.boardArray[i][j].islegalMove(newPos, board) == false) {illegalMove(); return;}
        board.boardArray[i][j].movePiece(newPos);
        if(newi == 7 && board.boardArray[i][j].getID().equals("bp")) {
            promotePawn(board.boardArray[i][j], newPos);
        }
        board.changePos(currentPos, newPos);
        if(board.blackCheck()){
            undo(board);
            illegalMove();
            return;
        }
        undo = false;
        legal = true;
        hasMoved = true;
        return;
    }

    public static void updatePrevBoard(ChessBoard board){
        prevBoard = new ChessBoard();
        for(int r = 0; r<8; r++){
            for(int c = 0; c<8; c++){
                if(board.boardArray[r][c]!= null){
                    if(board.boardArray[r][c] instanceof King){
                        prevBoard.boardArray[r][c] = new King(board.boardArray[r][c].color, board.boardArray[r][c].position);
                        prevBoard.boardArray[r][c].hasMoved = board.boardArray[r][c].hasMoved;
                    }
                    else if(board.boardArray[r][c] instanceof Queen){
                        prevBoard.boardArray[r][c] = new Queen(board.boardArray[r][c].color, board.boardArray[r][c].position);
                    }
                    else if(board.boardArray[r][c] instanceof Bishop){
                        prevBoard.boardArray[r][c] = new Bishop(board.boardArray[r][c].color, board.boardArray[r][c].position);
                    }
                    else if(board.boardArray[r][c] instanceof Knight){
                        prevBoard.boardArray[r][c] = new Knight(board.boardArray[r][c].color, board.boardArray[r][c].position);
                    }
                    else if(board.boardArray[r][c] instanceof Rook){
                        prevBoard.boardArray[r][c] = new Rook(board.boardArray[r][c].color, board.boardArray[r][c].position);
                        prevBoard.boardArray[r][c].hasMoved = board.boardArray[r][c].hasMoved;
                    }
                    else if(board.boardArray[r][c] instanceof Pawn){
                        prevBoard.boardArray[r][c] = new Pawn(board.boardArray[r][c].color, board.boardArray[r][c].position);
                        prevBoard.boardArray[r][c].hasMoved = board.boardArray[r][c].hasMoved;
                        prevBoard.boardArray[r][c].enpassant = board.boardArray[r][c].enpassant;
                    }
                }
            }
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
        log.addMove(copyBoard((board)));
        return;
    }

    public static ChessBoard copyBoard(ChessBoard board){
        ChessBoard copy = new ChessBoard();
        for(int r = 0; r<8; r++){
            for(int c = 0; c<8; c++){
                if(board.boardArray[r][c]!= null){
                    if(board.boardArray[r][c] instanceof King){
                        copy.boardArray[r][c] = new King(board.boardArray[r][c].color, board.boardArray[r][c].position);
                        copy.boardArray[r][c].hasMoved = board.boardArray[r][c].hasMoved;
                    }
                    else if(board.boardArray[r][c] instanceof Queen){
                        copy.boardArray[r][c] = new Queen(board.boardArray[r][c].color, board.boardArray[r][c].position);
                    }
                    else if(board.boardArray[r][c] instanceof Bishop){
                        copy.boardArray[r][c] = new Bishop(board.boardArray[r][c].color, board.boardArray[r][c].position);
                    }
                    else if(board.boardArray[r][c] instanceof Knight){
                        copy.boardArray[r][c] = new Knight(board.boardArray[r][c].color, board.boardArray[r][c].position);
                    }
                    else if(board.boardArray[r][c] instanceof Rook){
                        copy.boardArray[r][c] = new Rook(board.boardArray[r][c].color, board.boardArray[r][c].position);
                        copy.boardArray[r][c].hasMoved = board.boardArray[r][c].hasMoved;
                    }
                    else if(board.boardArray[r][c] instanceof Pawn){
                        copy.boardArray[r][c] = new Pawn(board.boardArray[r][c].color, board.boardArray[r][c].position);
                        copy.boardArray[r][c].hasMoved = board.boardArray[r][c].hasMoved;
                        copy.boardArray[r][c].enpassant = board.boardArray[r][c].enpassant;
                    }
                }
            }
        }
        return copy;
    }

    public static boolean containsPiece(View Square) {
        for(int row=0; row<8; row++) {
            for(int col=0; col<8; col++) {
                if(Square.getId() == squares[row][col].getId()) {
                    if(board.boardArray[row][col] != null) {
                        if(firstSelected == null) {i = row; j = col;}
                        else {
                            newi = row; newj = col;
                        }
                        return true;
                    }
                    else {
                        newi = row; newj = col;
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public static void illegalMove() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {firstSelected = null; secondSelected = null;});
        alert.setTitle("Invalid Move");
        alert.show();
    }

    public static void wrongMove(String color) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {firstSelected = null; secondSelected = null;});
        alert.setTitle(color+"'s " + "Turn!");
        alert.show();
    }

    public void promotePawn(ChessPiece pawn, String position) throws IOException, ClassNotFoundException {
        System.out.println(ai);
        if(ai) return;
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                squares[i][j].setClickable(false);
            }
        }
        findViewById(R.id.resign).setClickable(false);
        findViewById(R.id.draw).setClickable(false);
        findViewById(R.id.undo).setClickable(false);
        findViewById(R.id.aimove).setClickable(false);
        pawnPromoter.setVisibility(View.VISIBLE);
    }

    public void pawnSelection(View view) throws IOException, ClassNotFoundException {
        String pos = toStr(newi, newj);
        String color;
        if (counter % 2 == 0) color = "black";
        else color = "white";
        pawnPromoter.setVisibility(View.INVISIBLE);
        switch(view.getId()) {
            case(R.id.pawn2queen): {board.boardArray[newi][newj] = new Queen(color, pos); break;}
            case(R.id.pawn2bishop): {board.boardArray[newi][newj] = new Bishop(color, pos); break;}
            case(R.id.pawn2knight): {board.boardArray[newi][newj] = new Knight(color, pos); break;}
            case(R.id.pawn2rook): {board.boardArray[newi][newj] = new Rook(color, pos); break;}
            default: pawnSelectionInt = 1;
        }
        this.drawBoard(board);
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                squares[i][j].setClickable(true);
            }
        }
        findViewById(R.id.resign).setClickable(true);
        findViewById(R.id.draw).setClickable(true);
        findViewById(R.id.undo).setClickable(true);
        findViewById(R.id.aimove).setClickable(true);
        return;
    }
}

package com.example.android16;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Welcome To Chess!");
        newGame = findViewById(R.id.newGame);
    }

    public void newGame(View view) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, PlayGame.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void oldGames(View view) {
       Bundle bundle = new Bundle();
       Intent intent = new Intent(this, OldGamesList.class);
       intent.putExtras(bundle);
       startActivity(intent);
    }
}
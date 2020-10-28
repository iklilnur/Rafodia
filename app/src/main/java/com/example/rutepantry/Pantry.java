package com.example.rutepantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Pantry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        //memasang onclick listener
        RelativeLayout empty_groceries = (RelativeLayout) findViewById(R.id.tanggal2);
        RelativeLayout filled_groceries = (RelativeLayout) findViewById(R.id.tanggal1);
        Button home = (Button) findViewById(R.id.homeButton);
        Button back = (Button) findViewById(R.id.backButton);
        Button add_pantry = (Button) findViewById(R.id.addPantry);

        empty_groceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyGroceries();
            }
        });
        filled_groceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filledGroceries();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        add_pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPantry();
            }
        });
    }

    protected void emptyGroceries(){
        Intent intent = new Intent(this, EmptyGroceries.class);
        startActivity(intent);
    }

    protected void filledGroceries(){
        Intent intent = new Intent(this, FilledGroceries.class);
        startActivity(intent);
    }

    protected void home(){
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
    }

    protected void addPantry(){
        Intent intent = new Intent(this, InputGroceries.class);
        startActivity(intent);
    }

    protected void back(){
        Intent intent = new Intent(this, StartPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
    }
}
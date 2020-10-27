package com.example.rutepantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmptyGroceries extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_groceries);

        Button home = (Button) findViewById(R.id.homeButton);
        Button back_button = (Button) findViewById(R.id.backButton);
        Button add_item = (Button) findViewById(R.id.addItem);
        Button pantry = (Button) findViewById(R.id.pantryButton);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantry();
            }
        });
    }

    protected void back(){
        Intent intent = new Intent(this, Pantry.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        finish();
    }

    protected void home(){
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
    }

    protected void pantry(){
        Intent intent = new Intent(this, Pantry.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        finish();
    }


    protected void addItem(){
        Intent intent = new Intent(this, InputItem.class);
        startActivity(intent);
    }
}
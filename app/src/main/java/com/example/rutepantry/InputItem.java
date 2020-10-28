package com.example.rutepantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InputItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_item);

        Button home = (Button) findViewById(R.id.homeButton);
        Button back_button = (Button) findViewById(R.id.backButton);
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

    protected void pantry(){
        Intent intent = new Intent(this, Pantry.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
        finish();
    }

    protected void home(){
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
    }
}
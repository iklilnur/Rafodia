package com.example.rutepantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Foodpedia_category extends AppCompatActivity {


    Button veg_button;
    Button fruit_button;
    Button meat_button;
    Button seafood_button;
    Button other_button;
    Button home_button;
    Button back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodpedia_category);

        home_button = (Button) findViewById(R.id.homeButton);
        back_button = (Button) findViewById(R.id.backButton);
        veg_button = (Button) findViewById(R.id.button_veg);
        fruit_button = (Button) findViewById(R.id.button_fruit);
        meat_button = (Button) findViewById(R.id.button_meat);
        seafood_button = (Button) findViewById(R.id.button_seafood);
        other_button = (Button) findViewById(R.id.button_other);

        home_button.setOnClickListener(new View.OnClickListener() {
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
        veg_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                vegetables();
            }
        });
        fruit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fruit();
            }
        });
        meat_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                meat();
            }
        });
        seafood_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                seafood();
            }
        });
        other_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                otherIngredients();
            }
        });
    }

    public void home() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    public void otherIngredients() {
        Intent intent = new Intent(this, CategoryItem.class);
        startActivity(intent);
    }

    public void seafood() {
        Intent intent = new Intent(this, CategoryItem.class);
        startActivity(intent);
    }

    public void meat() {
        Intent intent = new Intent(this, CategoryItem.class);
        startActivity(intent);
    }

    public void fruit() {
        Intent intent = new Intent(this, CategoryItem.class);
        startActivity(intent);
    }

    public void vegetables() {
        Intent intent = new Intent(this, CategoryItem.class);
        startActivity(intent);
    }
    protected void back() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(intent, 0);
    }
}
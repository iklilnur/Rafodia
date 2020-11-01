package com.example.rutepantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Home extends AppCompatActivity {

    RelativeLayout food_pedia;
    RelativeLayout pantry_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        food_pedia = (RelativeLayout) findViewById(R.id.buttonFoodPedia);
        pantry_button = (RelativeLayout) findViewById(R.id.buttonPantry);

        pantry_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                pantry();
            }
        });
        food_pedia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                foodPedia();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //remove call to the super class
        //super.onBackPressed();
    }

    public void foodPedia(){
        Intent intent = new Intent(this, Foodpedia_category.class);
        startActivity(intent);
    }

    public void pantry(){
        Intent intent = new Intent(this, Pantry.class);
        startActivity(intent);
    }
}
package com.example.rutepantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryItem extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);

        Button home = (Button) findViewById(R.id.homeButton);
        Button back_button = (Button) findViewById(R.id.backButton);
        Button foodpedia = (Button) findViewById(R.id.foodpediaButton);
        Button item1 = (Button) findViewById(R.id.Item1Button);
        Button item2 = (Button) findViewById(R.id.Item2Button);
        Button item3 = (Button) findViewById(R.id.Item3Button);


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

        foodpedia.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               foodpedia();
            }
       });

        item1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View view){
        item1();
        }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                item2();
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                item3();
            }
        });
    }

    protected void home(){
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
    }
    protected void back(){
        Intent intent = new Intent(this, Foodpedia_category.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivityIfNeeded(intent, 0);
    }

    protected void foodpedia() {
        Intent intent = new Intent (this, Foodpedia_category.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivityIfNeeded(intent, 0);
    }

    protected void item1(){
        Intent intent = new Intent(this, ItemDetail.class);
        startActivity(intent);
    }
    protected void item2() {
        Intent intent = new Intent(this, itemDetail2.class);
        startActivity(intent);
    }
    protected void item3(){
        Intent intent = new Intent(this, itemDetail3.class);
        startActivity(intent);

    } }
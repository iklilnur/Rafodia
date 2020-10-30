package com.example.rutepantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetail extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Button home = (Button) findViewById(R.id.homeButton);
        Button back_button = (Button) findViewById(R.id.backButton);
        //Button foodpedia = (Button) findViewById((R.id.foodpediaButton);

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

        //foodpedia.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View view) {
                //foodpedia()
            //}
        //});
    }
    protected void home() {
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
    }

    protected void back(){
    Intent intent = new Intent(this, CategoryItem.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    startActivityIfNeeded(intent, 0);
    finish();
    }

    //protected void foodpedia() {
    //Intent intent = new Intent (this, .class) (--kembali ke pilihan kategori, activity B--)
    //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    //startActivityIfNeeded(intent, 0);
    // finish();
    //}
}

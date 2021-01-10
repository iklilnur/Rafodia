package com.example.rutepantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class Foodpedia_category extends AppCompatActivity {

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.btn_buah, R.drawable.btn_sayuran, R.drawable.btn_daging, R.drawable.btn_seafood, R.drawable.btn_lain_lain};

    String kategori;

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
        setContentView(R.layout.activity_foodpedia_new);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
//                Toast.makeText(Foodpedia_category.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
                switch(position){
                    case 0:
                        kategori = "buah";
                        break;
                    case 1:
                        kategori = "sayuran";
                        break;
                    case 2:
                        kategori = "daging";
                        break;
                    case 3:
                        kategori = "seafood";
                        break;
                    case 4:
                        kategori = "lain-lain";
                        break;
                }
                categoryItem(kategori);
            }
        });

        back_button = (Button) findViewById(R.id.backButton);
        Button home_foodpedia = (Button) findViewById(R.id.home_foodpedia);
        Button pantry_foodpedia = (Button) findViewById(R.id.pantry_foodpedia);
        Button foodped_foodpedia = (Button) findViewById(R.id.foodped_foodpedia);
        /*veg_button = (Button) findViewById(R.id.button_veg);
        fruit_button = (Button) findViewById(R.id.button_fruit);
        meat_button = (Button) findViewById(R.id.button_meat);
        seafood_button = (Button) findViewById(R.id.button_seafood);
        other_button = (Button) findViewById(R.id.button_other);*/

        home_foodpedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFoodpedia();
            }
        });
        pantry_foodpedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantryFoodpedia();
            }
        });
        foodped_foodpedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodpedFoodpedia();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        /*veg_button.setOnClickListener(new View.OnClickListener(){
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
        });*/
    }

    private void foodpedFoodpedia() {
        Intent intent = new Intent(this, Foodpedia_category.class);
        startActivity(intent);
    }

    private void pantryFoodpedia() {
        Intent intent = new Intent(this, Pantry.class);
        startActivity(intent);
    }

    private void homeFoodpedia() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
            //imageView.setClickable(true);
        }
    };

    public void home() {
        Intent intent = new Intent(this, StartPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivityIfNeeded(intent, 0);
    }

//    public void otherIngredients() {
//        Intent intent = new Intent(this, CategoryItem.class);
//        startActivity(intent);
//    }
//
//    public void seafood() {
//        Intent intent = new Intent(this, CategoryItem.class);
//        startActivity(intent);
//    }
//
//    public void meat() {
//        Intent intent = new Intent(this, CategoryItem.class);
//        startActivity(intent);
//    }
//
//    public void fruit() {
//        Intent intent = new Intent(this, CategoryItem.class);
//        startActivity(intent);
//    }
//
//    public void vegetables() {
//        Intent intent = new Intent(this, CategoryItem.class);
//        startActivity(intent);
//    }

    public void categoryItem(String category){
        Intent intent = new Intent(this, CategoryItem.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("kategori", kategori);
        startActivity(intent);
    }

    protected void back() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivityIfNeeded(intent, 0);
    }
}
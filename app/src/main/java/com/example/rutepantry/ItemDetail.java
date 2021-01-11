package com.example.rutepantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemDetail extends AppCompatActivity {
    ImageView item_image;
    String item_id;
    DatabaseHelper mydb;
    ArrayList<String> item;
    TextView title;
    String input_kategori;
    TextView penyimpanan, kadaluarsa, satuan, kesegaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail_new);

        mydb = new DatabaseHelper(getApplicationContext());
        mydb.createDatabase();

        item_id = getIntent().getStringExtra("id");
        input_kategori = getIntent().getStringExtra("kategori");

        title = findViewById(R.id.title2);
        title.setText(input_kategori.toUpperCase());

        item = mydb.getItemData(Integer.parseInt(item_id));

        item_image = findViewById(R.id.itemImage);


        String image_name = item.get(1).toLowerCase().replace(" ","_")+"_item_detail";
        int resID = getResources().getIdentifier(image_name, "drawable", "com.example.rutepantry");

        item_image.setImageResource(resID);

        penyimpanan = findViewById(R.id.caraPenyimpanan);
        penyimpanan.setText(item.get(3));

        kesegaran = findViewById(R.id.tingkatKesegaran);
        kesegaran.setText(item.get(4));

        kadaluarsa = findViewById(R.id.waktuKadaluarsa);
        kadaluarsa.setText(item.get(5) +" hari (jika disimpan dengan benar)");

        satuan = findViewById(R.id.satuan);
        satuan.setText(item.get(6));


        Button home_item = (Button) findViewById(R.id.home_item);
        Button pantry_item = (Button) findViewById(R.id.pantry_item);
        Button back_button = (Button) findViewById(R.id.backButton2);
        Button foodped_item = (Button) findViewById(R.id.foodped_item);

//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                home();
//            }
//        });

        back_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        back();
        }
        });
        home_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeItem();
            }
        });
        pantry_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantryItem();
            }
        });
        foodped_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodpedItem();
            }
        });

//        foodpedia.setOnClickListener(new View.OnClickListener() {
//           @Override
//            public void onClick(View view) {
//                foodpedia();
//            }
//        });
    }

    private void foodpedItem() {
        Intent intent = new Intent(this, Foodpedia_category.class);
        startActivity(intent);
    }

    private void pantryItem() {
        Intent intent = new Intent(this, Pantry.class);
        startActivity(intent);
    }

    private void homeItem() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    protected void home() {
        Intent intent = new Intent(this, StartPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivityIfNeeded(intent, 0);
    }

    protected void back(){
        Intent intent = new Intent(this, CategoryItem.class);
        intent.putExtra("kategori", input_kategori);
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
}

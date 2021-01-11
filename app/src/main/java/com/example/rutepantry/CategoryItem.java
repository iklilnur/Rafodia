package com.example.rutepantry;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryItem extends AppCompatActivity implements RecyclerAdapter.ItemClickListener{
    ArrayList<ArrayList<String>> category_items = new ArrayList<>();
    RecyclerAdapter adapter;
    DatabaseHelper mydb;
    String inputKategori;
    EditText search;
    Boolean resultEmpty = false;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_new);


        mydb = new DatabaseHelper(getApplicationContext());
        mydb.createDatabase();

        search = findViewById(R.id.searchItem);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchFilter(editable);
            }
        });

        inputKategori = getIntent().getStringExtra("kategori");

        title = findViewById(R.id.title);
        title.setText(inputKategori.toUpperCase());

        category_items = mydb.getItemCategory(inputKategori);

        RecyclerView recyclerView = findViewById(R.id.categoryItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryItem.this));

        adapter = new RecyclerAdapter(CategoryItem.this, category_items, "category_items");
        adapter.setClickListener(CategoryItem.this);

        recyclerView.setAdapter(adapter);

        //Button home = (Button) findViewById(R.id.homeButton);

        Button back_button = (Button) findViewById(R.id.backButton);
        Button home_kategori = (Button) findViewById(R.id.home_kategori);
        Button pantry_kategori = (Button) findViewById(R.id.pantry_kategori);
        Button foodped_kategori = (Button) findViewById(R.id.foodped_kategori);

        back_button.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               back();
            }
        });
        home_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeKategori();
            }
        });
        pantry_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantryKategori();
            }
        });
        foodped_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodpedKategori();
            }
        });

    }

    private void foodpedKategori() {
        Intent intent = new Intent(this, Foodpedia_category.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void pantryKategori() {
        Intent intent = new Intent(this, Pantry.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void homeKategori() {
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    protected void back(){
        Intent intent = new Intent(this, Foodpedia_category.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    protected void foodpedia() {
        Intent intent = new Intent (this, Foodpedia_category.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivityIfNeeded(intent, 0);
    }

//    protected void item1(){
//        Intent intent = new Intent(this, ItemDetail.class);
//        startActivity(intent);
//    }
//    protected void item2() {
//        Intent intent = new Intent(this, ItemDetail2.class);
//        startActivity(intent);
//    }
//    protected void item3(){
//        Intent intent = new Intent(this, ItemDetail3.class);
//        startActivity(intent);
//
//    }
    @Override
    public void onItemClick(View view, int position) {
        if(resultEmpty == false){
            Intent intent = new Intent(this, ItemDetail.class);
            intent.putExtra("id", category_items.get(position).get(0));
            intent.putExtra("kategori", inputKategori);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    private void searchFilter(Editable editable){
        String res = editable.toString();
        if(res.equals("")){
            category_items = mydb.getItemCategory(inputKategori);
            resultEmpty = false;

            RecyclerView recyclerView = findViewById(R.id.categoryItems);
            recyclerView.setLayoutManager(new LinearLayoutManager(CategoryItem.this));

            adapter = new RecyclerAdapter(CategoryItem.this, category_items, "category_items");
            adapter.setClickListener(CategoryItem.this);
            recyclerView.setAdapter(adapter);
        }
        else{
            category_items = mydb.getItemName(res, inputKategori);

            if(category_items != null){
                //Toast.makeText(this, category_items.get(0).get(1), Toast.LENGTH_SHORT).show();
                resultEmpty = false;

                RecyclerView recyclerView = findViewById(R.id.categoryItems);
                recyclerView.setLayoutManager(new LinearLayoutManager(CategoryItem.this));

                adapter = new RecyclerAdapter(CategoryItem.this, category_items, "category_items");
                adapter.setClickListener(CategoryItem.this);
                recyclerView.setAdapter(adapter);
            }
            else{
                resultEmpty = true;
                //Toast.makeText(this, "Item tidak ditemukan", Toast.LENGTH_SHORT).show();
                ArrayList<String> kosong = new ArrayList<>();
                kosong.add("");
                kosong.add("Bahan tidak ditemukan.");
                category_items = new ArrayList<>();
                category_items.add(kosong);

                RecyclerView recyclerView = findViewById(R.id.categoryItems);
                recyclerView.setLayoutManager(new LinearLayoutManager(CategoryItem.this));

                adapter = new RecyclerAdapter(CategoryItem.this, category_items, "category_items");
                adapter.setClickListener(CategoryItem.this);
                recyclerView.setAdapter(adapter);
            }
        }
    }
}
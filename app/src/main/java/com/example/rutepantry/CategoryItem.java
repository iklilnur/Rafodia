package com.example.rutepantry;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        category_items = mydb.getItemCategory(inputKategori);

        RecyclerView recyclerView = findViewById(R.id.categoryItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryItem.this));

        adapter = new RecyclerAdapter(CategoryItem.this, category_items, "category_items");
        adapter.setClickListener(CategoryItem.this);

        recyclerView.setAdapter(adapter);

        //Button home = (Button) findViewById(R.id.homeButton);

        Button back_button = (Button) findViewById(R.id.backButton);
        //Button foodpedia = (Button) findViewById(R.id.foodpediaButton);
//        Button item1 = (Button) findViewById(R.id.Item1Button);
//        Button item2 = (Button) findViewById(R.id.Item2Button);
//        Button item3 = (Button) findViewById(R.id.Item3Button);


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

//        foodpedia.setOnClickListener(new View.OnClickListener() {
//           @Override
//            public void onClick(View view) {
//               foodpedia();
//            }
//       });

//        item1.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick (View view){
//        item1();
//        }
//        });
//
//        item2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view){
//                item2();
//            }
//        });
//
//        item3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view){
//                item3();
//            }
//        });
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
            Toast.makeText(this, "Position: "+position, Toast.LENGTH_SHORT).show();
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
            category_items = mydb.getItemName(res);

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
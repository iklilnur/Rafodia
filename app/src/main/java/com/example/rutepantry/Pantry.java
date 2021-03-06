package com.example.rutepantry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Pantry extends AppCompatActivity implements RecyclerAdapter.ItemClickListener{
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    DatePickerDialog dateDialog;
    SimpleDateFormat dateFormat;
    ArrayList<ArrayList<String>> groceries_items = new ArrayList<>();
    RecyclerAdapter adapter;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_new);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        mydb = new DatabaseHelper(getApplicationContext());
        mydb.createDatabase();

        setRecyclerView();

        //memasang onclick listener
        //Button home = (Button) findViewById(R.id.homeButton);
        Button back = (Button) findViewById(R.id.backButton);
        Button add_pantry = (Button) findViewById(R.id.tambah_pantry);
        Button home_pantry = (Button) findViewById(R.id.home_pantry);
        Button foodped_pantry = (Button) findViewById(R.id.foodped_pantry);

        /*home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home();
            }
        });*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        add_pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPantry();
            }
        });
        home_pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homePantry();
            }
        });
        foodped_pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodpedPantry();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        setRecyclerView();
    }

    private void foodpedPantry() {
        Intent intent = new Intent(this, Foodpedia_category.class);
        startActivity(intent);
    }

    private void homePantry() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    protected void filledGroceries(){
        Intent intent = new Intent(this, FilledGroceries.class);
        startActivity(intent);
    }

    protected void home(){
        Intent intent = new Intent(this, StartPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivityIfNeeded(intent, 0);
    }

    protected void addPantry(){
        DateDialog();
    }

    protected void back(){
        Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void DateDialog() {
        Calendar newCalendar = Calendar.getInstance();

        dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                //ArrayList<String> item = new ArrayList<>();
                String date = dateFormat.format(newDate.getTime());
                Integer qty = 0;
                mydb.insertGroceries(date, qty);

                //groceries_items.add(item);
                // set up the RecyclerView
                setRecyclerView();

                //Toast.makeText(Pantry.this, "Tanggal yang dipilih: "+dateFormat.format(newDate.getTime()), Toast.LENGTH_SHORT).show();
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        dateDialog.show();
    }

    public void setRecyclerView(){
        groceries_items = sortDate(mydb.getAllGroceries());

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.groceriesItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(Pantry.this));

        adapter = new RecyclerAdapter(Pantry.this, groceries_items, "pantry");
        adapter.setClickListener(Pantry.this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        String id_groceries = groceries_items.get(position).get(2);
        Intent intent = new Intent(this, FilledGroceries.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("id_groceries", id_groceries);
        startActivity(intent);
    }

    private ArrayList<ArrayList<String>> sortDate(ArrayList<ArrayList<String>> date){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        for(int i=0; i<date.size()-1; i++){
            for(int j=i; j<date.size()-1; j++){

                try {
                    Date curDate = format.parse(date.get(i).get(0));
                    Date nextDate = format.parse(date.get(j+1).get(0));
                    ArrayList<String> temp;

                    if(nextDate.getTime() > curDate.getTime()){
                        temp = date.get(i);
                        date.set(i,date.get(j+1));
                        date.set(j+1, temp);
                    }
                }
                catch(ParseException pe){
                    pe.printStackTrace();
                }
            }
        }


        return date;
    }

}
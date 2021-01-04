package com.example.rutepantry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FilledGroceries extends AppCompatActivity implements RecyclerAdapter.ItemClickListener {
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    ArrayList<String> groceries = new ArrayList<>();
    ArrayList<ArrayList<String>> groceries_items = new ArrayList<>();
    ArrayList<ArrayList<String>> items = new ArrayList<>();
    ArrayList<ArrayList<String>> items_for_recycler = new ArrayList<>();
    ArrayList<String> items_name = new ArrayList<>();
    RecyclerAdapter adapter;
    String selectedItem;
    DatabaseHelper mydb;
    Integer id_groceries;
    TextView satuan;
    Button ubah, hapus;
    TextView penyimpanan, kesegaran, kadaluarsa, jumlahTitle;
    EditText jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries_new);

        //mengambil intent
        id_groceries = Integer.parseInt(getIntent().getStringExtra("id_groceries"));


        mydb = new DatabaseHelper(getApplicationContext());
        mydb.createDatabase();

        groceries = mydb.getGroceriesData(id_groceries);
        TextView groceries_date = (TextView) findViewById(R.id.date);
        groceries_date.setText(groceries.get(1));

        setRecyclerView();

        items = mydb.getAllItems();

        for(int i = 0; i<items.size();i++ ){
            items_name.add(items.get(i).get(1));
        }

        //Button home = (Button) findViewById(R.id.homeButton);
        Button back_button = (Button) findViewById(R.id.backButton);
        Button add_item = (Button) findViewById(R.id.addItem);
        //Button pantry = (Button) findViewById(R.id.pantryButton);

        /*home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home();
            }
        });*/

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        /*pantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pantry();
            }
        });*/
    }

    protected void back(){
        Intent intent = new Intent(this, Pantry.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        finish();
        startActivityIfNeeded(intent, 0);
    }

    protected void home(){
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
    }

    protected void pantry(){
        Intent intent = new Intent(this, Pantry.class);
        startActivity(intent);
    }


    protected void addItem(){
        InputDialog();
    }

    private void detailDialog(Integer position) {
        dialog = new AlertDialog.Builder(FilledGroceries.this);
        final Integer pos = position;
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.detail_item, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        ArrayList<String> item = new ArrayList<>();
        item = mydb.getItemData(Integer.parseInt(groceries_items.get(position).get(3)));

        jumlahTitle = dialogView.findViewById(R.id.sTitle2);
        jumlahTitle.setText("Jumlah (Satuan "+item.get(6)+")");

        penyimpanan = dialogView.findViewById(R.id.caraPenyimpanan2);
        penyimpanan.setText(item.get(3));

        kesegaran = dialogView.findViewById(R.id.tingkatKesegaran2);
        kesegaran.setText(item.get(4));

        kadaluarsa = dialogView.findViewById(R.id.waktuKadaluarsa2);

        jumlah = dialogView.findViewById(R.id.jumlah2);
        jumlah.setEnabled(false);
        //jumlah.setInputType(InputType.TYPE_NULL);
        jumlah.setFocusable(false);
        jumlah.setText(items_for_recycler.get(position).get(1));

        ubah = dialogView.findViewById(R.id.edit);
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ubah.getText().toString().equals("ubah")){
                    ubah.setText("simpan");
                    jumlah.setEnabled(true);
                    jumlah.setFocusableInTouchMode(true);
                    //jumlah.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                else{
                    ubah.setText("ubah");
                    mydb.updateGroceriesItem(Integer.parseInt(groceries_items.get(pos).get(0)), Integer.parseInt(jumlah.getText().toString()));
                    jumlah.setEnabled(false);
                   //jumlah.setInputType(InputType.TYPE_NULL);
                    jumlah.setFocusable(false);
                    setRecyclerView();
                }
            }
        });

        hapus = dialogView.findViewById(R.id.delete);
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydb.deleteGroceriesItem(Integer.parseInt(groceries_items.get(pos).get(0)));
                setRecyclerView();
            }
        });

        dialog.setNegativeButton("TUTUP", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void InputDialog() {
        dialog = new AlertDialog.Builder(FilledGroceries.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_input_item, null);

        satuan = dialogView.findViewById(R.id.satuanInput);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        Spinner dropdown = dialogView.findViewById(R.id.item);
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedItem = items.get(position).get(0);

                if(items.get(position).get(6).equals("kg")){
                    satuan.setText("Kg");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_name);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(spinner_adapter);

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText_qty = (EditText) dialogView.findViewById(R.id.qty);
                String qty_text = editText_qty.getText().toString();

                if(qty_text.equals("") || qty_text == null){
                    Toast.makeText(FilledGroceries.this, "Jumlah bahan harus diisi!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Integer qty = Integer.parseInt(qty_text);
                    Integer id_g = id_groceries;
                    Integer id_i = Integer.parseInt(selectedItem);


                    mydb.insertGroceriesItem(qty, id_g, id_i);

                    setRecyclerView();

                    dialog.dismiss();
                }
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onItemClick(View view, int position) {
        this.detailDialog(position);
    }

    private void setRecyclerView(){
        groceries_items = mydb.getGroceriesItems(id_groceries);
        items_for_recycler = new ArrayList<>();

        for(int i = 0;i<groceries_items.size(); i++){
            ArrayList<String> item = new ArrayList<>();
            item = mydb.getItemData(Integer.parseInt(groceries_items.get(i).get(3)));

            ArrayList<String> recycler_item = new ArrayList<>();
            recycler_item.add(item.get(1));
            recycler_item.add(groceries_items.get(i).get(1));

            items_for_recycler.add(recycler_item);
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.groceriesItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(FilledGroceries.this));

        adapter = new RecyclerAdapter(FilledGroceries.this, items_for_recycler, "groceries");
        adapter.setClickListener(FilledGroceries.this);

        recyclerView.setAdapter(adapter);
    }
}
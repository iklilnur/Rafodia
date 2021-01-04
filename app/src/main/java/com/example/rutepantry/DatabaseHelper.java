package com.example.rutepantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static String DB_NAME = "rafodia_db.sql";
    private static String DB_PATH = "";
    public static final String GROCERIES_TABLE_NAME = "groceries";
    public static final String GROCERIES_COLUMN_ID = "ID_groceries";
    public static final String GROCERIES_COLUMN_DATE = "tanggal_groceries";
    public static final String GROCERIES_COLUMN_QTY = "item_qty";
    public static final String GROCERIES_ITEMS_COLUMN_ID = "ID_groceries_item";
    public static final String GROCERIES_ITEMS_COLUMN_QTY = "groceries_item_qty";
    public static final String ITEMS_COLUMN_ID = "ID_item";
    public static final String ITEMS_COLUMN_NAME = "nama_item";
    public static final String ITEMS_COLUMN_CATEGORY = "kategori_item";
    public static final String ITEMS_COLUMN_CARA_PENYIMPANAN = "cara_penyimpanan";
    public static final String ITEMS_COLUMN_TINGKAT_KESEGARAN = "tingkat_kesegaran";
    public static final String ITEMS_COLUMN_WAKTU_KADALUARSA = "waktu_kadaluarsa";
    public static final String ITEMS_COLUMN_SATUAN = "satuan";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        //copyDataBase();

        //this.getReadableDatabase();
    }

    //GROCERIES AREA

    public boolean insertGroceries (String date, Integer qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tanggal_groceries", date);
        contentValues.put("item_qty", qty);
        Cursor res = db.rawQuery( "SELECT * FROM groceries WHERE tanggal_groceries LIKE '"+date+"'", null );
        if(res.getCount() == 0){
            db.insert("groceries", null, contentValues);
        }
        else{
            Toast.makeText(mContext, "Tanggal "+date+" sudah terdaftar.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public ArrayList<String> getGroceriesData(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery( "select * from groceries where ID_groceries LIKE '"+id+"'", null );
        res.moveToFirst();
        ArrayList<String> row = new ArrayList<>();
        row.add(res.getString(res.getColumnIndex(GROCERIES_COLUMN_ID)));
        row.add(res.getString(res.getColumnIndex(GROCERIES_COLUMN_DATE)));
        row.add(res.getString(res.getColumnIndex(GROCERIES_COLUMN_QTY)));
        res.close();
        return row;
    }

    public int groceriesRowsCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, GROCERIES_TABLE_NAME);
        return numRows;
    }

    public boolean updateGroceries (Integer id, String date, Integer qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(!date.equals("")) {
            contentValues.put("tanggal_groceries", date);
        }
        contentValues.put("item_qty", qty);
        db.update("groceries", contentValues, "ID_groceries = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteGroceries (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("groceries",
                "ID_groceries = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<ArrayList<String>> getAllGroceries() {
        ArrayList<ArrayList<String>> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res;
        try{
            res = db.rawQuery( "SELECT * FROM groceries ORDER BY tanggal_groceries DESC", null );
            Log.d("alldata", "Success");
            if(res == null){
                return null;
            }
            res.moveToFirst();
            do{
                ArrayList<String> row = new ArrayList<>();
                row.add(res.getString(res.getColumnIndex(GROCERIES_COLUMN_DATE)));
                row.add(res.getString(res.getColumnIndex(GROCERIES_COLUMN_QTY)));
                row.add(res.getString(res.getColumnIndex(GROCERIES_COLUMN_ID)));
                array_list.add(row);

            }while(res.moveToNext());
            res.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        //db.close();
        return array_list;
    }

    //GROCERIES ITEM AREA

    public boolean insertGroceriesItem (Integer qty, Integer ID_groceries, Integer ID_item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GROCERIES_ITEMS_COLUMN_QTY, qty);
        contentValues.put(GROCERIES_COLUMN_ID, ID_groceries);
        contentValues.put(ITEMS_COLUMN_ID, ID_item);
        ArrayList<String> curGroceries = new ArrayList<>();
        Cursor res = db.rawQuery( "select * from groceries_item where ID_item LIKE '"+ID_item+"' AND ID_groceries LIKE '"+ID_groceries+"'", null );
        if(res.getCount() == 0){
            db.insert("groceries_item", null, contentValues);
            curGroceries = this.getGroceriesData(ID_groceries);
            Integer curGroceriesQty = Integer.parseInt(curGroceries.get(2));
            curGroceriesQty = curGroceriesQty + 1;
            this.updateGroceries(ID_groceries, "", curGroceriesQty);
            //Toast.makeText(mContext, "berhasil input groceries item", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(mContext, "Bahan tersebut sudah terdaftar. Silahkan edit jika ingin mengganti jumlah.", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public ArrayList<String> getGroceriesItemData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery( "select * from groceries_item where ID_groceries_item LIKE '"+id+"'", null );
        res.moveToFirst();
        ArrayList<String> row = new ArrayList<>();
        row.add(res.getString(res.getColumnIndex(GROCERIES_ITEMS_COLUMN_ID)));
        row.add(res.getString(res.getColumnIndex(GROCERIES_ITEMS_COLUMN_QTY)));
        row.add(res.getString(res.getColumnIndex(GROCERIES_COLUMN_ID)));
        row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_ID)));
        res.close();
        return row;
    }

    public int groceriesItemRowsCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, GROCERIES_TABLE_NAME);
        return numRows;
    }

    public boolean updateGroceriesItem (Integer id, Integer qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("groceries_item_qty", qty);
        db.update("groceries_item", contentValues, "ID_groceries_item = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteGroceriesItem (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery( "select * from groceries_item where ID_groceries_item LIKE '"+id+"'", null );
        ArrayList<String> curGroceries = new ArrayList<>();
        if(res.getCount() != 0){
            res.moveToFirst();
            Integer groceriesID = Integer.parseInt(res.getString(res.getColumnIndex(GROCERIES_COLUMN_ID)));
            curGroceries = this.getGroceriesData(groceriesID);
            Integer curGroceriesQty = Integer.parseInt(curGroceries.get(2));
            curGroceriesQty = curGroceriesQty - 1;
            this.updateGroceries(groceriesID, "", curGroceriesQty);
            //Toast.makeText(mContext, "berhasil input groceries item", Toast.LENGTH_SHORT).show();
            return db.delete("groceries_item",
                    "ID_groceries_item = ? ",
                    new String[] { Integer.toString(id) });
        }
        else{
            return null;
        }
    }

    public ArrayList<ArrayList<String>> getGroceriesItems(Integer groceriesID) {
        ArrayList<ArrayList<String>> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res;
        try{
            res = db.rawQuery( "SELECT * FROM groceries_item WHERE ID_groceries LIKE '"+groceriesID+"' ORDER BY ID_groceries_item ASC", null );
            Log.d("alldata", "Success");
            if(res == null){
                return null;
            }
            res.moveToFirst();
            do{
                ArrayList<String> row = new ArrayList<>();
                row.add(res.getString(res.getColumnIndex(GROCERIES_ITEMS_COLUMN_ID)));
                row.add(res.getString(res.getColumnIndex(GROCERIES_ITEMS_COLUMN_QTY)));
                row.add(res.getString(res.getColumnIndex(GROCERIES_COLUMN_ID)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_ID)));
                array_list.add(row);

            }while(res.moveToNext());
            res.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        //db.close();
        return array_list;
    }

    //ITEM AREA

    public ArrayList<ArrayList<String>> getAllItems() {
        ArrayList<ArrayList<String>> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res;
        try{
            res = db.rawQuery( "SELECT * FROM item ORDER BY nama_item ASC", null );
            Log.d("alldata", "Success");
            if(res == null){
                return null;
            }
            res.moveToFirst();
            do{
                ArrayList<String> row = new ArrayList<>();
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_ID)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_NAME)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_CATEGORY)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_CARA_PENYIMPANAN)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_TINGKAT_KESEGARAN)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_WAKTU_KADALUARSA)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_SATUAN)));
                array_list.add(row);

            }while(res.moveToNext());
            res.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        //db.close();
        return array_list;
    }

    public boolean insertItem (String nama_item, String kategori_item, String cara_penyimpanan, String tingkat_kesegaran, Integer waktu_kadaluarsa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEMS_COLUMN_CARA_PENYIMPANAN, cara_penyimpanan);
        contentValues.put(ITEMS_COLUMN_NAME, nama_item);
        contentValues.put(ITEMS_COLUMN_TINGKAT_KESEGARAN, tingkat_kesegaran );
        contentValues.put(ITEMS_COLUMN_CATEGORY, kategori_item);
        contentValues.put(ITEMS_COLUMN_WAKTU_KADALUARSA, waktu_kadaluarsa);
        Cursor res = db.rawQuery( "select * from item where nama_item LIKE '"+nama_item+"'", null );
        if(res.getCount() == 0){
            db.insert("item", null, contentValues);
        }
        else{
            Toast.makeText(mContext, "Bahan tersebut sudah terdaftar.", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public ArrayList<String> getItemData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery( "select * from item where ID_item LIKE '"+id+"'", null );
        res.moveToFirst();
        ArrayList<String> row = new ArrayList<>();
        row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_ID)));
        row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_NAME)));
        row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_CATEGORY)));
        row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_CARA_PENYIMPANAN)));
        row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_TINGKAT_KESEGARAN)));
        row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_WAKTU_KADALUARSA)));
        row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_SATUAN)));
        res.close();
        return row;
    }

    public ArrayList<ArrayList<String>> getItemCategory(String kategori) {
        ArrayList<ArrayList<String>> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res;
        try{
            res = db.rawQuery( "SELECT * FROM item WHERE kategori_item LIKE '%"+kategori+"%' ORDER BY nama_item ASC", null );
            Log.d("alldata", "Success");
            if(res == null){
                return null;
            }
            res.moveToFirst();
            do{
                ArrayList<String> row = new ArrayList<>();
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_ID)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_NAME)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_CATEGORY)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_CARA_PENYIMPANAN)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_TINGKAT_KESEGARAN)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_WAKTU_KADALUARSA)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_SATUAN)));
                array_list.add(row);

            }while(res.moveToNext());
            res.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        //db.close();
        return array_list;
    }

    public ArrayList<ArrayList<String>> getItemName(String name, String kategori) {
        ArrayList<ArrayList<String>> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res;
        try{
            res = db.rawQuery( "SELECT * FROM item WHERE nama_item LIKE '%"+name+"%' AND WHERE kategori_item LIKE '%"+kategori+"%' ORDER BY nama_item ASC", null );
            Log.d("alldata", "Success");
            if(res.getCount() == 0){
                return null;
            }
            res.moveToFirst();
            do{
                ArrayList<String> row = new ArrayList<>();
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_ID)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_NAME)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_CATEGORY)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_CARA_PENYIMPANAN)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_TINGKAT_KESEGARAN)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_WAKTU_KADALUARSA)));
                row.add(res.getString(res.getColumnIndex(ITEMS_COLUMN_SATUAN)));
                array_list.add(row);

            }while(res.moveToNext());
            res.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        //db.close();
        return array_list;
    }

    public int itemRowsCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, GROCERIES_TABLE_NAME);
        return numRows;
    }

    public boolean updateItem (Integer id, String date, Integer qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tanggal_groceries", date);
        contentValues.put("item_qty", qty);
        db.update("groceries_item", contentValues, "ID_groceries = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteItem (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("groceries_item",
                "ID_groceries = ? ",
                new String[] { Integer.toString(id) });
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }


    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try{
            String path = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        if(tempDB != null)
            tempDB.close();
        return tempDB!=null?true:false;
        //File dbFile = new File(DB_PATH + DB_NAME);
        //return dbFile.exists();
    }

    private void copyDataBase() {
        try{
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH+DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while((length=myInput.read(buffer))> 0){
                myOutput.write(buffer,0,length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        /*if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }*/
    }
    public int insertFromFile(Context context, int resourceId) throws IOException {
        // Reseting Counter
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        // Open the resource
        InputStream insertsStream = context.getResources().openRawResource(resourceId);
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(insertsStream));

        // Iterate through lines (assuming each insert has its own line and theres no other stuff)
        String exec = "";
        while (insertReader.ready()) {
            String insertStmt = insertReader.readLine();
            if(insertStmt.substring(insertStmt.length()-1).equals(";")){
                exec += insertStmt;
                db.execSQL(exec);
                exec = "";
            }
            else{
                exec += insertStmt;
            }
            result++;
        }
        insertReader.close();

        // returning number of inserted rows
        return result;
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.rafodia_db);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);

        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public void openDataBase() throws SQLException {
        String path = DB_PATH+DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDatabase(){
        boolean isDBExist = checkDataBase();
        if(isDBExist){
        }
        else{
            this.getReadableDatabase();
            try{
                insertFromFile(mContext, R.raw.rafodia_db);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d("createDb","success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }
}

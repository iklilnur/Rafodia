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
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static String DB_NAME = "rafodia_db.sql";
    private static String DB_PATH = "";
    public static final String GROCERIES_TABLE_NAME = "groceries";
    public static final String GROCERIES_COLUMN_ID = "ID_groceries";
    public static final String GROCERIES_COLUMN_DATE = "tanggal_groceries";
    public static final String GROCERIES_COLUMN_QTY = "item_qty";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
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

    public boolean insertGroceries (String date, Integer qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tanggal_groceries", date);
        contentValues.put("item_qty", qty);
        Toast.makeText(mContext, "Masuk insert groceries", Toast.LENGTH_SHORT).show();
        db.insert("groceries", null, contentValues);
        return true;
    }

    public Cursor getGroceriesData(int id){
        SQLiteDatabase db = mDataBase;
        Cursor res =  db.rawQuery( "select * from groceries where ID_groceries="+id+"", null );
        return res;
    }

    public int groceriesRowsCount(){
        SQLiteDatabase db = mDataBase;
        int numRows = (int) DatabaseUtils.queryNumEntries(db, GROCERIES_TABLE_NAME);
        return numRows;
    }

    public boolean updateGroceries (Integer id, String date, Integer qty) {
        SQLiteDatabase db = mDataBase;
        ContentValues contentValues = new ContentValues();
        contentValues.put("tanggal_groceries", date);
        contentValues.put("item_qty", qty);
        db.update("groceries", contentValues, "ID_groceries = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteGroceries (Integer id) {
        SQLiteDatabase db = mDataBase;
        return db.delete("groceries",
                "ID_groceries = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<ArrayList<String>> getAllGroceries() {
        ArrayList<ArrayList<String>> array_list = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getWritableDatabase();
        Toast.makeText(mContext, db.toString(), Toast.LENGTH_SHORT).show();

        Cursor res;
        try{
            res = db.rawQuery( "SELECT * FROM groceries ORDER BY tanggal_groceries DESC", null );
            Log.d("alldata", "Success");
            if(res == null){
                return null;
            }
            res.moveToFirst();
            Toast.makeText(mContext, "tes"+res.getString(res.getColumnIndex(GROCERIES_COLUMN_DATE)), Toast.LENGTH_SHORT).show();
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

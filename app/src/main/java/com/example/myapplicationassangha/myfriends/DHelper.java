package com.example.myapplicationassangha.myfriends;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class DHelper extends SQLiteOpenHelper{
    String TAG = "panga";
    public static final String DATABASE_NAME = "lab6.db";
    public static final String TABLE_NAME = "lab6";

    private static String DB_PATH = "/data/data/com.example.myapplicationassangha.myfriends/databases/";

    private SQLiteDatabase myDataBase;

    private final Context myContext;


    public DHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.myContext=context;
        Log.d(TAG, "CREATE");
    }

    public void createDataBase() throws IOException {
        Log.d(TAG, "CREATE2");
        boolean dbExist = checkDataBase();
        if(dbExist){
            Log.d(TAG, "EXISTS");
        }else{
            Toast.makeText(myContext,"Database is not found", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkDataBase(){
        Log.d(TAG, "CHECK");
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            Log.d(TAG, "WOW");
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }



    private void copyDataBase() throws IOException{
        Log.d(TAG, "COPY");
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select Name from "+ TABLE_NAME, null);
        return res;
    }

    public void openDataBase() throws SQLException {

        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

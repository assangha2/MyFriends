package com.example.myapplicationassangha.myfriends;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    String keyWord;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.listView);
        Button bCheckDb;

        final DHelper myDbHelper = new DHelper(this);
        db = myDbHelper.getWritableDatabase();
        String o = "panga";
        final Cursor curs = db.rawQuery("SELECT _id, Name FROM lab6", null);
        CAdapter adapt = new CAdapter(this, curs);
        lv.setAdapter(adapt);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {


                String query = "SELECT _id, Email, Phone from lab6 where _id = " + (position+1);
                Cursor cur = db.rawQuery(query,null);
                String email=" ";
                String phone=" ";
                Log.i("gsfg", cur.getCount() + "fhgdfhdf");
                cur.moveToFirst();
                do {
                    email = cur.getString(cur.getColumnIndexOrThrow("Email"));
                    phone = cur.getString(cur.getColumnIndexOrThrow("Phone"));

                    cur.close();
                }while (cur.moveToNext());

                TextView c = (TextView) view.findViewById(R.id.lview);
                String keyWord = c.getText().toString();
                Toast.makeText(getApplicationContext(), "Phone: " + phone + " Email: " + email, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void viewAll(Cursor s){
        CAdapter adapt1 = new CAdapter(this, s);

        StringBuffer buffer = new StringBuffer();
        while(s.moveToNext()){
            buffer.append("Id :"+ s.getString(0)+"\n");
            buffer.append("Name :"+ s.getString(1)+"\n");
        }
        showMessage("Data", buffer.toString());

    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

       if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.menu_help){
            Intent intent=new Intent(this,help.class);
            startActivityForResult(intent,0);
            return true;
        }
        if(id==R.id.menu_about){
            Intent intent=new Intent(this,about.class);
            startActivityForResult(intent,0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

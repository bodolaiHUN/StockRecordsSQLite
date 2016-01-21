package com.bodoo.stockrecordssqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LekerdezesActivity extends Activity {
    // Declare Variables
    ListViewAdapter adapter;
    String today;
    private DatabaseHandler mClass = new DatabaseHandler(this);
    private ArrayList<Stock> stockdatalist = null;
    private int queryString;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        queryString = i.getIntExtra("queryString", queryString);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        today = df.format(c.getTime());
        lekerdezes(queryString);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lekerdezes(queryString);
    }

    public void lekerdezes(int queryString){
        String[] mQuery = {"SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " ORDER BY " + DatabaseHandler.STOCK_ID + " DESC",
                "SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " WHERE " + DatabaseHandler.SZAV_IDO_FIGYEL + " >= " + today,
                "SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " WHERE " + DatabaseHandler.DARAB + " < " + DatabaseHandler.MIN_DARAB};
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
        ListView myListView = (ListView) findViewById(R.id.listview);
        Toast.makeText(getApplicationContext(), today, Toast.LENGTH_SHORT).show();
        ArrayList<Stock> stockdatalist = new TableControllerStock(this).readCustomQuery(mQuery[queryString]);
        adapter = new ListViewAdapter(LekerdezesActivity.this, stockdatalist);
        myListView.setAdapter(adapter);

    }
}
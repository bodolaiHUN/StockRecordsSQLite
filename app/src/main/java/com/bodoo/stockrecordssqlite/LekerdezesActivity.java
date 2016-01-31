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
    public ArrayList termekList = new ArrayList<>();
    public ArrayList termekek = new ArrayList<>();
    public ArrayList stockdatalist = new ArrayList<>();
    private int queryString;
    TableControllerTermek myTCT = new TableControllerTermek(this);
    TableControllerStock myTCS = new TableControllerStock(this);
    Stock myStock = new Stock();
    Stock myTermek;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        queryString = i.getIntExtra("queryString", queryString);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        today = df.format(c.getTime());
        lekerdezes();
    }

    //@Override
    //protected void onResume() {
    //    super.onResume();
    //    lekerdezes();      //(queryString);
    //}

    public void lekerdezes(int queryString){
        String[] mQuery = {"SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " ORDER BY " + DatabaseHandler.STOCK_ID + " DESC",
                "SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " WHERE " + DatabaseHandler.SZAV_IDO_FIGYEL + " >= " + today,
                "SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " WHERE " + DatabaseHandler.DARAB + " < " + DatabaseHandler.MIN_DARAB};
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
        ListView myListView = (ListView) findViewById(R.id.listview);
        ArrayList<Stock> stockdatalist = new TableControllerStock(this).readCustomQuery(mQuery[queryString]);
        adapter = new ListViewAdapter(LekerdezesActivity.this, stockdatalist);
        myListView.setAdapter(adapter);

    }

    public void lekerdezes(){
        int darabInt = 0, i = 0;
        setContentView(R.layout.listview_main);
        ListView myListView = (ListView) findViewById(R.id.listview);
        ArrayList<Termek> termekList = myTCT.getAll();
        Toast.makeText(getApplicationContext(), Integer.toString(myTCT.count()), Toast.LENGTH_SHORT).show();
        while (i != myTCT.count()){
            String mQuery = "SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " WHERE " + DatabaseHandler.TERMEK + " = " + "'" + termekList.get(i).getTermek() + "'";
            ArrayList<Stock> stockdatalist = myTCS.readCustomQuery(mQuery);
            for (Stock myStock : stockdatalist){
                darabInt += Integer.parseInt(myStock.getDarab());
                //Toast.makeText(getApplicationContext(), Integer.toString(darabInt), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(getApplicationContext(), Integer.toString(i), Toast.LENGTH_SHORT).show();
            myTermek = new Stock();
            myTermek.setTermek(termekList.get(i).getTermek());
            myTermek.setDarab(Integer.toString(darabInt));
            myTermek.setMinDarab(stockdatalist.get(0).getMinDarab());
            termekek.add(myTermek);
            darabInt = 0;
            i++;
        }
        adapter = new ListViewAdapter(LekerdezesActivity.this, termekek);
        myListView.setAdapter(adapter);

    }
}
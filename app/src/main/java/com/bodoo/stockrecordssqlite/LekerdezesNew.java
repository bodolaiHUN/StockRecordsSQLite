package com.bodoo.stockrecordssqlite;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LekerdezesNew extends ExpandableListActivity {
    // Declare Variables
    String today;
    private DatabaseHandler mClass = new DatabaseHandler(this);
    //public ArrayList termekList = new ArrayList<>();
    public ArrayList termekek = new ArrayList<>();
    public ArrayList stockdatalist = new ArrayList<>();
    //private int queryString;
    TableControllerTermek myTCT = new TableControllerTermek(this);
    TableControllerStock myTCS = new TableControllerStock(this);
    Stock myTermek;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExpandableListView expandableList = getExpandableListView(); // you can use (ExpandableListView) findViewById(R.id.list)

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        lekerdezesChildern();
        lekerdezesGroup();

        MyExpandableAdapter adapter = new MyExpandableAdapter(myTermek, stockdatalist);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        today = df.format(c.getTime());
        //Intent i = getIntent();
        //queryString = i.getIntExtra("queryString", queryString);
        //selectedItem = i.getStringExtra("termek");
        //if (queryString == 3){
        //    lekerdezes(3);
        //}else {
        //    lekerdezes();
        //}
    }

    //@Override
    //protected void onResume() {
    //    super.onResume();
    //    lekerdezes();
    //}

    public void lekerdezesChildern(){
        String mQuery = {"SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " ORDER BY " + DatabaseHandler.STOCK_ID + " DESC";
        // Get the view from listview_main.xml
        setContentView(R.layout.listview_main);
        ListView myListView = (ListView) findViewById(R.id.listview);
        ArrayList<Stock> stockdatalist = new TableControllerStock(this).readCustomQuery(mQuery);
        //adapter = new ListViewAdapter(LekerdezesActivity.this, stockdatalist);
        //myListView.setAdapter(adapter);

    }

    public void lekerdezesGroup(){
        int darabInt = 0, i = 0;
        setContentView(R.layout.listview_main);
        ListView myListView = (ListView) findViewById(R.id.listview);
        ArrayList<Termek> termekList = myTCT.getAll();
        while (i != myTCT.count()){
            String mQuery = "SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " WHERE " + DatabaseHandler.TERMEK + " = " + "'" + termekList.get(i).getTermek() + "'";
            ArrayList<Stock> stockdatalist = myTCS.readCustomQuery(mQuery);
            for (Stock myStock : stockdatalist){
                darabInt += Integer.parseInt(myStock.getDarab());
            }
            myTermek = new Stock();
            myTermek.setTermek(termekList.get(i).getTermek());
            myTermek.setDarab(Integer.toString(darabInt));
            myTermek.setMinDarab(stockdatalist.get(0).getMinDarab());
            termekek.add(myTermek);
            darabInt = 0;
            i++;
        }
        //adapter = new ListViewAdapter(LekerdezesActivity.this, termekek);
        //myListView.setAdapter(adapter);

    }
}

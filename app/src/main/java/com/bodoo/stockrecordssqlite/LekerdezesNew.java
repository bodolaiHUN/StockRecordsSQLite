package com.bodoo.stockrecordssqlite;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LekerdezesNew extends ExpandableListActivity {
    // Declare Variables
    String today;
    public ArrayList termekek = new ArrayList<>();
    public ArrayList stockdata = new ArrayList<>();
    TableControllerTermek myTCT = new TableControllerTermek(this);
    TableControllerStock myTCS = new TableControllerStock(this);
    MyExpandableAdapter adapter = new MyExpandableAdapter( termekek );
    Notifications myNotifications = new Notifications();
    Stock myTermek, myChild;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lekerdezes_new);
        ExpandableListView expandableList = getExpandableListView();

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        lekerdezesGroup();                                                                          //termekek

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        //expandableList.setOnChildClickListener(this);

        expandableList = getExpandableListView();

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO A törlés nem működik, a kijelzést frissíteni kell!
                Toast.makeText(getBaseContext(), Long.toString(adapter.getChildId(groupPosition, childPosition)), Toast.LENGTH_SHORT).show();
                if (myNotifications.infoDialog(LekerdezesNew.this, "Törlés ", "Valóban törölni akarod?", 2)){
                    myTCS.delete(adapter.getChildId(groupPosition, childPosition));
                }
                lekerdezesGroup();
                return true;
            }
        });


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        today = df.format(c.getTime());

    }

    public void lekerdezesGroup(){
        int darabInt = 0, i = 0, j = 0;
        ArrayList<Termek> termekList = myTCT.getAll();
        while (i != myTCT.count()){
            String mQuery = "SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " WHERE " + DatabaseHandler.TERMEK + " = " + "'" + termekList.get(i).getTermek() + "'";
            ArrayList<Stock> stockdatalist = myTCS.readCustomQuery(mQuery);
            for (Stock myStock : stockdatalist){
                darabInt += Integer.parseInt(myStock.getDarab());
                myChild = new Stock();
                myChild.setId(myStock.getId());
                myChild.setTermek(myStock.getTermek());
                myChild.setHelye(myStock.getHelye());
                myChild.setDarab(myStock.getDarab());
                myChild.setMinDarab(myStock.getMinDarab());
                myChild.setBarcode(myStock.getBarcode());
                myChild.setSzavIdo(myStock.getSzavIdo());
                myChild.setErtekeles(myStock.getErtekeles());
                if (stockdata.add(myChild)){
                    Log.d("stockdata", "ok");
                }
                Log.d("stockdata", Integer.toString(stockdata.size()));
                Log.d("darab", myStock.getDarab());
                j++;
            }
            myTermek = new Stock();
            myTermek.setTermek(termekList.get(i).getTermek());
            myTermek.setDarab(Integer.toString(darabInt));
            myTermek.setMinDarab(stockdatalist.get(0).getMinDarab());
            myTermek.setArrayChildren(stockdata);
            myTermek.setCount(j);
            termekek.add(myTermek);
            Log.e("termek", myTermek.getTermek());
            Log.e("childCount", Integer.toString(myTermek.childrenSize()));
            darabInt = 0;
            j = 0;
            i++;
        }
    }


}

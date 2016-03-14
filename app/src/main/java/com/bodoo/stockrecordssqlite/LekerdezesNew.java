package com.bodoo.stockrecordssqlite;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LekerdezesNew extends ExpandableListActivity {
    // Declare Variables
    String today;
    public ArrayList termekek = new ArrayList<>();
	ArrayList stockdata;
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

	    getExpandableListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

		    @Override
		    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			    long packedPosition = getExpandableListView().getExpandableListPosition(position);
			    if (ExpandableListView.getPackedPositionType(packedPosition) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
				    // get item ID's
				    int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
				    int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);

				    removeItemFromList(childPosition, groupPosition);
			    }
			    return true;
		    }
	    });

		    Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        today = df.format(c.getTime());

    }

	protected void removeItemFromList(int groupPosition, int childPosition) {
		final int gPosition = groupPosition;
		final int cPosition = childPosition;

		AlertDialog.Builder alert = new AlertDialog.Builder(LekerdezesNew.this);

		alert.setTitle("Törlés");
		alert.setMessage("Valóban törlöd ezt a terméket?");
		alert.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (adapter.getChildrenCount(gPosition) > 1) {
					myTCS.delete(adapter.getChildId(gPosition, cPosition));
				} else {
					myTCT.delete(adapter.getChild(gPosition, cPosition).getTermek());
					myTCS.delete(adapter.getChildId(gPosition, cPosition));
				}
				finish();
				startActivity(getIntent());
			}
		});
		alert.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		alert.show();

	}


    public void lekerdezesGroup(){
        int darabInt = 0, i = 0, j = 0;
	    stockdata = new ArrayList<>();
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
                stockdata.add(myChild);
                j++;
            }
            myTermek = new Stock();
            myTermek.setTermek(termekList.get(i).getTermek());
            myTermek.setDarab(Integer.toString(darabInt));
//            myTermek.setMinDarab(stockdatalist.get(0).getMinDarab());                               //get(0)
            myTermek.setArrayChildren(stockdata);
            myTermek.setCount(j);
            termekek.add(myTermek);
            darabInt = 0;
            j = 0;
	        stockdata = new ArrayList<>();
            i++;
        }
    }
}

package com.bodoo.stockrecordssqlite;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LekerdezesNew extends ExpandableListActivity {
    // Declare Variables
    String today, termek, minMennyiseg, megjegyzes;
	long id;
	int darabInt;
	public ArrayList termekek = new ArrayList<>();
	ArrayList stockdata;
    TableControllerTermek myTCT = new TableControllerTermek(this);
    TableControllerStock myTCS = new TableControllerStock(this);
    MyExpandableAdapter adapter = new MyExpandableAdapter( termekek );
	Stock myTermek, myChild, result;



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
	    registerForContextMenu(expandableList);

	    Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        today = df.format(c.getTime());

    }

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle("Válassz:");
		menu.add(0, v.getId(), 0, "Törlés");
		menu.add(0, v.getId(), 0, "Módosítás (-1)");
		menu.add(0, v.getId(), 0, "Bevásárló listába");
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item
				.getMenuInfo();
		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
		final int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
		final int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);
		if (item.getTitle() == "Törlés") {
			Toast.makeText(this, "törlés", Toast.LENGTH_SHORT).show();
			removeItemFromList(groupPosition, childPosition);

		} else if (item.getTitle() == "Módosítás (-1)") {
			darabInt = Integer.valueOf(adapter.getChild(groupPosition, childPosition).getDarab());
			if (darabInt <= 1) {
				removeItemFromList(groupPosition, childPosition);
				return true;
			} else {
				writeStock(groupPosition, childPosition, 0, "N");
			}
			finish();
			startActivity(getIntent());

		} else if (item.getTitle() == "Bevásárló listába") {
			promptForResult(" ", "Megjegyzés a termékhez:", 0, new PromptRunnable() {
				// put whatever code you want to run after user enters a result
				public void run() {
					// get the value we stored from the dialog
					String value = this.getValue();
					writeStock(groupPosition, childPosition, 1, value);
				}
			});
		} else {
			return false;
		}
		return true;
	}

	protected void removeItemFromList(int groupPosition, int childPosition) {
		final int gPosition = groupPosition;
		final int cPosition = childPosition;
		promptForResult("Törlés", "Valóban törölni akarod?", 1, new PromptRunnable() {
			public void run() {
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
	}


    public void lekerdezesGroup(){
        int darabInt = 0, minDarab = 1000, i = 0, j = 0;
	    stockdata = new ArrayList<>();
        ArrayList<Termek> termekList = myTCT.getAll();
        while (i != myTCT.count()){
            String mQuery = "SELECT * FROM " + DatabaseHandler.TABLE_STOCK + " WHERE " + DatabaseHandler.TERMEK + " = " + "'" + termekList.get(i).getTermek() + "'";
            ArrayList<Stock> stockdatalist = myTCS.readCustomQuery(mQuery);
            for (Stock myStock : stockdatalist) {
	            darabInt += Integer.parseInt(myStock.getDarab());
	            myChild = new Stock();
	            myChild.setId(myStock.getId());
	            myChild.setTermek(myStock.getTermek());
	            myChild.setHelye(myStock.getHelye());
	            myChild.setDarab(myStock.getDarab());
	            myChild.setMinDarab(myStock.getMinDarab());
	            if (myStock.getMinDarab().length() != 0){
		            if (Integer.parseInt(myStock.getMinDarab()) < minDarab) {
			            minDarab = Integer.parseInt(myStock.getMinDarab());
		            }
                 }
                myChild.setBarcode(myStock.getBarcode());
                myChild.setSzavIdo(myStock.getSzavIdo());
                myChild.setErtekeles(myStock.getErtekeles());
	            myChild.setMegjegyzes(" ");
	            myChild.setBevListaba("N");
	            stockdata.add(myChild);
                j++;
            }
            myTermek = new Stock();
            myTermek.setTermek(termekList.get(i).getTermek());
            myTermek.setDarab(Integer.toString(darabInt));
	        if (minDarab != 0) {
		        myTermek.setMinDarab(Integer.toString(minDarab));
	        }
            myTermek.setArrayChildren(stockdata);
            myTermek.setCount(j);
            termekek.add(myTermek);
            darabInt = 0;
	        minDarab = 1000;
            j = 0;
	        stockdata = new ArrayList<>();
            i++;
        }
    }

	void promptForResult(String title, String message, int callerID, final PromptRunnable postrun) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(title);
		alert.setMessage(message);
		// Create textbox to put into the dialog
		final EditText input = new EditText(this);
		// put the textbox into the dialog
		alert.setView(input);
		// procedure for when the ok button is clicked.
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				if (value.length() == 0) {
					value = " ";
				}
				dialog.dismiss();
				// set value from the dialog inside our runnable implementation
				postrun.setValue(value);
				// ** HERE IS WHERE THE MAGIC HAPPENS! **
				// now that we have stored the value, lets run our Runnable
				postrun.run();
				return;
			}
		});

		alert.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				return;
			}
		});
		alert.show();
	}

	public void writeStock(int groupPosition, int childPosition, int callerID, String value) {
		Stock stock = new Stock();
		stock.setId(adapter.getChild(groupPosition, childPosition).getId());
		stock.setTermek(adapter.getChild(groupPosition, childPosition).getTermek());
		stock.setDarab(Integer.toString(darabInt));
		stock.setBarcode(adapter.getChild(groupPosition, childPosition).getBarcode());
		stock.setMinDarab(adapter.getChild(groupPosition, childPosition).getMinDarab());
		stock.setHelye(adapter.getChild(groupPosition, childPosition).getHelye());
		stock.setSzavIdo(adapter.getChild(groupPosition, childPosition).getSzavIdo());
		stock.setSzavIdoFigyel(adapter.getChild(groupPosition, childPosition).getSzavIdoFigyel());
		stock.setErtekeles(adapter.getChild(groupPosition, childPosition).getErtekeles());
		if (callerID == 1) {
			stock.setBevListaba("I");
			stock.setMegjegyzes(value);
		}
		new TableControllerStock(this).update(stock);
	}
}


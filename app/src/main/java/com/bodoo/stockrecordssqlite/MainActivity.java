package com.bodoo.stockrecordssqlite;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

	static final int DIALOG_ID1 = 1, DIALOG_ID2 = 2;
	public static Activity activity;
	Button scanButton, szavatossagButton, szavFigyelButton, lekerdezesButton, elkuldesButton;
    EditText termekNeveEditText, minMennyisegEditText, helyeEditText, mennyisegEditText, ertekelesEditText;
    TextView szavatossagTextView, szavFigyelTextView, scanTextView;
    Spinner spinnerQuery;
    int queryString;
    String year_x, month_x, day_x;
	int year, month, day, cur = 0, layout;
	String scanString, year_LW, month_LW, day_LW;
    Boolean barcodeMarVan = false;
    Barcode myBarcode = new Barcode();
    Stock myStock = new Stock();
    Termek myTermek = new Termek();
    TableControllerBarcode myTCB = new TableControllerBarcode(this);
    Notifications myNotifications = new Notifications();
    //ConnectionDetector cd;
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

	    // when dialog box is closed, below method will be called.
	    public void onDateSet(DatePicker view, int selectedYear,
	                          int selectedMonth, int selectedDay) {

		    Calendar c = Calendar.getInstance();
		    c.set(selectedYear, selectedMonth, selectedDay);
		    c.add(Calendar.WEEK_OF_YEAR, -1);

		    year_x = String.valueOf(selectedYear);
		    month_x = String.valueOf(selectedMonth + 1);
		    day_x = String.valueOf(selectedDay);
		    year_LW = String.valueOf(c.get(Calendar.YEAR));
		    month_LW = String.valueOf(c.get(Calendar.MONTH) + 1);
		    day_LW = String.valueOf(c.get(Calendar.DAY_OF_MONTH));


		    if (cur == DIALOG_ID1) {
			    if (Integer.valueOf(month_x) < 10) {

				    month_x = "0" + month_x;
			    }
			    if (Integer.valueOf(day_x) < 10) {

				    day_x = "0" + day_x;
			    }
			    // set selected date into textview
			    szavatossagTextView.setText(year_x + "-" + month_x + "-" + day_x);
			    if (Integer.valueOf(month_LW) < 10) {

				    month_LW = "0" + month_LW;
			    }
			    if (Integer.valueOf(day_LW) < 10) {

				    day_LW = "0" + day_LW;
			    }
			    // set selected date into textview
			    szavFigyelTextView.setText(year_LW + "-" + month_LW + "-" + day_LW);
		    } else {
			    if (Integer.valueOf(month_x) < 10) {

				    month_x = "0" + month_x;
			    }
			    if (Integer.valueOf(day_x) < 10) {

				    day_x = "0" + day_x;
			    }
			    szavFigyelTextView.setText(year_x + "-" + month_x + "-" + day_x);
		    }

	    }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        spinnerQuery = (Spinner) findViewById(R.id.spinnerQuery);
        spinnerQuery.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerQuery, android.R.layout.simple_spinner_item);                        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);             // Apply the adapter to the spinner
        spinnerQuery.setAdapter(adapter);
        scanButton = (Button) findViewById(R.id.scanButton);
        lekerdezesButton = (Button) findViewById(R.id.lekerdezesButton);
        elkuldesButton = (Button) findViewById(R.id.elkuldesButton);
        termekNeveEditText = (EditText) findViewById(R.id.termekNeveEditText);
	    termekNeveEditText.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    inputDialog(1);
		    }
	    });
        minMennyisegEditText = (EditText) findViewById(R.id.minMennyisegEditText);
	    minMennyisegEditText.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    inputDialog(2);
		    }
	    });
        helyeEditText = (EditText) findViewById(R.id.helyeEditText);
	    helyeEditText.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    inputDialog(3);
		    }
	    });
        mennyisegEditText = (EditText) findViewById(R.id.mennyisegEditText);
	    mennyisegEditText.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    inputDialog(4);
		    }
	    });
        ertekelesEditText = (EditText) findViewById(R.id.ertekelesEditText);
	    ertekelesEditText.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    inputDialog(5);
		    }
	    });
        scanTextView = (TextView) findViewById(R.id.scanTextView);
        szavatossagTextView = (TextView) findViewById(R.id.szavatossagTextView);
        szavFigyelTextView = (TextView) findViewById(R.id.szavFigyelTextView);

        resetData();

        //cd = new ConnectionDetector(getApplicationContext());                                     // Internet kapcsolat ellenorzese
        //Boolean isInternetPresent = cd.isConnectingToInternet();                                  // true or false

        lekerdezesButton.setOnClickListener(new View.OnClickListener() {                            // Lekérdezés activity megnyitása
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LekerdezesNew.class);
                //intent.putExtra("queryString", queryString);
                startActivity(intent);
            }
        });

        elkuldesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
	            if (termekNeveEditText.getText().toString().length() == 0) {
		            myNotifications.infoDialog(context, " ", "Nem adtad meg a nevét!", 1);
                        return;
                }
	            myStock.setTermek(termekNeveEditText.getText().toString());
	            if ( mennyisegEditText.getText().toString().length() == 0 ) {
                    myNotifications.infoDialog(context, " ", "Nem adtál meg darabszámot!", 1);
                        return;
                }
                myStock.setDarab(mennyisegEditText.getText().toString());
                myStock.setBarcode(scanTextView.getText().toString());
                if ( !barcodeMarVan && scanTextView.getText() != null){
                    myBarcode.setBarcode(scanTextView.getText().toString());
                    myBarcode.setTermek(termekNeveEditText.getText().toString());
                    myBarcode.setMinDarab(minMennyisegEditText.getText().toString());
                }
                myTermek.setTermek(termekNeveEditText.getText().toString());
                myStock.setHelye(helyeEditText.getText().toString());
                myStock.setMinDarab(minMennyisegEditText.getText().toString());
                myStock.setSzavIdo(szavatossagTextView.getText().toString());
                myStock.setSzavIdoFigyel(szavFigyelTextView.getText().toString());
                myStock.setErtekeles(ertekelesEditText.getText().toString());

                new TableControllerStock(context).create(myStock);
                if (scanTextView.getText() != null){
                    new TableControllerBarcode(context).create(myBarcode);
                }
                boolean createSuccessfulTermek = new TableControllerTermek(context).create(myTermek);
                if(createSuccessfulTermek){
                    resetData();
                }else{
                    resetData();
                }
            }
        });



        final Calendar cal = Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);

        showDialogOnButtonClick();
        addListenerOnButton();
    }

    public void showDialogOnButtonClick(){                                                          // Scan gomb kezelése
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bs.integrator();
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,                                    // An item was selected. You can retrieve the selected item using
                               int pos, long id) {                                                  // parent.getItemAtPosition(pos)
        String item = parent.getItemAtPosition(pos).toString();
        switch (item) {
            case "Mind" : queryString = 0;
                break;
            case "Le fog járni" : queryString = 1;
                break;
            case "Kevés" : queryString = 2;
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {                                          // Date picker megnyitása
        // Another interface callback
    }

    public void resetData(){
        scanTextView.setText("");
        szavatossagTextView.setText("");
        szavFigyelTextView.setText("");
        ertekelesEditText.setText("");
        mennyisegEditText.setText("");
        minMennyisegEditText.setText("");
        helyeEditText.setText("");
        termekNeveEditText.setText("");
    }

    public void addListenerOnButton() {

        szavatossagButton = (Button) findViewById(R.id.szavatossagButton);

        szavatossagButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DIALOG_ID1);

            }

        });
        szavFigyelButton = (Button) findViewById(R.id.szavFigyelButton);

        szavFigyelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DIALOG_ID2);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DIALOG_ID1:
                System.out.println("onCreateDialog  : " + id);
                cur = DIALOG_ID1;
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);
            case DIALOG_ID2:
                cur = DIALOG_ID2;
                System.out.println("onCreateDialog2  : " + id);
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,
                        day);

        }
        return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {                  // Scannelés
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            scanString = scanResult.getContents();
            scanTextView.setText(scanString);
            if (myTCB.checkIfExists(scanString)){
                termekNeveEditText.setTextColor(Color.BLUE);
                termekNeveEditText.setText(myTCB.readTermek(scanString)[0]);
                minMennyisegEditText.setTextColor(Color.BLUE);
                minMennyisegEditText.setText(myTCB.readTermek(scanString)[1]);
                barcodeMarVan = true;
            }else {
                barcodeMarVan = false;
            }
        }
    }

	public void inputDialog(final int txtID) {

		// get prompts.xml view
		LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
		switch (txtID) {
			case 1:
			case 3:
				layout = R.layout.input_dialog_text;
				break;
			case 2:
			case 4:
			case 5:
				layout = R.layout.input_dialog_number;
				break;
		}

		View promptView = layoutInflater.inflate(layout, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
		alertDialogBuilder.setView(promptView);
		final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
		switch (txtID) {
			case 1:
				editText.setHint("Termék");
				break;
			case 2:
				editText.setHint("Minimális mennyiség");
				break;
			case 3:
				editText.setHint("Helye");
				break;
			case 4:
				editText.setHint("Darabszám");
				break;
			case 5:
				editText.setHint("Értékelés, 1 - 5");
				break;
		}

		// setup a dialog window
		alertDialogBuilder.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						switch (txtID) {
							case 1:
								termekNeveEditText.setText(editText.getText());
								break;
							case 2:
								minMennyisegEditText.setText(editText.getText());
								break;
							case 3:
								helyeEditText.setText(editText.getText());
								break;
							case 4:
								mennyisegEditText.setText(editText.getText());
								break;
							case 5:
								ertekelesEditText.setText(editText.getText());
								break;
						}
					}
				})
				.setNegativeButton("Mégsem",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		final AlertDialog alert = alertDialogBuilder.create();
		alert.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
			}
		});
		alert.show();
	}
}

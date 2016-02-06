package com.bodoo.stockrecordssqlite;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button scanButton, szavatossagButton, szavFigyelButton, lekerdezesButton, elkuldesButton;
    EditText termekNeveEditText, minMennyisegEditText, helyeEditText, mennyisegEditText, ertekelesEditText;
    TextView szavatossagTextView, szavFigyelTextView, scanTextView;
    Spinner spinnerQuery;
    int queryString;
    String year_x, month_x, day_x;
    int year, month, day, cur = 0;
    //String scanString;
    Boolean barcodeMarVan = false;
    static final int DIALOG_ID1 = 1, DIALOG_ID2 = 2;
    Barcode myBarcode = new Barcode();
    Stock myStock = new Stock();
    Termek myTermek = new Termek();
    BarcodeScan bs = new BarcodeScan();
    TableControllerBarcode myTCB = new TableControllerBarcode(this);
    Notifications myNotifications = new Notifications();
    //TableControllerTermek myTCT = new TableControllerTermek(this);
    //ConnectionDetector cd;
    public static Activity activity;
    //final Context context = this;


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
        minMennyisegEditText = (EditText) findViewById(R.id.minMennyisegEditText);
        helyeEditText = (EditText) findViewById(R.id.helyeEditText);
        mennyisegEditText = (EditText) findViewById(R.id.mennyisegEditText);
        ertekelesEditText = (EditText) findViewById(R.id.ertekelesEditText);
        scanTextView = (TextView) findViewById(R.id.scanTextView);
        szavatossagTextView = (TextView) findViewById(R.id.szavatossagTextView);
        szavFigyelTextView = (TextView) findViewById(R.id.szavFigyelTextView);

        resetData();

        //cd = new ConnectionDetector(getApplicationContext());                                     // Internet kapcsolat ellenorzese
        //Boolean isInternetPresent = cd.isConnectingToInternet();                                  // true or false

        lekerdezesButton.setOnClickListener(new View.OnClickListener() {                            // Lekérdezés activity megnyitása
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LekerdezesActivity.class);
                intent.putExtra("queryString", queryString);
                startActivity(intent);
            }
        });

        elkuldesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                if ( termekNeveEditText.getText().toString().length() == 0) {
                    myNotifications.infoDialog(context, " ", "Nem adtad meg a nevét!", 1);
                        return;
                }
                myStock.setTermek(termekNeveEditText.getText().toString());
                if ( mennyisegEditText.getText().toString().length() == 0 ) {
                    myNotifications.infoDialog(context, " ", "Nem adtál meg darabszámot!", 1);
                        return;
                }
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
                    Toast.makeText(context, "Termek nem OK", Toast.LENGTH_SHORT).show();
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
                bs.integrator();
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

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year_x = String.valueOf(selectedYear);
            month_x = String.valueOf(selectedMonth+1);
            day_x = String.valueOf(selectedDay);

            if(cur == DIALOG_ID1){
                if(Integer.valueOf (month_x) < 10){

                    month_x = "0" + month_x;
                }
                if(Integer.valueOf (day_x) < 10){

                    day_x  = "0" + day_x ;
                }
                // set selected date into textview
                szavatossagTextView.setText(year_x + "-" + month_x + "-" + day_x);
            }
            else{
                if(Integer.valueOf (month_x) < 10){

                    month_x = "0" + month_x;
                }
                if(Integer.valueOf (day_x) < 10){

                    day_x  = "0" + day_x ;
                }
                szavFigyelTextView.setText(year_x + "-" + month_x + "-" + day_x);
            }

        }
    };

}

package com.bodoo.stockrecordssqlite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by bodoo on 2016.02.01..
 */
public class BarcodeScan extends Activity {

    String scanString;
    Boolean barcodeMarVan = false;
    TextView scanTextView;
    Button scanButton;
    EditText termekNeveEditText, minMennyisegEditText;
    TableControllerBarcode myTCB = new TableControllerBarcode(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanButton = (Button) findViewById(R.id.scanButton);
        scanTextView = (TextView) findViewById(R.id.scanTextView);
        termekNeveEditText = (EditText) findViewById(R.id.termekNeveEditText);
        minMennyisegEditText = (EditText) findViewById(R.id.minMennyisegEditText);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {                  // Scannelés
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        scanString = scanResult.getContents();
        if (scanString != null) {
            scanTextView.setText(scanString);
            if ( myTCB.checkIfExists(scanString )) {
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

    public void integrator(){                                                                       // Scan gomb kezelése
        IntentIntegrator integrator = new IntentIntegrator(MainActivity.activity);
        integrator.initiateScan();
    }


}

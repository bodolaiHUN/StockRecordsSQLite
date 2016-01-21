package com.bodoo.stockrecordssqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SingleItemView extends Activity implements View.OnLongClickListener {

    Context context;
    int id;
    String termek;
    String helye;
    String darab;
    String szavatossag;
    String minDarab;
    String ertekeles;
    TableControllerStock mStock = new TableControllerStock(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.listview_singleitem);

        Intent i = getIntent();
        id = i.getIntExtra("id", id);
        termek = i.getStringExtra("termek");
        helye = i.getStringExtra("helye");
        darab = i.getStringExtra("darab");
        minDarab = i.getStringExtra("minDarab");
        szavatossag = i.getStringExtra("szavatossag");
        ertekeles = i.getStringExtra("ertekeles");

        // Locate the TextViews in singleitemview.xml
        TextView txttermek = (TextView) findViewById(R.id.termek);
        txttermek.setOnLongClickListener(this);
        TextView txthelye = (TextView) findViewById(R.id.helye);
        TextView txtdarab = (TextView) findViewById(R.id.darab);
        TextView txtmindarab = (TextView) findViewById(R.id.minmennyiseg);
        TextView txtszavatossag = (TextView) findViewById(R.id.szavatossag);
        TextView txtertekeles = (TextView) findViewById(R.id.ertekeles);

        // Set results to the TextViews
        txttermek.setText(termek);
        txthelye.setText(helye);
        txtdarab.setText(darab);
        txtmindarab.setText(minDarab);
        txtszavatossag.setText(szavatossag);
        txtertekeles.setText(ertekeles);
    }

    public boolean onLongClick(View arg0) {
        mStock.delete(id);
        this.finish();
        return false;
    }

}
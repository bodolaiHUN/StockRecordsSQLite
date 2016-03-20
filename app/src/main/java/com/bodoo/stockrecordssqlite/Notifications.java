package com.bodoo.stockrecordssqlite;

/**
 * Created by bodoo on 2016.02.05..
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

class Notifications {

    boolean buttonValue; // = false;
	String resultText = "";

    public boolean infoDialog(Context ctx, String title, String message, int buttonNumber) {
        AlertDialog.Builder b = new AlertDialog.Builder(ctx);
        b.setMessage(message);
        b.setCancelable(true);
        b.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
	            buttonValue = true;
                dialog.cancel();
            }
        });
        if (buttonNumber != 1){
            b.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    buttonValue = false;
	                dialog.cancel();
                }
            });
        }

        b.setTitle(title);
        AlertDialog ad = b.create();
        ad.show();
        return buttonValue;
    }
}
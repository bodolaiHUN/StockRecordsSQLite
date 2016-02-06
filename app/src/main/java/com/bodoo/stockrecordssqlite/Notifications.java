package com.bodoo.stockrecordssqlite;

/**
 * Created by bodoo on 2016.02.05..
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

class Notifications {

    boolean buttonValue = false;

    public boolean infoDialog(Context ctx, String title, String message, int buttonNumber) {
        AlertDialog.Builder b = new AlertDialog.Builder(ctx);
        //buttonValue = false;
        b.setMessage(message);
        b.setCancelable(true);
        b.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                buttonValue = true;
            }
        });
        if (buttonNumber != 1){
            b.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    buttonValue = false;
                }
            });
        }

        b.setTitle(title);
        AlertDialog ad = b.create();
        ad.show();
        return buttonValue;
    }
}
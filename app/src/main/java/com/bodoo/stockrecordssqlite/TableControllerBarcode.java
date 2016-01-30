package com.bodoo.stockrecordssqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TableControllerBarcode extends DatabaseHandler {

    Barcode mBarcode;

    public TableControllerBarcode(Context context) {
        super(context);
    }

    public boolean create(Barcode mBarcode) {

        ContentValues values = new ContentValues();

        values.put(BARCODE_TERMEK, mBarcode.getTermek());
        values.put(BARCODE, mBarcode.getBarcode());
        values.put(BARCODE_MINDARAB, mBarcode.getMinDarab());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(BARCODE, null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + BARCODE;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public ArrayList<Barcode> read() {

        ArrayList<Barcode> recordsList = new ArrayList<Barcode>();

        String sql = "SELECT * FROM " + TABLE_BARCODE + " ORDER BY " + BARCODE_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(BARCODE_ID)));
                String termek = cursor.getString(cursor.getColumnIndex(BARCODE_TERMEK));
                String barcode = cursor.getString(cursor.getColumnIndex(BARCODE));

                mBarcode = new Barcode();
                mBarcode.setId(id);
                mBarcode.setTermek(termek);
                mBarcode.setBarcode(barcode);
                recordsList.add(mBarcode);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }


    public String[] readTermek(String item) {

        //Barcode mBarcode = null;
        String data[] = new String[2];

        String sql = "SELECT " + BARCODE_TERMEK + " , " + BARCODE_MINDARAB + " FROM " + TABLE_BARCODE + " WHERE " + BARCODE + " = " + item;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            data[0] = cursor.getString(cursor.getColumnIndex(BARCODE_TERMEK));
            data[1] = cursor.getString(cursor.getColumnIndex(BARCODE_MINDARAB));
        }

        cursor.close();
        db.close();

        return data;
    }


    public boolean update(Barcode mBarcode) {

        ContentValues values = new ContentValues();

        values.put(BARCODE_ID, mBarcode.getId());
        values.put(BARCODE_TERMEK, mBarcode.getTermek());
        values.put(BARCODE, mBarcode.getBarcode());

        String where = BARCODE_ID + " = ?";

        String[] whereArgs = { Integer.toString(mBarcode.getId()) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update(TABLE_BARCODE, values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete(TABLE_BARCODE, BARCODE_ID + " =?", null) > 0;
        db.close();

        return deleteSuccessful;

    }

    public boolean checkIfExists(String objectName){

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+ BARCODE_ID + " FROM " + TABLE_BARCODE + " WHERE " +  BARCODE + "= " + objectName + " ", null);

        if(cursor!=null) {

            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();

        return recordExists;
    }

}
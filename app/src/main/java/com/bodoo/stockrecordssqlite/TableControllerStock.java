package com.bodoo.stockrecordssqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TableControllerStock extends DatabaseHandler {

    Stock stock;

    public TableControllerStock(Context context) {
        super(context);
    }

    public boolean create(Stock stock) {

        ContentValues values = new ContentValues();

        values.put(TERMEK, stock.getTermek());
        values.put(HELYE, stock.getHelye());
        values.put(DARAB, stock.getDarab());
        values.put(MIN_DARAB, stock.getMinDarab());
        values.put(SZAV_IDO, stock.getSzavIdo());
        values.put(SZAV_IDO_FIGYEL, stock.getSzavIdoFigyel());
        values.put(BARCODE_STOCK, stock.getBarcode());
        values.put(ERTEKELES, stock.getErtekeles());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_STOCK, null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + TABLE_STOCK;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public ArrayList<Stock> read() {

        ArrayList<Stock> recordsList = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_STOCK + " ORDER BY " + STOCK_ID + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(STOCK_ID)));
                String termek = cursor.getString(cursor.getColumnIndex(TERMEK));
                String helye = cursor.getString(cursor.getColumnIndex(HELYE));
                String darab = cursor.getString(cursor.getColumnIndex(DARAB));
                String minDarab = cursor.getString(cursor.getColumnIndex(MIN_DARAB));
                String szavIdo = cursor.getString(cursor.getColumnIndex(SZAV_IDO));
                String szavIdoFigyel = cursor.getString(cursor.getColumnIndex(SZAV_IDO_FIGYEL));
                String barcode = cursor.getString(cursor.getColumnIndex(BARCODE_STOCK));
                String ertekeles = cursor.getString(cursor.getColumnIndex(ERTEKELES));

                stock = new Stock();
                stock.setId(id);
                stock.setTermek(termek);
                stock.setHelye(helye);
                stock.setDarab(darab);
                stock.setMinDarab(minDarab);
                stock.setSzavIdo(szavIdo);
                stock.setSzavIdoFigyel(szavIdoFigyel);
                stock.setBarcode(barcode);
                stock.setErtekeles(ertekeles);
                recordsList.add(stock);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public ArrayList<Stock> readCustomQuery(String myQuery) {

        ArrayList<Stock> recordsList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(myQuery, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(STOCK_ID)));
                String termek = cursor.getString(cursor.getColumnIndex(TERMEK));
                String helye = cursor.getString(cursor.getColumnIndex(HELYE));
                String darab = cursor.getString(cursor.getColumnIndex(DARAB));
                String minDarab = cursor.getString(cursor.getColumnIndex(MIN_DARAB));
                String szavIdo = cursor.getString(cursor.getColumnIndex(SZAV_IDO));
                String szavIdoFigyel = cursor.getString(cursor.getColumnIndex(SZAV_IDO_FIGYEL));
                String barcode = cursor.getString(cursor.getColumnIndex(BARCODE_STOCK));
                String ertekeles = cursor.getString(cursor.getColumnIndex(ERTEKELES));

                stock = new Stock();
                stock.setId(id);
                stock.setTermek(termek);
                stock.setHelye(helye);
                stock.setDarab(darab);
                stock.setMinDarab(minDarab);
                stock.setSzavIdo(szavIdo);
                stock.setSzavIdoFigyel(szavIdoFigyel);
                stock.setBarcode(barcode);
                stock.setErtekeles(ertekeles);
                recordsList.add(stock);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public Stock readSingleRecord(int stockId) {

        Stock stock = null;

        String sql = "SELECT * FROM " + TABLE_STOCK + " WHERE " + STOCK_ID + " = " + stockId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(STOCK_ID)));
            String termek = cursor.getString(cursor.getColumnIndex(TERMEK));
            String helye = cursor.getString(cursor.getColumnIndex(HELYE));
            String darab = cursor.getString(cursor.getColumnIndex(DARAB));
            String minDarab = cursor.getString(cursor.getColumnIndex(MIN_DARAB));
            String szavIdo = cursor.getString(cursor.getColumnIndex(SZAV_IDO));
            String szavIdoFigyel = cursor.getString(cursor.getColumnIndex(SZAV_IDO_FIGYEL));
            String barcode = cursor.getString(cursor.getColumnIndex(BARCODE_STOCK));
            String ertekeles = cursor.getString(cursor.getColumnIndex(ERTEKELES));

            stock = new Stock();
            stock.setId(id);
            stock.setTermek(termek);
            stock.setHelye(helye);
            stock.setDarab(darab);
            stock.setMinDarab(minDarab);
            stock.setSzavIdo(szavIdo);
            stock.setSzavIdoFigyel(szavIdoFigyel);
            stock.setBarcode(barcode);
            stock.setErtekeles(ertekeles);

        }

        cursor.close();
        db.close();

        return stock;
    }

    public Stock readSingleItem(String item) {

        Stock stock = null;

        String sql = "SELECT * FROM " + TABLE_STOCK + " WHERE " + TERMEK + " = " + item;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(STOCK_ID)));
            String termek = cursor.getString(cursor.getColumnIndex(TERMEK));
            String helye = cursor.getString(cursor.getColumnIndex(HELYE));
            String darab = cursor.getString(cursor.getColumnIndex(DARAB));
            String minDarab = cursor.getString(cursor.getColumnIndex(MIN_DARAB));
            String szavIdo = cursor.getString(cursor.getColumnIndex(SZAV_IDO));
            String szavIdoFigyel = cursor.getString(cursor.getColumnIndex(SZAV_IDO_FIGYEL));
            String barcode = cursor.getString(cursor.getColumnIndex(BARCODE_STOCK));
            String ertekeles = cursor.getString(cursor.getColumnIndex(ERTEKELES));

            stock = new Stock();
            stock.setId(id);
            stock.setTermek(termek);
            stock.setHelye(helye);
            stock.setDarab(darab);
            stock.setMinDarab(minDarab);
            stock.setSzavIdo(szavIdo);
            stock.setSzavIdoFigyel(szavIdoFigyel);
            stock.setBarcode(barcode);
            stock.setErtekeles(ertekeles);

        }

        cursor.close();
        db.close();

        return stock;
    }

    public boolean update(Stock stock) {

        ContentValues values = new ContentValues();

        values.put(STOCK_ID, stock.getId());
        values.put(TERMEK, stock.getTermek());
        values.put(HELYE, stock.getHelye());
        values.put(DARAB, stock.getDarab());
        values.put(MIN_DARAB, stock.getMinDarab());
        values.put(SZAV_IDO, stock.getSzavIdo());
        values.put(SZAV_IDO_FIGYEL, stock.getSzavIdoFigyel());
        values.put(BARCODE_STOCK, stock.getBarcode());
        values.put(ERTEKELES, stock.getErtekeles());

        String where = STOCK_ID + " = ?";

        String[] whereArgs = { Integer.toString(stock.getId()) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update(TABLE_STOCK, values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete(TABLE_STOCK, STOCK_ID + " ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

}
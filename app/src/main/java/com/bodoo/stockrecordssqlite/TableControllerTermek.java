package com.bodoo.stockrecordssqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TableControllerTermek extends DatabaseHandler {

    Termek mTermek;

    public TableControllerTermek(Context context) {
        super(context);
    }

    public boolean create(Termek mTermek) {

        ContentValues values = new ContentValues();

        values.put(TERMEK_ITEM, mTermek.getTermek());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_TERMEK, null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + TABLE_TERMEK;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public ArrayList<Termek> read() {

        ArrayList<Termek> recordsList = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_TERMEK + " ORDER BY " + TERMEK_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TERMEK_ID)));
                String termek = cursor.getString(cursor.getColumnIndex(TERMEK_ITEM));

                mTermek = new Termek();
                mTermek.setId(id);
                mTermek.setTermek(termek);
                recordsList.add(mTermek);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }


    public Termek readSingleItem(String item) {

        Termek mTeremek = null;

        String sql = "SELECT * FROM " + TABLE_TERMEK + " WHERE " + TERMEK_ITEM + " = " + item;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TERMEK_ID)));
            String termek = cursor.getString(cursor.getColumnIndex(TERMEK_ITEM));

            mTermek = new Termek();
            mTermek.setId(id);
            mTermek.setTermek(termek);

        }

        cursor.close();
        db.close();

        return mTeremek;
    }

    public ArrayList<Termek> getAll() {
        ArrayList<Termek> items = new ArrayList<>();
        Termek item;
        Cursor c = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_TERMEK;
        try {
            c = db.rawQuery(sql, null);
            if (c.moveToFirst()) {
                do {
                    item = new Termek();
                    item.setId(c.getInt(c.getColumnIndex(TERMEK_ID)));
                    item.setTermek(c.getString(c.getColumnIndex(TERMEK_ITEM)));
                    items.add(item);
                } while (c.moveToNext());
            }
            return items;
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
    }

    public boolean update(Termek mTermek) {

        ContentValues values = new ContentValues();

        values.put(TERMEK_ID, mTermek.getId());
        values.put(TERMEK_ITEM, mTermek.getTermek());

        String where = TERMEK_ID + " = ?";

        String[] whereArgs = { Integer.toString(mTermek.getId()) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update(TABLE_TERMEK, values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(long id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete(TABLE_TERMEK, TERMEK_ID + " ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

	public boolean delete(String termek) {
		boolean deleteSuccessful = false;

		SQLiteDatabase db = this.getWritableDatabase();
		deleteSuccessful = db.delete(TABLE_TERMEK, TERMEK_ITEM + " ='" + termek + "'", null) > 0;
		db.close();

		return deleteSuccessful;

	}

    public boolean checkIfExists(String objectName){

        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + TERMEK_ID + " FROM " + TABLE_TERMEK + " WHERE " + TERMEK_ITEM + " = " + objectName + " ", null);

        if(cursor!= null) {

            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }

        cursor.close();
        db.close();

        return recordExists;
    }

}
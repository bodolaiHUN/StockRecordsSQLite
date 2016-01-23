package com.bodoo.stockrecordssqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    // Database name
    protected static final String DATABASE_NAME = "StockRecords";
    // Table names
    public static final String TABLE_STOCK = "stock";
    public static final String TABLE_BARCODE = "barcode";
    public static final String TABLE_TERMEK = "termek";
    // stock column name
    public static final String STOCK_ID ="id";
    public static final String TERMEK = "termek";
    public static final String HELYE = "helye";
    public static final String DARAB ="darab";
    public static final String MIN_DARAB = "minDarab";
    public static final String SZAV_IDO = "szavIdo";
    public static final String SZAV_IDO_FIGYEL = "szavIdoFigyel";
    public static final String BARCODE_STOCK = "barcode";
    public static final String ERTEKELES = "ertekeles";
    // barcode column name
    public static final String BARCODE_ID = "id";
    public static final String BARCODE = "barcode";
    public static final String BARCODE_TERMEK = "termek";
    public static final String BARCODE_MINDARAB = "minDarab";
    // termek column name
    public static final String TERMEK_ID = "id";
    public static final String TERMEK_ITEM = "termek";
    public String today;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_STOCK + "(" +
                STOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TERMEK + " TEXT, " +
                HELYE + " TEXT, " +
                DARAB + " TEXT, " +
                MIN_DARAB + " TEXT, " +
                SZAV_IDO + " TEXT, " +
                SZAV_IDO_FIGYEL + " TEXT, " +
                BARCODE_STOCK + " TEXT, " +
                ERTEKELES + " TEXT)";

        String barcodeSql = "CREATE TABLE " + TABLE_BARCODE + "(" +
                BARCODE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BARCODE + " TEXT UNIQUE, " +
                BARCODE_TERMEK + " TEXT, " +
                BARCODE_MINDARAB + " TEXT)";

        String termekSql = "CREATE TABLE " + TABLE_TERMEK + "(" +
                TERMEK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TERMEK_ITEM + " TEXT UNIQUE)";

        db.execSQL(sql);
        db.execSQL(barcodeSql);
        db.execSQL(termekSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_STOCK;
        db.execSQL(sql);
        String barcodeSql = "DROP TABLE IF EXISTS " + TABLE_BARCODE;
        db.execSQL(barcodeSql);
        String termekSql = "DROP TABLE IF EXISTS "+ TABLE_TERMEK;
        db.execSQL(termekSql);

        onCreate(db);
    }

}
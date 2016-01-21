package com.bodoo.stockrecordssqlite;

/**
 * Created by bodoo on 2016.01.17..
 */
public class Barcode {

    private int id;
    private String barcodeSql;
    private String termek;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcodeSql() {
        return barcodeSql;
    }

    public void setBarcodeSql(String barcodeSql) {
        this.barcodeSql = barcodeSql;
    }

    public String getTermek() {
        return termek;
    }

    public void setTermek(String termek) {
        this.termek = termek;
    }
}

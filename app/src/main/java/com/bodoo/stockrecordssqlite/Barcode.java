package com.bodoo.stockrecordssqlite;

/**
 * Created by bodoo on 2016.01.17..
 */
public class Barcode {

    private int id;
    private String barcode;
    private String termek;
    private String minDarab;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTermek() {
        return termek;
    }

    public void setTermek(String termek) {
        this.termek = termek;
    }

    public String getMinDarab() {return minDarab;}

    public void setMinDarab(String minDarab) {this.minDarab = minDarab;}
}

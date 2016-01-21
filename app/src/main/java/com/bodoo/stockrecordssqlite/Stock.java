package com.bodoo.stockrecordssqlite;

/**
 * Created by bodoo on 2015.12.29..
 */
public class Stock {

    private int id;
    private String termek;
    private String helye;
    private String darab;
    private String minDarab;
    private String szavIdo;
    private String szavIdoFigyel;
    private String barcode;
    private String ertekeles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTermek() {
        return termek;
    }

    public void setTermek(String termek) {
        this.termek = termek;
    }

    public String getHelye() {
        return helye;
    }

    public void setHelye(String helye) {
        this.helye = helye;
    }

    public String getDarab() {
        return darab;
    }

    public void setDarab(String darab) {
        this.darab = darab;
    }

    public String getMinDarab() {
        return minDarab;
    }

    public void setMinDarab(String minDarab) {
        this.minDarab = minDarab;
    }

    public String getSzavIdo() {
        return szavIdo;
    }

    public void setSzavIdo(String szavIdo) {
        this.szavIdo = szavIdo;
    }

    public String getSzavIdoFigyel() {
        return szavIdoFigyel;
    }

    public void setSzavIdoFigyel(String szavIdoFigyel) {
        this.szavIdoFigyel = szavIdoFigyel;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getErtekeles() {
        return ertekeles;
    }

    public void setErtekeles(String ertekeles) {
        this.ertekeles = ertekeles;
    }

}

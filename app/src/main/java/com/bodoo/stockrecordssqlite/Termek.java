package com.bodoo.stockrecordssqlite;

/**
 * Created by bodoo on 2016.01.17..
 */
public class Termek {

    private int id;
    private String termek;
    private String minDarab;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTermek() {
        return termek;
    }

    public void setTermek(String termek) {this.termek = termek;}

    public String getMinDarab() {return minDarab;}

    public void setMinDarab(String minDarab) {this.minDarab = minDarab;}
}

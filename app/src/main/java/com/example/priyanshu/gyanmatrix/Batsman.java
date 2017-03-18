package com.example.priyanshu.gyanmatrix;

/**
 * Created by Priyanshu on 12-Mar-17.
 */

public class Batsman {
    private String mdescription;
    private int mruns;
    private int mplayed;



    private String mnationality;
    private String mBatsmanname;

    private String mBatsmanimageurl;

    public Batsman(String name, String imageurl, String discription, String country,int runs,int match) {
        mBatsmanname=name;
        mdescription=discription;
        mBatsmanimageurl=imageurl;
        mnationality=country;
        mruns=runs;
        mplayed=match;
    }


    public String getBatsmanname() {
        return mBatsmanname;
    }

    public String getBatsmanimageurl() {
        return mBatsmanimageurl;
    }

    public String getdescription() {
        return mdescription;
    }

    public int getruns() {
        return mruns;
    }

    public int getplayed() {
        return mplayed;
    }

    public String getnationality() {
        return mnationality;
    }








}

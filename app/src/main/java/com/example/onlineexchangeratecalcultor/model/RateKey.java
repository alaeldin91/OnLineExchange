package com.example.onlineexchangeratecalcultor.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "rateKey")
public class RateKey {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String usd;
    private String aed;
    private String afn;
    private String all;
    private String amd;
    private String ang;
    private String aoa;
    private String ars;
    private String aud;
    private String awg;

    public RateKey( String usd, String aed, String afn, String all, String amd, String ang, String aoa, String ars, String aud, String awg) {
        this.usd = usd;
        this.aed = aed;
        this.afn = afn;
        this.all = all;
        this.amd = amd;
        this.ang = ang;
        this.aoa = aoa;
        this.ars = ars;
        this.aud = aud;
        this.awg = awg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getAed() {
        return aed;
    }

    public void setAed(String aed) {
        this.aed = aed;
    }

    public String getAfn() {
        return afn;
    }

    public void setAfn(String afn) {
        this.afn = afn;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getAmd() {
        return amd;
    }

    public void setAmd(String amd) {
        this.amd = amd;
    }

    public String getAng() {
        return ang;
    }

    public void setAng(String ang) {
        this.ang = ang;
    }

    public String getAoa() {
        return aoa;
    }

    public void setAoa(String aoa) {
        this.aoa = aoa;
    }

    public String getArs() {
        return ars;
    }

    public void setArs(String ars) {
        this.ars = ars;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getAwg() {
        return awg;
    }

    public void setAwg(String awg) {
        this.awg = awg;
    }
}

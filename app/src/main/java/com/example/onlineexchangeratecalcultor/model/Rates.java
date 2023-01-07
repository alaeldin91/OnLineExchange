package com.example.onlineexchangeratecalcultor.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

@Entity(tableName = "rate")
public class Rates {

    @PrimaryKey(autoGenerate = true)
    int id;
    @SerializedName("USD")
    @Expose
    private double usd;
    @SerializedName("AED")
    @Expose
    private double aed;
    @SerializedName("AFN")
    @Expose
    private double afn;
    @SerializedName("ALL")
    @Expose
    private double all;
    @SerializedName("AMD")
    @Expose
    private double amd;
    @SerializedName("ANG")
    @Expose
    private double ang;
    @SerializedName("AOA")
    @Expose
    private double aoa;

    @SerializedName("ARS")
    @Expose
    private double ars;
    @SerializedName("AUD")
    @Expose
    private double aud;
    @SerializedName("AWG")
    @Expose
    private double awg;

    public Rates(double usd, double aed, double afn, double all, double amd, double ang, double aoa, double ars, double aud, double awg) {
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

    public Double getUsd() {
        return usd;
    }

    public double getAed() {
        return aed;
    }

    public double getAfn() {
        return afn;
    }

    public double getAll() {
        return all;
    }

    public double getAmd() {
        return amd;
    }


    public double getAng() {
        return ang;
    }


    public double getAoa() {
        return aoa;
    }


    public double getArs() {
        return ars;
    }


    public double getAud() {
        return aud;
    }


    public double getAwg() {
        return awg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public void setAed(double aed) {
        this.aed = aed;
    }

    public void setAfn(double afn) {
        this.afn = afn;
    }

    public void setAll(double all) {
        this.all = all;
    }

    public void setAmd(double amd) {
        this.amd = amd;
    }

    public void setAng(double ang) {
        this.ang = ang;
    }

    public void setAoa(double aoa) {
        this.aoa = aoa;
    }

    public void setArs(double ars) {
        this.ars = ars;
    }

    public void setAud(double aud) {
        this.aud = aud;
    }

    public void setAwg(double awg) {
        this.awg = awg;
    }
}

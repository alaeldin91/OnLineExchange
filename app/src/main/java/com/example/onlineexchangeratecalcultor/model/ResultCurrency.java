package com.example.onlineexchangeratecalcultor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultCurrency {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("documentation")
    @Expose
    private String documentation;
    @SerializedName("terms_of_use")
    @Expose
    private String termsOfUse;
    @SerializedName("time_last_update_unix")
    @Expose
    private int timeLastUpdateUnix;
    @SerializedName("time_last_update_utc")
    @Expose
    private String timeLastUpdateUtc;
    @SerializedName("time_next_update_unix")
    @Expose
    private int timeNextUpdateUnix;
    @SerializedName("time_next_update_utc")
    @Expose
    private String timeNextUpdateUtc;
    @SerializedName("time_eol_unix")
    @Expose
    private int timeEolUnix;
    @SerializedName("base_code")
    @Expose
    private String baseCode;
    @SerializedName("rates")
    @Expose
    private Rates rates;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Rates getRates() {
        return rates;
    }
}

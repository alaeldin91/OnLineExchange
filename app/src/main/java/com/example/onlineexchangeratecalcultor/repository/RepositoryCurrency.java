package com.example.onlineexchangeratecalcultor.repository;


import androidx.lifecycle.LiveData;

import com.example.onlineexchangeratecalcultor.database.RateDao;
import com.example.onlineexchangeratecalcultor.model.RateKey;
import com.example.onlineexchangeratecalcultor.model.Rates;
import com.example.onlineexchangeratecalcultor.model.ResultCurrency;
import com.example.onlineexchangeratecalcultor.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class RepositoryCurrency {
    private final ApiService apiService;
    private final RateDao rateDao;

    @Inject
    public RepositoryCurrency(ApiService apiService, RateDao rateDao) {
        this.apiService = apiService;
        this.rateDao = rateDao;
    }
    public Observable<ResultCurrency> getObservableCurrency() {
        return apiService.getCurrency();
    }
    public void insertRate(Rates rate) {
        rateDao.insertRate(rate);
    }
    public void insertRateKey(RateKey rateKey) {
        rateDao.insertRatKey(rateKey);
    }
    public LiveData<List<Rates>> ratesLiveData() {
        return rateDao.getAllRate();
    }
    public LiveData<List<RateKey>> rateKeyLiveData(){
        return rateDao.getRateKey();
    }
}

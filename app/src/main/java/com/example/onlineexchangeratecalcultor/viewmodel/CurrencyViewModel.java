package com.example.onlineexchangeratecalcultor.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.onlineexchangeratecalcultor.model.RateKey;
import com.example.onlineexchangeratecalcultor.model.Rates;
import com.example.onlineexchangeratecalcultor.repository.RepositoryCurrency;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class CurrencyViewModel extends ViewModel {
    private final MutableLiveData<HashMap<String, Double>> ratesMutableLiveData = new MutableLiveData<>();
    private final RepositoryCurrency repositoryCurrency;
    private LiveData<Rates> liveDataRate;
    private LiveData<List<RateKey>>liveDataKey;

    @Inject
    public CurrencyViewModel(RepositoryCurrency repositoryCurrency) {
        this.repositoryCurrency = repositoryCurrency;
        this.liveDataRate = repositoryCurrency.ratesLiveData();
        this.liveDataKey = repositoryCurrency.rateKeyLiveData();
    }

    public MutableLiveData<HashMap<String, Double>> getRatesMutableLiveData() {
        return ratesMutableLiveData;
    }

    public void observerCurrency() {
        repositoryCurrency.getObservableCurrency().subscribeOn(Schedulers.io()).map(resultCurrency -> {
            Log.i("ala", resultCurrency + "");
            Rates rates = resultCurrency.getRates();
            HashMap<String, Double> hashMapRate = new HashMap<>();
            hashMapRate.put("USD", rates.getUsd());
            hashMapRate.put("AED", rates.getAed());
            hashMapRate.put("AFN", rates.getAfn());
            hashMapRate.put("ALL", rates.getAll());
            hashMapRate.put("AMD", rates.getAmd());
            hashMapRate.put("ANG", rates.getAng());
            hashMapRate.put("AOA", rates.getAoa());
            hashMapRate.put("ARS", rates.getArs());
            hashMapRate.put("AUD", rates.getAud());
            hashMapRate.put("AWG", rates.getAwg());
            return hashMapRate;
        }).observeOn(Schedulers.io()).subscribe(result -> ratesMutableLiveData.postValue(result),
                error -> Log.i("error", error.getMessage()));
    }


    public void insertRate(Rates rates) {
        repositoryCurrency.insertRate(rates);
    }
   public void insertRateKey(RateKey rateKey){
        repositoryCurrency.insertRateKey(rateKey);
   }
    public void getLocalRate() {
        this.liveDataRate = repositoryCurrency.ratesLiveData();
    }

    public void getLocalRateKey() {
        this.liveDataKey= repositoryCurrency.rateKeyLiveData();
    }

    public LiveData<List<RateKey>>getLiveDataRateKey() {
        return liveDataKey;
    }
    public LiveData<Rates> getLiveDataRate() {
        return liveDataRate;
    }

}

package com.example.onlineexchangeratecalcultor.network;

import com.example.onlineexchangeratecalcultor.model.ResultCurrency;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
public interface ApiService {
    @GET("latest/usd")
    Observable<ResultCurrency> getCurrency();
}

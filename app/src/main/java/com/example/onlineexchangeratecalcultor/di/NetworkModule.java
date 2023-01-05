package com.example.onlineexchangeratecalcultor.di;

import com.example.onlineexchangeratecalcultor.network.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    public final String baseUrl = "https://open.er-api.com/v6/";

    @Provides
    @Singleton
    public ApiService provideMealApiService() {
        return new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()).build().create(ApiService.class);
    }


}

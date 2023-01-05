package com.example.onlineexchangeratecalcultor.di;

import android.app.Application;

import androidx.room.Room;

import com.example.onlineexchangeratecalcultor.database.CurrencyDB;
import com.example.onlineexchangeratecalcultor.database.RateDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataBaseModule {
    @Provides
    @Singleton
    public static CurrencyDB providePokemonDB(Application application) {
        return Room.databaseBuilder(application, CurrencyDB.class, "currency").fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    public static RateDao provideCurrencyRate(CurrencyDB currencyDB) {
        return currencyDB.rateDao();
    }
}
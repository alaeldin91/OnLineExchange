package com.example.onlineexchangeratecalcultor.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.onlineexchangeratecalcultor.model.RateKey;
import com.example.onlineexchangeratecalcultor.model.Rates;

@Database(entities = {Rates.class, RateKey.class}, version = 1, exportSchema = false)
public abstract class CurrencyDB extends RoomDatabase {
    public abstract RateDao rateDao();
}

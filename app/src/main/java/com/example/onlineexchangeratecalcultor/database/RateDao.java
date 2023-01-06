package com.example.onlineexchangeratecalcultor.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.onlineexchangeratecalcultor.model.RateKey;
import com.example.onlineexchangeratecalcultor.model.Rates;

@Dao
public interface RateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRate(Rates rates);

    @Query("SELECT * FROM Rate")
    LiveData<Rates> getAllRate();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRatKey(RateKey rateKey);

    @Query("SELECT * FROM Rate WHERE id =:id")
    LiveData<Rates> getRateByIdRate(int id);
}

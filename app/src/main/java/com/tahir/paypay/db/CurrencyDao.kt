package com.tahir.paypay.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    // inserts currency data into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: ArrayList<Currencies>): List<Long>

    // get all currencies.
    @Query("SELECT * FROM Currencies") suspend fun getAllCurrencies(): List<Currencies>

    // insert currency rates into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyRates(rates: ArrayList<CurrenciesRates>): List<Long>

    // get all currency rates from the database.
    @Query("SELECT * FROM CurrenciesRates") fun getAllRates(): LiveData<List<CurrenciesRates>>
}

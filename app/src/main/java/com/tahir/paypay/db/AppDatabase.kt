package com.tahir.paypay.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * abstract class to setup app database and contains the abstract method for db operations - Data access object.
 */
@Database(entities = [Currencies::class, CurrenciesRates::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linkDao(): CurrencyDao
}
package com.tahir.paypay.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["currencyShortName"], unique = true)])
data class CurrenciesRates(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val currencyShortName: String,
    val currencyRate: Double,
)
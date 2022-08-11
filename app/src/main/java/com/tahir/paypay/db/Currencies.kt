package com.tahir.paypay.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["currency_short", "currency_full_name"], unique = true)])
data class Currencies(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "currency_short") val currencyShortName: String,
    @ColumnInfo(name = "currency_full_name") val currencyFullName: String
) {
    override fun toString(): String = currencyShortName
}

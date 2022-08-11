package com.tahir.paypay.repo

import com.tahir.paypay.db.Currencies
import com.tahir.paypay.db.CurrenciesRates
import com.tahir.paypay.db.CurrencyDao
import javax.inject.Inject

/**
 * a holds all the local database operations
 * @constructor injection currencyDao
 */
class LocalDataSource @Inject constructor(private val currencyDao: CurrencyDao) {

    suspend fun getallCurrencies() = currencyDao.getAllCurrencies()

    suspend fun insertCurrencies(currencies: ArrayList<Currencies>) =
        currencyDao.insertCurrencies(currencies)

    fun getAllRates() = currencyDao.getAllRates()

    suspend fun insertRates(rates: ArrayList<CurrenciesRates>) =
        currencyDao.insertCurrencyRates(rates)
}

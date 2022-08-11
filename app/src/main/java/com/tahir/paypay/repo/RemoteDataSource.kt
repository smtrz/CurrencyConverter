package com.tahir.paypay.repo

import com.tahir.paypay.api.CurrencyConversionService
import javax.inject.Inject

/**
 * all operations related to remote datasource are contained in RemoteDataSource
 * @constructor currencyConversionService
 */
class RemoteDataSource @Inject constructor(private val currencyConversionService: CurrencyConversionService) {

    // suspended function that calls the getShortUrl method
    suspend fun getAllCurrencies() =
        currencyConversionService.getCurrencies()

    // suspended function that calls the getShortUrl method
    suspend fun getCurrencyRatesFromServer() =
        currencyConversionService.getLatestRates()
}
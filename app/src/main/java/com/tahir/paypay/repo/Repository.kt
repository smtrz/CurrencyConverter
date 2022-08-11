package com.tahir.paypay.repo

import androidx.lifecycle.LiveData
import com.tahir.paypay.db.Currencies
import com.tahir.paypay.db.CurrenciesRates
import com.tahir.paypay.generics.BaseApiResponse
import com.tahir.paypay.generics.NetworkResult
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

/**
 * Repository is the Single Source of truth that contains instance of both local and remote data
 * source.
 * @constructor remoteDataSource , localDataSource (using construction injection)
 */
@Singleton
class Repository
@Inject
constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BaseApiResponse() {
    /**
     * calls API for url shortening, emits data on IO Dispatcher.
     * @param url
     * @return Flow<NetworkResult<Shorten>>
     */
    suspend fun getAllCurrencies(): Flow<NetworkResult<ResponseBody>> {
        return flow {
            //   emit(NetworkResult.Loading())
            emit(safeApiCall { remoteDataSource.getAllCurrencies() })
        }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getAllCurrencyRatesFromServer(): NetworkResult<ResponseBody> {
        return safeApiCall { remoteDataSource.getCurrencyRatesFromServer() }
    }

    suspend fun getAllCurrenciesFromServer(): NetworkResult<ResponseBody> {

        return safeApiCall { remoteDataSource.getAllCurrencies() }
    }

    suspend fun insertCurrencies(currencies: ArrayList<Currencies>): List<Long> {
        return localDataSource.insertCurrencies(currencies)
    }

    suspend fun insertRates(rates: ArrayList<CurrenciesRates>): List<Long> {
        return localDataSource.insertRates(rates)
    }

    suspend fun allCurrencies(): List<Currencies> {
        return localDataSource.getallCurrencies()
    }

    fun getLatestRates(): LiveData<List<CurrenciesRates>> {
        return localDataSource.getAllRates()
    }
}

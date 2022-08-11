package com.tahir.paypay.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahir.paypay.db.Currencies
import com.tahir.paypay.db.CurrenciesRates
import com.tahir.paypay.generics.NetworkResult
import com.tahir.paypay.helpers.JsonHelper
import com.tahir.paypay.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel that contains operation and connects to the repository for all the domain logic
 * @constructor injected Repository
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    // Livedata getters and private variables

    private val _currenciesResponse: MutableLiveData<NetworkResult<List<Currencies>?>> =
        MutableLiveData()
    val currenciesResponse: MutableLiveData<NetworkResult<List<Currencies>?>>
        get() = _currenciesResponse
    private val _currencyRatesResponse: MutableLiveData<NetworkResult<List<CurrenciesRates>?>> =
        MutableLiveData()
    val currencyRatesResponse: MutableLiveData<NetworkResult<List<CurrenciesRates>?>>
        get() = _currencyRatesResponse

    fun getAllCurrencies() {

        viewModelScope.launch(Dispatchers.IO) {
            var rs = repository.getAllCurrenciesFromServer()
            var body = rs.data
            var dataInDb = repository.allCurrencies()

            when (rs) {
                is NetworkResult.Success -> {

                    var currenciesArray = JsonHelper.MapCurrencyData(body)

                    // insert the data into the database.

                    repository.insertCurrencies(currenciesArray)
                    dataInDb = repository.allCurrencies()

                    _currenciesResponse.postValue(NetworkResult.Success(dataInDb))
                }
                is NetworkResult.Error -> {
                    _currenciesResponse.postValue(NetworkResult.Error(rs.message!!, dataInDb))
                }
                is NetworkResult.Loading -> {
                    _currenciesResponse.postValue(NetworkResult.Loading())
                }
            }
        }
    }

    // get latest currency rates.
    fun getLatestCurrencyRatesFromUrl() {

        viewModelScope.launch(Dispatchers.IO) {
            var rs = repository.getAllCurrencyRatesFromServer()
            var ratesArray = JsonHelper.MapCurrencyRatesData(rs.data)
            repository.insertRates(ratesArray)
        }
    }

    fun getLatestRatesUpdatesFromDb(): LiveData<List<CurrenciesRates>> {

        return repository.getLatestRates()
    }
}

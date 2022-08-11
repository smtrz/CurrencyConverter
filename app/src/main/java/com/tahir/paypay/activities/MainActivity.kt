package com.tahir.paypay.activities

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.tahir.paypay.R
import com.tahir.paypay.adapters.CurrencyAdapter
import com.tahir.paypay.constants.AppConstants
import com.tahir.paypay.db.Currencies
import com.tahir.paypay.db.CurrenciesRates
import com.tahir.paypay.generics.NetworkResult
import com.tahir.paypay.helpers.AmountConverter
import com.tahir.paypay.helpers.UiHelper
import com.tahir.paypay.models.CurrencyRates
import com.tahir.paypay.viewmodel.MainActivityViewModel
import com.tahir.paypay.worker.RatesFetcher
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.android.synthetic.main.currency_converter.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var currencyAdapter: CurrencyAdapter
    var rateList: List<CurrencyRates> = emptyList()
    lateinit var mLayoutManager: LinearLayoutManager
    var rates: List<CurrenciesRates> = emptyList()
    var currencies: List<Currencies> = emptyList()

    // get view model instance via Hilt
    private val mainActivityVM by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_converter)
        // loading all the currencies from server else load it from DB
        loadCurrencies()
        init()
    }

    fun loadCurrencies() {

        mainActivityVM.getAllCurrencies()

        mainActivityVM.currenciesResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    UiHelper.displayProgressBar(false, progressbar, this)
                    response.data?.let { currencies = it }
                }
                is NetworkResult.Error -> {
                    // show error message
                    response.data?.let { currencies = it }

                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    // show a progress bar
                    UiHelper.displayProgressBar(true, progressbar, this)
                }
            }
            setUpAutoComplete(currencies)
        }
    }

    private fun subscribeCurrencyRatesFromDb() {

        mainActivityVM.getLatestRatesUpdatesFromDb().observe(this) { response -> rates = response }
    }

    private fun setUpAutoComplete(currencies: List<Currencies>) {

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, currencies)

        currency.setAdapter(adapter)
    }

    /** initializes the recyclerview and initializes the work manager. */
    private fun init() {
        currencyAdapter.setList(rateList)
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        currency_list.layoutManager = mLayoutManager
        currency_list.adapter = currencyAdapter
        subscribeCurrencyRatesFromDb()
        initRateWorker()
    }

    /**
     * Submit method is bind to the button that gets the relative currency rates
     * @see AmountConverter.convertAllCurrencies
     * - calculates the relative currency amounts.
     */
    fun onConvertClick(v: View) {
        if (amount.text.toString().equals("") || currency.text.toString().equals("")) {

            UiHelper.showToast(this, "fields cannot be empty")

            return
        }

        var exchangeList =
            AmountConverter.convertAllCurrencies(
                currencies,
                amount.text.toString().toDouble(),
                rates,
                currency.text.toString()
            )
        if (exchangeList.isNotEmpty()) {
            emptyview.visibility = View.GONE
            exchangelistlayout.visibility = View.VISIBLE
            currencyAdapter.setList(exchangeList)
        } else {
            emptyview.visibility = View.VISIBLE
            exchangelistlayout.visibility = View.GONE
        }
    }

    // initializes the work manager and setup the periodic unique work
    private fun initRateWorker() {
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val sendDataBuilder: PeriodicWorkRequest.Builder =
            PeriodicWorkRequest.Builder(
                RatesFetcher::class.java,
                AppConstants.WOKER_INTERVAL,
                TimeUnit.MINUTES
            )
                .setConstraints(constraints)
        val periodicWorkRequest = sendDataBuilder.build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "RecieveRates",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
    }
}

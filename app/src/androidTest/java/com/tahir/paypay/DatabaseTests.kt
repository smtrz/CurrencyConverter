package com.tahir.paypay

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.tahir.paypay.db.AppDatabase
import com.tahir.paypay.db.Currencies
import com.tahir.paypay.db.CurrenciesRates
import com.tahir.paypay.db.CurrencyDao
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTests {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var db: AppDatabase
    private lateinit var dao: CurrencyDao

    @Before
    fun setUp() {
        // setting up in memory database
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        // getting data access object from the database created.
        dao = db.linkDao()
    }

    @Test
    fun insertCurrencyData() = runBlocking {
        val currenciesList: ArrayList<Currencies> = ArrayList()
        currenciesList.add(
            Currencies(
                currencyShortName = "PKR",
                currencyFullName = "Pakistani Rupees"
            )
        )
        currenciesList.add(Currencies(currencyShortName = "JPY", currencyFullName = "Japanese Yen"))
        Assert.assertTrue(dao.insertCurrencies(currenciesList).isNotEmpty())
    }

    @Test
    fun getCurrencyData() = runBlocking {
        insertCurrencyData()
        var currenciesList = dao.getAllCurrencies()
        Assert.assertTrue(currenciesList.isNotEmpty())
    }

    @Test
    fun insertCurrencyRates() = runBlocking {
        val currenciesRateList: ArrayList<CurrenciesRates> = ArrayList()
        currenciesRateList.add(
            CurrenciesRates(
                currencyShortName = "PKR",
                currencyRate = 224.4
            )
        )
        currenciesRateList.add(
            CurrenciesRates(
                currencyShortName = "JPY",
                currencyRate = 135.15
            )
        )
        Assert.assertTrue(dao.insertCurrencyRates(currenciesRateList).isNotEmpty())
    }

    @Test
    fun getCurrencyRatesData() = runBlocking {
        insertCurrencyRates()
        var rateList = dao.getAllRates().getOrAwaitValue()
        Assert.assertTrue(rateList.isNotEmpty())
    }

    @After
    fun tearDown() {
        // closing the database after it has been used.
        db.close()
        // removing the observer afterwards

    }
}

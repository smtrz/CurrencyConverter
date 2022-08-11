package com.tahir.paypay.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tahir.paypay.generics.NetworkResult
import com.tahir.paypay.getOrAwaitValue
import com.tahir.paypay.repo.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository

    lateinit var viewModel: MainActivityViewModel

    lateinit var fakeRepoImpl: FakeRepoImpl

    @Before
    fun setUp() {
        fakeRepoImpl = FakeRepoImpl()

        MockitoAnnotations.openMocks(this)


    }

    @Test
    fun `rate data is returned`() = runBlocking {
        try {

            `when`(repository.getLatestRates()).thenReturn(
                fakeRepoImpl.getAllRates()

            )
            viewModel = MainActivityViewModel(repository)
            viewModel.getLatestRatesUpdatesFromDb().observeForever {
                println("SUCCESS")
                Assert.assertTrue(it.isNotEmpty())

            }

        } catch (exception: Exception) {
            println("FAILED")
            fail()
        }
    }

    @Test
    fun `currency data is returned`() = runBlocking {

        try {

            `when`(repository.allCurrencies()).thenReturn(
                fakeRepoImpl.getAllCurrencies()
            )

            `when`(repository.getAllCurrenciesFromServer()).thenReturn(
                NetworkResult.Success(
                    fakeRepoImpl.currencies.toResponseBody("application/json".toMediaTypeOrNull())
                )
            )


            viewModel = MainActivityViewModel(repository)

            viewModel.getAllCurrencies()
            println(viewModel.currenciesResponse.getOrAwaitValue().data.toString())
            viewModel.currenciesResponse.observeForever {
                Assert.assertTrue(it.data?.size!! > 0)

            }
        } catch (exception: Exception) {
            fail()
        }
    }


}

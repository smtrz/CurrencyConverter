package com.tahir.paypay.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tahir.paypay.generics.NetworkResult
import com.tahir.paypay.helpers.JsonHelper
import com.tahir.paypay.repo.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody


@HiltWorker
class RatesFetcher @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        var rs: NetworkResult<ResponseBody>? = null
        withContext(Dispatchers.IO) {
            rs = repository.getAllCurrencyRatesFromServer()
            when (rs) {
                is NetworkResult.Success -> {
                    var ratesArray = JsonHelper.MapCurrencyRatesData(rs?.data)
                    repository.insertRates(ratesArray)

                }

                else -> {
                    return@withContext Result.failure()
                }
            }

        }
        return when (rs) {
            is NetworkResult.Success -> Result.success()
            else -> Result.failure()
        }
    }

}



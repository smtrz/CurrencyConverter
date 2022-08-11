package com.tahir.paypay

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import androidx.work.testing.TestListenableWorkerBuilder
import com.tahir.paypay.worker.RatesFetcher
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RatesFetcherWorkerTests {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testRatesFetcherWorker() {
        val worker = TestListenableWorkerBuilder<RatesFetcher>(context).build()
        runBlocking {
            val result = worker.doWork()
            assertThat(result, `is`(Result.success(null)))

        }
    }
}
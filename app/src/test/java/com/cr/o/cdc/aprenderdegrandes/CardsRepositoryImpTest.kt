package com.cr.o.cdc.aprenderdegrandes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSourceImp
import com.cr.o.cdc.aprenderdegrandes.mocks.MockFirebaseDatabase
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepositoryImp
import com.cr.o.cdc.aprenderdegrandes.repos.model.Type
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class CardsRepositoryImpTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val repository = CardsRepositoryImp(
        CardsDataSourceImp(MockFirebaseDatabase),
        object : CardsDao{
            override fun get(): Flow<Cards> {
                TODO("Not yet implemented")
            }

            override fun insert(cards: Cards) {
                TODO("Not yet implemented")
            }

            override fun deleteAll() {
                TODO("Not yet implemented")
            }
        }
    )

    @Test
    fun getCards() = runTest {
        val data = repository.getCards().firstOrNull()?.data?.cards!!
        assertEquals(1, data.size)
        val first = data.first()
        assertEquals(1, first.number)
        assertEquals(Type.GOLD, first.type)
        assertEquals("pregunta", first.text)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun <T> LiveData<T>.getOrAwaitValueTest(): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValueTest.removeObserver(this)
            }
        }

        this@getOrAwaitValueTest.observeForever(observer)

        try {
            if (!latch.await(2, TimeUnit.SECONDS)) {
                throw TimeoutException("LiveData value was never set.")
            }
        } finally {
            this@getOrAwaitValueTest.removeObserver(observer)
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}
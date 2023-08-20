package com.cr.o.cdc.aprenderdegrandes.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.Database
import com.cr.o.cdc.aprenderdegrandes.mocks.Mocks
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.sql.Timestamp
import java.time.Instant
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class CardsDaoTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dao = Room.inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
        Database::class.java,
    ).build().cardsDao()

    @Test
    fun insertCards() {
        val cards = Cards(
            Instant.now().toEpochMilli(),
            Mocks.ONE_CARD.value!!
        )
        dao.insert(cards)
        assertEquals(
            "text",
            dao.get().getOrAwaitValueTest().cards[0].text
        )
    }

    @Test
    fun deleteAll() {
        val cards = Cards(
            1,
            Mocks.ONE_CARD.value!!
        )
        dao.insert(cards)
        dao.deleteAll()
        assertEquals(
            null,
            dao.get().getOrAwaitValueTest()
        )
    }

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
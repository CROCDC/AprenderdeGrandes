package com.cr.o.cdc.aprenderdegrandes

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepositoryImp
import com.cr.o.cdc.aprenderdegrandes.repos.model.Type
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class CardsRepositoryImpTest {
    private val repository = CardsRepositoryImp()

    @Test
    fun getCards() {
        val data = repository.getCards().getOrAwaitValueTest()
        assertEquals(48, data.size)
        val first = data.first()
        assertEquals(1, first.number)
        assertEquals(Type.GOLD, first.type)
        assertEquals("¿Sentís que tenéis un propósito en la vida? ¿Cual es?", first.text)
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
        GlobalScope.launch(Dispatchers.Main) {
            this@getOrAwaitValueTest.observeForever(observer)
        }

        try {
            if (!latch.await(2, TimeUnit.SECONDS)) {
                throw TimeoutException("LiveData value was never set.")
            }
        } finally {
            GlobalScope.launch(Dispatchers.Main) {
                this@getOrAwaitValueTest.removeObserver(observer)
            }
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}
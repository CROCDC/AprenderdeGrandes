package com.cr.o.cdc.aprenderdegrandes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.cr.o.cdc.aprenderdegrandes.mocks.Mocks
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotSame
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class MainViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: CardsRepository


    @Test
    fun initialCard() {
        every { repository.getCards() } returns Mocks.ONE_CARD
        val showCard = MainViewModel(repository).showCard.getOrAwaitValueTest()
        val expected = Mocks.ONE_CARD.value!!.first()
        assertEquals(expected.text, showCard?.text)
    }

    @Test
    fun notShowSameCardTwoTimes() {
        every { repository.getCards() } returns Mocks.nCards(2)
        val viewModel = MainViewModel(repository)
        val firstCard = viewModel.showCard.getOrAwaitValueTest()
        viewModel.anotherCard()
        val secondCard = viewModel.showCard.getOrAwaitValueTest()
        assertNotSame(firstCard, secondCard)
    }

    @Test
    fun noMoreCards() {
        every { repository.getCards() } returns Mocks.nCards(2)
        val viewModel = MainViewModel(repository)
        viewModel.anotherCard()
        viewModel.anotherCard()
        assertEquals(null, viewModel.showCard.getOrAwaitValueTest())
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
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: CardsRepository

    @Before
    fun before() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun initialCard() = runTest {
        every { repository.getCards() } returns Mocks.CARD_RESOURCE
        val showCard = MainViewModel(repository).showCard.first()
        val expected = Mocks.CARD_RESOURCE.first().data?.cards?.get(0)
        assertEquals(expected?.text, showCard?.text)
    }

    @Test
    fun notShowSameCardTwoTimes() = runTest {
        every { repository.getCards() } returns Mocks.nCards(2)
        val viewModel = MainViewModel(repository)
        val firstCard = viewModel.showCard.first()
        viewModel.anotherCard()
        val secondCard = viewModel.showCard.first()
        assertNotSame(firstCard, secondCard)
    }

    @Test
    fun noMoreCards() = runTest {
        every { repository.getCards() } returns Mocks.nCards(2)
        val viewModel = MainViewModel(repository)
        viewModel.anotherCard()
        viewModel.anotherCard()
        assertEquals(null, viewModel.showCard.first())
    }
}
package com.cr.o.cdc.aprenderdegrandes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cr.o.cdc.aprenderdegrandes.mocks.MockCardsRepository
import com.cr.o.cdc.aprenderdegrandes.mocks.Mocks
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val repository: MockCardsRepository = MockCardsRepository()

    @Before
    fun before() {
        MockCardsRepository.clean()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun initialCard() = runTest {
        val showCard = MainViewModel(repository).showCard.first()
        val expected = Mocks.CARD_RESOURCE.first().data?.cards?.get(0)
        assertEquals(expected?.text, showCard?.text)
    }

    @Test
    fun notShowSameCardTwoTimes() = runTest {
        val viewModel = MainViewModel(repository)
        val firstCard = viewModel.showCard.first()
        viewModel.anotherCard()
        val secondCard = viewModel.showCard.first()
        assertNotSame(firstCard, secondCard)
        assertFalse(viewModel.notMoreCards.first())
    }

    @Test
    fun noMoreCards() = runTest {
        val viewModel = MainViewModel(repository)
        viewModel.anotherCard()
        viewModel.anotherCard()
        assertEquals(MockCardsRepository.THIRD_CARD, viewModel.showCard.first())
        assertTrue(viewModel.notMoreCards.first())

    }

    @After
    fun checkManyTimesCalledRepo() {
        assertEquals(1, MockCardsRepository.manyTimeCalledFlow)
    }
}
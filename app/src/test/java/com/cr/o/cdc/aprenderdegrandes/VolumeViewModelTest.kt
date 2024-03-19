package com.cr.o.cdc.aprenderdegrandes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cr.o.cdc.aprenderdegrandes.mocks.CardEntityMock
import com.cr.o.cdc.aprenderdegrandes.mocks.MockCardsRepository
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class VolumeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var repository: MockCardsRepository

    @Before
    fun before() {
        repository = MockCardsRepository()
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun initialCard() = runTest {
        val showCard = VolumeViewModel(repository).showCard.first()
        val expected = CardEntityMock.cardEntities()
        assertEquals(1, showCard?.viewedTimes)
        assertEquals(expected[0].text, showCard?.text)
    }

    @Test
    fun notShowSameCardTwoTimes() = runTest {
        val viewModel = VolumeViewModel(repository)
        val firstCard = viewModel.showCard.first()
        viewModel.anotherCard()
        val secondCard = viewModel.showCard.first()
        assertNotSame(firstCard, secondCard)
    }

    @Test
    fun noMoreCards() = runTest {
        val viewModel = VolumeViewModel(repository)
        viewModel.anotherCard()
        viewModel.anotherCard()
        assertEquals(CardEntityMock.getSecondCardEntity().id, viewModel.showCard.first()?.id)
        assertTrue(viewModel.notMoreCards.first())
    }
}
package com.cr.o.cdc.aprenderdegrandes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSource
import com.cr.o.cdc.aprenderdegrandes.mocks.MockCardsDao
import com.cr.o.cdc.aprenderdegrandes.mocks.Mocks
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepositoryImp
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.time.Instant

class CardsRepositoryImpTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var dataSource: CardsDataSource

    private lateinit var repository: CardsRepositoryImp

    private val dao = MockCardsDao()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = CardsRepositoryImp(
            dataSource,
            dao
        )
    }

    @Test
    fun shouldFetchTrue() {
        var assert = false
        runTest {
            every { dataSource.getCards() } returns Mocks.getCardList("dataSource")
            dao.insert(Mocks.getCards(text = "database"))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            "dataSource",
                            it.data?.cards?.get(0)?.text
                        )
                        this.cancel()
                    }
                }
            }

        }
        assert(assert)
    }

    @Test
    fun shouldFetchFalse() {
        var assert = false
        runTest {
            every { dataSource.getCards() } returns Mocks.getCardList("dataSource")
            dao.insert(Mocks.getCards(text = "database", timestamp = System.currentTimeMillis()))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            "database",
                            it.data?.cards?.get(0)?.text
                        )
                        this.cancel()
                    }
                }
            }

        }
        assert(assert)
    }

    @Test
    fun shouldFetchAfterWeekend() {
        var assert = false
        runTest {
            every { dataSource.getCards() } returns Mocks.getCardList("dataSource")
            val timestamp = Instant.now().minusMillis(7 * 24 * 60 * 60 * 1000).toEpochMilli()
            dao.insert(Mocks.getCards(text = "database", timestamp = timestamp))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            "dataSource",
                            it.data?.cards?.get(0)?.text
                        )
                        this.cancel()
                    }
                }
            }

        }
        assert(assert)
    }

    @Test
    fun shouldFetchAfterDay() {
        var assert = false
        runTest {
            every { dataSource.getCards() } returns Mocks.getCardList("dataSource")
            val timestamp = Instant.now().minusMillis(24 * 60 * 60 * 1000).toEpochMilli()
            dao.insert(Mocks.getCards(text = "database", timestamp = timestamp))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            "database",
                            it.data?.cards?.get(0)?.text
                        )
                        this.cancel()
                    }
                }
            }

        }
        assert(assert)
    }
}
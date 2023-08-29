package com.cr.o.cdc.aprenderdegrandes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity
import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSource
import com.cr.o.cdc.aprenderdegrandes.mocks.CardEntityMock
import com.cr.o.cdc.aprenderdegrandes.mocks.CardMock
import com.cr.o.cdc.aprenderdegrandes.mocks.MockCardsDao
import com.cr.o.cdc.aprenderdegrandes.mocks.MockRemoteConfigDataSource
import com.cr.o.cdc.aprenderdegrandes.mocks.SavedTimeEntityMock
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepositoryImp
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
            dao,
            MockRemoteConfigDataSource()
        )
        MockRemoteConfigDataSource.setForceUpdate(false)
    }

    @Test
    fun shouldFetchWithDatabaseInTrueAndRemoteConfigForceUpdateInFalse() {
        var assert = false
        runTest {
            coEvery { dataSource.getCards() } returns CardMock.getCards(DATASOURCE)
            dao.insert(CardEntityMock.cardEntities(DATABASE))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            DATASOURCE,
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
    fun shouldFetchWithDatabaseInFalseAndRemoteConfigForceUpdateInFalse() {
        var assert = false
        runTest {
            val time = System.currentTimeMillis()
            coEvery { dataSource.getCards() } returns CardMock.getCards(DATASOURCE)
            dao.insert(CardEntityMock.cardEntities(firstText = DATABASE, firstSaveTimeId = time))
            dao.insert(SavedTimeEntityMock.getSavedTimeEntity(time))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            DATABASE,
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
    fun shouldFetchWithRemoteConfigForceUpdateInTrueAndDatabaseInFalse() {
        var assert = false
        MockRemoteConfigDataSource.setForceUpdate(true)
        runTest {
            val time = System.currentTimeMillis()
            coEvery { dataSource.getCards() } returns CardMock.getCards(DATASOURCE)
            dao.insert(CardEntityMock.cardEntities(firstText = DATABASE, firstSaveTimeId = time))
            dao.insert(SavedTimeEntityMock.getSavedTimeEntity(timeStamp = time))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            DATASOURCE,
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
    fun shouldFetchWithRemoteConfigForceUpdateInTrueAndDatabaseInTrue() {
        var assert = false
        MockRemoteConfigDataSource.setForceUpdate(true)
        runTest {
            coEvery { dataSource.getCards() } returns CardMock.getCards(DATASOURCE)
            dao.insert(CardEntityMock.cardEntities(firstText = DATABASE))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            DATASOURCE,
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
            coEvery { dataSource.getCards() } returns CardMock.getCards(DATASOURCE)
            val timestamp = Instant.now().minusMillis(7 * 24 * 60 * 60 * 1000).toEpochMilli()
            dao.insert(
                CardEntityMock.cardEntities(
                    firstText = DATABASE,
                    firstSaveTimeId = timestamp
                )
            )
            dao.insert(SavedTimeEntity(timestamp))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            DATASOURCE,
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
            coEvery { dataSource.getCards() } returns CardMock.getCards(DATASOURCE)
            val timestamp = Instant.now().minusMillis(24 * 60 * 60 * 1000).toEpochMilli()
            dao.insert(
                CardEntityMock.cardEntities(
                    firstText = DATABASE,
                    firstSaveTimeId = timestamp
                )
            )
            dao.insert(SavedTimeEntity(timestamp))
            val flow = repository.getCards()
            launch {
                flow.collect {
                    if (it !is Resource.Loading) {
                        assert = true
                        assertEquals(
                            DATABASE,
                            it.data?.cards?.get(0)?.text
                        )
                        this.cancel()
                    }
                }
            }

        }
        assert(assert)
    }

    companion object {
        const val DATASOURCE = "DATASOURCE"
        const val DATABASE = "DATABASE"
    }
}
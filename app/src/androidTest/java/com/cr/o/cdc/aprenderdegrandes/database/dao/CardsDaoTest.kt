package com.cr.o.cdc.aprenderdegrandes.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.cr.o.cdc.aprenderdegrandes.mocks.CardsMock
import com.cr.o.cdc.aprenderdegrandes.modules.DatabaseModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CardsDaoTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dao = DatabaseModule().provideDatabase(
        InstrumentationRegistry.getInstrumentation().targetContext
    ).cardsDao()

    @Before
    fun before(){
        dao.deleteAllCardEntity()
        dao.deleteAllSaveTimeEntity()
    }

    @Test
    fun insertCards() = runTest {
        dao.insert(CardsMock.getCardsEntities())
        dao.insert(CardsMock.getSavedTimeEntity())
        assertEquals(
            "text",
            dao.get().first()?.cards?.get(0)?.text
        )
    }

    @Test
    fun deleteAll() = runTest {
        dao.insert(CardsMock.getCardsEntities())
        dao.insert(CardsMock.getSavedTimeEntity())
        dao.deleteAllCardEntity()
        dao.deleteAllSaveTimeEntity()
        assertEquals(
            null,
            dao.get().first()?.cards?.get(0)?.text
        )
    }

    @Test
    fun updateCardEntity() = runTest {
        dao.insert(CardsMock.getCardsEntities())
        dao.insert(CardsMock.getSavedTimeEntity())
        dao.update(
            CardsMock.getCardsEntities()[0].copy(
                viewedTimes = 1
            )
        )
        assertEquals(
            1,
            dao.get().first()?.cards?.get(0)?.viewedTimes
        )
    }
}
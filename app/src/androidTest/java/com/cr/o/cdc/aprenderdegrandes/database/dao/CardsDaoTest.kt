package com.cr.o.cdc.aprenderdegrandes.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.mocks.Mocks
import com.cr.o.cdc.aprenderdegrandes.modules.DatabaseModule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.time.Instant

class CardsDaoTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dao = DatabaseModule().provideDatabase(
        InstrumentationRegistry.getInstrumentation().targetContext
    ).cardsDao()

    @Test
    fun insertCards() = runTest {
        val cards = Cards(
            Instant.now().toEpochMilli(),
            Mocks.ONE_CARD.value
        )
        dao.insert(cards)
        assertEquals(
            "text",
            dao.get().first()?.cards?.get(0)?.text
        )
    }

    @Test
    fun deleteAll() = runTest {
        val cards = Cards(
            1,
            Mocks.ONE_CARD.value
        )
        dao.insert(cards)
        dao.deleteAll()
        assertEquals(
            null,
            dao.get().first()?.cards?.get(0)?.text
        )
    }
}
package com.cr.o.cdc.aprenderdegrandes

import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSourceImp
import com.cr.o.cdc.aprenderdegrandes.mocks.MockFirebaseDatabase
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CardsDataSourceImpTest {

    private val dataSource = CardsDataSourceImp(MockFirebaseDatabase)

    @Test
    fun getCards() = runTest {
        val cards = dataSource.getCards()
        assertEquals(
            Card(
                "pregunta",
                0
            ),
            cards[0]
        )
    }
}
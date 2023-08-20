package com.cr.o.cdc.aprenderdegrandes

import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSourceImp
import com.cr.o.cdc.aprenderdegrandes.mocks.MockFirebaseDatabase
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.cr.o.cdc.aprenderdegrandes.repos.model.Type
import org.junit.Assert.assertEquals
import org.junit.Test

class CardsDataSourceImpTest {

    private val dataSource = CardsDataSourceImp(MockFirebaseDatabase)

    @Test
    fun getCards() {
        val cards = dataSource.getCards()
        assertEquals(
            Card(
                "pregunta",
                Type.GOLD,
                1
            ),
            cards[0]
        )
    }
}
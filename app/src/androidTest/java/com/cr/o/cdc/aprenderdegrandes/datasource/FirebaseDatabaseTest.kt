package com.cr.o.cdc.aprenderdegrandes.datasource

import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabaseImp
import org.junit.Assert.assertEquals
import org.junit.Test

class FirebaseDatabaseTest {
    private val firebaseDatabase = FirebaseDatabaseImp()

    @Test
    fun getCards() {
        val list = firebaseDatabase.getCards()
        assertEquals(
            "¿Sentís que tenéis un propósito en la vida? ¿Cual es?",
            list[0]["text"]
        )
    }
}
package com.cr.o.cdc.aprenderdegrandes.datasource

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FirebaseDatabaseTest {
    private val firebaseDatabase = FirebaseDatabaseImp()

    @Test
    fun getCards() = runTest {
        val list = firebaseDatabase.getCards()
        assertEquals(
            "¿Sentís que tenéis un propósito en la vida? ¿Cual es?",
            list[0]["text"]
        )
    }
}
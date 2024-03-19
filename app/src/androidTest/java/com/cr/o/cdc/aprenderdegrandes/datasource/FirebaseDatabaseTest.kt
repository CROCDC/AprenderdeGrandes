package com.cr.o.cdc.aprenderdegrandes.datasource

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FirebaseDatabaseTest {
    private val firebaseDatabase = FirebaseDatabaseImp()

    @Test
    fun getCards() = runTest {
        val list = firebaseDatabase.getCards()
        assertEquals(
            "¿Sentís que tenés un propósito en la vida? ¿Cual es?",
            list[0]["text"]
        )
    }

    @Test
    fun getVolume1() = runTest {
        val list = firebaseDatabase.getCardsByVolume(1)
        assertEquals(
            "¿Sentís que tenés un propósito en la vida? ¿Cual es?",
            list[0]["text"]
        )
    }

    @Test
    fun getVolume2() = runTest {
        val list = firebaseDatabase.getCardsByVolume(2)
        assertEquals(
            "¿Qué harías si no tuvieras que trabajar para ganar dinero?",
            list[0]["text"]
        )
    }

    @Test
    fun getVolumeEmpty() = runTest {
        val list = firebaseDatabase.getCardsByVolume(Int.MAX_VALUE)
        assertTrue(list.isEmpty())
    }
}
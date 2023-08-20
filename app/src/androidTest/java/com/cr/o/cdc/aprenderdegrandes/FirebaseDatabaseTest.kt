package com.cr.o.cdc.aprenderdegrandes

import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabaseImp
import org.junit.Test

class FirebaseDatabaseTest {
    private val firebaseDatabase = FirebaseDatabaseImp()

    @Test
    fun getCards() {
        firebaseDatabase.getCards()
    }
}
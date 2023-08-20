package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class MockCardsDao : CardsDao {
    private var cards: MutableStateFlow<Cards?> = MutableStateFlow(null)
    override fun get(): Flow<Cards?> = cards

    override fun insert(cards: Cards) {
        runBlocking {
            this@MockCardsDao.cards.emit(cards)
        }
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

}
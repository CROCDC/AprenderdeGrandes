package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockCardsDao : CardsDao {
    private var cards: MutableStateFlow<Cards?> = MutableStateFlow(null)
    override fun get(): Flow<Cards?> = cards
    override fun insert(cards: List<CardEntity>) {
        this.cards.value = this.cards.value?.copy(cards = cards) ?: Cards(
            SavedTimeEntity(0),
            cards
        )
    }

    override fun insert(savedTimeEntity: SavedTimeEntity) {
        this.cards.value = this.cards.value?.copy(savedTimeEntity = savedTimeEntity) ?: Cards(
            savedTimeEntity,
            listOf()
        )
    }

    override fun deleteAllSaveTimeEntity() {
    }

    override fun deleteAllCardEntity() {
    }

    override fun update(cardEntity: CardEntity) {
        this.cards.value = this.cards.value?.copy(
            cards = listOf(cardEntity)
        )
    }
}
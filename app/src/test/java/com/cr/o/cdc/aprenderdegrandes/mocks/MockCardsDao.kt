package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeWithCards
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockCardsDao : CardsDao {
    private var cards: MutableStateFlow<Cards?> = MutableStateFlow(null)
    override fun getVolumeWithCards(id: Int): Flow<VolumeWithCards?> {
        TODO("Not yet implemented")
    }

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

    override fun insert(volumeEntity: VolumeEntity) {
        TODO("Not yet implemented")
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
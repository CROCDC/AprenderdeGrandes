package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity

object CardsMock {

    fun getCards(
        savedTimeEntity: SavedTimeEntity = SavedTimeEntityMock.getSavedTimeEntity(),
        cardEntities: List<CardEntity> = CardEntityMock.cardEntities()
    ) = Cards(
        savedTimeEntity,
        cardEntities
    )
}
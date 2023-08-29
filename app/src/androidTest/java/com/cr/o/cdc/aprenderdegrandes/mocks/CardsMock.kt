package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity

object CardsMock {

    fun getSavedTimeEntity(timeStamp: Long = 0) = SavedTimeEntity(timeStamp)

    fun getCardsEntities() = listOf(
        CardEntity(
            1,
            "text",
            10,
            0
        ),
        CardEntity(
            2,
            "text 2",
            0,
            0
        )
    )

    fun getCards(
        savedTimeEntity: SavedTimeEntity = getSavedTimeEntity(),
        cardEntities: List<CardEntity> = getCardsEntities()
    ) = Cards(
        savedTimeEntity,
        cardEntities
    )
}
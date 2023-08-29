package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity

object CardEntityMock {

    fun getFirstCardEntity(
        text: String = "text",
        saveTimeId: Long = 0
    ) = CardEntity(
        0,
        text,
        0,
        saveTimeId
    )

    fun getSecondCardEntity(
        text: String = "text 2",
        saveTimeId: Long = 0
    ) = CardEntity(
        1,
        text,
        0,
        saveTimeId
    )

    fun cardEntities(
        firstText: String = "text",
        firstSaveTimeId: Long = 0,
        secondText: String = "text 2",
        secondSaveTimeId: Long = 0,
    ) = listOf(
        getFirstCardEntity(firstText, firstSaveTimeId),
        getSecondCardEntity(secondText, secondSaveTimeId)

    )
}
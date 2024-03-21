package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity

object CardEntityMock {

    fun getFirstCardEntity(
        text: String = "text",
        volumeEntityId: Int = 1
    ) = CardEntity(
        0,
        text,
        0,
        volumeEntityId
    )

    fun getSecondCardEntity(
        text: String = "text 2",
        volumeEntityId: Int = 1
    ) = CardEntity(
        1,
        text,
        0,
        volumeEntityId
    )

    fun cardEntities(
        firstText: String = "text",
        firstVolumeEntityId: Int = 1,
        secondText: String = "text 2",
        secondVolumeEntityId: Int = 1,
    ) = listOf(
        getFirstCardEntity(firstText, firstVolumeEntityId),
        getSecondCardEntity(secondText, secondVolumeEntityId)

    )
}
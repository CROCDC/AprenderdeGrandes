package com.cr.o.cdc.aprenderdegrandes.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class VolumeWithCards(
    @Embedded val volume: VolumeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "volumeEntityId"
    )
    val cards: List<CardEntity>
)

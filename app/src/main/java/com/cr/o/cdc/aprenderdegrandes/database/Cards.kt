package com.cr.o.cdc.aprenderdegrandes.database

import androidx.room.Embedded
import androidx.room.Relation
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity

data class Cards(
    @Embedded val savedTimeEntity: SavedTimeEntity,
    @Relation(
        parentColumn = "timeStamp",
        entityColumn = "saveTimeId"
    )
    val cards: List<CardEntity>
)
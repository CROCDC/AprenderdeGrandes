package com.cr.o.cdc.aprenderdegrandes.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = VolumeEntity::class,
            parentColumns = ["id"],
            childColumns = ["volumeEntityId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CardEntity(
    @PrimaryKey val id: String,
    val text: String,
    val viewedTimes: Int,
    val volumeEntityId: Int
)
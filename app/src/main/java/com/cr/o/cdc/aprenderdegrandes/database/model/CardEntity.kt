package com.cr.o.cdc.aprenderdegrandes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardEntity(
    @PrimaryKey val id: Int,
    val text: String,
    val viewedTimes: Int,
    val saveTimeId: Long
)
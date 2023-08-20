package com.cr.o.cdc.aprenderdegrandes.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card

@Entity
data class Cards(
    @PrimaryKey val timeStamp: Long,
    val cards: List<Card>
) {
    constructor(cards: List<Card>) : this(System.currentTimeMillis(), cards)
}
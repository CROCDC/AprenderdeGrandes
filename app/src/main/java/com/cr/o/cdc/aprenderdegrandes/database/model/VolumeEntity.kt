package com.cr.o.cdc.aprenderdegrandes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VolumeEntity(
    @PrimaryKey val id: Int,
    val saveTime: Long
)
package com.cr.o.cdc.aprenderdegrandes.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedTimeEntity(@PrimaryKey val timeStamp: Long)
package com.cr.o.cdc.aprenderdegrandes.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cr.o.cdc.aprenderdegrandes.database.Cards
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {
    @Query("SELECT * FROM Cards")
    fun get(): Flow<Cards?>

    @Insert
    fun insert(cards: Cards)

    @Query("DELETE FROM Cards")
    fun deleteAll()
}
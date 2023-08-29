package com.cr.o.cdc.aprenderdegrandes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {
    @Query("SELECT * FROM savedtimeentity")
    fun get(): Flow<Cards?>

    @Transaction
    @Insert
    fun insert(cards: List<CardEntity>)

    @Insert
    fun insert(savedTimeEntity: SavedTimeEntity)

    @Query("DELETE FROM savedtimeentity")
    fun deleteAllSaveTimeEntity()

    @Query("DELETE FROM CardEntity")
    fun deleteAllCardEntity()

    @Update
    fun update(cardEntity: CardEntity)
}
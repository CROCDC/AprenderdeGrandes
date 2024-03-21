package com.cr.o.cdc.aprenderdegrandes.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeWithCards
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {
    @Transaction
    @Query("SELECT * FROM VolumeEntity WHERE id = :id")
    fun getVolumeWithCards(id: Int): Flow<VolumeWithCards?>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cards: List<CardEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(savedTimeEntity: SavedTimeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(volumeEntity: VolumeEntity)

    @Query("DELETE FROM savedtimeentity")
    fun deleteAllSaveTimeEntity()

    @Query("DELETE FROM CardEntity")
    fun deleteAllCardEntity()

    @Update
    fun update(cardEntity: CardEntity)
}
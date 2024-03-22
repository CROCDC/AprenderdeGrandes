package com.cr.o.cdc.aprenderdegrandes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeEntity
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [CardEntity::class, VolumeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun cardsDao(): CardsDao
}

class Converters {
    @TypeConverter
    fun cardListToJson(cards: List<Card>): String = Gson().toJson(cards)

    @TypeConverter
    fun jsonToCardList(json: String): List<Card> = Gson().fromJson(
        json,
        object : TypeToken<List<Card>>() {}.type
    )
}
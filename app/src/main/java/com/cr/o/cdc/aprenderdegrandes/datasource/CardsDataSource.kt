package com.cr.o.cdc.aprenderdegrandes.datasource

import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.cr.o.cdc.aprenderdegrandes.repos.model.Volume
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

interface CardsDataSource {
    suspend fun getCards(): List<Card>

    suspend fun getVolume(id: Int): Volume
}

class CardsDataSourceImp @Inject constructor(private val firebaseDatabase: FirebaseDatabase) :
    CardsDataSource {

    override suspend fun getCards(): List<Card> = Gson().fromJson(
        Gson().toJson(firebaseDatabase.getCards()),
        object : TypeToken<List<Card>>() {}.type
    )

    override suspend fun getVolume(id: Int): Volume = Volume(
        id,
        Gson().fromJson(
            Gson().toJson(firebaseDatabase.getCards()),
            object : TypeToken<List<Card>>() {}.type
        )
    )

}
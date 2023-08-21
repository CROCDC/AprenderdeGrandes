package com.cr.o.cdc.aprenderdegrandes.datasource

import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

interface CardsDataSource {
    fun getCards(): List<Card>
}

class CardsDataSourceImp @Inject constructor(private val firebaseDatabase: FirebaseDatabase) :
    CardsDataSource {

    override fun getCards(): List<Card> = Gson().fromJson(
        Gson().toJson(firebaseDatabase.getCards()),
        object : TypeToken<List<Card>>() {}.type
    )
}
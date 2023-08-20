package com.cr.o.cdc.aprenderdegrandes.datasource

import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface CardsDataSource {
    fun getCards(): List<Card>
}

class CardsDataSourceImp(private val firebaseDatabase: FirebaseDatabase) : CardsDataSource {

    override fun getCards(): List<Card> = listOf()

}
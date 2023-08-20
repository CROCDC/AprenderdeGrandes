package com.cr.o.cdc.aprenderdegrandes.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSource
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.google.gson.Gson

interface CardsRepository {
    fun getCards(): LiveData<List<Card>>
}

class CardsRepositoryImp(private val dataSource: CardsDataSource) : CardsRepository {


    override fun getCards(): LiveData<List<Card>> {
        val mutableLiveData = MutableLiveData<List<Card>>()
        Gson().toJson(dataSource.getCards())
        return mutableLiveData
    }
}
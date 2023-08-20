package com.cr.o.cdc.aprenderdegrandes.repos

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSource
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.networking.networkBoundResource
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import kotlinx.coroutines.flow.Flow

interface CardsRepository {
    fun getCards(): Flow<Resource<Cards>>
}

class CardsRepositoryImp(
    private val dataSource: CardsDataSource,
    private val dao: CardsDao
) : CardsRepository {

    override fun getCards() = networkBoundResource<Cards, List<Card>>(
        query = {
            dao.get()
        },
        fetch = {
            dataSource.getCards()
        },
        saveFetchResult = {
            dao.insert(Cards(it))
        },
        shouldFetch = {
            System.currentTimeMillis() - it.timeStamp >= 7 * 24 * 60 * 60 * 1000
        }
    )
}
package com.cr.o.cdc.aprenderdegrandes.repos

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSource
import com.cr.o.cdc.aprenderdegrandes.datasource.RemoteConfigDataSource
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.networking.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CardsRepository {
    fun getCards(): Flow<Resource<Cards?>>
}

class CardsRepositoryImp @Inject constructor(
    private val dataSource: CardsDataSource,
    private val dao: CardsDao,
    private val config: RemoteConfigDataSource
) : CardsRepository {

    override fun getCards() = networkBoundResource(
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
            it?.let {
                System.currentTimeMillis() - it.timeStamp >= 7 * 24 * 60 * 60 * 1000 || config.getForceUpdate()
            } ?: true
        }
    )
}
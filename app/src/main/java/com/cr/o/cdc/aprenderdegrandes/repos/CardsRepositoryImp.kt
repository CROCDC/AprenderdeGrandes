package com.cr.o.cdc.aprenderdegrandes.repos

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity
import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSource
import com.cr.o.cdc.aprenderdegrandes.datasource.RemoteConfigDataSource
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.networking.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CardsRepository {
    fun getCards(): Flow<Resource<Cards?>>

    suspend fun viewCard(viewedCardEntity: CardEntity)
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
            val time = System.currentTimeMillis()
            dao.deleteAllCardEntity()
            dao.deleteAllSaveTimeEntity()
            dao.insert(
                it.map {
                    CardEntity(
                        it.id,
                        it.text,
                        0,
                        time
                    )
                }
            )
            dao.insert(SavedTimeEntity(time))
        },
        shouldFetch = {
            it?.let {
                System.currentTimeMillis() - it.savedTimeEntity.timeStamp >= 7 * 24 * 60 * 60 * 1000 || config.getForceUpdate()
            } ?: true
        }
    )

    override suspend fun viewCard(viewedCardEntity: CardEntity) {
        withContext(Dispatchers.IO) {
            dao.update(
                viewedCardEntity.copy(
                    viewedTimes = viewedCardEntity.viewedTimes.inc()
                )
            )
        }
    }
}
package com.cr.o.cdc.aprenderdegrandes.repos

import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeWithCards
import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSource
import com.cr.o.cdc.aprenderdegrandes.datasource.RemoteConfigDataSource
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.networking.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CardsRepository {

    fun getVolume(volumeId: Int): Flow<Resource<VolumeWithCards?>>

    suspend fun viewCard(viewedCardEntity: CardEntity)
}

class CardsRepositoryImp @Inject constructor(
    private val dataSource: CardsDataSource,
    private val dao: CardsDao,
    private val config: RemoteConfigDataSource
) : CardsRepository {

    override fun getVolume(volumeId: Int): Flow<Resource<VolumeWithCards?>> = networkBoundResource(
        query = {
            dao.getVolumeWithCards(volumeId)
        },
        fetch = {
            dataSource.getVolume(volumeId)
        },
        saveFetchResult = {
            dao.insert(
                VolumeEntity(volumeId, System.currentTimeMillis())
            )
            dao.insert(
                it.cards.map {
                    CardEntity(
                        volumeId.toString() + it.id,
                        it.text,
                        0,
                        volumeId
                    )
                }
            )

        },
        shouldFetch = {
            it?.volume?.let {
                System.currentTimeMillis() - it.saveTime >= 7 * 24 * 60 * 60 * 1000 || config.getForceUpdate()
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
package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeWithCards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class MockCardsRepository : CardsRepository {
    override fun getVolume(volumeId: Int): Flow<Resource<VolumeWithCards?>> =
        if (forceLoadingResource) {
            flowOf(Resource.Loading(null))
        } else {
            volume
        }

    override suspend fun viewCard(viewedCardEntity: CardEntity) {

    }

    companion object {
        var forceLoadingResource = false
        val volume: MutableStateFlow<Resource<VolumeWithCards?>> = MutableStateFlow(
            Resource.Success(VolumeWithCardsMock.getVolumeWithCards())
        )
    }
}
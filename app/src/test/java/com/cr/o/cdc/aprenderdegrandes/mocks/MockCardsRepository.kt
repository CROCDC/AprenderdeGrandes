package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeWithCards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockCardsRepository : CardsRepository {
    val volume: MutableStateFlow<Resource<VolumeWithCards?>> =
        MutableStateFlow(Resource.Success(VolumeWithCardsMock.getVolumeWithCards()))

    override fun getVolume(volumeId: Int): Flow<Resource<VolumeWithCards?>> = volume

    override suspend fun viewCard(viewedCardEntity: CardEntity) {
        val cards = volume.value.data?.cards ?: emptyList()
        volume.value = Resource.Success(
            volume.value.data?.copy(
                cards = cards.map {
                    if (viewedCardEntity == it) {
                        it.copy(viewedTimes = it.viewedTimes.inc())
                    } else {
                        it
                    }
                }
            )
        )
    }


}
package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeWithCards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockCardsRepository : CardsRepository {
    override fun getVolume(volumeId: Int): Flow<Resource<VolumeWithCards?>> {
        TODO("Not yet implemented")
    }

    override suspend fun viewCard(viewedCardEntity: CardEntity) {
        val data = cards.value.data
        cards.value = Resource.Success(
            data?.copy(
                cards = data.cards.map {
                    if (viewedCardEntity == it) {
                        it.copy(viewedTimes = it.viewedTimes.inc())
                    } else {
                        it
                    }
                }
            )
        )
    }

    val cards: MutableStateFlow<Resource<Cards?>> =
        MutableStateFlow(Resource.Success(CardsMock.getCards()))

}
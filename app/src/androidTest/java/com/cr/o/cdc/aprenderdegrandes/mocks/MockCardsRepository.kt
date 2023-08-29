package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class MockCardsRepository : CardsRepository {
    override fun getCards(): Flow<Resource<Cards?>> = if (forceLoadingResource) {
        flowOf(Resource.Loading(null))
    } else {
        getCards
    }

    override suspend fun viewCard(viewedCardEntity: CardEntity) {

    }

    companion object {
        var forceLoadingResource = false
        val getCards: MutableStateFlow<Resource<Cards?>> = MutableStateFlow(
            Resource.Success(CardsMock.getCards())
        )
    }
}
package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockCardsRepository : CardsRepository {
    override fun getCards(): Flow<Resource<Cards?>> = getCards
    override suspend fun viewCard(viewedCardEntity: CardEntity) {

    }

    companion object {
        var manyTimeCalledFlow = 0

        fun clean() {
            manyTimeCalledFlow = 0
        }

        val getCards: Flow<Resource<Cards?>> = flow {
            manyTimeCalledFlow = manyTimeCalledFlow.inc()
            emit(Resource.Success(CardsMock.getCards()))

        }
    }
}
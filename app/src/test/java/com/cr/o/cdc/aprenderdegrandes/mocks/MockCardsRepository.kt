package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.cr.o.cdc.aprenderdegrandes.repos.model.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockCardsRepository : CardsRepository {
    override fun getCards(): Flow<Resource<Cards?>> = getCards

    companion object {
        var manyTimeCalledFlow = 0

        fun clean() {
            manyTimeCalledFlow = 0
        }

        val ONE_CARD = Card(
            "text",
            Type.GOLD,
            1
        )

        val TWO_CARD = Card(
            "text 2",
            Type.GOLD,
            2
        )

        val THIRD_CARD = Card(
            "text 3",
            Type.GOLD,
            3
        )

        val getCards: Flow<Resource<Cards?>> = flow {
            manyTimeCalledFlow = manyTimeCalledFlow.inc()
            emit(
                Resource.Success(
                    Cards(listOf(ONE_CARD, TWO_CARD, THIRD_CARD))
                )
            )

        }
    }
}
package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.cr.o.cdc.aprenderdegrandes.repos.model.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

object Mocks {
    fun getCardList(text: String = "text") = listOf(
        Card(
            text,
            Type.COARSE,
            1
        )
    )

    val CARD_RESOURCE: Flow<Resource<Cards?>> = flowOf(
        Resource.Success(
            Cards(
                1,
                getCardList()
            )
        )
    )

    fun getCardsFlow(timestamp: Long = 0) = flowOf(
        getCards(timestamp)
    )

    fun getCards(timestamp: Long = 0, text: String = "text") = Cards(
        timestamp,
        getCardList(text)
    )

    var manyTimes = 0
    fun nCards(xCards: Int): Flow<Resource<Cards?>> = flow {
        manyTimes = manyTimes.inc()
        emit(
            Resource.Success(
                Cards(
                    1,
                    ((1..xCards).map {
                        Card("Text", Type.COARSE, it)
                    })
                )
            )
        )
    }
}
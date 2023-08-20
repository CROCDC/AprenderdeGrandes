package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.cr.o.cdc.aprenderdegrandes.repos.model.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object Mocks {
    val ONE_CARD: Flow<Resource<Cards>> = flowOf(
        Resource.Success(
            Cards(
                1,
                listOf(
                    Card(
                        "text",
                        Type.COARSE,
                        1
                    )
                )
            )
        )
    )

    fun nCards(xCards: Int): Flow<Resource<Cards>> = flowOf(
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
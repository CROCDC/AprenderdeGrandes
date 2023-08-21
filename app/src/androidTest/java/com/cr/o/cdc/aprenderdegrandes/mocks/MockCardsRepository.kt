package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.Cards
import com.cr.o.cdc.aprenderdegrandes.networking.Resource
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockCardsRepository : CardsRepository {
    override fun getCards(): Flow<Resource<Cards?>> = getCards

    companion object {
        val getCards: MutableStateFlow<Resource<Cards?>> = MutableStateFlow(
            Resource.Success(
                Cards(
                    Mocks.ONE_CARD.value
                )
            )
        )
    }
}
package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.repos.model.Card

object CardMock {

    fun getCards(text: String = "text") = listOf(
        Card(text, 1)
    )
}
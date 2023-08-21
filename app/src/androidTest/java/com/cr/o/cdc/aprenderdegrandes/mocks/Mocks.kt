package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.cr.o.cdc.aprenderdegrandes.repos.model.Type
import kotlinx.coroutines.flow.MutableStateFlow

object Mocks {
    val ONE_CARD = MutableStateFlow(
        listOf(
            Card(
                "text",
                Type.COARSE,
                1
            )
        )
    )

    val TWO_CARD = MutableStateFlow(
        listOf(
            Card(
                "text",
                Type.COARSE,
                1
            ),
            Card(
                "text 2",
                Type.COARSE,
                2
            )
        )
    )
}
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

    val firstCardText = "¿Sentís que tenés un propósito en la vida? ¿Cual es?"
    val secondCardText = "¿Sentís que tenés un propósito en la vida? ¿Cual es? 2"

    val TWO_CARD = MutableStateFlow(
        listOf(
            Card(
                firstCardText,
                Type.COARSE,
                1
            ),
            Card(
                secondCardText,
                Type.COARSE,
                2
            )
        )
    )
}
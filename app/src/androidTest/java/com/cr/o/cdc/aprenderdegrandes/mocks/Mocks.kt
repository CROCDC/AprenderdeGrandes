package com.cr.o.cdc.aprenderdegrandes.mocks

import androidx.lifecycle.MutableLiveData
import com.cr.o.cdc.aprenderdegrandes.repos.model.Card
import com.cr.o.cdc.aprenderdegrandes.repos.model.Type

object Mocks {
    val ONE_CARD = MutableLiveData(
        listOf(
            Card(
                "text",
                Type.COARSE,
                1
            )
        )
    )
}
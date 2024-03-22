package com.cr.o.cdc.aprenderdegrandes.states

import com.cr.o.cdc.aprenderdegrandes.database.model.CardEntity

abstract class VolumeUIState {

    object Loading : VolumeUIState()

    data class ShowCard(val card: CardEntity) : VolumeUIState()

    object EmptyCards : VolumeUIState()
}
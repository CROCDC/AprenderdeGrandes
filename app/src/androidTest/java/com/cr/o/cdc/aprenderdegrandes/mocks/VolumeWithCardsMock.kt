package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeWithCards

object VolumeWithCardsMock {

    fun getVolumeWithCards(): VolumeWithCards {
        val volume = VolumeEntityMock.getVolumeEntity()

        val cards = listOf(
            CardEntityMock.getFirstCardEntity(),
            CardEntityMock.getSecondCardEntity()
        )

        return VolumeWithCards(
            volume = volume,
            cards = cards
        )
    }
}

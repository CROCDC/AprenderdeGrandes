package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.model.VolumeEntity

object VolumeEntityMock {

    fun getVolumeEntity() = VolumeEntity(
        id = 1,
        saveTime = System.currentTimeMillis()
    )
}
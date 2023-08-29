package com.cr.o.cdc.aprenderdegrandes.mocks

import com.cr.o.cdc.aprenderdegrandes.database.model.SavedTimeEntity

object SavedTimeEntityMock {

    fun getSavedTimeEntity(timeStamp: Long = 0) = SavedTimeEntity(timeStamp)
}
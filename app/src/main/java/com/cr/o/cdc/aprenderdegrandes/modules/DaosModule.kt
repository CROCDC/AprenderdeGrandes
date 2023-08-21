package com.cr.o.cdc.aprenderdegrandes.modules

import com.cr.o.cdc.aprenderdegrandes.database.Database
import com.cr.o.cdc.aprenderdegrandes.database.dao.CardsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DaosModule {

    @Provides
    fun providesCardsDao(database: Database): CardsDao = database.cardsDao()
}
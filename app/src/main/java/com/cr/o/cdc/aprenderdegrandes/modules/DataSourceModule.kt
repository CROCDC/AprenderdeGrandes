package com.cr.o.cdc.aprenderdegrandes.modules

import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSource
import com.cr.o.cdc.aprenderdegrandes.datasource.CardsDataSourceImp
import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DataSourceModule {
    @Provides
    fun providesCardsDataSource(firebaseDatabase: FirebaseDatabase): CardsDataSource =
        CardsDataSourceImp(firebaseDatabase)
}
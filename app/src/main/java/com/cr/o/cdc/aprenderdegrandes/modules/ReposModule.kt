package com.cr.o.cdc.aprenderdegrandes.modules

import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReposModule {

    @Binds
    abstract fun bindCardsRepository(repository: CardsRepositoryImp): CardsRepository
}
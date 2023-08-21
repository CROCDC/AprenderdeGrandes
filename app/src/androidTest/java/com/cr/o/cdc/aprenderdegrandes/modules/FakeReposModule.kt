package com.cr.o.cdc.aprenderdegrandes.modules

import com.cr.o.cdc.aprenderdegrandes.mocks.MockCardsRepository
import com.cr.o.cdc.aprenderdegrandes.repos.CardsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [ReposModule::class]
)
class FakeReposModule {

    @Provides
    fun providesRepositoryModule(): CardsRepository = MockCardsRepository()
}
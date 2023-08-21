package com.cr.o.cdc.aprenderdegrandes.modules

import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabase
import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class FirebaseDatabaseModule {

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabaseImp()
}
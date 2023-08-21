package com.cr.o.cdc.aprenderdegrandes.modules

import android.content.Context
import androidx.room.Room
import com.cr.o.cdc.aprenderdegrandes.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(
            context,
            Database::class.java, "database_aprender"
        ).build()
}
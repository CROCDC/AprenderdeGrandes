package com.cr.o.cdc.aprenderdegrandes.modules

import android.content.Context
import com.cr.o.cdc.aprenderdegrandes.R
import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabase
import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabaseImp
import com.cr.o.cdc.aprenderdegrandes.datasource.RemoteConfigDataSource
import com.cr.o.cdc.aprenderdegrandes.datasource.RemoteConfigDataSourceImp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabaseImp()

    @Provides
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics =
        FirebaseAnalytics.getInstance(context)

    @Provides
    fun provideRemoteConfigDataSource(): RemoteConfigDataSource = RemoteConfigDataSourceImp()
}
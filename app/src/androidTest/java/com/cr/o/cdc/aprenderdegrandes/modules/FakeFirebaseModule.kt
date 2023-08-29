package com.cr.o.cdc.aprenderdegrandes.modules

import com.cr.o.cdc.aprenderdegrandes.analitycs.MyFirebaseAnalytics
import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabase
import com.cr.o.cdc.aprenderdegrandes.datasource.FirebaseDatabaseImp
import com.cr.o.cdc.aprenderdegrandes.datasource.RemoteConfigDataSource
import com.cr.o.cdc.aprenderdegrandes.datasource.RemoteConfigDataSourceImp
import com.cr.o.cdc.aprenderdegrandes.mocks.MockMyFirebaseAnalyticsImp
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FirebaseModule::class]
)
class FakeFirebaseModule {

    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabaseImp()

    @Provides
    fun provideMyFirebaseAnalyticsImp(): MyFirebaseAnalytics = MockMyFirebaseAnalyticsImp()

    @Provides
    fun provideRemoteConfigDataSource(): RemoteConfigDataSource = RemoteConfigDataSourceImp()
}
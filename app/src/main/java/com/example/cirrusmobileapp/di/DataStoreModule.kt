package com.example.cirrusmobileapp.di

import com.example.cirrusmobileapp.data.local.datasource.shared_pref_datastore.PreferencesManager
import com.example.cirrusmobileapp.data.local.datasource.shared_pref_datastore.PreferencesManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesManager(impl: PreferencesManagerImpl): PreferencesManager = impl
}

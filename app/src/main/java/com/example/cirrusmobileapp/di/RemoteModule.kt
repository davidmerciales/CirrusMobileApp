package com.example.cirrusmobileapp.di

import com.example.cirrusmobileapp.data.remote.api.ProductApiService
import com.example.cirrusmobileapp.data.remote.api.ProductApiServiceImpl
import com.example.cirrusmobileapp.data.remote.datasource.ProductRemoteDataSource
import com.example.cirrusmobileapp.data.remote.datasource.ProductRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Binds
    @Singleton
    abstract fun bindProductApiService(
        productApiServiceImpl: ProductApiServiceImpl
    ): ProductApiService

    @Binds
    @Singleton
    abstract fun bindProductRemoteDataSource(
        productRemoteDataSourceImpl: ProductRemoteDataSourceImpl
    ): ProductRemoteDataSource
}
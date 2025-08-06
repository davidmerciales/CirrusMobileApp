package com.example.cirrusmobileapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.cirrusmobileapp.data.local.dao.ProductDao
import com.example.cirrusmobileapp.data.local.datasource.ProductLocalDataSource
import com.example.cirrusmobileapp.data.local.datasource.ProductLocalDataSourceImpl
import com.example.cirrusmobileapp.data.local.db.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    companion object {
        @Provides
        @Singleton
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }

        @Provides
        @Singleton
        fun provideAppDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "cirrus_database"
            ).build()
        }

        @Provides
        @Singleton
        fun provideProductDao(appDatabase: AppDatabase): ProductDao {
            return appDatabase.productDao
        }
    }

    @Binds
    @Singleton
    abstract fun bindProductLocalDataSource(
        localProductDataSourceImpl: ProductLocalDataSourceImpl
    ): ProductLocalDataSource
}
package com.example.cirrusmobileapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.cirrusmobileapp.data.local.dao.ProductDao
import com.example.cirrusmobileapp.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Named
import javax.inject.Singleton

//const val DATABASE_NAME = "cirrus.db"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    @Named("database_name")
//    fun provideDatabaseName() = DATABASE_NAME
//
//    @Provides
//    @Singleton
//    fun provideAppDatabase(
//        app: Context,
//        @Named("database_name") dbName: String
//    ): AppDatabase {
//        return Room.databaseBuilder(
//            app,
//            AppDatabase::class.java,
//            dbName
//        ).build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideProductDao(db :AppDatabase) : ProductDao {
//        return db.productDao
//    }


    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }
}
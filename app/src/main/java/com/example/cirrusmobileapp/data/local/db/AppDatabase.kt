package com.example.cirrusmobileapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cirrusmobileapp.data.local.dao.ProductDao
import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import com.example.cirrusmobileapp.data.local.entities.VariantEntity
import com.example.cirrusmobileapp.data.local.utils.converter.VariantTypeConverter

@Database(
    entities = [ProductEntity :: class, VariantEntity:: class],
    version = 1)
@TypeConverters(VariantTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val productDao : ProductDao
}
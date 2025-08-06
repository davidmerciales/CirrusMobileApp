package com.example.cirrusmobileapp.data.local.utils.converter

import androidx.room.TypeConverter
import com.example.cirrusmobileapp.data.local.entities.VariantEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class VariantTypeConverter {

    @TypeConverter
    fun fromVariantList(variantEntities: List<VariantEntity>): String {
        return Gson().toJson(variantEntities)
    }

    @TypeConverter
    fun toVariantList(variantString: String): List<VariantEntity> {
        val type = object : TypeToken<List<VariantEntity>>() {}.type
        return Gson().fromJson(variantString, type)
    }
}
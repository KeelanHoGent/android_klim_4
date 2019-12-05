package com.klimaatmobiel.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.klimaatmobiel.domain.Product
import java.util.*
import kotlin.collections.ArrayList

class Converters {
    @TypeConverter
    fun fromString(value : String?): List<Product> {
        val type = object : TypeToken<List<Product>>() { }.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list : List<Product>?) : String {
        return Gson().toJson(list);
    }
}
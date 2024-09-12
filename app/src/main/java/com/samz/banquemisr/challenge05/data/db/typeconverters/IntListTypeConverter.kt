package com.samz.banquemisr.challenge05.data.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object IntListTypeConverter {
    /**
     * Convert ArrayList of Integer value to String value to be apply to save in the database table
     *
     * @param list arrayList of Integer
     * @return Converted String of the list value
     */
    @JvmStatic
    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return Gson().toJson(list)
    }

    /**
     * Convert Text field from table to it's origin value as ArrayList of Integer
     *
     * @param value the String value in the database table
     * @return the origin value format as ArrayList Of Integer
     */
    @JvmStatic
    @TypeConverter
    fun toIntList(value: String): List<Int>? {
        val listType: Type = object : TypeToken<List<Int>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}
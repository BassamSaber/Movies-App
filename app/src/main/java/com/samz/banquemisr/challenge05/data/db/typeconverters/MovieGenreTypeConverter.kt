package com.samz.banquemisr.challenge05.data.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samz.banquemisr.challenge05.data.remote.model.MovieGenre
import java.lang.reflect.Type


object MovieGenreTypeConverter {
    /**
     * Convert ArrayList of Movie Genre value to String value to be apply to save in the database table
     *
     * @param list arrayList of MovieGenreDto
     * @return Converted String of the list value
     */
    @JvmStatic
    @TypeConverter
    fun fromCountryDistrictsList(countryDistricts: List<MovieGenre>?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<MovieGenre>?>() {}.type
        return gson.toJson(countryDistricts, type)
    }


    /**
     * Convert Text field from table to it's origin value as ArrayList of MovieGenreDto
     *
     * @param value the String value in the database table
     * @return the origin value format as ArrayList Of MovieGenreDto
     */
    @JvmStatic
    @TypeConverter
    fun toCountryDistrictsList(value: String): List<MovieGenre>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<MovieGenre>?>() {}.type
        return gson.fromJson<List<MovieGenre>>(value, type)
    }
}
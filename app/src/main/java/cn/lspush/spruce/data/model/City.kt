package cn.lspush.spruce.data.model

import com.google.gson.annotations.SerializedName

/**
 * City bean of YY Weather
 */
data class City(
        @SerializedName("city_id") val cityId: String,
        val name: String,
        val en: String,
        val list: List<City>
)
package com.example.practicesession.superballgame.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SuperBallConverters {

    @TypeConverter
    fun fromSelectedList(source: ArrayList<BallTypeModel>): String? {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun toSelectedList(name: String): ArrayList<BallTypeModel> {
        return Gson().fromJson(name, object : TypeToken<ArrayList<BallTypeModel>>() {}.type)
    }
}
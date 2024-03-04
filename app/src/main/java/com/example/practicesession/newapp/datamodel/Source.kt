package com.example.practicesession.newapp.datamodel

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null

)
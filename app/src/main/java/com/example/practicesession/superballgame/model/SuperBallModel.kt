package com.example.practicesession.superballgame.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "superball"
)
data class SuperBallModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var gameName: String,
    var selectedList: ArrayList<BallTypeModel>? = arrayListOf(),
    var maxNormal: Int,
    var maxSuper: Int,
    var nRangeMin: Int = 0,
    var nRangeMax: Int = 0,
    var sRangeMin: Int = 0,
    var sRangeMax: Int = 0
)
package com.example.practicesession.superballgame.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.practicesession.newapp.database.ArticleDBDao
import com.example.practicesession.newapp.datamodel.Article
import com.example.practicesession.superballgame.model.SuperBallConverters
import com.example.practicesession.superballgame.model.SuperBallModel

@Database(entities = [SuperBallModel::class], version = 2)
@TypeConverters(SuperBallConverters::class)
abstract class SuperBallAppDatabase : RoomDatabase() {
    abstract fun getArticleDBDao(): SuperBallDao
}
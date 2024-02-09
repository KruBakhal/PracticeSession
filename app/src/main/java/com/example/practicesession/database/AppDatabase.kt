package com.example.practicesession.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.practicesession.datamodel.Article
import com.example.practicesession.datamodel.Converters

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getArticleDBDao(): ArticleDBDao
}
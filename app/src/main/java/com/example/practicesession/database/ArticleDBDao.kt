package com.example.practicesession.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicesession.datamodel.Article

@Dao
interface ArticleDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM articles where url=:url")
    fun geStatusArticle(url: String): Article

    @Query("DELETE FROM articles WHERE url = :article")
    suspend fun deleteArticle(article: String?)
}
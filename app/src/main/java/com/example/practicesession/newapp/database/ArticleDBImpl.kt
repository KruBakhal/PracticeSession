package com.example.practicesession.newapp.database

import com.example.practicesession.newapp.datamodel.Article
import javax.inject.Inject

class ArticleDBImpl @Inject constructor(val db: ArticleDBDao) {


    fun getSavedNews() = db.getAllArticles()
    suspend fun deleteSavedNews(article: String?) = db.deleteArticle(article)
    suspend fun insertNews(article: Article) = db.upsert(article)
    suspend fun getArticleStatus(article: Article) = article.url?.let {
        db.geStatusArticle(
            it
        )
    }


}
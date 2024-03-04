package com.example.practicesession.superballgame.database

import com.example.practicesession.superballgame.model.SuperBallModel
import javax.inject.Inject

class SuperBallDBImpl @Inject constructor(val db: SuperBallDao) {

    fun getAllData() = db.getAllSuperBallModels()
    suspend fun deleteSavedNews(article: SuperBallModel) = db.deleteModel(article)
    suspend fun insertNews(article: SuperBallModel) = db.upsert(article)
    fun getSpecificGameList(article: String) = db.getGameSuperBallIList(article)
    fun getList() = db.getAllSuperBallList()
    suspend fun getArticleStatus(article: SuperBallModel) = article.gameName?.let {
        db.getSuperBallItem(
            it, article.id!!
        )
    }


}
package com.example.practicesession.superballgame.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicesession.superballgame.model.SuperBallModel

@Dao
interface SuperBallDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(SuperBallModel: SuperBallModel): Long

    @Query("SELECT * FROM superball")
    fun getAllSuperBallModels(): List<SuperBallModel>

    @Query("SELECT * FROM superball")
    fun getAllSuperBallList(): List<SuperBallModel>

    @Query("SELECT * FROM superball WHERE gameName = :name & id=:id")
    fun getSuperBallItem(name: String?, id: Int): SuperBallModel

    @Query("SELECT * FROM superball WHERE gameName = :name ")
    fun getGameSuperBallIList(name: String): LiveData<List<SuperBallModel>>

    @Query("SELECT * FROM superball where gameName=:url")
    fun geStatusSuperBallModel(url: String): SuperBallModel

    @Delete
    suspend fun deleteModel(id: SuperBallModel)
}
package com.example.practicesession.newapp.remote

import com.example.practicesession.newapp.datamodel.NewsResponse
import com.example.practicesession.newapp.datamodel.Article
import com.example.practicesession.utils.Constant.Companion.API_KEY
import com.squareup.moshi.Json
import retrofit2.http.GET
import retrofit2.http.Query

data class ResponseItems<T>(
    @field:Json(name = "articles") val articles: List<T>
)

data class NewsResponse(val id: Int, val article: Article)
interface RemoteInterface {


    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

    @GET("v2/top-headlines")
    suspend fun getBreakingNews1(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): ResponseItems<Article>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

}
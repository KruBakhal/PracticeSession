package com.example.practicesession.newapp.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.practicesession.newapp.datamodel.Article
import com.example.practicesession.newapp.datamodel.Resource
import com.example.practicesession.newapp.remote.RemoteInterface
import com.example.practicesession.newapp.utils.Constant.Companion.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RemoteInterfaceImpl @Inject constructor(val remoteInterface: RemoteInterface)  {


    suspend fun getBreakingNew(breakingPage: Int): Resource<List<Article>> {

        try {
            val data = remoteInterface.getBreakingNews(pageNumber = breakingPage)
            return Resource.Success(data.articles)

        } catch (es: Exception) {
            return Resource.Error(es.message.toString(), null)
        }
    }

    suspend fun getSearch(text: String): Resource<List<Article>> {
        try {
            val data = remoteInterface.searchForNews(searchQuery = text)
            return Resource.Success(data.articles)

        } catch (es: Exception) {
            return Resource.Error(es.message.toString(), null)
        }
    }

     fun getPagination(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service = remoteInterface)
            }
        ).flow
    }

}



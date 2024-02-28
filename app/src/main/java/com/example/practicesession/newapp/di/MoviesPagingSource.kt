package com.example.practicesession.newapp.di

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practicesession.newapp.datamodel.Article
import com.example.practicesession.newapp.remote.RemoteInterface
import com.example.practicesession.newapp.utils.Constant.Companion.TMDB_STARTING_PAGE_INDEX
import java.io.IOException
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val service: RemoteInterface
) : PagingSource<Int, Article>() {


    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, Article> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = service.getBreakingNews1(
                pageNumber = pageIndex
            )
            val movies = response.articles
            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + 1
                }
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
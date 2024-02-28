package com.example.practicesession.newapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newappdi.NewsApp.Adapter.NewAdapter
import com.example.practicesession.newapp.datamodel.Article
import com.example.practicesession.newapp.datamodel.Resource
import com.example.practicesession.newapp.database.ArticleDBImpl
import com.example.practicesession.newapp.di.RemoteInterfaceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewAppViewModel @Inject constructor(
    val remoteInterfaceImpl: RemoteInterfaceImpl,
    val articleDBImpl: ArticleDBImpl
) :
    ViewModel() {

    var listBreakingNew: MutableList<Article>? = null
    val favStaus = MutableLiveData<Boolean>(false)
    val _breakingNewList = MutableLiveData<Resource<List<Article>>>()
    val breakingNewList: LiveData<Resource<List<Article>>> get() = _breakingNewList
    val breakingPage = 1;
    val _searchgNewList = MutableLiveData<Resource<List<Article>>>()
    val searchNewList: LiveData<Resource<List<Article>>> get() = _searchgNewList
    val searchPage = 1;
    var _adapter: NewAdapter? = null

    fun getBreakingAdapter(): NewAdapter {
        if (_adapter == null) {
            _adapter = NewAdapter()
            listBreakingNew = mutableListOf()
        }
        return _adapter!!

    }


    fun getBreakingNew() {
        viewModelScope.launch {
            _breakingNewList.postValue(Resource.Loading())
            val data = remoteInterfaceImpl.getBreakingNew(breakingPage)
            _breakingNewList.postValue(data)

            //            _breakingNewList.value.
        }
    }

    fun getBreakingNewDatabase() = articleDBImpl.getSavedNews()

    fun getSearch(breakingPage: String) {
        viewModelScope.launch {
            _searchgNewList.postValue(Resource.Loading())
            val data = remoteInterfaceImpl.getSearch(breakingPage)
            _searchgNewList.postValue(data)
        }
    }

    fun getMovies(): Flow<PagingData<Article>> {
        return remoteInterfaceImpl.getPagination()
            .map { pagingData ->
                pagingData.map {
                    it
                }
            }
            .cachedIn(viewModelScope)
    }

    fun getStatusArticle(artcile: Article) {
        viewModelScope.launch {
            val status = articleDBImpl.getArticleStatus(artcile)
            if (status != null) {
                favStaus.postValue(true)
            } else {
                favStaus.postValue(false)
            }
        }
    }

    fun performFavOurite(artcile: Article) {
        viewModelScope.launch {
            if (favStaus.value == true) {
                articleDBImpl.deleteSavedNews(artcile.url)
            } else {
                articleDBImpl.insertNews(artcile)
            }
            getStatusArticle(artcile)
        }
    }

}
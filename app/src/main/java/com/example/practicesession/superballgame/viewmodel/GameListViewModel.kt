package com.example.practicesession.superballgame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicesession.superballgame.database.SuperBallDBImpl
import com.example.practicesession.superballgame.model.SuperBallModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameListViewModel @Inject constructor(val superBallDBImpl: SuperBallDBImpl) : ViewModel() {

    var superBallModel: SuperBallModel? = null
    val _listOfGame = MutableLiveData<List<SuperBallModel>>()
    val listOfGame: LiveData<List<SuperBallModel>> get() = _listOfGame


    fun getGameList() = superBallDBImpl.getSpecificGameList(superBallModel!!.gameName)

    fun deleteModel(superBallModel: SuperBallModel) {
        viewModelScope.launch {
            superBallDBImpl.deleteSavedNews(superBallModel)
        }
    }

}
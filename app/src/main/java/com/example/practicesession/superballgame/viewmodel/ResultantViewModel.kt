package com.example.practicesession.superballgame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicesession.superballgame.database.SuperBallDBImpl
import com.example.practicesession.superballgame.model.ResultantModel
import com.example.practicesession.superballgame.model.SuperBallModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultantViewModel @Inject constructor(val superBallDBImpl: SuperBallDBImpl) : ViewModel() {

    val _listOfGame = MutableLiveData<List<ResultantModel>>()
    val listOfGame: LiveData<List<ResultantModel>> get() = _listOfGame


    fun getGameList() {
        viewModelScope.launch {
            val list = superBallDBImpl.getAllData()
            if (!list.isNullOrEmpty()) {
                val listString = arrayListOf<String>()
                var listSibbgle = arrayListOf<SuperBallModel>()
                val resultantList = arrayListOf<ResultantModel>()
                var model: ResultantModel? = null
                list.forEachIndexed { index, it ->
                    if (listString.contains(it.gameName)) {
                        listSibbgle.add(it)
                    } else {
                        model?.let {
                            it.selected = listSibbgle.clone() as List<SuperBallModel>
                            resultantList.add(it)
                        }
                        listSibbgle.clear()
                        listSibbgle= arrayListOf()
                        listSibbgle.add(it)
                        model = ResultantModel(it.gameName, arrayListOf<SuperBallModel>())
                        listString.add(it.gameName)
                    }
                    if (index == list.size - 1) {
                        model?.let {
                            it.selected = listSibbgle
                            resultantList.add(it)
                        }
                    }
                }

                _listOfGame.postValue(resultantList)
            }
        }
    }


}
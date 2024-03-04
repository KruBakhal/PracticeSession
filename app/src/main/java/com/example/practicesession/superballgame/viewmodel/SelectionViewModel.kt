package com.example.practicesession.superballgame.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicesession.superballgame.database.SuperBallDBImpl
import com.example.practicesession.superballgame.model.BallTypeModel
import com.example.practicesession.superballgame.model.SuperBallModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class SelectionViewModel @Inject constructor(val databaseImpl: SuperBallDBImpl) :
    ViewModel() {

    private var isDatabaseFound: Boolean = false
    var superBallModel: SuperBallModel? = null
    val listNormal = ArrayList<BallTypeModel>()
    val listSuper = ArrayList<BallTypeModel>()
    var listSelected = ArrayList<BallTypeModel>()
    val updateNormal = MutableLiveData<Boolean>(false);
    val updateSuper = MutableLiveData<Boolean>(false);
    val updateSelected = MutableLiveData<Boolean>(false);
    val saveStatus = MutableLiveData<Boolean>();
    var countNormal = 0
    var countSuper = 0
    var maxNormal = 0
    var maxSuper = 0

    fun setupData(normal: Int, supers: Int) {
        listNormal.clear()
        listSuper.clear()
        listSelected.clear()
        maxNormal = normal
        maxSuper = supers
        for (index: Int in superBallModel!!.nRangeMin..superBallModel!!.nRangeMax) {
            listNormal.add(BallTypeModel(true, index, false))
        }
        for (index: Int in superBallModel!!.sRangeMin..superBallModel!!.sRangeMax) {
            listSuper.add(BallTypeModel(false, index, false))
        }
        updateNormal.postValue(true)
        updateSelected.postValue(true)
        updateSuper.postValue(true)
    }

    fun syncWithDBGameData() {
        viewModelScope.launch {
//            superBallModel = databaseImpl.getArticleStatus(superBallModel!!)
            if (superBallModel != null) {
                isDatabaseFound = true
                listSelected = superBallModel?.selectedList!!;
                listSelected?.forEachIndexed { index, ballTypeModel ->
                    if (ballTypeModel.isNormal) {
                        listNormal.forEachIndexed { index, it ->
                            if (it.number == ballTypeModel.number && ballTypeModel.selected) {
                                it.selected = true
                                listNormal.set(index, it)
                                countNormal++;
                            }
                        }
                    } else {
                        listSuper.forEachIndexed { index, it ->
                            if (it.number == ballTypeModel.number && ballTypeModel.selected) {
                                it.selected = true
                                listSuper.set(index, it)
                                countSuper++
                            }
                        }
                    }
                }
                updateNormal.postValue(true)
                updateSelected.postValue(true)
                updateSuper.postValue(true)
            }
        }

    }

    fun addToNormal(position: Int, model: BallTypeModel) {
        if (countNormal < maxNormal) {
            model.selected = !model.selected
            listNormal.set(position, model)
            listSelected.clear()
            updateNormal.postValue(true)
            countNormal++
            calculateSelectedBall();
        } else {

        }
    }

    fun addToSuper(position: Int, model: BallTypeModel) {
        if (countSuper < maxSuper) {
            model.selected = !model.selected
            listSuper.set(position, model)
            listSelected.clear()
            updateSuper.postValue(true)
            countSuper++
            calculateSelectedBall();
        } else {

        }
    }

    private fun calculateSelectedBall() {
        listNormal.forEach { it ->
            if (it.selected) {
                listSelected.add(it)
            }
        }
        listSuper.forEach { it ->
            if (it.selected) {
                listSelected.add(it)
            }
        }
        updateSelected.postValue(true)
    }

    fun saveToDB() {
        viewModelScope.launch {
            superBallModel?.let {
                it.selectedList = listSelected
                val long = databaseImpl.insertNews(it)
                Log.d("TAG", "getGameList: " + databaseImpl.getList().toString())

                saveStatus.postValue(true)
            }
        }
    }
}
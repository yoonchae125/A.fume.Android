package com.afume.afume_android.ui.filter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.SearchRepository
import com.afume.afume_android.data.vo.response.ResponseKeyword
import kotlinx.coroutines.launch

class FilterViewModel :ViewModel(){
    private val searchRepository = SearchRepository()

    private val _incenseBadgeCount = MutableLiveData<Int>(0)
    val incenseBadgeCount:LiveData<Int> get()=_incenseBadgeCount
    private val _brandBadgeCount = MutableLiveData<Int>(0)
    val brandBadgeCount:LiveData<Int> get()=_brandBadgeCount
    private val _keywordBadgeCount = MutableLiveData<Int>(0)
    val keywordBadgeCount:LiveData<Int> get()=_keywordBadgeCount

    private val _applyBtn = MutableLiveData<Int>(0)
    val applyBtn:LiveData<Int> get()=_applyBtn

    val selectedKeywordList: MutableLiveData<MutableList<Int>> = MutableLiveData()
    private var tempSelectedKeywordList = mutableListOf<Int>()

    private val _keywordList: MutableLiveData<MutableList<ResponseKeyword>> = MutableLiveData()
    val keywordList: LiveData<MutableList<ResponseKeyword>> get() = _keywordList

    init {
        viewModelScope.launch{
            _keywordList.value = searchRepository.getKeyword()
        }
    }

    fun increaseBadgeCount(index:Int){
        when(index){
            0->{ _incenseBadgeCount.value =(incenseBadgeCount.value?:0)+1 }
            1->{ _brandBadgeCount.value =(brandBadgeCount.value?:0)+1 }
            2->{ _keywordBadgeCount.value =(keywordBadgeCount.value?:0)+1 }
        }
        _applyBtn.value = (_incenseBadgeCount.value?:0) + (_brandBadgeCount.value?:0) + (_keywordBadgeCount.value?:0)
    }
    fun decreaseBadgeCount(index:Int){
        when(index){
            0->{ if(incenseBadgeCount.value?:0 > 0) _incenseBadgeCount.value = (incenseBadgeCount.value?:0)-1 }
            1->{ if(brandBadgeCount.value?:0 > 0) _brandBadgeCount.value =(brandBadgeCount.value?:0)-1 }
            2->{ if(keywordBadgeCount.value?:0 > 0) _keywordBadgeCount.value =(keywordBadgeCount.value?:0)-1 }
        }
        _applyBtn.value = (_incenseBadgeCount.value?:0) + (_brandBadgeCount.value?:0) + (_keywordBadgeCount.value?:0)
    }

    fun addKeywordList(index: Int) {
        if (selectedKeywordList.value != null) tempSelectedKeywordList = selectedKeywordList.value!!

        if (!tempSelectedKeywordList.contains(index)) tempSelectedKeywordList.add(index)
        selectedKeywordList.value = tempSelectedKeywordList

        Log.e("index", index.toString())
        Log.e("add keyword", selectedKeywordList.value.toString())
    }

    fun removeKeywordList(index: Int) {
        selectedKeywordList.value?.remove(index)

        Log.e("index", index.toString())
        Log.e("remove keyword", selectedKeywordList.value.toString())
    }

}
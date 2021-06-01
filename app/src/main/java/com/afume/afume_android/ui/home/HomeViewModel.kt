package com.afume.afume_android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.AfumeApplication
import com.afume.afume_android.data.repository.HomeRepository
import com.afume.afume_android.data.vo.response.NewPerfumeItem
import com.afume.afume_android.data.vo.response.RecentPerfumeItem
import com.afume.afume_android.data.vo.response.RecommendPerfumeItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.*

class HomeViewModel : ViewModel(){
    private val homeRepository = HomeRepository()

    // 고정값 설정
    val nickTxt = MutableLiveData<String>("")
    val ageTxt = MutableLiveData<String>("")

    fun setUserInfo(){
        if(AfumeApplication.prefManager.userEmail.isNotEmpty()){
            nickTxt.postValue(AfumeApplication.prefManager.userNickname)
            ageTxt.postValue(getAgeGroupInfo().toString() + "대 " + getGenderInfo())
        }
    }

    // 나이 구하기
    private fun getAgeGroupInfo() : Int{
        val age = getYear() - AfumeApplication.prefManager.userAge + 1

        return (age/10)*10
    }

    // 현재 년도 구하기
    private fun getYear(): Int{
        val instance = Calendar.getInstance()
        return instance.get(Calendar.YEAR)
    }


    private fun getGenderInfo(): String{
        return if(AfumeApplication.prefManager.userGender == "MAN"){
            "남성"
        }else{
            "여성"
        }
    }
    private val _recommendPerfumeList : MutableLiveData<MutableList<RecommendPerfumeItem>> = MutableLiveData()
    val recommendPerfumeList : LiveData<MutableList<RecommendPerfumeItem>>
        get() = _recommendPerfumeList

    private val _commonPerfumeList : MutableLiveData<MutableList<RecommendPerfumeItem>> = MutableLiveData()
    val commonPerfumeList : LiveData<MutableList<RecommendPerfumeItem>>
        get() = _commonPerfumeList

    private val _recentPerfumeList : MutableLiveData<MutableList<RecentPerfumeItem>> = MutableLiveData()
    val recentPerfumeList : LiveData<MutableList<RecentPerfumeItem>>
        get() = _recentPerfumeList

    private val _isValidRecentList = MutableLiveData<Boolean>(true)
    val isValidRecentList : LiveData<Boolean>
        get() = _isValidRecentList

    private val _newPerfumeList : MutableLiveData<MutableList<NewPerfumeItem>> = MutableLiveData()
    val newPerfumeList : LiveData<MutableList<NewPerfumeItem>>
        get() = _newPerfumeList

    init {
        viewModelScope.launch {
            try{
                _isValidRecentList.postValue(true)
                _recommendPerfumeList.value = homeRepository.getRecommendPerfumeList(AfumeApplication.prefManager.accessToken)
                _commonPerfumeList.value = homeRepository.getCommonPerfumeList(AfumeApplication.prefManager.accessToken)
                _newPerfumeList.value = homeRepository.getNewPerfumeList()
                _recentPerfumeList.value = homeRepository.getRecentPerfumeList(AfumeApplication.prefManager.accessToken)
            }catch (e : HttpException){
                when(e.response()?.code()){
                    401 -> { // 최근 검색 향수 없음
                        _isValidRecentList.postValue(false)
                    }
                }
            }

        }
    }
}
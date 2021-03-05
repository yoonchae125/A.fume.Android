package com.afume.afume_android.ui.signup

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afume.afume_android.data.repository.SignRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.regex.Pattern

class SignUpViewModel  : ViewModel() {
    private val signRepository = SignRepository()

    // 입력 내용
    val emailTxt = MutableLiveData<String>("")
    val nickTxt = MutableLiveData<String>("")
    val passwordTxt = MutableLiveData<String>("")
    val againPasswordTxt = MutableLiveData<String>("")

    // 안내문 내용
    val emailNotice = MutableLiveData<String>()
    val nickNotice = MutableLiveData<String>()

    // 이메일 형식 검사 - 하단 안내문
    private val _isValidEmailNotice = MutableLiveData<Boolean>(false)
    val isValidEmailNotice : LiveData<Boolean>
        get() = _isValidEmailNotice

    // 이메일 중복확인 버튼 노출 여부
    private val _isValidEmailBtn = MutableLiveData<Boolean>(false)
    val isValidEmailBtn : LiveData<Boolean>
        get() = _isValidEmailBtn

    // 이메일 중복확인
    private val _isValidEmail = MutableLiveData<Boolean>(false)
    val isValidEmail : LiveData<Boolean>
        get() = _isValidEmail

    // 닉네임 형식 검사 - 하단 안내문
    private val _isValidNickNotice = MutableLiveData<Boolean>(false)
    val isValidNickNotice : LiveData<Boolean>
        get() = _isValidNickNotice

    // 닉네임 중복확인 버튼 노출 여부
    private val _isValidNickBtn = MutableLiveData<Boolean>(true)
    val isValidNickBtn : LiveData<Boolean>
        get() = _isValidNickBtn

    // 닉네임 중복확인
    private val _isValidNick = MutableLiveData<Boolean>(false)
    val isValidNick : LiveData<Boolean>
        get() = _isValidNick

    // 닉네임 입력란 노출
    private val _nickForm = MutableLiveData<Boolean>(false)
    val nickForm : LiveData<Boolean>
        get() = _nickForm

    // 다음 버튼 노출 - 이메일
    private val _emailNextBtn = MutableLiveData<Boolean>(false)
    val emailNextBtn : LiveData<Boolean>
        get() = _emailNextBtn

    // 비밀번호 형식 검사 - 하단 안내문
    private val _isValidPasswordNotice = MutableLiveData<Boolean>(false)
    val isValidPasswordNotice : LiveData<Boolean>
        get() = _isValidPasswordNotice

    // 비밀번호 형식 검사 - 우측 이미지
    private val _isValidPassword = MutableLiveData<Boolean>(false)
    val isValidPassword : LiveData<Boolean>
        get() = _isValidPassword

    // 비밀번호 일치 검사 - 하단 안내문
    private val _isValidAgainNotice = MutableLiveData<Boolean>(false)
    val isValidAgainNotice : LiveData<Boolean>
        get() = _isValidAgainNotice

    // 비밀번호 일치 검사 - 우측 이미지
    private val _isValidAgain = MutableLiveData<Boolean>(false)
    val isValidAgain : LiveData<Boolean>
        get() = _isValidAgain

    // 비밀번호 확인란 노출
    private val _againPasswordForm = MutableLiveData<Boolean>(false)
    val againPasswordForm : LiveData<Boolean>
        get() = _againPasswordForm

    // 다음 버튼 노출 - 비밀번호
    private val _passwordNextBtn = MutableLiveData<Boolean>(false)
    val passwordNextBtn : LiveData<Boolean>
        get() = _passwordNextBtn

    // 성별 선택 여부 - 남자
    private val _isCheckMan = MutableLiveData<Boolean>(false)
    val isCheckMan : LiveData<Boolean>
        get() = _isCheckMan

    // 성별 선택 여부 - 여자
    private val _isCheckWoman = MutableLiveData<Boolean>(false)
    val isCheckWoman : LiveData<Boolean>
        get() = _isCheckWoman

    // 다음 버튼 노출 - 성별
    private val _genderNextBtn = MutableLiveData<Boolean>(false)
    val genderNextBtn : LiveData<Boolean>
        get() = _genderNextBtn

    // 이메일 입력 실시간 확인
    fun inputEmail(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkEmailForm() }, 0L)

        resetValidateEmail()
    }

    // 이메일 형식 확인
    private fun checkEmailForm() {
        emailNotice.value = "이메일 형식이 맞지 않습니다."
        _isValidEmailNotice.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt.value.toString()).matches()

        checkEmptyEmail()
    }

    // 이메일 빈칸확인
    private fun checkEmptyEmail(){
        if(emailTxt.value?.length == 0){
            _isValidEmailBtn.value = false
            _isValidEmailNotice.value = false
        }else{
            _isValidEmailBtn.value = true
        }
    }

    private fun resetValidateEmail(){
        if(_isValidEmail.value!!){
            _isValidEmail.value = false
            _isValidEmailBtn.value = true
        }
    }

    // 이메일 중복확인
    fun getValidateEmail(){
        viewModelScope.launch{
            try{
                _isValidEmail.value = signRepository.getValidateEmail(emailTxt.value.toString())

                if(_isValidEmail.value == true){
                    _isValidEmailNotice.value = false
                    _isValidEmailBtn.value = false

                    if(!_nickForm.value!!) _nickForm.value = true
                }
            } catch (e : HttpException){
                emailNotice.value = "이미 사용 중인 이메일입니다."
                _isValidEmail.value = false
                _isValidEmailNotice.value = true
                _isValidEmailBtn.value = true
            }
        }
    }

    // 닉네임 입력 실시간 확인
    fun inputNick(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkNickForm()},0L)

        resetValidateNick()
    }

    // 닉네임 형식 확인
    private fun checkNickForm() {
        nickNotice.value = "특수문자는 사용하실 수 없습니다."
        val nickPattern: Pattern = Pattern.compile("[ㄱ-ㅎ가-힣ㅏ-ㅣa-zA-Z0-9]{0,30}")

        _isValidNickNotice.value = !nickPattern.matcher(nickTxt.value.toString()).matches()

        checkEmptyNick()
    }

    // 닉네임 빈칸확인
    private fun checkEmptyNick(){
        if(nickTxt.value?.length == 0){
            _isValidNickBtn.value = false
            _isValidNickNotice.value = false
        }else{
            _isValidNickBtn.value = true
        }
    }

    private fun resetValidateNick(){
        if(_isValidNick.value!!){
            _isValidNick.value = false
            _isValidNickBtn.value = true
        }
    }

    // 닉네임 중복확인
    fun getValidateNickname(){
        viewModelScope.launch {
            try{
                _isValidNick.value = signRepository.getValidateNickname(nickTxt.value.toString())

                if(_isValidNick.value == true){
                    _isValidNickNotice.value = false
                    _isValidNickBtn.value = false
                }
            }catch (e : HttpException){
                nickNotice.value = "이미 등록된 닉네임입니다."
                _isValidNick.value = false
                _isValidNickNotice.value = true
                _isValidNickBtn.value = true
            }
        }
    }

    // 다음 버튼 노출 여부 검사 - 이메일
    fun checkNextBtn(){
        _emailNextBtn.value = _isValidEmail.value == true && _isValidNick.value == true
    }

    // 비밀번호 입력 실시간 확인
    fun inputPassword(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkPasswordForm() }, 0L)
    }

    // 비밀번호 자리수 확인
    private fun checkPasswordForm() {
        when(passwordTxt.value?.length){
            0 -> _isValidPasswordNotice.value = false
            in 1..3 -> {
                _isValidPasswordNotice.value = true
                _isValidPassword.value = false
            }
            else -> {
                _isValidPasswordNotice.value = false
                _isValidPassword.value = true

                if(!_againPasswordForm.value!!) _againPasswordForm.value = true
            }
        }
        checkAgainForm()
    }

    // 확인 비밀번호 실시간 확인
    fun inputAgainPassword(s: CharSequence?, start: Int, before: Int, count: Int) {
        Handler().postDelayed({ checkAgainForm() }, 0L)
    }

    // 비밀번호 일치 확인
    private fun checkAgainForm(){
        when {
            againPasswordTxt.value.toString() == passwordTxt.value.toString() -> {
                _isValidAgainNotice.value = false
                _isValidAgain.value = true

                _passwordNextBtn.value = true
            }
            againPasswordTxt.value?.length == 0 -> {
                _isValidAgainNotice.value = false
            }
            else -> {
                _isValidAgainNotice.value = true
                _isValidAgain.value = false
            }
        }
        Log.d("명",emailTxt.value.toString()+"dd")
    }

    // 다음 버튼 노출 여부 검사 - 비밀번호
    fun checkPasswordNextBtn(){
        _passwordNextBtn.value = _isValidPassword.value == true && _isValidAgain.value == true
    }

    // 남자 버튼 클릭
    fun onClickManBtn(){
        _isCheckMan.value = true
        _isCheckWoman.value = false

        _genderNextBtn.value = true
    }

    // 여자 버튼 클릭
    fun onClickWomanBtn(){
        _isCheckMan.value = false
        _isCheckWoman.value = true

        _genderNextBtn.value = true
    }

}
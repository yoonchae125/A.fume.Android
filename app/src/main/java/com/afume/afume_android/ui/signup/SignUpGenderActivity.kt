package com.afume.afume_android.ui.signup

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.afume.afume_android.R
import com.afume.afume_android.databinding.ActivitySignUpGenderBinding
import com.afume.afume_android.util.startActivity

class SignUpGenderActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpGenderBinding
    private val signUpViewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_gender)
        binding.lifecycleOwner = this
        binding.viewModel = signUpViewModel

    }

    fun onClickNextBtn(view: View){
        this.startActivity(SignUpAgeActivity::class.java)

        signUpViewModel.addUserInfo("gender")
    }

    fun onClickBackBtn(view: View){
        finish()
    }
}
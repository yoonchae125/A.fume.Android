package com.scentsnote.android.ui.signin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.scentsnote.android.R
import com.scentsnote.android.databinding.ActivitySignHomeBinding
import com.scentsnote.android.ui.signup.SignUpEmailActivity
import com.scentsnote.android.util.startActivity

class SignHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_home)
        binding.lifecycleOwner = this
    }

    fun onClickSignInBtn(view : View){
        this.startActivity(SignInActivity::class.java)
    }

    fun onClickSignUpBtn(view : View){
        this.startActivity(SignUpEmailActivity::class.java)
    }
}
package com.example.lrffirebase.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lrffirebase.R
import com.example.lrffirebase.databinding.ActivitySplashBinding
import com.example.lrffirebase.login.LoginActivity
import com.example.util.Utils
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    val mBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.apply {

            Utils.auth = FirebaseAuth.getInstance()
            val loginEmail = Utils.auth?.currentUser?.email.toString()
            Log.d("SplashScreen11","${loginEmail}")

            if(loginEmail==null){
              //  Intent(Intent(this@SplashActivity,LoginActivity::class.java))
                Intent(Intent(this@SplashActivity,LoginActivity::class.java))

            }
            else{
                Intent(Intent(this@SplashActivity,LoginActivity::class.java))

            }

        }
    }
}
package com.example.lrffirebase.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lrffirebase.databinding.ActivityRegisterBinding
import com.example.lrffirebase.login.LoginActivity
import com.example.util.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class RegisterActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.apply {
            Utils.auth = FirebaseAuth.getInstance()

            tvRegister.setOnClickListener {
                val userInfo = HashMap<String, Any>()
                userInfo["fname"] = etName.text.toString()
                userInfo["email"] = etEmail.text.toString()
                userInfo["password"] = etPassword.text.toString()
                userInfo["address"] = etAddress.text.toString()


                if (etName.text.toString().isNullOrEmpty() && etEmail.text.toString()
                        .isNullOrEmpty() && etPassword.text.toString()
                        .isNullOrEmpty() && etAddress.text.toString().isNullOrEmpty()
                ) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please fill all Field",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Utils.auth?.createUserWithEmailAndPassword(
                        etEmail.text.toString().trim(),
                        etPassword.text.toString().trim()
                    )?.addOnSuccessListener {
                        FirebaseDatabase.getInstance().reference.child("user").child("register")
                            .push().setValue(userInfo)
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

                    }?.addOnFailureListener {
                        Toast.makeText(this@RegisterActivity, "${it.message}", Toast.LENGTH_SHORT)
                            .show()

                    }

                }

            }


        }
    }
}
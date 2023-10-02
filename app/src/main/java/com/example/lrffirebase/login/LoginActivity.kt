package com.example.lrffirebase.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lrffirebase.R
import com.example.lrffirebase.databinding.ActivityLoginBinding
import com.example.lrffirebase.home.HomeActivity
import com.example.lrffirebase.register.RegisterActivity
import com.example.util.Utils
import com.example.util.showToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    val req_Code: Int = 123
    private val mBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.apply {

            Utils.auth = FirebaseAuth.getInstance()
            //    Log.d("DharmendraToken",FirebaseMessaging.getInstance().token.toString())
            // particular device getting token  getting  device send message
            //5 device  send message  getting token every device message send 5 device
            FirebaseMessaging.getInstance().token.addOnCompleteListener {

                if (it.isSuccessful) {
                    Log.d("ResponseDataUserToken", "${it.result.toString()}")
                }
            }



            tvLogin.setOnClickListener {
                if (etEmailPassword.text.isNullOrEmpty() && etPassword.text.isNullOrEmpty()) {
                    Toast.makeText(this@LoginActivity, "Fill The Record", Toast.LENGTH_SHORT).show()
                } else {
                    Utils.auth?.signInWithEmailAndPassword(
                        etEmailPassword.text.toString().trim(),
                        etPassword.text.toString().trim()
                    )?.addOnCompleteListener {

                        if (it.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            Log.d("ResponseData1", "${it.result}")

                        }

                    }?.addOnFailureListener {
                        Toast.makeText(this@LoginActivity, "${it.message}", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("ResponseData2", "${it.message}")

                    }
                }
            }
            ivFaceBook.setOnClickListener {

            }


            tvDonHaveAccountignUp.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            //google sign in option
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

            Utils.mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)
            ivGoogle.setOnClickListener {
                signInGoogle()
            }
        }
    }


    private fun signInGoogle() {
        val signIntent = Utils.mGoogleSignInClient?.signInIntent
        resultLauncher.launch(signIntent)

    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                handleResult(account)
            } else {
                Log.d(
                    "ResponseEmailID2",
                    "${GoogleSignIn.getSignedInAccountFromIntent(result.data)}"
                )

            }
        }

    /*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == req_Code) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                //
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                handleResult(account)

            } else {
                Log.d("ResponseEmailID2", "${GoogleSignIn.getSignedInAccountFromIntent(data)}")

            }
        }*/

    private fun handleResult(account: GoogleSignInAccount?) {
        val credentia = GoogleAuthProvider.getCredential(account?.idToken, null)
        //method Google Sign In
        Utils.auth?.signInWithCredential(credentia)?.addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, HomeActivity::class.java))
                showToast(it.isSuccessful.toString())
            } else {
                Toast.makeText(this, "${it}", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
package com.example.lrffirebase.home

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lrffirebase.R
import com.example.lrffirebase.databinding.ActivityHomeBinding
import com.example.lrffirebase.databinding.ProfileDialogBoxBinding
import com.example.util.Utils
import com.example.util.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val customDialogboxBinding by lazy { ProfileDialogBoxBinding.inflate(layoutInflater) }
    lateinit var useListData: ArrayList<ProfileResponse>
    private var loginEmail = ""
    var imgUri: Uri? = null
    private val allTypeUserList = arrayListOf<Any>()
    private var insertValue: Boolean = false
    private val profileAdapter by lazy {
        ProfileAdapter { position, viewId ->
            if (viewId == R.id.imgEdit) {
                showDialogBox()
                customDialogboxBinding.apply {
                    etName.setText(useListData[position].name)
                    etEmail.setText(useListData[position].email)
                    etAddress.setText(useListData[position].address)
                    insertValue = true

                }
            }
            if (viewId == R.id.imgDelete) {
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Delete Record Particular")
                alert.setMessage("Delete......!")
                alert.setPositiveButton("Yes") { _, _ ->
                    val mPostReference = FirebaseDatabase.getInstance().reference
                        .child("user").child("user_details")
                        .child(allTypeUserList[position].toString())
                    mPostReference.removeValue()

                }

                alert.setNeutralButton("Cancel") { _, _ ->
                    Toast.makeText(
                        applicationContext,
                        "clicked cancel\n operation cancel",
                        Toast.LENGTH_LONG
                    ).show()
                }
                alert.setNegativeButton("No") { _, _ ->
                    Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
                }

                val alertDialog: AlertDialog = alert.create()
                alertDialog.setCancelable(false)
                alertDialog.show()


            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.apply {
            Utils.auth = FirebaseAuth.getInstance()
            loginEmail = Utils.auth?.currentUser?.email.toString()
            Log.d("SplashScreen","${loginEmail}")

            useListData = arrayListOf()
            customDialogboxBinding.ivProfile.setOnClickListener {
                val pickImg =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                changeImage.launch(pickImg)

            }

            displayRecordUser()
            toolbarLayout.tvLogOut.setOnClickListener {
                Utils.mGoogleSignInClient?.signOut()?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(it.isSuccessful.toString())

                        Utils.auth?.signOut()
                        finish()
                    } else {
                        showToast(it.isSuccessful.toString())

                    }
                }
            }
            btnAdd.setOnClickListener {
                insertValue = false
                showDialogBox()
            }

            rvPostProfile.apply {
                layoutManager = LinearLayoutManager(this@HomeActivity)
                adapter = profileAdapter
            }


            toolbarLayout.etSearchItems.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val query = p0.toString()
                    val searchItem = useListData.filter {
                        it.name.startsWith(query, true)
                    }
                    profileAdapter.setData(searchItem ?: arrayListOf())
                }
                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }




    }

    private fun displayRecordUser() {
        FirebaseDatabase.getInstance().reference.child("user").child("user_details")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        useListData.clear()
                        for (listData in snapshot.children) {
                            val listDataUser = listData.getValue(ProfileResponse::class.java)
                            useListData.add(listDataUser!!)
                            allTypeUserList.add(listData.key.toString())
                        }

                        profileAdapter.setData(useListData)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })
    }


    private fun showDialogBox() {
        customAlertDialog()
        customDialogboxBinding.apply {
            tvInsert.setOnClickListener {
                val userInfo = HashMap<String, Any>()
                userInfo["image"] = imgUri.toString()
                userInfo["name"] = etName.text.toString()
                userInfo["email"] = etEmail.text.toString()
                userInfo["address"] = etAddress.text.toString()

                if (!etName.text.toString().isNullOrEmpty() && !etName.text.toString().isNullOrEmpty() && !etEmail.text.toString().isNullOrEmpty() && !etAddress.text.toString().isNullOrEmpty()){
                    if(insertValue){
                        FirebaseDatabase.getInstance().reference.child("user").child("user_details").child("-NfgHKagXv0LTpTsZxne").updateChildren(userInfo).addOnSuccessListener {
                            showToast("update")
                         //   dialog.dismiss()

                        }.addOnFailureListener {
                            showToast("Update Failure")

                        }
                    }else {
                        FirebaseDatabase.getInstance().reference.child("user").child("user_details")
                            .push()
                            .setValue(userInfo).addOnCompleteListener {
                                mBinding.progressBar.visibility = View.VISIBLE
                                if (it.isSuccessful) {
                                    mBinding.progressBar.visibility = View.GONE
                                    Toast.makeText(this@HomeActivity, "Insert Data", Toast.LENGTH_SHORT).show()
                                  //  dialog.dismiss()

                                } else {
                                    Toast.makeText(
                                        this@HomeActivity,
                                        "Insert Data1",
                                        Toast.LENGTH_SHORT
                                    )

                                }
                            }
                    }

                } else{
                       showToast("Please Fill The All Field")


                }

            }
        }
    }

    private var changeImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                imgUri = data?.data
                customDialogboxBinding.ivProfile.setImageURI(data?.data)
            }

        }

    private fun customAlertDialog() {
        val dialog = Dialog(this@HomeActivity)
        dialog.setContentView(customDialogboxBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(650, 1200)
        dialog.show()
        customDialogboxBinding.iclose.setOnClickListener {
            dialog.dismiss()
        }
    }
}

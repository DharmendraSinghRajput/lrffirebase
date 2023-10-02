package com.example.util

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showToast(message:String,length:Int=Toast.LENGTH_LONG)=Toast.makeText(this,message,length).show()


/*
fun alertDialogBox(){
    val dialog = Dialog(this@HomeActivity)
    dialog.setContentView(customDialogboxBinding.root)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window?.setLayout(650, 1200)
    dialog.show()
}
*/

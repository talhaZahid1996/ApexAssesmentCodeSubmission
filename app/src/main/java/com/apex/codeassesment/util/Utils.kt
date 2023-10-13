package com.apex.codeassesment.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.apex.codeassesment.R
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

fun <A : Activity> FragmentActivity.openActivity(
    activity: Class<A>, newAct: Boolean = true, extras: Bundle.() -> Unit = {}
) {
    val intent = Intent(this, activity)
    intent.putExtras(Bundle().apply(extras))
    if (newAct) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    startActivity(intent)
}

fun View.snackBar(message: String) {
    val snackBar = Snackbar.make(this, message, 5000)
    snackBar.setAction("Ok") { snackBar.dismiss() }
    snackBar.show()
}

fun ImageView.loadImage(image: Any?) {

    try {
        Glide.with(this.context).load(image).placeholder(R.drawable.ic_image_placeholder)
            .into(this)
    } catch (e: Exception) {
        Log.e("Utils", "loadImage: ${e.printStackTrace()}")
    }

}
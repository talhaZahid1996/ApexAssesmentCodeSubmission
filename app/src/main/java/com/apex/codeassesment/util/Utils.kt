package com.apex.codeassesment.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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

inline fun Context.toast(message: () -> String) {
    Toast.makeText(this, message(), Toast.LENGTH_LONG).show()
}

inline fun Fragment.toast(message: () -> String) {
    Toast.makeText(this.context, message(), Toast.LENGTH_LONG).show()
}
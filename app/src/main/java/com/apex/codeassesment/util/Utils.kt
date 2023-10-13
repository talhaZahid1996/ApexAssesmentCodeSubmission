package com.apex.codeassesment.util

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.apex.codeassesment.R
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

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

fun ImageView.loadImage(image: Any?) {

    try {
        Glide.with(this.context).load(image).placeholder(R.drawable.ic_image_placeholder)
            .into(this)
    } catch (e: Exception) {
        Log.e("Utils", "loadImage: ${e.printStackTrace()}")
    }

}

fun FragmentActivity.getSinglePermission(
    permission: String, callBack: GeneralListener
) {
    Dexter.withContext(this).withPermission(permission).withListener(object : PermissionListener {
        override fun onPermissionGranted(response: PermissionGrantedResponse) {
            callBack.permissionGranted(true)
        }

        override fun onPermissionDenied(response: PermissionDeniedResponse) {
            callBack.permissionGranted(false)
            if (response.isPermanentlyDenied) {
                showSettingsDialog()
            }
        }

        override fun onPermissionRationaleShouldBeShown(
            permission: PermissionRequest, token: PermissionToken
        ) {
            token.continuePermissionRequest()
        }
    }).check()
}

fun FragmentActivity.showSettingsDialog() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Need Permissions")
    builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
    builder.setPositiveButton("GOTO SETTINGS") { dialog: DialogInterface, _: Int ->
        dialog.cancel()
        openSettings()
    }
    builder.setNegativeButton(
        "Cancel"
    ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
    builder.show()
}

private fun FragmentActivity.openSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}
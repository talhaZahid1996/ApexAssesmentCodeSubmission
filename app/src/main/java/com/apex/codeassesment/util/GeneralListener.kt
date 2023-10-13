package com.apex.codeassesment.util

import android.location.Location

interface GeneralListener {
    fun permissionGranted(result: Boolean) {}
    fun location(location: Location) {}
}
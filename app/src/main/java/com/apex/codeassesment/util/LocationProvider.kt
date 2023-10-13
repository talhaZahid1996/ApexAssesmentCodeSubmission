package com.apex.codeassesment.util

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

class LocationProvider {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun getLocation(context: FragmentActivity, listener: GeneralListener) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        context.getSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION,
            object : GeneralListener {
                override fun permissionGranted(result: Boolean) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        context.toast { "Permission required to get location" }
                        return
                    }
                    fusedLocationClient.getCurrentLocation(
                        com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY,
                        object : CancellationToken() {
                            override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                                CancellationTokenSource().token

                            override fun isCancellationRequested() = false
                        })
                        .addOnSuccessListener { location: Location? ->
                            if (location == null)
                                context.toast { "Cannot get location." }
                            else {
                                listener.location(location)
                            }

                        }
                }
            })

    }

}
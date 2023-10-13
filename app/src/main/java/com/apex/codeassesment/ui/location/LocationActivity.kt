package com.apex.codeassesment.ui.location

import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apex.codeassesment.R
import com.apex.codeassesment.databinding.ActivityLocationBinding
import com.apex.codeassesment.util.GeneralListener
import com.apex.codeassesment.util.LocationProvider


// TODO (Optional Bonus 8 points): Calculate distance between 2 coordinates using phone's location
class LocationActivity : AppCompatActivity() {

    private val locationProvider = LocationProvider()
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationProvider.getLocation(this, object : GeneralListener {
            override fun location(location: Location) {
                latitude = location.latitude
                longitude = location.longitude
                binding.locationPhone.text =
                    getString(R.string.location_phone, latitude.toString(), longitude.toString())
            }
        })

        val latitudeRandomUser = intent.getStringExtra("user-latitude-key")
        val longitudeRandomUser = intent.getStringExtra("user-longitude-key")

        binding.locationRandomUser.text =
            getString(R.string.location_random_user, latitudeRandomUser, longitudeRandomUser)
        binding.locationCalculateButton.setOnClickListener {
            val distanceHelper = DistanceHelper()
            val distance = distanceHelper.distance(
                latitudeRandomUser?.toDouble() ?: 0.0,
                longitudeRandomUser?.toDouble() ?: 0.0,
                latitude,
                longitude,
                'M'
            )
            binding.locationDistance.text = getString(R.string.location_result_miles, distance)
            /*Toast.makeText(
                this,
                "TODO (8): Bonus - Calculate distance between 2 coordinates using phone's location",
                Toast.LENGTH_SHORT
            ).show()*/
        }
    }
}

package com.example.calculatedistance

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.calculatedistance.databinding.ActivityMainBinding
import com.google.android.gms.location.places.ui.PlacePicker
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var startCoordinates: Array<String?>? = null
    private var endCoordinates: Array<String?>? = null

    private var isMapCalledForStartLocation = true

    private val setCoordinates = fun(coordinates: List<String>, isStart: Boolean) = when (isStart) {
        true -> startCoordinates = coordinates.toTypedArray()
        else -> endCoordinates = coordinates.toTypedArray()
    }

    private val hasPermission = fun(context: Context, permissions: Array<String>) =
        permissions.all { perm ->
            ContextCompat.checkSelfPermission(
                context,
                perm
            ) == PackageManager.PERMISSION_GRANTED
        }


    private fun calculateDistance(
        latitudeFirst: String,
        longitudeFirst: String,
        latitudeSecond: String,
        longitudeSecond: String
    ) {
        val flon = Math.toRadians(longitudeFirst.toDouble())
        val slon = Math.toRadians(longitudeSecond.toDouble())
        val flat = Math.toRadians(latitudeFirst.toDouble())
        val slat = Math.toRadians(latitudeSecond.toDouble())

        // Haversine formula
        val dlon: Double = slon - flon
        val dlat: Double = slat - flat
        val a = (sin(dlat / 2).pow(2.0)
                + (cos(flat) * cos(slat)
                * sin(dlon / 2).pow(2.0)))

        val c = 2 * asin(sqrt(a))
        // Radius of earth in kilometers.
        val r = 6371.0

        // calculate the result
        val dist = "${c * r}"
        val result = dist.substring(0, min(dist.indexOf('.') + 3, dist.length)) + "km"
        binding.tvResult.text = result
    }


    private fun outputOfMapToHumanReadableFormat(latlng: String): List<String> {
        val x = latlng.substring(10, latlng.length - 1).split(",")
        return listOf(
            x[0].substring(0, min(x[0].indexOf('.') + 6, x[0].length)),
            x[1].substring(0, min(x[1].indexOf('.') + 6, x[1].length))
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val place = PlacePicker.getPlace(this, data!!)
            val location = outputOfMapToHumanReadableFormat(place.latLng.toString())
            val editText =
                if (isMapCalledForStartLocation) binding.etStartLocation else binding.etEndLocation

            setCoordinates(location, isMapCalledForStartLocation)
            editText.text = location.joinToString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val launchMap = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            activityResultRegistry
        ) {

            val place = PlacePicker.getPlace(this, it.data!!)

            val location = outputOfMapToHumanReadableFormat(place.latLng.toString())
            val editText =
                if (isMapCalledForStartLocation) binding.etStartLocation else binding.etEndLocation

            setCoordinates(location, isMapCalledForStartLocation)
            editText.text = location.joinToString()
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val btnOnClickListener = fun(isStart: Boolean) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions, 1)
            if (hasPermission(this, permissions)) {
                val builder = PlacePicker.IntentBuilder()
                isMapCalledForStartLocation = isStart
                startActivityForResult(builder.build(this), 1)
//                launchMap.launch(builder.build(this))
            }
        }


        binding.btnSetStartLocation.setOnClickListener {
            btnOnClickListener(true)
        }


        binding.btnSetEndLocation.setOnClickListener {
            btnOnClickListener(false)
        }

        binding.btnCalculateDistance.setOnClickListener {
            if (startCoordinates != null && endCoordinates != null && startCoordinates!!.all { it != null } && endCoordinates!!.all { it != null }) {
                Thread {
                    calculateDistance(
                        startCoordinates!![0]!!,
                        startCoordinates!![1]!!,
                        endCoordinates!![0]!!,
                        endCoordinates!![1]!!
                    )
                }.start()
            } else {
                Toast.makeText(this, "Not Enough Data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
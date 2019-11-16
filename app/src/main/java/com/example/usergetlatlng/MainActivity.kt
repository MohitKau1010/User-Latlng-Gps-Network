package com.example.usergetlatlng

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


     private val REQUEST_LOCATION=1;
    //
    //    Button getlocationBtn;
    //    TextView showLocationTxt;
    //
        private var locationManager:LocationManager?=null
        private var latitude:String?=""
        private var longitude:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Add permission

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION
        )

//        show_location
//        getLocation

        getLocation.setOnClickListener(View.OnClickListener {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            //Check gps is enable or not

            if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Write Function To enable gps

                OnGPS()
            } else {
                //GPS is already On then

                getLocation()
            }
        })
    }


    fun getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MainActivity,

                Manifest.permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val LocationGps = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val LocationNetwork =
                locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val LocationPassive =
                locationManager?.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

            if (LocationGps != null) {
                val lat = LocationGps.latitude
                val longi = LocationGps.longitude

                latitude = lat.toString()
                longitude = longi.toString()

                show_location.setText("Your Location:\nLatitude= $latitude\nLongitude= $longitude")
            } else if (LocationNetwork != null) {
                val lat = LocationNetwork.latitude
                val longi = LocationNetwork.longitude

                latitude = lat.toString()
                longitude = longi.toString()

                show_location.setText("Your Location:\nLatitude= $latitude\nLongitude= $longitude")
            } else if (LocationPassive != null) {
                val lat = LocationPassive.latitude
                val longi = LocationPassive.longitude

                latitude = lat.toString()
                longitude = longi.toString()

                show_location.setText("Your Location:\nLatitude= $latitude\nLongitude= $longitude")
            } else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show()
            }

            //Thats All Run Your App
        }

    }

    private fun OnGPS() {

        val builder = AlertDialog.Builder(this)

        builder.setMessage("Enable GPS")
            .setCancelable(false)
            .setPositiveButton("YES") { dialog, which ->
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton("NO") { dialog, which ->
                    dialog.cancel() }

        val alertDialog = builder.create()

        alertDialog.show()
    }
}

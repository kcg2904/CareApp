package com.KingsStory.Application

import android.app.Activity
import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.KingsStory.Application.PopUp.PopUp
import com.KingsStory.Application.databinding.ActivityFindCaregiverBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray

class FindCaregiverActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityFindCaregiverBinding
    private lateinit var mMap: GoogleMap
    private lateinit var seachtext:String
    private val recyclerAdapter = UCaregiverListAdapter()
    var dialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = ActivityFindCaregiverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        GoogleMapView().CreateMapView(binding.mapView2, savedInstanceState)

    }

    override fun onBackPressed() {
        BackPresseClose(this)
    }

    var toast : Toast? = null
    var backKeyPressedTime:Long = 0

    fun BackPresseClose(activity:Activity){
        if (System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis()
            showToast(activity)
            return
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            toast?.cancel()
            ActivityCompat.finishAffinity(this)
            System.exit(0)
        }
    }
    fun showToast(activity: Activity){
        toast = Toast.makeText(activity,"\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT)
        toast!!.show()
    }

    private fun init() {
        menubar().menu(this)
        GoogleMapView().MapAsync(binding.mapView2, this)
        DataPutExtra().DataReset(this,"seach")
        seachtext = DataGetExtra().StringExtra(this,"seach")
        DataPutExtra().EditTextputExtra(this, this, binding.searchBtn, binding.searchText, "seach")
        binding.recyclerView2.adapter = recyclerAdapter
        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        binding.caregiverMenu.CaregiverBar.setTextColor(Color.BLACK)
        binding.footer.TermsOfService.setOnClickListener {
            PopUp().TermsofService(this)
        }
        binding.footer.privacytext.setOnClickListener {
            PopUp().TermsofService(this)
        }
        binding.footer.disclaimer.setOnClickListener {
            PopUp().TermsofService(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMaxZoomPreference(18f)
        mMap.setMinZoomPreference(10f)
        mMap.setPadding(0, 0, 0, 250)
        val seoul = LatLng(37.5642135, 127.0016985)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul))
        if (seachtext != null && seachtext != "null" && seachtext != "") {
            Geocoder().StringGeocoder(seachtext, this, mMap)
            if (seachtext != null && seachtext != "null" && seachtext != "") {
                dialog = ProgressDialog.show(this, "", "데이터를 불러오는 중 입니다...", true)
            } else {
                dialog?.dismiss()
            }
            GoogleMapView().getData(this, seachtext, mMap, recyclerAdapter, dialog)
            DataPutExtra().DataReset(this, "seach")
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView2.onStart()
    }

    //
    override fun onResume() {
        super.onResume()
        binding.mapView2.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView2.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView2.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        DataPutExtra().DataReset(this, "seach")
        binding.mapView2.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView2.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView2.onLowMemory()
    }

}
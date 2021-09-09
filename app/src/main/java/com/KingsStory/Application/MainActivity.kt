package com.KingsStory.Application


import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.KingsStory.Application.PopUp.PopUp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var search_btn: ImageView
    private lateinit var search_edit: EditText
    private lateinit var seachtext: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var Terms_of_service: TextView
    private lateinit var privacy:TextView
    private lateinit var disclaimer:TextView
    private val recyclerAdapter = UCareListAdapter()

    private lateinit var home: TextView
    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //세로모드 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_main)
        init()
        GoogleMapView().CreateMapView(mapView, savedInstanceState)

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
        initView(this)
        menubar().menu(this)
        GoogleMapView().MapAsync(mapView, this)
        DataPutExtra().DataReset(this, "seach")
        seachtext = DataGetExtra().StringExtra(this, "seach")
        DataPutExtra().EditTextputExtra(this, this, search_btn, search_edit, "seach")
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        home.setTextColor(Color.BLACK)
        Terms_of_service.setOnClickListener {
            PopUp().TermsofService(this)
        }
        privacy.setOnClickListener {
            PopUp().TermsofService(this)
        }
        disclaimer.setOnClickListener {
            PopUp().TermsofService(this)
        }

    }

    private fun initView(activity: Activity) {
        //mauebarOnClick(activity)
        mapView = activity.findViewById(R.id.mapView1)
        search_btn = activity.findViewById(R.id.search_btn)
        search_edit = activity.findViewById(R.id.search_text)
        recyclerView = activity.findViewById(R.id.recyclerView1)
        Terms_of_service = activity.findViewById(R.id.Terms_of_service)
        home = activity.findViewById(R.id.home_menu)
        privacy = activity.findViewById(R.id.privacytext)
        disclaimer = activity.findViewById(R.id.disclaimer)
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
            if(seachtext != null && seachtext != "null" && seachtext != "") {
                dialog = ProgressDialog.show(this, "", "데이터를 불러오는 중 입니다...", true)
            }else{
                dialog?.dismiss()
            }
            GoogleMapView().getData(this, seachtext, mMap, recyclerAdapter, seachtext, dialog)
            DataPutExtra().DataReset(this, "seach")



        }

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    //
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        DataPutExtra().DataReset(this, "seach")
        mapView.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


}



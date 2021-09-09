package com.KingsStory.Application

import android.app.Activity
import android.app.ProgressDialog
import android.location.Address
import android.location.Geocoder
import android.util.Log

import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class Geocoder{
    //입력 받은 주소값으로 위도 경도 값으로 변경해주고 이동시켜주는 함수
    fun StringGeocoder(address :String, activity: Activity, mMap:GoogleMap, zoom:Float = 15f) {
        val geocoder: Geocoder = Geocoder(activity)
        val list: List<Address>

        val str: String = address

        list = geocoder.getFromLocationName(str, 10)
        if (list != null) {
            if (list.size == 0) {
                Toast.makeText(activity, "주소를 찾을수 없습니다", Toast.LENGTH_SHORT).show()

            } else {
                // 해당되는 주소로 인텐트 날리기
                val addr: Address = list.get(0);
                val lat: Double = addr.getLatitude()
                val lon: Double = addr.getLongitude()
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat,lon),zoom))

            }
        }
    }
    fun ReturnData(address :String,activity: Activity): LatLng? {
        val lat :Double
        val lon : Double
        val geocoder: Geocoder = Geocoder(activity)
        val list: List<Address>

        val str: String = address
        list = geocoder.getFromLocationName(str, 10)
        if (list != null) {
            if (list.size == 0) {
                Toast.makeText(activity, "주소를 찾을수 없습니다", Toast.LENGTH_SHORT).show()
                return null
            } else {
                // 해당되는 주소로 인텐트 날리기
                val addr: Address = list.get(0);
                lat = addr.getLatitude()
                lon = addr.getLongitude()
                return LatLng(lat,lon)
            }
        }
        return null
    }
}
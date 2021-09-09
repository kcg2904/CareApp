package com.KingsStory.Application

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast


import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoogleMapView {
    //구글맵 생성 함수
    fun CreateMapView(mapView: MapView, State: Bundle?) {
        mapView.onCreate(State)
    }

    //구글맵 콜백 등록
    fun MapAsync(mapView: MapView, Callback: OnMapReadyCallback) {
        mapView.getMapAsync(Callback)
    }

    //구글맵에 마커 그리기 datalist의 List 타입은 따로 정의 해줘야함
    fun updateMarker(datalist: List<UcareModel>, GoogleMap: GoogleMap, activity: Activity) {

        datalist.forEach { datalist ->
            var test: LatLng? = LatLng(datalist.lat.toDouble(), datalist.lng.toDouble())
            if (test != null) {
                val markeradd = GoogleMap.addMarker(
                    MarkerOptions()
                        .position(test)
                )
                markeradd.tag = datalist.no
            }
        }
    }

    fun updateMarker2(datalist: List<CaregiverDto>, GoogleMap: GoogleMap, activity: Activity) {

        datalist.forEach { datalist ->
            var test: LatLng? = LatLng(datalist.lat.toDouble(), datalist.lng.toDouble())
            if (test != null) {
                val markeradd = GoogleMap.addMarker(
                    MarkerOptions()
                        .position(test)
                )
                markeradd.tag = datalist.no

            }
        }
    }

    // 데이터를 받아와서 뿌려주는 함수
    // 받아와야하는 데이터에 따라 리스트의 타입을 변경해줘야함
    fun getData(
        activity: Activity,
        data: String,
        mMap: GoogleMap,
        recyclerAdapter: UCareListAdapter,
        Text: String,
        dialog: ProgressDialog?
    ) {
        (activity.application as GlobalApplication).service3.getUcaregiverList("select", Text)
            .enqueue(object : Callback<UcareDto> {
                override fun onResponse(
                    call: Call<UcareDto>,
                    response: Response<UcareDto>
                ) {
                    if (!response.isSuccessful) {

                        Log.e("Retrofit", "연결실패1")
                        dialog?.dismiss()
                        return
                    }

                    response.body()?.let { dto ->
                        dto.items.forEach { datas ->
                            Log.d("testt", "${dto.items}")
                            if (data != null && data != "null") {
                                if (datas.permission == "1") {
                                    updateMarker(dto.items, mMap, activity)
                                    recyclerAdapter.submitList(dto.items)
                                    (activity.application as GlobalApplication).test()
                                }
                            }
                        }
                        dialog?.dismiss()

                    }
                }

                override fun onFailure(call: Call<UcareDto>, t: Throwable) {
                    Toast.makeText(activity, "해당지역에 등록된 일자리가 없습니다", Toast.LENGTH_SHORT).show()
                    Log.e("Retrofit", "연결실패22")
                    dialog?.dismiss()
                }
            })

    }

    fun getData(
        activity: Activity,
        data: String,
        mMap: GoogleMap,
        recyclerAdapter: UCaregiverListAdapter,
        dialog: ProgressDialog?
    ) {
        (activity.application as GlobalApplication).service3.getUcareList("find", data)
            .enqueue(object : Callback<UcaregiverDao> {
                override fun onResponse(
                    call: Call<UcaregiverDao>,
                    response: Response<UcaregiverDao>
                ) {
                    if (!response.isSuccessful) {
                        Log.e("Retrofit", "연결실패")
                        return
                    }
                    Log.e("testt", response.body().toString())
                    response.body()?.let { dto ->
                        //TODO: db에서 검색 부분이 조건으로 들어가서 해당하는 데이터만 가져와야함 이게 안된다면 전체 데이터에서 필요한 값만 가져오게 해야함
                        dto.items.forEach { datas ->
                            if (data != null && data != "null") {
                                updateMarker2(dto.items, mMap, activity)
                                recyclerAdapter.submitList(dto.items)
                                (activity.application as GlobalApplication).test()
                            }
                        }
                        dialog?.dismiss()
                    }

                }

                override fun onFailure(call: Call<UcaregiverDao>, t: Throwable) {
                    Toast.makeText(activity, "해당지역에 등록된 요양사가 없습니다", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }
            })
    }
}
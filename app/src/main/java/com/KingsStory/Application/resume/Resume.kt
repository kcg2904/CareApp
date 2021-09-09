package com.KingsStory.Application

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Resume {
    fun Resume(
        type: String,
        title: String,
        name: String,
        phone: String,
        email: String,
        address: String,
        objectivearea: String,
        parttime: String,
        career: String?,
        certificate: String,
        specialty: String,
        img: String,
        birthyear: String,
        suggestion: String,
        emailopen: String,
        addressopen: String,
        resumeopen: String,
        id: String,
        lat: String,
        lng:String,
        activity: Activity
    ) {
        (activity.application as GlobalApplication).service3.resumedata(
            type, title, name, phone, email, address, objectivearea, parttime, career, certificate,
            specialty, img, birthyear, suggestion, emailopen, addressopen, resumeopen, id,lat,lng
        ).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (!response.isSuccessful) {
                    return
                }
                response.body()?.let {
                    if(it == "ok"){
                        Toast.makeText(activity,"정상적으로 저장 되었습니다.", Toast.LENGTH_SHORT).show()
                        activity.startActivity(Intent(activity, UserinfoActivity::class.java))
                        activity.finish()
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("testt", "erorr : $t")
                Log.e("testt", "erorr : type: $type , title: $title, name:$name, phone:$phone, email:$email, " +
                        "address:$address, objectivearea:$objectivearea, parttime:$parttime, career:$career, certificate: $certificate, " +
                        "specialty:$specialty, img:$img, study:$birthyear, suggestion:$suggestion, emailopen:$emailopen," +
                        " addressopen:$addressopen, resumeopen:$resumeopen ,id:$id,lat:$lat ,lng:$lng")
                Toast.makeText(activity, "접속실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun ResumeSelect(
        type: String,
        id: String,
        activity: Activity
    ) {
        (activity.application as GlobalApplication).service3.resumeselect(
            type, id
        ).enqueue(object : Callback<resumeDto> {
            override fun onResponse(call: Call<resumeDto>, response: Response<resumeDto>) {
                if (!response.isSuccessful) {
                    return
                }
                response.body().let { dto ->
                    if (dto != null) {
                        Log.d("testt","들어옴")

                        activity.startActivity(Intent(activity,activity::class.java))
                        activity.finish()
                    }
                }
            }

            override fun onFailure(call: Call<resumeDto>, t: Throwable) {
                Log.d("testt", "$t")
            }
        })
    }

    fun ResumeData(
        type: String,
        id: String,
        activity: Activity,
        title: View,
        name: View,
        phone: View,
        email: View,
        address: View,
        objectivearea: View,
        parttime1: View,
        parttime2: View,
        parttime3: View,
        parttime4: View,
        parttime5: View,
        parttime6: View,
        parttime7: View,
        carerr: View,
        certificate1: View,
        certificate2: View,
        specialty: View,
        img: View,
        birthyear: View,
        suggestion: View,
        emailopen_true: View,
        emailopen_false: View,
        addressopen_true: View,
        addressopen_false: View,
        resumeopen_true: View,
        resumeopen_false: View
    ) {
        (activity.application as GlobalApplication).service3.resumeselect(
            type, id
        ).enqueue(object : Callback<resumeDto> {
            override fun onResponse(call: Call<resumeDto>, response: Response<resumeDto>) {
                if (!response.isSuccessful) {
                    return
                }
                response.body().let { dto ->
                    Log.d("testt", "$dto")
                    if (dto != null) {
                        (title as EditText).setText(dto.title)
                        (name as EditText).setText(dto.name)
                        (phone as EditText).setText(dto.phone)
                        (email as EditText).setText(dto.email)
                        (address as TextView).setText(dto.address)
                        (objectivearea as EditText).setText(dto.objectivearea)
                        (parttime1 as RadioButton).isChecked = dto.pattime.contains("오전")
                        (parttime2 as RadioButton).isChecked = dto.pattime.contains("오후")
                        (parttime3 as RadioButton).isChecked = dto.pattime.contains("야간")
                        (parttime4 as RadioButton).isChecked = dto.pattime.contains("5시간이상")
                        (parttime5 as RadioButton).isChecked = dto.pattime.contains("입주요양")
                        (parttime6 as RadioButton).isChecked = dto.pattime.contains("입주대근")
                        (parttime7 as RadioButton).isChecked = dto.pattime.contains("(요양)병원")
                        (carerr as EditText).setText(dto.career)
                        if (dto.certificate != null) {
                            (certificate1 as RadioButton).isChecked =
                                dto.certificate.contains("요양보호사 1급")
                            (certificate2 as RadioButton).isChecked =
                                dto.certificate.contains("운전면허")
                        }
                        (specialty as EditText).setText(dto.specialty)
                        if (dto.img != null) {
                            Glide.with(activity).load(dto.img).into(img as ImageView)
                            ResumActivity().img = dto.img
                        }
                        (birthyear as EditText).setText(dto.birthyear)
                        (suggestion as EditText).setText(dto.suggestion)
                        if (dto.emailopen == "1") {
                            (emailopen_true as RadioButton).isChecked = true
                        } else {
                            (emailopen_false as RadioButton).isChecked = true
                        }

                        if (dto.addressopen == "1") {
                            (addressopen_true as RadioButton).isChecked = true
                        } else {
                            (addressopen_false as RadioButton).isChecked = true
                        }

                        if (dto.resumeopen == "1") {
                            (resumeopen_true as RadioButton).isChecked = true
                        } else {
                            (resumeopen_false as RadioButton).isChecked = true
                        }
                    }
                }
            }

            override fun onFailure(call: Call<resumeDto>, t: Throwable) {
                Log.d("testt", "$t")
            }
        })
    }
}
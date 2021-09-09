package com.KingsStory.Application

import android.app.Activity
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp {
    fun PersonalSignUp(
        id: String,
        pass: String,
        objectivearea: String,
        name: String,
        gender: String,
        date: String,
        phone: String,
        email: String,
        activity:Activity
        ) {
        (activity.application as GlobalApplication).service3.insert(
            id,pass,objectivearea,name,gender,date,phone,email
        ).enqueue(object : Callback<text>{
            override fun onResponse(call: Call<text>, response: Response<text>) {
                if(!response.isSuccessful){
                    return
                }
                response.body()?.let {
                    if(it.response == "ok") {
                        Toast.makeText(activity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(activity, "중복된아이디입니다", Toast.LENGTH_SHORT).show()
                    }
                }  
            }

            override fun onFailure(call: Call<text>, t: Throwable) {
                Log.d("testt","연결실패")
                Log.d("testt","$t")
            }
        })
    }

    fun CompanySignUp(
        id:String,
        pass: String,
        businessnumber:String,
        companyname:String,
        ceo:String,
        business:String,
        zipcode:String,
        address:String,
        name:String,
        date :String,
        phone : String,
        email:String,
        sms:Boolean,
        activity:Activity
    ){
        (activity.application as GlobalApplication).service3.insertcompany(
            id,pass,businessnumber,companyname,ceo, business, zipcode, address, name, date, phone, email, sms
        ).enqueue(object : Callback<text>{
            override fun onResponse(call: Call<text>, response: Response<text>) {
                if(!response.isSuccessful){
                    return
                }
                response.body()?.let {
                    Log.d("testt","${it.response}")
                    if(it.response != "중복된 아이디") {
                        Toast.makeText(activity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(activity, "중복된 아이디입니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<text>, t: Throwable) {
                Log.d("testt","연결실패")
                Log.d("testt","$t")
            }
        })


    }
}
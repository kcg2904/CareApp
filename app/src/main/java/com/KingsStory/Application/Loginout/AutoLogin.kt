package com.KingsStory.Application

import android.app.Activity
import android.content.Context

class AutoLogin {
    //자동로그인을 위해 사용자기기에 데이터를 저장하는 함수
    fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp", ""+token)
        editor.commit()
    }
    fun saveUserid(id: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp_id", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp_id", ""+id)
        editor.commit()
    }
}
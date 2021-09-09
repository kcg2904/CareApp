package com.KingsStory.Application

import android.app.Activity

class DataGetExtra {
    // key라는 태그를 달고 전송한 데이터 값을 string으로 받아오는 함수
    // 받으려는 activity와 key값을 입력받아야함
    fun StringExtra(activity:Activity,key:String):String{
        return activity.intent.getStringExtra(key).toString()
    }
}
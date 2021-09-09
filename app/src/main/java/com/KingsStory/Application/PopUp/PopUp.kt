package com.KingsStory.Application.PopUp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.ScrollView
import android.widget.TextView
import com.KingsStory.Application.R
import java.util.zip.Inflater

class PopUp {
    fun TermsofService(activity: Activity){
        val inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.terms_of_service, null)
        val ScrollView:ScrollView = view.findViewById(R.id.TermsView)

        val alertDialog = AlertDialog.Builder(activity)
            .setTitle("이용약관")
            .setPositiveButton("확인"){dialog, which ->
                Log.d("testt","팝업확인누름")
            }
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }
    fun PrivacyPolicy(activity: Activity){
        val inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.privacy_policy, null)
        val ScrollView:ScrollView = view.findViewById(R.id.PrivacyView)

        val alertDialog = AlertDialog.Builder(activity)
            .setTitle("개인정보처리방침")
            .setPositiveButton("확인"){dialog, which ->
                Log.d("testt","팝업확인누름")
            }
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }
    fun Sms(activity: Activity){
        val inflater: LayoutInflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.sms, null)
        val ScrollView:ScrollView = view.findViewById(R.id.SmsView)

        val alertDialog = AlertDialog.Builder(activity)
            .setTitle("정보수신동의 SMS/MMS")
            .setPositiveButton("확인"){dialog, which ->
                Log.d("testt","팝업확인누름")
            }
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }
}
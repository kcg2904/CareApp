package com.KingsStory.Application

import android.app.Activity
import android.content.Intent
import android.widget.*
import org.mozilla.javascript.tools.jsc.Main

class menubar {
    fun menu(activity: Activity) {
        val home: TextView = activity.findViewById(R.id.home_menu)
        val mypage: TextView = activity.findViewById(R.id.Mypage_bar)
        val caregiver: TextView = activity.findViewById(R.id.Caregiver_bar)

        if (activity::class.java.name != MainActivity::class.java.name) {
            home.setOnClickListener {
                activity.startActivity(Intent(activity, MainActivity::class.java))
                activity.finish()
            }
        }
        if (activity::class.java.name != FindCaregiverActivity::class.java.name) {
            caregiver.setOnClickListener {
                activity.startActivity(Intent(activity, FindCaregiverActivity::class.java))
                activity.finish()
            }
        }
        mypage.setOnClickListener {
            activity.startActivity(Intent(activity, UserinfoActivity::class.java))
        }

    }


    fun toolbar(activity: Activity) {
        val backbtn: ImageView = activity.findViewById(R.id.backbtn)
        val homebtn: ImageView = activity.findViewById(R.id.homebtn)

        backbtn.setOnClickListener {
            activity.finish()
        }
        homebtn.setOnClickListener {
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.finish()
        }
    }
}
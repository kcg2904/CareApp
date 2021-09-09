package com.KingsStory.Application

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class LogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_logo)

        Handler().postDelayed({ startActivity(Intent(this, MainActivity::class.java))
                                this.finish()
                              }, 5000)
//
    }
}
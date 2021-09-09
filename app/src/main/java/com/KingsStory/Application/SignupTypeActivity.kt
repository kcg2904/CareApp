package com.KingsStory.Application

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class SignupTypeActivity : AppCompatActivity() {

    private lateinit var personal : TextView
    private lateinit var company : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        setContentView(R.layout.activity_signup_type)
        initView(this)
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
//            super.onBackPressed()
            toast?.cancel()
            ActivityCompat.finishAffinity(this)
            System.exit(0)
        }
    }
    fun showToast(activity: Activity){
        toast = Toast.makeText(activity,"\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT)
        toast!!.show()
    }

    private fun initView(activity : Activity){
        menubar().toolbar(activity)
        personal = activity.findViewById(R.id.personal_signup)
        company = activity.findViewById(R.id.company_signup)

        personal.setOnClickListener {
            activity.startActivity(Intent(activity,SignUpActivity::class.java))
            activity.finish()
        }
        company.setOnClickListener {
            activity.startActivity(Intent(activity,CompanySignupActivity::class.java))
            activity.finish()
        }

    }
}
package com.KingsStory.Application

import android.app.Activity

import android.content.Intent
import android.content.pm.ActivityInfo

import android.os.Bundle

import android.util.Log
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.tasks.Task

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var login_btn: Button
    private lateinit var useridText: EditText
    private lateinit var userpwText: EditText
    private lateinit var personallogin : RadioButton
    private lateinit var companylogin : RadioButton


    //    lateinit var kakaologin_btn: ImageButton
//    lateinit var googlelogin_btn : SignInButton
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_login)
        initView(this@LoginActivity)

//        kakaologin_btn.setOnClickListener {
//            KakaoLogin().Login(this)
//        }
//        googlelogin_btn.setOnClickListener {
//            GoogleLogin().Login(gso,mGoogleSignInClient,this)
//        }

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
            toast?.cancel()
            ActivityCompat.finishAffinity(this)
            System.exit(0)
        }
    }
    fun showToast(activity: Activity){
        toast = Toast.makeText(activity,"\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT)
        toast!!.show()
    }


    private fun initView(activity: Activity) {
//        kakaologin_btn = activity.findViewById(R.id.kakao_login_button)
        login_btn = activity.findViewById(R.id.login_button)
        useridText = activity.findViewById(R.id.user_id)
        userpwText = activity.findViewById(R.id.user_pw)
//        googlelogin_btn = activity.findViewById(R.id.google_login_button)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        personallogin = activity.findViewById(R.id.personal_login)
        companylogin = activity.findViewById(R.id.company_login)
        login_btn.setOnClickListener {
            LoginOnClickListener(useridText,userpwText)
        }
        menubar().toolbar(this)
        loginmenu(this)
    }
    //로그인 버튼 클릭시 이벤트 함수
    fun LoginOnClickListener(id:EditText,pass:EditText){
        val mid : String = id.text.toString()
        val mpw : String = pass.text.toString()
        //개인회원 로그인일 경우
        if(personallogin.isChecked){
            Login().PersonalLogin(mid,mpw, this)
        }
        //기업회원로그인일경우
        else if (companylogin.isChecked){
            Login().CompanyLogin(mid,mpw,this)
        }else{
            Toast.makeText(this,"로그인타입을 선택해주세요",Toast.LENGTH_SHORT).show()
        }
    }
    fun loginmenu(activity: Activity){

//        val findid : TextView = activity.findViewById(R.id.find_id)
//        val findpass : TextView = activity.findViewById(R.id.find_pass)
        val signup : TextView = activity.findViewById(R.id.signup)


//        findid.setOnClickListener {
//            //TODO 아이디찾기 액티비티 만들어야함 일해라!
//        }
//
//        findpass.setOnClickListener {
//            //TODO 비밀번호 찾기 액티비티 만들어야함 일해라!
//        }
        signup.setOnClickListener {
            activity.startActivity(Intent(activity,SignupTypeActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 200) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            GoogleLogin().handleSignInResult(task, this)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}

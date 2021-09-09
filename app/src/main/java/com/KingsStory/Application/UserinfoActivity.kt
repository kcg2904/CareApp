package com.KingsStory.Application

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.KingsStory.Application.databinding.ActivityUserinfoBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserinfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserinfoBinding
    lateinit var shar: SharedPreferences
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var gso: GoogleSignInOptions
    var name: String? = null
    var id: String? = null
    var mid: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = ActivityUserinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        kakaologincheck()
    }

    override fun onBackPressed() {
        BackPresseClose(this)
    }

    var toast: Toast? = null
    var backKeyPressedTime: Long = 0

    fun BackPresseClose(activity: Activity) {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showToast(activity)
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast?.cancel()
            ActivityCompat.finishAffinity(this)
            System.exit(0)
        }
    }

    fun showToast(activity: Activity) {
        toast = Toast.makeText(activity, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT)
        toast!!.show()
    }

    fun logout() {
        if (name!!.contains("google")) {
            googlelogout()
        } else if (name!!.contains("personal") || name!!.contains("company")) {
            //로그인 초기화
            (application as GlobalApplication).test()
        } else if (name!!.contains("kakao")) {
            kakaologout()
        }
        val editor = shar.edit()
        editor.putString("login_sp", "null")
        editor.commit()
        startActivity(Intent(this@UserinfoActivity, this@UserinfoActivity::class.java))
        this.finish()
    }

    private fun googlelogout() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                Toast.makeText(this@UserinfoActivity, "구글 로그아웃 성공", Toast.LENGTH_LONG).show()
            })
    }

    fun kakaologout() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (tokenInfo != null) {
                UserApiClient.instance.logout { error ->
                    if (error != null) {
                        Toast.makeText(this@UserinfoActivity, "로그아웃 실패", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@UserinfoActivity, "로그아웃 성공", Toast.LENGTH_LONG).show()

                    }
                }
            }
        }
    }

    fun cookieupdate(type: String, id: String) {
        (application as GlobalApplication).service.cookieSelect(
            type, id
        ).enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (!response.isSuccessful) {
                    Log.e("testt", "에러")
                    return
                }
                response.body()?.let {
                    binding.cookieCount.text = it.toString()
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("testt", "error : $t")
            }
        })
    }

    fun kakaologincheck() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                //Toast.makeText(this, "토큰 정보보기 실패", Toast.LENGTH_LONG).show()
                if (name != null && name != "null") {
                    if (!name!!.contains("google")) {
                        binding.helloUser.text = "${name}"
                        binding.loginoutButton.text = "로그아웃"
                    } else {
                        val account = GoogleSignIn.getLastSignedInAccount(this)
                        binding.helloUser.text = "${account?.displayName}"
                        binding.loginoutButton.text = "로그아웃"
                    }
                }
            } else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_LONG).show()
                UserApiClient.instance.me { user, error ->
                    binding.helloUser.text = "${user?.kakaoAccount?.profile?.nickname}"
                    binding.loginoutButton.text = "로그아웃"
                }
            }
        }
    }

    fun init() {
        menubar().toolbar(this)
        shar = getSharedPreferences("login_sp", MODE_PRIVATE)
        name = shar.getString("login_sp", "null")
        id = name

        if (name != "null" && name != null) {
            name = name!!.substring((name!!.lastIndexOf("_")) + 1)
        }

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.loginoutButton.setOnClickListener {
            if (name != null && name != "null") {
                logout()
            } else {
                startActivity(Intent(this@UserinfoActivity, LoginActivity::class.java))
                this.finish()
            }
        }


        binding.resume.setOnClickListener {
            if (name != null && name != "null" && id!!.contains("personal")) {
                startActivity(Intent(this, ResumActivity::class.java))
            } else {
                Toast.makeText(this, "요양사 로그인후 이용해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cookieplus.setOnClickListener {
            if (name != null && name != "null") {
                startActivity(Intent(this, CookieActivity::class.java))
            } else {
                Toast.makeText(this, "로그인후 이용해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        binding.job.setOnClickListener {
            if (id!!.contains("company")) {
                startActivity(Intent(this, JobOfferActivity::class.java))
            } else {
                Toast.makeText(this, "구인 로그인후 이용해주세요", Toast.LENGTH_SHORT).show()
            }
        }
        if (id!!.contains("company")) {
            mid =
                getSharedPreferences("login_sp_id", MODE_PRIVATE).getString("login_sp_id", "null")!!
            Log.d("testt", "111: ${mid}")
            cookieupdate("company", mid)
            binding.job.setTextColor(Color.BLACK)
        }
        if (id!!.contains("personal")) {
            mid =
                getSharedPreferences("login_sp_id", MODE_PRIVATE).getString("login_sp_id", "null")!!
            Log.d("testt", "222: ${mid}")
            cookieupdate("personal", mid)
            binding.resume.setTextColor(Color.BLACK)
        }
    }


}


package com.KingsStory.Application

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.KingsStory.Application.databinding.ActivityCookieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CookieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCookieBinding
    private lateinit var shar: SharedPreferences
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = ActivityCookieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }
    override fun onBackPressed() {
        BackPresseClose(this)
    }

    var toast : Toast? = null
    var backKeyPressedTime:Long = 0

    fun BackPresseClose(activity: Activity){
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

    private fun init() {
        menubar().toolbar(this)
        shar = getSharedPreferences("login_sp_id", MODE_PRIVATE)
        token = shar.getString("login_sp_id", "null")
        binding.cookieGroup.setOnCheckedChangeListener(CheckboxListener())
//        binding.cookieCashAmountInput.addTextChangedListener(EditTextChange())
        binding.cookieBtn.setOnClickListener {
            var cookie_name :String = binding.cookieCashAmount.text.toString().substring(0,binding.cookieCashAmount.text.toString().indexOf("원"))
            cookie_name = cookie_name.substring(0,cookie_name.indexOf(",")) + cookie_name.substring(cookie_name.indexOf(",")+1)
            Log.d("testt","$cookie_name")
            val cookie : Int = cookie_name.toInt()
                (application as GlobalApplication).service3.cookie(
                    "insert",token!!,cookie,cookie/1000).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (!response.isSuccessful) {
                            return
                        }
                        response.body()?.let{
                            if (it == "ok"){
                                Toast.makeText(this@CookieActivity,"쿠키충전 요청이 접수되었습니다.",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@CookieActivity,UserinfoActivity::class.java))
                                this@CookieActivity.finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("testt", "erorr : $t")
                    }
                })

            }
        }

    inner class CheckboxListener : RadioGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
            when (group?.id) {
                R.id.cookie_group ->
                    when (checkedId) {
                        R.id.cookie_5000 -> {
                            binding.cookieCashAmount.text = binding.cookie5000.text
//                            binding.cookieCashAmountInput.isEnabled = false
                        }
                        R.id.cookie_10000 -> {
                            binding.cookieCashAmount.text = binding.cookie10000.text
//                            binding.cookieCashAmountInput.isEnabled = false
                        }
                        R.id.cookie_30000 -> {
                            binding.cookieCashAmount.text = binding.cookie30000.text
//                            binding.cookieCashAmountInput.isEnabled = false
                        }
                        R.id.cookie_50000 -> {
                            binding.cookieCashAmount.text = binding.cookie50000.text
//                            binding.cookieCashAmountInput.isEnabled = false
                        }
//                        R.id.cookie_input -> binding.cookieCashAmountInput.isEnabled = true
                    }
            }
        }
    }

    inner class EditTextChange : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //입력이 끝낫을때 조치
//            binding.cookieCashAmount.text = binding.cookieCashAmountInput.text.toString() + "원"
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // 입력난에 변화가 있을때 조치
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //입력하기 전에 조치
        }
    }
}
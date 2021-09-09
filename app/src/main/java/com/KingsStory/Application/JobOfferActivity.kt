package com.KingsStory.Application

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.KingsStory.Application.databinding.ActivityJobOfferBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class JobOfferActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobOfferBinding
    private lateinit var shar: SharedPreferences
    private var id: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = ActivityJobOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        menubar().toolbar(this)
        shar = getSharedPreferences("login_sp_id", MODE_PRIVATE)
        id = shar.getString("login_sp_id", "null")
        Log.d("testt","$id")
        OnClickListener(binding.jobOfferAddressbtn)
        OnClickListener(binding.jobOfferBtn)
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

    private fun OnClickListener(view: View) {
        when (view) {
            binding.jobOfferAddressbtn -> {
                view.setOnClickListener {
                    Log.i("testt", "주소입력 클릭")
                    Intent(this, AddressApiActivity::class.java).apply {
                        startActivityForResult(this, 1000)
                    }
                }
            }
            binding.jobOfferBtn -> {
                view.setOnClickListener {
                    if (binding.jobOfferName.text.toString() == "") {
                        binding.errerJobOfferName.text = "요양대상자 이름을 입력해주세요"
                    } else {
                        binding.errerJobOfferName.text = ""
                    }

                    if (binding.jobOfferArea.text.toString() == "") {
                        binding.errerJobOfferArea.text = "법정동을 입력해주세요"
                    } else {
                        binding.errerJobOfferArea.text = ""
                    }

                    if (binding.jobOfferAddress.text.toString() == "") {
                        binding.errerJobOfferAddress.text = "주소를 입력해주세요"
                    } else {
                        binding.errerJobOfferAddress.text = ""
                    }

                    if (!binding.jobOfferParttime1.isChecked && !binding.jobOfferParttime2.isChecked && !binding.jobOfferParttime3.isChecked && !binding.jobOfferParttime4.isChecked &&
                        !binding.jobOfferParttime5.isChecked && !binding.jobOfferParttime6.isChecked && !binding.jobOfferParttime7.isChecked
                    ) {
                        binding.errerJobOfferParttime.text = "근무시간을 선택해주세요"
                    } else {
                        binding.errerJobOfferParttime.text = ""
                    }

                    if (!binding.jobOfferMale.isChecked && !binding.jobOfferFemale.isChecked) {
                        binding.errerJobOfferGender.text = "요양대상자의 성별을 선택해주세요"
                    } else {
                        binding.errerJobOfferGender.text = ""
                    }

                    if (binding.jobOfferRank.text.toString() == "") {
                        binding.errerJobOfferRank.text = "노인등급을 입력해주세요"
                    } else {
                        binding.errerJobOfferRank.text = ""
                    }

                    if (binding.jobOfferPhone.text.toString() == "") {
                        binding.errerJobOfferPhone.text = "휴대폰번호를 입력해주세요"
                    } else if (binding.jobOfferPhone.text.contains("-")) {
                        binding.errerJobOfferPhone.text = "- 를 빼고 숫자만 입력해주세요"
                    } else {
                        binding.errerJobOfferPhone.text = ""
                    }

                    if (binding.jobOfferCompany.text.toString() == "") {
                        binding.errerJobOfferCompany.text = "기업명을 입력해주세요"
                    } else {
                        binding.errerJobOfferCompany.text = ""
                    }

                    if (binding.jobOfferCeo.text.toString() == "") {
                        binding.errerJobOfferCeo.text = "대표자명을 입력해주세요"
                    } else {
                        binding.errerJobOfferCeo.text = ""
                    }

                    if (binding.jobOfferNum.text.toString() == "") {
                        binding.errerJobOfferNum.text = "등록 하실 일수를 입력해주세요"
                    } else {
                        binding.errerJobOfferNum.text = ""
                    }

                    if (binding.errerJobOfferName.text.toString() == "" && binding.errerJobOfferArea.text.toString() == "" && binding.errerJobOfferAddress.text.toString() == "" &&
                        binding.errerJobOfferParttime.text.toString() == "" && binding.errerJobOfferGender.text.toString() == "" && binding.errerJobOfferRank.text.toString() == "" &&
                        binding.errerJobOfferPhone.text.toString() == "" && binding.errerJobOfferCompany.text.toString() == "" && binding.errerJobOfferCeo.text.toString() == "" &&
                        binding.errerJobOfferNum.text.toString() == ""
                    ) {

                        var mPartime: String = ""
                        if (binding.jobOfferParttime1.isChecked && mPartime == "") {
                            mPartime += binding.jobOfferParttime1.text.toString()
                        } else if (binding.jobOfferParttime1.isChecked) {
                            mPartime += ("/" + binding.jobOfferParttime1.text.toString())
                        }
                        if (binding.jobOfferParttime2.isChecked && mPartime == "") {
                            mPartime += binding.jobOfferParttime2.text.toString()
                        } else if (binding.jobOfferParttime2.isChecked) {
                            mPartime += ("/" + binding.jobOfferParttime2.text.toString())
                        }
                        if (binding.jobOfferParttime3.isChecked && mPartime == "") {
                            mPartime += binding.jobOfferParttime3.text.toString()
                        } else if (binding.jobOfferParttime3.isChecked) {
                            mPartime += ("/" + binding.jobOfferParttime3.text.toString())
                        }
                        if (binding.jobOfferParttime4.isChecked && mPartime == "") {
                            mPartime += binding.jobOfferParttime4.text.toString()
                        } else if (binding.jobOfferParttime4.isChecked) {
                            mPartime += ("/" + binding.jobOfferParttime4.text.toString())
                        }
                        if (binding.jobOfferParttime5.isChecked && mPartime == "") {
                            mPartime += binding.jobOfferParttime5.text.toString()
                        } else if (binding.jobOfferParttime5.isChecked) {
                            mPartime += ("/" + binding.jobOfferParttime5.text.toString())
                        }
                        if (binding.jobOfferParttime6.isChecked && mPartime == "") {
                            mPartime += binding.jobOfferParttime6.text.toString()
                        } else if (binding.jobOfferParttime6.isChecked) {
                            mPartime += ("/" + binding.jobOfferParttime6.text.toString())
                        }
                        if (binding.jobOfferParttime7.isChecked && mPartime == "") {
                            mPartime += binding.jobOfferParttime7.text.toString()
                        } else if (binding.jobOfferParttime7.isChecked) {
                            mPartime += ("/" + binding.jobOfferParttime7.text.toString())
                        }

                        val name: String = binding.jobOfferName.text.toString()
                        val area: String = binding.jobOfferArea.text.toString()
                        val address: String = binding.jobOfferAddress.text.toString()
                        val partime: String = mPartime
                        val gender: String = if (binding.jobOfferMale.isChecked) "남" else "여"
                        val rank: String = binding.jobOfferRank.text.toString()
                        val phone: String = binding.jobOfferPhone.text.toString()
                        val company: String = binding.jobOfferCompany.text.toString()
                        val ceo: String = binding.jobOfferCeo.text.toString()
                        val num: Int = binding.jobOfferNum.text.toString().toInt()

                        val mFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                        val mdate: Calendar = Calendar.getInstance()
                        mdate.add(mdate.time.day, num)
                        val endday: String = mFormat.format(mdate.time)
                        val lat: String =
                            (Geocoder().ReturnData(address, this)!!.latitude).toString()
                        val lng: String =
                            (Geocoder().ReturnData(address, this)!!.longitude).toString()
                        Job_Offer(
                            this,
                            num,
                            name,
                            area,
                            partime,
                            gender,
                            rank,
                            phone,
                            company,
                            ceo,
                            endday,
                            lat,
                            lng,
                            id!!
                        )

                    }
                }
            }

        }
    }

    fun Job_Offer(
        activity: Activity,
        num: Int,
        name: String,
        area: String,
        partime: String,
        gender: String,
        rank: String,
        phone: String,
        company: String,
        ceo: String,
        endday: String,
        lat: String,
        lng: String,
        id:String
    ) {
        val inflater: LayoutInflater =
            activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.jobofferpop, null)
        val TextView: TextView = view.findViewById(R.id.popText)
        TextView.text = "정말로 $num 쿠키를 사용 하여 등록하시겠습니까?"

        val alertDialog = AlertDialog.Builder(activity)
            .setTitle("일자리등록")
            .setPositiveButton("확인") { dialog, which ->
                Log.d("testt", "팝업확인누름")
                (application as GlobalApplication).service3.joboffer(
                    "insert",
                    id,
                    num,
                    lat,
                    lng,
                    name,
                    area,
                    partime,
                    gender,
                    rank,
                    phone,
                    company,
                    ceo,
                    endday
                ).enqueue(object : Callback<String> {
                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {
                        if (!response.isSuccessful) return;

                        response.body()?.let {
                            if (it == "ok") {
                                Toast.makeText(activity, "정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(activity, UserinfoActivity::class.java))
                                activity.finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(activity, "접속에러", Toast.LENGTH_SHORT).show()
                        Log.d("testt","$t,$id")

                    }
                })
            }
            .setNegativeButton("취소", null)
            .create()

        alertDialog.setView(view)
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1000 -> {
                    if (resultCode == RESULT_OK) {
                        val zip = data?.getStringExtra("address").toString()
                            .substring(data?.getStringExtra("address").toString().indexOf(",") + 1)
                        Log.d("testt", "$zip")
                        binding.jobOfferAddress.text = zip
                    }
                }

            }
        }
    }
}
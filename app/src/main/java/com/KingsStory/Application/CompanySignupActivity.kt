package com.KingsStory.Application


import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo


import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.KingsStory.Application.PopUp.PopUp


class CompanySignupActivity : AppCompatActivity() {

    private lateinit var companyid: EditText
    private lateinit var companypass: EditText
    private lateinit var companypass2: EditText
    private lateinit var companynumber: EditText
    private lateinit var companyname: EditText
    private lateinit var companyceoname: EditText
    private lateinit var companybusiness1: RadioButton
    private lateinit var companybusiness2: RadioButton
    private lateinit var companybusiness3: RadioButton
    private lateinit var companybusiness4: RadioButton
    private lateinit var zipcode: TextView
    private lateinit var address: TextView
    private lateinit var address2: EditText
    private lateinit var name: EditText
    private lateinit var date: DatePicker
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var terms: RadioButton
    private lateinit var privacy: RadioButton
    private lateinit var smstrue: RadioButton
    private lateinit var smsfalse: RadioButton
    private lateinit var errorcompanyid: TextView
    private lateinit var errorcompanypass: TextView
    private lateinit var errorcompanypass2: TextView
    private lateinit var errorcompanynumber: TextView
    private lateinit var errorcompanyname: TextView
    private lateinit var errorcompanyceoname: TextView
    private lateinit var errorcompanybusiness: TextView
    private lateinit var errorcompanyzipcode: TextView
    private lateinit var errorcompanyaddress: TextView
    private lateinit var errorcompanyaddress2: TextView
    private lateinit var errorname: TextView
    private lateinit var errordate: TextView
    private lateinit var errorphone: TextView
    private lateinit var erroremail: TextView
    private lateinit var errorterms: TextView
    private lateinit var errorprivacy: TextView
    private lateinit var errorsms: TextView

    private lateinit var terms_pop : ImageView
    private lateinit var privacy_pop : ImageView
    private lateinit var sms_pop:ImageView

    private lateinit var zipcode_btn: Button
    private lateinit var signupbtn: Button

    private var today: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_company_signup)
        initView(this)
        OnClickListener(zipcode_btn)
        OnClickListener(signupbtn)

        init()

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

    fun init() {
        today =
            date.year.toString() + date.month.toString() + date.dayOfMonth.toString()
        terms_pop.setOnClickListener {
            PopUp().TermsofService(this)
        }
        privacy_pop.setOnClickListener {
            PopUp().PrivacyPolicy(this)
        }
        sms_pop.setOnClickListener {
            PopUp().Sms(this)
        }
        menubar().toolbar(this)
    }

    private fun OnClickListener(view: View) {
        when (view) {
            zipcode_btn -> {
                zipcode_btn.setOnClickListener {
                    Log.i("testt", "주소입력 클릭")
                    Intent(this, AddressApiActivity::class.java).apply {
                        startActivityForResult(this, 10000)
                    }
                }
            }

            signupbtn -> {
                signupbtn.setOnClickListener {
                    //아이디
                    if (companyid.text.length < 4 && companyid.text.toString() != "") {
                        errorcompanyid.text = "아이디가 너무 짧습니다 4~15자로 작성해주세요"
                    } else if (companyid.text.toString() == "") {
                        errorcompanyid.text = "아이디를 입력해주세요"
                    } else {
                        errorcompanyid.text = ""
                    }
                    //비밀번호
                    if (companypass.text.length < 6 && companypass.text.toString() != "") {
                        errorcompanypass.text = "비밀번호가 너무 짧습니다 6~16자로 작성해주세요"
                    } else if (companypass2.text.toString() == "") {
                        errorcompanypass.text = "비밀번호를 입력해주세요"
                    } else {
                        errorcompanypass.text = ""
                    }
                    //비밀번호 확인
                    if (companypass.text.toString() != companypass2.text.toString()) {
                        errorcompanypass2.text = "비밀번호가 다릅니다"
                    } else if (companypass2.text.toString() == "") {
                        errorcompanypass2.text = "비밀번호를 입력해주세요"
                    } else {
                        errorcompanypass2.text = ""
                    }
                    //사업자등록번호
//                    if (companynumber.text.toString() == "") {
//                        errorcompanynumber.text = "사업자등록번호를 입력해주세요"
//                    } else {
//                        errorcompanynumber.text = ""
//                    }
                    //회사명
//                    if (companyname.text.toString() == "") {
//                        errorcompanyname.text = "회사명을 입력해주세요"
//                    } else {
//                        errorcompanyname.text = ""
//                    }
                    //대표자명
//                    if (companyceoname.text.toString() == "") {
//                        errorcompanyceoname.text = "대표자명을 입력해주세요"
//                    } else {
//                        errorcompanyceoname.text = ""
//                    }
                    //주요사업
                    if (companybusiness1.isChecked == false && companybusiness2.isChecked == false && companybusiness3.isChecked == false && companybusiness4.isChecked == false) {
                        errorcompanybusiness.text = "주요사업을 선택해주세요"
                    } else {
                        errorcompanybusiness.text = ""
                    }
                    //우편번호
                    if (zipcode.text == "") {
                        errorcompanyzipcode.text = "우편번호를 검색해주세요"
                    } else {
                        errorcompanyzipcode.text = ""
                    }
                    //사업장주소
                    if (address.text == "") {
                        errorcompanyaddress.text = "우편번호를 검색해주세요"
                    } else {
                        errorcompanyaddress.text = ""
                    }
                    //가입자명
                    if (name.text.toString() == "") {
                        errorname.text = "가입자명을 입력해주세요"
                    } else {
                        errorname.text = ""
                    }
                    //생년월일
                    if (today == date.year.toString() + date.month.toString() + date.dayOfMonth.toString()) {
                        errordate.text = "생년월일을 변경해주세요"
                    } else {
                        errordate.text = ""
                    }
                    //휴대폰번호
                    if (phone.text.toString().contains("-")) {
                        errorphone.text = "-를 빼고 입력해주세요"

                    } else if (phone.text.length < 10) {
                        errorphone.text = "잘못된 번호 입니다 다시 입력해주세요"

                    } else {
                        errorphone.text = ""
                    }
                    //이메일
                    if (email.text.toString() == "") {
                        erroremail.text = "이메일을 입력해주세요"
                    } else {
                        erroremail.text = ""
                    }
                    //이용약관
                    if (terms.isChecked == false) {
                        errorterms.text = "이용약관에 동의해주세요"
                    } else {
                        errorterms.text = ""
                    }
                    //개인정보처리방침
                    if (privacy.isChecked == false) {
                        errorprivacy.text = "개인정보처리방침에 동의해주세요"
                    } else {
                        errorprivacy.text = ""
                    }
                    //정보수신동의 SMS/MMS
                    if (smstrue.isChecked == false && smsfalse.isChecked == false) {
                        errorsms.text = "정보수신동의를 선택해주세요"
                    } else {
                        errorsms.text = ""
                    }
                    // 에러체크
                    var isError: Boolean = errorcompanyid.text == "" &&
                            errorcompanypass.text == "" &&
                            errorcompanypass2.text == "" &&
                            errorcompanynumber.text == "" &&
                            errorcompanyname.text == "" &&
                            errorcompanyceoname.text == "" &&
                            errorcompanybusiness.text == "" &&
                            errorcompanyzipcode.text == "" &&
                            errorcompanyaddress.text == "" &&
                            errorcompanyaddress2.text == "" &&
                            errorname.text == "" &&
                            errordate.text == "" &&
                            errorphone.text == "" &&
                            erroremail.text == "" &&
                            errorterms.text == "" &&
                            errorprivacy.text == "" &&
                            errorsms.text == ""
                    //주요사업 추출
                    var mbusiness: String = ""
                    if (companybusiness1.isChecked == true) {
                        if (mbusiness == "") {
                            mbusiness += companybusiness1.text.toString()
                        } else {
                            mbusiness += (companybusiness1.text.toString() + ",")
                        }
                    } else if (companybusiness2.isChecked == true) {
                        if (mbusiness == "") {
                            mbusiness += companybusiness2.text.toString()
                        } else {
                            mbusiness += (companybusiness2.text.toString() + ",")
                        }
                    } else if (companybusiness3.isChecked == true) {
                        if (mbusiness == "") {
                            mbusiness += companybusiness3.text.toString()
                        } else {
                            mbusiness += (companybusiness3.text.toString() + ",")
                        }
                    }

                    // 상세주소와 주소 결합
                    var maddress: String = address.text.toString() + " " + address2.text.toString()

                    //날짜 string 변환
                    var mdate: String =
                        date.year.toString() + "-" + (date.month + 1).toString() + "-" + date.dayOfMonth.toString()

                    //정보수신동의 SMS/MMS 값 저장
                    var msms: Boolean = false

                    if (smstrue.isChecked == true) {
                        msms = true
                    }

                    //데이터 삽입
                    if (isError) {
                        val id: String = companyid.text.toString()
                        val pass: String = companypass.text.toString()
                        val businessnumber: String = companynumber.text.toString()
                        val companyname: String = companyname.text.toString()
                        val ceo: String = companyceoname.text.toString()
                        val business: String = mbusiness
                        val zipcode: String = zipcode.text.toString()
                        val address: String = maddress
                        val name: String = name.text.toString()
                        val date: String = mdate
                        val phone: String = phone.text.toString()
                        val email: String = email.text.toString()
                        val sms: Boolean = msms
                        SignUp().CompanySignUp(
                            id,
                            pass,
                            businessnumber,
                            companyname,
                            ceo,
                            business,
                            zipcode,
                            address,
                            name,
                            date,
                            phone,
                            email,
                            sms,
                            this
                        )
                    }
                }
            }


        }
    }

    fun initView(activity: Activity) {
        companyid = activity.findViewById(R.id.company_signup_id)
        companypass = activity.findViewById(R.id.company_signup_pass1)
        companypass2 = activity.findViewById(R.id.company_signup_pass2)
        companynumber = activity.findViewById(R.id.company_signup_number)
        companyname = activity.findViewById(R.id.company_signup_campanyname)
        companyceoname = activity.findViewById(R.id.company_signup_ceoname)
        companybusiness1 = activity.findViewById(R.id.company_signup_business1)
        companybusiness2 = activity.findViewById(R.id.company_signup_business2)
        companybusiness3 = activity.findViewById(R.id.company_signup_business3)
        companybusiness4 = activity.findViewById(R.id.company_signup_business4)
        zipcode = activity.findViewById(R.id.company_signup_zipcode)
        address = activity.findViewById(R.id.company_signup_address)
        zipcode_btn = activity.findViewById(R.id.company_signup_zipcodebtn)
        address2 = activity.findViewById(R.id.company_signup_address2)
        name = activity.findViewById(R.id.company_signup_name)
        date = activity.findViewById(R.id.company_signup_date)
        phone = activity.findViewById(R.id.company_signup_phone)
        email = activity.findViewById(R.id.company_signup_email)
        terms = activity.findViewById(R.id.company_signup_Termsofservice)
        privacy = activity.findViewById(R.id.company_signup_PrivacyPolicy)
        smstrue = activity.findViewById(R.id.company_signup_sms_true)
        smsfalse = activity.findViewById(R.id.company_signup_sms_false)

        errorcompanyid = activity.findViewById(R.id.errer_id)
        errorcompanypass = activity.findViewById(R.id.errer_pass)
        errorcompanypass2 = activity.findViewById(R.id.errer_pass2)
        errorcompanynumber = activity.findViewById(R.id.errer_number)
        errorcompanyname = activity.findViewById(R.id.errer_campanyname)
        errorcompanyceoname = activity.findViewById(R.id.errer_ceoname)
        errorcompanybusiness = activity.findViewById(R.id.company_signup_business)
        errorcompanyzipcode = activity.findViewById(R.id.errer_zipcode)
        errorcompanyaddress = activity.findViewById(R.id.errer_address)
        errorcompanyaddress2 = activity.findViewById(R.id.errer_address2)
        errorname = activity.findViewById(R.id.errer_name)
        errordate = activity.findViewById(R.id.errer_date)
        errorphone = activity.findViewById(R.id.errer_phone)
        erroremail = activity.findViewById(R.id.errer_email)
        errorterms = activity.findViewById(R.id.errer_Termsofservice)
        errorprivacy = activity.findViewById(R.id.errer_PrivacyPolicy)
        errorsms = activity.findViewById(R.id.errer_sms)

        signupbtn = activity.findViewById(R.id.company_signup_btn)
        terms_pop = activity.findViewById(R.id.Company_termstext)
        privacy_pop = activity.findViewById(R.id.Company_privacytext)
        sms_pop = activity.findViewById(R.id.company_smstext)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10000 -> {
                if (resultCode == RESULT_OK) {
                    val code = data?.getStringExtra("address").toString()
                        .substring(0, data?.getStringExtra("address").toString().indexOf(","))
                    val zip = data?.getStringExtra("address").toString()
                        .substring(data?.getStringExtra("address").toString().indexOf(",") + 1)
                    zipcode.text = code
                    address.text = zip
                }
            }
        }
    }
}





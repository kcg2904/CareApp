package com.KingsStory.Application


import android.app.Activity
import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.KingsStory.Application.PopUp.PopUp
import org.json.JSONArray


class SignUpActivity : AppCompatActivity() {

    private lateinit var signupid: EditText
    private lateinit var signuppass: EditText
    private lateinit var signuppass2: EditText
    private lateinit var objectivearea: EditText
    private lateinit var name: EditText
    private lateinit var gendergroup: RadioGroup
    private lateinit var male: RadioButton
    private lateinit var female: RadioButton
    private lateinit var signupdate: DatePicker
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var terms: RadioButton
    private lateinit var privacy: RadioButton
    private lateinit var sms: RadioGroup
    private lateinit var smstrue: RadioButton
    private lateinit var smsfalse: RadioButton

    private lateinit var signupbtn: Button
    private lateinit var errorid: TextView
    private lateinit var errorpass: TextView
    private lateinit var errorpass2: TextView
    private lateinit var errorgender: TextView
    private lateinit var errordate: TextView
    private lateinit var errorarea: TextView
    private lateinit var errorname: TextView
    private lateinit var errorphone: TextView
    private lateinit var errorterms: TextView
    private lateinit var errorprivacy: TextView
    private lateinit var errorsms: TextView

    private lateinit var terms_pop: ImageView
    private lateinit var privacy_pop: ImageView
    private lateinit var sms_pop: ImageView


    private var today: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_sign_up)

        initView(this)
        init()
        OnClickListener(signupbtn)

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

    //버튼 클릭 이벤트 설정 함수
    private fun OnClickListener(view: View) {
        when (view) {
            signupbtn ->
                view.setOnClickListener {
                    //id
                    if (signupid.text.length < 4 && signupid.text.toString() != "") {
                        signupid.setBackgroundResource(R.drawable.red_edittext)
                        errorid.text = "아이디가 너무 짧습니다 4~15자로 작성해주세요"

                    } else if (signupid.text.toString() == "") {
                        signupid.setBackgroundResource(R.drawable.red_edittext)
                        errorid.text = "아이디를 입력해주세요"
                    } else {
                        signupid.setBackgroundResource(R.drawable.white_edittext)
                        errorid.text = ""
                    }
                    //pass
                    if (signuppass.text.length < 6 && signuppass.text.toString() != "") {
                        signuppass.setBackgroundResource(R.drawable.red_edittext)
                        errorpass.text = "비밀번호가 너무 짧습니다 6~16자로 작성해주세요"
                    } else if (signuppass.text.toString() == "") {
                        signuppass.setBackgroundResource(R.drawable.red_edittext)
                        errorpass.text = "비밀번호를 입력해주세요"
                    } else {
                        signuppass.setBackgroundResource(R.drawable.white_edittext)
                        errorpass.text = ""
                    }
                    //pass2
                    if (signuppass.text.toString() != signuppass2.text.toString()) {
                        signuppass2.setBackgroundResource(R.drawable.red_edittext)
                        errorpass2.text = "비밀번호가 다릅니다. 다시 입력해주세요"
                    } else {
                        signuppass2.setBackgroundResource(R.drawable.white_edittext)
                        errorpass2.text = ""
                    }
                    //objectivearea
                    if (objectivearea.text.toString() == "") {
                        objectivearea.setBackgroundResource(R.drawable.red_edittext)
                        errorarea.text = "희망근무지를 입력해주세요"
                    } else {
                        objectivearea.setBackgroundResource(R.drawable.white_edittext)
                        errorarea.text = ""
                    }

                    //name
                    if (name.text.toString() == "") {
                        name.setBackgroundResource(R.drawable.red_edittext)
                        errorname.text = "이름을 입력해주세요"
                    } else {
                        name.setBackgroundResource(R.drawable.white_edittext)
                        errorname.text = ""
                    }

                    //gender
                    if (male.isChecked == false && female.isChecked == false) {
                        gendergroup.setBackgroundResource(R.drawable.red_edittext)
                        errorgender.text = "성별을 선택해주세요"
                    } else {
                        gendergroup.setBackgroundResource(R.drawable.white_edittext)
                        errorgender.text = ""
                    }

                    //date
                    if (today == signupdate.year.toString() + signupdate.month.toString() + signupdate.dayOfMonth.toString()) {
                        signupdate.setBackgroundResource(R.drawable.red_edittext)
                        errordate.text = "생년월일을 변경해주세요"
                    } else {
                        signupdate.setBackgroundResource(R.drawable.white_edittext)
                        Log.d(
                            "testt",
                            "h" + signupdate.year.toString() + signupdate.month.toString() + signupdate.dayOfMonth.toString()
                        )
                        errordate.text = ""
                    }

                    //phone
                    if (phone.text.toString().contains("-")) {
                        phone.setBackgroundResource(R.drawable.red_edittext)
                        errorphone.text = "-를 빼고 입력해주세요"

                    } else if (phone.text.length < 10) {
                        phone.setBackgroundResource(R.drawable.red_edittext)
                        errorphone.text = "잘못된 번호 입니다 다시 입력해주세요"

                    } else {
                        phone.setBackgroundResource(R.drawable.white_edittext)
                        errorphone.text = ""

                    }
                    //Termsofservice
                    if (terms.isChecked == false) {
                        terms.setBackgroundResource(R.drawable.red_edittext)
                        errorterms.text = "이용약관에 동의해주세요"
                    } else {
                        terms.setBackgroundResource(R.drawable.white_edittext)
                        errorterms.text = ""
                    }
                    //PrivacyPolicy
                    if (privacy.isChecked == false) {
                        privacy.setBackgroundResource(R.drawable.red_edittext)
                        errorprivacy.text = "개인정보처리방침에 동의해주세요"
                    } else {
                        privacy.setBackgroundResource(R.drawable.white_edittext)
                        errorprivacy.text = ""
                    }
                    //sms/mms
                    if (smstrue.isChecked == false && smsfalse.isChecked == false) {
                        sms.setBackgroundResource(R.drawable.red_edittext)
                        errorsms.text = "정보수신동의를 선택해주세요"
                    } else {
                        sms.setBackgroundResource(R.drawable.white_edittext)
                        errorsms.text = ""
                    }


                    var isError: Boolean =
                        errorid.text == "" && errorpass.text == "" && errorpass2.text == "" &&
                                errorarea.text == "" && errorname.text == "" && errorgender.text == "" &&
                                errordate.text == "" && errorphone.text == "" && errorterms.text == "" &&
                                errorprivacy.text == "" && errorsms.text == ""
                    var mgender: String = ""

                    if (male.isChecked) {
                        mgender = male.text.toString()
                    } else if (female.isChecked) {
                        mgender = female.text.toString()
                    }

                    var mdate: String =
                        signupdate.year.toString() + "-" + (signupdate.month + 1).toString() + "-" + signupdate.dayOfMonth.toString()
                    Log.d(
                        "testt", signupid.text.toString() +
                                signuppass.text.toString() +
                                objectivearea.text.toString() +
                                name.text.toString() +
                                mgender +
                                mdate +
                                phone.text.toString() +
                                email.text.toString()
                    )
                    if (isError) {
                        val id: String = signupid.text.toString()
                        val pass: String = signuppass.text.toString()
                        val area: String = objectivearea.text.toString()
                        val name: String = name.text.toString()
                        val phone: String = phone.text.toString()
                        val email: String = email.text.toString()
                        SignUp().PersonalSignUp(
                            id,
                            pass,
                            area,
                            name,
                            mgender,
                            mdate,
                            phone,
                            email,
                            this
                        )

                    }
                }
        }

    }

    private fun init() {
        //생년월일을 수정했는지 알기위한 변수
        today =
            signupdate.year.toString() + signupdate.month.toString() + signupdate.dayOfMonth.toString()
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
    //변수에 뷰 연동 함수
    private fun initView(activity: Activity) {
        signupid = activity.findViewById(R.id.signup_id)
        signuppass = activity.findViewById(R.id.signup_pass1)
        signuppass2 = activity.findViewById(R.id.signup_pass2)
        objectivearea = activity.findViewById(R.id.signup_area)
        name = activity.findViewById(R.id.signup_name)
        gendergroup = activity.findViewById(R.id.signup_gendergroup)
        male = activity.findViewById(R.id.signup_male)
        female = activity.findViewById(R.id.signup_female)
        signupdate = activity.findViewById(R.id.signup_date)
        phone = activity.findViewById(R.id.signup_phone)
        email = activity.findViewById(R.id.signup_email)
        signupbtn = activity.findViewById(R.id.signup_btn)
        errorid = activity.findViewById(R.id.errer_id)
        errorpass = activity.findViewById(R.id.errer_pass)
        errorpass2 = activity.findViewById(R.id.errer_pass2)
        errorgender = activity.findViewById(R.id.errer_gender)
        errordate = activity.findViewById(R.id.errer_date)
        errorarea = activity.findViewById(R.id.errer_area)
        errorname = activity.findViewById(R.id.errer_name)
        errorphone = activity.findViewById(R.id.errer_phone)
        terms = activity.findViewById(R.id.signup_Termsofservice)
        errorterms = activity.findViewById(R.id.errer_Termsofservice)
        privacy = activity.findViewById(R.id.signup_PrivacyPolicy)
        errorprivacy = activity.findViewById(R.id.errer_PrivacyPolicy)
        sms = activity.findViewById(R.id.signup_sms)
        smstrue = activity.findViewById(R.id.signup_sms_true)
        smsfalse = activity.findViewById(R.id.signup_sms_false)
        errorsms = activity.findViewById(R.id.errer_sms)

        terms_pop = activity.findViewById(R.id.termstext)
        privacy_pop = activity.findViewById(R.id.privacytext)
        sms_pop = activity.findViewById(R.id.smstext)
    }


}
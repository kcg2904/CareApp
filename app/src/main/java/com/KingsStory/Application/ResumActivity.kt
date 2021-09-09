package com.KingsStory.Application

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.KingsStory.Application.databinding.ActivityResumBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class ResumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResumBinding
    private lateinit var shar: SharedPreferences
    private var token: String? = null
    var img: String = ""
    var result: Boolean? = null
    private var id: String = ""
    private var type: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        binding = ActivityResumBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun dataset(result: Boolean) {
        if (result == true) {
            Log.d("testt", "트루들어옴2")
            OnClickListener(binding.resumeUpdate)
            OnClickListener(binding.resumeDelete)
        } else if (result == false) {
            Log.d("testt", "펄스들어옴2")
            OnClickListener(binding.resumeCreate)
        }
    }

    private fun enabled(state: Boolean) {
        binding.curriculumTitle.isEnabled = state
        binding.curriculumName.isEnabled = state
        binding.curriculumPhone.isEnabled = state
        binding.curriculumEmail.isEnabled = state
        binding.curriculumEmailTrue.isEnabled = state
        binding.curriculumEmailFalse.isEnabled = state
        binding.curriculumAddressbtn.isEnabled = state
        binding.curriculumAddress2.isEnabled = state
        binding.curriculumAddressTrue.isEnabled = state
        binding.curriculumAddressFalse.isEnabled = state
        binding.curriculumObjectivearea.isEnabled = state
        binding.curriculumParttime1.isEnabled = state
        binding.curriculumParttime2.isEnabled = state
        binding.curriculumParttime3.isEnabled = state
        binding.curriculumParttime4.isEnabled = state
        binding.curriculumParttime5.isEnabled = state
        binding.curriculumParttime6.isEnabled = state
        binding.curriculumParttime7.isEnabled = state
        binding.curriculumCareer.isEnabled = state
        binding.curriculumCertificate1.isEnabled = state
        binding.curriculumCertificate2.isEnabled = state
        binding.curriculumSpecialty.isEnabled = state
        binding.curriculumImgbtn.isEnabled = state
        binding.curriculumBirthyear.isEnabled = state
        binding.curriculumTrue.isEnabled = state
        binding.curriculumFalse.isEnabled = state
        binding.curriculumBtn.isEnabled = state
        binding.parttimeReset.isEnabled = state
    }

    private fun init() {
        shar = getSharedPreferences("login_sp_id", MODE_PRIVATE)
        token = shar.getString("login_sp_id", "null")
        menubar().toolbar(this)
        OnClickListener(binding.curriculumAddressbtn)
        OnClickListener(binding.curriculumImg)

        if (getSharedPreferences("login_sp_id", MODE_PRIVATE).getString(
                "login_sp_id",
                ""
            ) != null
        ) {
            id = getSharedPreferences("login_sp_id", MODE_PRIVATE).getString("login_sp_id", "")!!
            //TODO : 이력서가 있는지 없는지 확인 -> 있으면 등록 비활성화 없으면 수정 and 삭제 비활성화
            if (id != null && result == null) {
                id = id!!.substring((id!!.lastIndexOf("_")) + 1)
//                Resume().ResumeSelect("resume", id!!, this)
                (application as GlobalApplication).service3.resumeselect("resume", id!!)
                    .enqueue(object :
                        Callback<resumeDto> {
                        override fun onResponse(
                            call: Call<resumeDto>,
                            response: Response<resumeDto>
                        ) {
                            if (!response.isSuccessful) {
                                return
                            }
                            response.body().let { dto ->
                                if (dto?.title != null) {

                                    result = true
                                    dataset(result!!)
                                    Toast.makeText(this@ResumActivity, "작성해놓은 이력서가 있습니다 \'이력서수정\' 버튼을 눌러주세요",Toast.LENGTH_SHORT).show()
                                    binding.resumeUpdate.setOnClickListener {
                                        Resume().ResumeData(
                                            "resume",
                                            token!!,
                                            this@ResumActivity,
                                            binding.curriculumTitle,
                                            binding.curriculumName,
                                            binding.curriculumPhone,
                                            binding.curriculumEmail,
                                            binding.curriculumAddress,
                                            binding.curriculumObjectivearea,
                                            binding.curriculumParttime1,
                                            binding.curriculumParttime2,
                                            binding.curriculumParttime3,
                                            binding.curriculumParttime4,
                                            binding.curriculumParttime5,
                                            binding.curriculumParttime6,
                                            binding.curriculumParttime7,
                                            binding.curriculumCareer,
                                            binding.curriculumCertificate1,
                                            binding.curriculumCertificate2,
                                            binding.curriculumSpecialty,
                                            binding.curriculumImg,
                                            binding.curriculumBirthyear,
                                            binding.pushid,
                                            binding.curriculumEmailTrue,
                                            binding.curriculumEmailFalse,
                                            binding.curriculumAddressTrue,
                                            binding.curriculumAddressFalse,
                                            binding.curriculumTrue,
                                            binding.curriculumFalse
                                        )
                                        img = dto.img
                                        Log.d("testt", "트루들어옴 :$result")
                                        type = "update"
                                        enabled(true)
                                        OnClickListener(binding.curriculumBtn)
                                    }
                                }
                            }
                        }

                        override fun onFailure(call: Call<resumeDto>, t: Throwable) {
                            Log.d("testt", "$t")
                            Toast.makeText(this@ResumActivity, "작성해놓은 이력서가 없습니다 \'이력서등록\' 버튼을 눌러주세요",Toast.LENGTH_SHORT).show()
                            result = false
                            dataset(result!!)
                            Log.d("testt", "펄스트루들어옴 :$result")
                        }
                    })


            }


        }

        binding.parttimeReset.setOnClickListener {
            binding.curriculumParttime1.isChecked = false
            binding.curriculumParttime2.isChecked = false
            binding.curriculumParttime3.isChecked = false
            binding.curriculumParttime4.isChecked = false
            binding.curriculumParttime5.isChecked = false
            binding.curriculumParttime6.isChecked = false
            binding.curriculumParttime7.isChecked = false
        }
    }

    fun OnClickListener(view: View) {
        when (view) {
            binding.resumeCreate -> binding.resumeCreate.setOnClickListener {
                enabled(true)
                type = "insert"
                Log.d("testt", "이력서등록")
                OnClickListener(binding.curriculumBtn)
            }
            binding.resumeUpdate -> binding.resumeUpdate.setOnClickListener {
                Log.d("testt", "이력서수정")
                type = "update"
                enabled(true)
                OnClickListener(binding.curriculumBtn)
            }

            binding.resumeDelete -> binding.resumeDelete.setOnClickListener {
                type = "delete"
                Toast.makeText(this, "데이터삭제", Toast.LENGTH_SHORT).show()
            }

            binding.curriculumAddressbtn -> {
                binding.curriculumAddressbtn.setOnClickListener {
                    Intent(this, AddressApiActivity::class.java).apply {
                        startActivityForResult(this, 1000)
                    }
                }
            }
            binding.curriculumBtn -> {
                view.setOnClickListener {
                    //필수 입력 체크
                    //제목
                    if (binding.curriculumTitle.text.toString() == "") {
                        binding.errerTitle.text = "제목을 입력해주세요"
                    } else {
                        binding.errerTitle.text = ""
                    }
                    //이름
                    if (binding.curriculumName.text.toString() == "") {
                        binding.errerName.text = "이름을 입력해주세요"
                    } else {
                        binding.errerName.text = ""
                    }
                    //휴대폰
                    if (binding.curriculumPhone.text.toString() == "") {
                        binding.errerPhone.text = "휴대폰번호를 입력해주세요"
                    } else if (binding.curriculumPhone.text.contains("-")) {
                        binding.errerPhone.text = "-를 빼고 숫자만 입력해주세요"
                    } else {
                        binding.errerPhone.text = ""
                    }
                    //이메일
                    if (binding.curriculumEmail.text.toString() == "") {
                        binding.errerEmail.text = "이메일을 입력해주세요"
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.curriculumEmail.text)
                            .matches()
                    )
                        binding.errerEmail.text = "이메일 형식이 아닙니다"
                    else {
                        binding.errerEmail.text = ""
                    }

                    if (!binding.curriculumEmailTrue.isChecked && !binding.curriculumEmailFalse.isChecked) {
                        binding.errerEmailcheck.text = "이메일 공개여부를 선택해주세요"
                    } else {
                        binding.errerEmailcheck.text = ""
                    }
                    //주소
                    if (binding.curriculumAddress.text == "") {
                        binding.errerAddress.text = "주소 찾기 버튼을 이용해주세요"
                    } else {
                        binding.errerAddress.text = ""
                    }

                    if (!binding.curriculumAddressTrue.isChecked && !binding.curriculumAddressFalse.isChecked) {
                        binding.errerAddresscheck.text = "주소 공개여부를 설정해주세요"
                    } else {
                        binding.errerAddresscheck.text = ""

                    }

                    //희망지역
                    if (binding.curriculumObjectivearea.text.toString() == "") {
                        binding.errerObjectivearea.text = "희망 근무지역을 입력해주세요"
                    } else {
                        binding.errerObjectivearea.text = ""
                    }

                    //근무시간
                    if (!binding.curriculumParttime1.isChecked && !binding.curriculumParttime2.isChecked && !binding.curriculumParttime3.isChecked &&
                        !binding.curriculumParttime4.isChecked && !binding.curriculumParttime5.isChecked && !binding.curriculumParttime6.isChecked &&
                        !binding.curriculumParttime7.isChecked
                    ) {
                        binding.errerParttime.text = "희망 근무시간을 선택해주세요"
                    } else {
                        binding.errerParttime.text = ""
                    }
                    //이력서 설정
                    if (!binding.curriculumTrue.isChecked && !binding.curriculumFalse.isChecked) {
                        binding.errerCurriculum.text = "이력서 공개여부를 설정해주세요"
                    } else {
                        binding.errerCurriculum.text = ""
                    }
                    val isError: Boolean =
                        binding.errerTitle.text == "" && binding.errerName.text == "" && binding.errerPhone.text == ""
                                && binding.errerEmail.text == "" && binding.errerEmailcheck.text == "" && binding.errerAddress.text == ""
                                && binding.errerAddresscheck.text == "" && binding.errerObjectivearea.text == "" && binding.errerParttime.text == ""
                                && binding.errerCurriculum.text == ""

                    if (isError) {
                        //주소와 상세주소 합침
                        val maddress: String
                        if (binding.curriculumAddress2.text.toString() != "") {
                            maddress =
                                binding.curriculumAddress.text.toString() + " " + binding.curriculumAddress2.text.toString()
                        } else {
                            maddress = binding.curriculumAddress.text.toString()
                        }
                        //희망 업무시간
                        var mparttime: String = ""
                        if (binding.curriculumParttime1.isChecked && mparttime == "") {
                            mparttime += binding.curriculumParttime1.text.toString()
                        } else if (binding.curriculumParttime1.isChecked) {
                            mparttime += ("," + binding.curriculumParttime1.text.toString())
                        }

                        if (binding.curriculumParttime2.isChecked && mparttime == "") {
                            mparttime += binding.curriculumParttime2.text.toString()
                        } else if (binding.curriculumParttime2.isChecked) {
                            mparttime += ("," + binding.curriculumParttime2.text.toString())
                        }

                        if (binding.curriculumParttime3.isChecked && mparttime == "") {
                            mparttime += binding.curriculumParttime3.text.toString()
                        } else if (binding.curriculumParttime3.isChecked) {
                            mparttime += ("," + binding.curriculumParttime3.text.toString())
                        }

                        if (binding.curriculumParttime4.isChecked && mparttime == "") {
                            mparttime += binding.curriculumParttime4.text.toString()
                        } else if (binding.curriculumParttime4.isChecked) {
                            mparttime += ("," + binding.curriculumParttime4.text.toString())
                        }

                        if (binding.curriculumParttime5.isChecked && mparttime == "") {
                            mparttime += binding.curriculumParttime5.text.toString()
                        } else if (binding.curriculumParttime5.isChecked) {
                            mparttime += ("," + binding.curriculumParttime5.text.toString())
                        }

                        if (binding.curriculumParttime6.isChecked && mparttime == "") {
                            mparttime += binding.curriculumParttime6.text.toString()
                        } else if (binding.curriculumParttime6.isChecked) {
                            mparttime += ("," + binding.curriculumParttime6.text.toString())
                        }

                        if (binding.curriculumParttime7.isChecked && mparttime == "") {
                            mparttime += binding.curriculumParttime7.text.toString()
                        } else if (binding.curriculumParttime7.isChecked) {
                            mparttime += ("," + binding.curriculumParttime7.text.toString())
                        }

                        //이메일 공개여부
                        var mEmailCheck: Boolean = false
                        if (binding.curriculumEmailTrue.isChecked) {
                            mEmailCheck = binding.curriculumEmailTrue.isChecked
                        } else if (binding.curriculumEmailFalse.isChecked) {
                            mEmailCheck = binding.curriculumEmailFalse.isChecked
                        }

                        //주소공개여부
                        var mAddressCheck: Boolean = false
                        if (binding.curriculumEmailTrue.isChecked) {
                            mAddressCheck = binding.curriculumAddressTrue.isChecked
                        } else if (binding.curriculumEmailFalse.isChecked) {
                            mAddressCheck = binding.curriculumAddressFalse.isChecked
                        }

                        //이력서 공개여부
                        var mCurriculumCheck: Boolean = false
                        if (binding.curriculumEmailTrue.isChecked) {
                            mCurriculumCheck = binding.curriculumTrue.isChecked
                        } else if (binding.curriculumEmailFalse.isChecked) {
                            mCurriculumCheck = binding.curriculumFalse.isChecked
                        }

                        //자격증
                        var mCertificate: String = ""
                        if (binding.curriculumCertificate1.isChecked && mCertificate == "") {
                            mCertificate += binding.curriculumCertificate1.text.toString()
                        } else if (binding.curriculumCertificate1.isChecked) {
                            mCertificate += (binding.curriculumCertificate1.text.toString() + ",")
                        }

                        if (binding.curriculumCertificate2.isChecked && mCertificate == "") {
                            mCertificate += binding.curriculumCertificate2.text.toString()
                        } else if (binding.curriculumCertificate2.isChecked) {
                            mCertificate += (binding.curriculumCertificate2.text.toString() + ",")
                        }

                        //데이터를 넣기위한 변수 선언
                        val title: String = binding.curriculumTitle.text.toString()
                        val name: String = binding.curriculumName.text.toString()
                        val phone: String = binding.curriculumPhone.text.toString()
                        val email: String = binding.curriculumEmail.text.toString()
                        val emailCheck: String = if (mEmailCheck) "1" else "0"
                        val address: String = maddress
                        val addressCheck: String = if (mAddressCheck) "1" else "0"
                        val objectivearea: String = binding.curriculumObjectivearea.text.toString()
                        val parttime: String = mparttime
                        val career: String? =
                            if (binding.curriculumCareer.text.toString() == "") null else binding.curriculumCareer.text.toString()
                        val certificate: String = mCertificate
                        val specialty: String = binding.curriculumSpecialty.text.toString()
                        val image: String = img
                        val study: String = binding.curriculumBirthyear.text.toString()
                        val suggestion: String = binding.pushid.text.toString()
                        val resumeCheck: String = if (mCurriculumCheck) "1" else "0"
                        val lat: String =
                            (Geocoder().ReturnData(address, this)!!.latitude).toString()
                        val lng: String =
                            (Geocoder().ReturnData(address, this)!!.longitude).toString()
                        Log.d("testt", "$lng")
                        if (type != "") {
                            Resume().Resume(
                                type,
                                title,
                                name,
                                phone,
                                email,
                                address,
                                objectivearea,
                                parttime,
                                career,
                                certificate,
                                specialty,
                                image,
                                study,
                                suggestion,
                                emailCheck,
                                addressCheck,
                                resumeCheck,
                                token!!,
                                lat,
                                lng,
                                this
                            )

                        }

                    } else {
                        Toast.makeText(this, "잘못된 입력값이 있습니다", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            binding.curriculumImgbtn -> {
                view.setOnClickListener {

                    if (checkPermissions(PERMISSIONS, PERMISSIONS_REQUEST)) {
                        openGallery()
                    } else {
                        //권한획득
                        checkPermissions(PERMISSIONS, PERMISSIONS_REQUEST)
                    }
                }
            }

        }
    }


    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val PERMISSIONS_REQUEST = 101


    private fun checkPermissions(permissions: Array<String>, permissionsRequest: Int): Boolean {
        val permissionList: MutableList<String> = mutableListOf()
        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }
        if (permissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionList.toTypedArray(),
                PERMISSIONS_REQUEST
            )
            return false
        }
        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 승인이 필요합니다", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private val OPEN_GALLERY: Int = 102
    private fun openGallery() {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    var dialog: ProgressDialog? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1000 -> {
                    if (resultCode == RESULT_OK) {
                        val zip = data?.getStringExtra("address").toString()
                            .substring(data?.getStringExtra("address").toString().indexOf(",") + 1)
                        binding.curriculumAddress.text = zip
                    }
                }
                //갤러리에서 가져오기
                OPEN_GALLERY -> {
                    val currentImageUri: Uri? = data?.data
                    try {
                        val bitmap: Bitmap =
                            MediaStore.Images.Media.getBitmap(contentResolver, currentImageUri)
                        Log.d("testt", "1 : ${currentImageUri}")
                        val f = File(currentImageUri.toString())

                        Log.d("testt", "2 : ${f.name}")

                        binding.curriculumImg.setImageBitmap(bitmap)
                        data?.data.let { returnUri ->
                            contentResolver.query(returnUri!!, null, null, null, null)
                        }?.use { cursor ->
                            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            cursor.moveToFirst()
                            val test: String = getFilePath(cursor.getString(nameIndex))
                            Log.d(
                                "uploadFile",
                                "3 : ${token} + ${f.path} + ${cursor.getString(nameIndex)} + ${test}"
                            )
                            dialog = ProgressDialog.show(this, "", "Uploading file...", true)
                            testRetrofit(test)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun getFilePath(name: String): String {
        //리턴 데이터
        var returnData: String = ""
        try {
            var file_extensionFlag: Boolean = false
            val file_extensionArray: Array<String> = arrayOf(
                ".bmp", ".gif", ".jpeg", ".jpg", ".png", ".psd", ".pic",
                ".raw", ".tiff", ".avi", ".flv", ".mkv", ".mov", ".mp3",
                ".mp4", ".wav", "wma", ".doc", ".docx", ".html", ".hwp",
                ".pdf", ".txt", ".exe", ".zip"
            )
            for (i in 0 until file_extensionArray.size step 1) {
                if (name.contains(file_extensionArray[i])) {
                    file_extensionFlag = true
                    break
                }
            }

            if (file_extensionFlag) {
                val dir: File = Environment.getExternalStorageDirectory()
                val directoryFileList: Queue<File> = LinkedList()

                directoryFileList.addAll(dir.listFiles());
                var fileContainsFlag: Boolean = false

                while (!directoryFileList.isEmpty()) {
                    val file: File = directoryFileList.remove()

                    if (file.isDirectory) {
                        directoryFileList.addAll(file.listFiles())
                    } else {
                        if (file.name.contains(name)) {
                            var tempPath: String = file.path
                            val checkName = "/" + name
                            if (tempPath.contains(checkName) == false) {
                                tempPath = tempPath.replace(name, checkName)
                            }
                            Log.d("uploadFile", " 파일명 : ${name}")
                            Log.d("uploadFile", " 경로 : ${tempPath}")
                            fileContainsFlag = true
                            returnData = tempPath
                            break
                        }
                    }
                }
                if (!fileContainsFlag) {
                    Log.d("uploadFile", "파일을 찾지 못했습니다 $name")
                } else {
                    Log.d("uploadFile", "파일 확장자 미포함 $name")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return returnData
    }

    private fun testRetrofit(path: String) {
        val file = File(path)
        var file2 = token + ".jpg"
        img = "http://test1234.dothome.co.kr/img/${file2}"
        var requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        var body: MultipartBody.Part =
            MultipartBody.Part.createFormData("uploaded_file", file2, requestBody)
        (application as GlobalApplication).service3.resume(
            token!!, body
        ).enqueue(object : retrofit2.Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (!response.isSuccessful) {
                    Log.d("레트로핏 결과2", "접속 성공하였지만 실패")
                    dialog?.dismiss()
                    return
                }
                dialog?.dismiss()
                Toast.makeText(
                    this@ResumActivity,
                    "레드로핏결과2: ${response.body()}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("레트로핏결과1", "$t")
                dialog?.dismiss()
            }
        })
    }
}

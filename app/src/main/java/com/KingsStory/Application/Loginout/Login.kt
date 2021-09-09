package com.KingsStory.Application

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login {
    //개인로그인
    fun PersonalLogin(id: String, pass: String, activity: Activity) {
        (activity.application as GlobalApplication).service3.test(
            id, pass
        ).enqueue(object : Callback<User2> {
            override fun onResponse(call: Call<User2>, response: Response<User2>) {
                if (response.isSuccessful) {
                    //dto에 넣는곳
                    response.body().let { dto ->
                        if (dto?.registration == "1") {
                            AutoLogin().saveUserToken("personal_" + dto.name, activity)
                            AutoLogin().saveUserid(dto.id, activity)
                            (activity.application as GlobalApplication).test()
                            Toast.makeText(activity, "로그인 성공", Toast.LENGTH_SHORT).show()
                            activity.startActivity(
                                Intent(
                                    activity,
                                    UserinfoActivity::class.java
                                )
                            )
                            activity.finish()
                        }else if(dto?.registration == "0") {
                            Toast.makeText(activity, "회원가입 처리중인 아이디입니다", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "잘못된 아이디/비밀번호 입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<User2>, t: Throwable) {
                Toast.makeText(activity, "접속실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //기업로그인
    fun CompanyLogin(id: String, pass: String, activity: Activity) {
        (activity.application as GlobalApplication).service3.companylogin(
            id, pass
        ).enqueue(object : Callback<CompanyDto> {
            override fun onResponse(call: Call<CompanyDto>, response: Response<CompanyDto>) {
                if (response.isSuccessful) {
                    //dto에 넣는곳
                    response.body().let { dto ->
                        Log.d("testt","$dto")
                        if (dto?.registration == "1") {
                            AutoLogin().saveUserToken("company_" + dto.companyname, activity)
                            AutoLogin().saveUserid(id,activity)
                            (activity.application as GlobalApplication).test()
                            Toast.makeText(activity, "로그인 성공", Toast.LENGTH_SHORT).show()
                            activity.startActivity(
                                Intent(
                                    activity,
                                    UserinfoActivity::class.java
                                )
                            )
                            activity.finish()
                        }
                        else if(dto?.registration == "0") {
                            Toast.makeText(activity, "회원가입 처리중인 아이디입니다", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(activity, "잘못된 아이디/비밀반호 입니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CompanyDto>, t: Throwable) {
                Toast.makeText(activity, "접속실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

//    fun test(account:String,pass:String,activity: Activity){
//        (activity.application as GlobalApplication).service3.also {
//            it.test(
//                account,pass
//            )
//                .enqueue(object : Callback<UserDto> {
//                    override fun onResponse(
//                        call: Call<UserDto>,
//                        response: Response<UserDto>
//                    ) {
//                        if (!response.isSuccessful) {
//                            Log.e("Retrofit", "연결실패")
//                            return
//                        }
//                        response.body()?.let { dto ->
//                            Log.d("Retrofit", dto.toString())
//                            //TODO: db에서 검색 부분이 조건으로 들어가서 해당하는 데이터만 가져와야함 이게 안된다면 전체 데이터에서 필요한 값만 가져오게 해야함
//                        }
//                    }
//
//                    override fun onFailure(call: Call<UserDto>, t: Throwable) {
//                        Log.e("err","$t")
//                        Toast.makeText(activity, "접속실패", Toast.LENGTH_SHORT).show()
//                    }
//                })
//        }
//    }
}

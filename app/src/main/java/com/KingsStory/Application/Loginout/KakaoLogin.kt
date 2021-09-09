package com.KingsStory.Application

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLogin {

    //로그인 함수
    fun Login(activity: Activity){
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(activity, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(activity, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(activity, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(activity, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(activity, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(activity, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(activity, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(activity, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(activity, "기타 에러", Toast.LENGTH_SHORT).show()
                        Log.d("errorrr", "" + error)
                    }
                }
            } else if (token != null) {
                Toast.makeText(activity, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                UserApiClient.instance.me { user, error ->
                    AutoLogin().saveUserToken("kakao "+user?.id.toString(),activity)
                }
                activity.startActivity(Intent(activity, UserinfoActivity::class.java))
                activity.finish()
            }
        }

        if (LoginClient.instance.isKakaoTalkLoginAvailable(activity)) {
            LoginClient.instance.loginWithKakaoTalk(activity, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(activity, callback = callback)
        }
    }
}
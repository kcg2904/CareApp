package com.KingsStory.Application

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.KingsStory.Application.UserinfoActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleLogin {
    //로그인 함수 startActivityForResult로 데이터를 보내기 때문에 그것에 대한 처리를 해줘야함
    fun Login(mGoogleSignInOptions:GoogleSignInOptions, mGoogleSignInClient: GoogleSignInClient, activity: Activity){
        val signInIntent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent,200)
    }

//    데이터 처리 구문
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//
//        if (requestCode == 200) {
//            val task: Task<GoogleSignInAccount> =
//                GoogleSignIn.getSignedInAccountFromIntent(data)
//            handleSignInResult(task)
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    //연동된 구글아이디의 정보 가져오는 함수
    fun handleSignInResult(task: Task<GoogleSignInAccount>,activity: Activity) {
        try {
            val acct: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            if (acct != null) {
                val personName = acct.displayName
                val personGivenName = acct.givenName
                val personFamilyName = acct.familyName
                val personEmail = acct.email
                val personId = acct.id
                val personPhoto: Uri? = acct.photoUrl
                AutoLogin().saveUserToken("google "+personId,activity)
                activity.startActivity(Intent(activity, UserinfoActivity::class.java))
                activity.finish()
            }
        } catch (e: ApiException) {
            Log.e("test", "signInResult:failed code=" + e.statusCode)
        }
    }



}
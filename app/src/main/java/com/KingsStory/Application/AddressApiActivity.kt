package com.KingsStory.Application

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient

//우편번호 검색 액티비티
class AddressApiActivity : AppCompatActivity() {
    private val webView: WebView by lazy {
        this.findViewById(R.id.zipcode)
    }
    @SuppressLint
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_api)

        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(KaKaoJavaScriptInterface(), "Android")
        webView.webViewClient = object  : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.loadUrl("javascript:execKakaoPostcode();")
            }
        }
        webView.loadUrl("http://test1234.dothome.co.kr/daum.html")
    }

    inner class KaKaoJavaScriptInterface{
        @JavascriptInterface
        fun processDATA(address: String?){
            Intent().apply {
                
                putExtra("address",address)
                setResult(RESULT_OK,this)
            }
            finish()
        }
    }
}

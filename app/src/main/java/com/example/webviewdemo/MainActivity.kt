package com.example.webviewdemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val webView = findViewById<WebView>(R.id.webView)
        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    Log.i("LinLi", "onPageStarted:$url")
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    Log.i("LinLi", "onPageFinished:$url")
                    super.onPageFinished(view, url)
                }
            }
//            loadUrl("https://www.yahoo.com")
            addJavascriptInterface(WebAppInterface(context), "Android")
            webView.loadUrl("file:///android_asset/example.html")
        }
        val buttonBack: Button = findViewById(R.id.button_back)
        val buttonForward: Button = findViewById(R.id.button_forward)
        val buttonRefresh: Button = findViewById(R.id.button_refresh)
        val buttonCallJavaScript: Button = findViewById(R.id.button_callJavaScript)

        // 按鈕事件
        buttonBack.setOnClickListener {
            if (webView.canGoBack()) webView.goBack()
        }
        buttonForward.setOnClickListener {
            if (webView.canGoForward()) webView.goForward()
        }
        buttonRefresh.setOnClickListener {
            webView.reload()
        }
        buttonCallJavaScript.setOnClickListener {
            webView.evaluateJavascript(
                "javascript:changeContent('這是由 Android 調用的 JavaScript 更新的內容')",
                null
            )
        }
    }
}

class WebAppInterface(private val context: Context) {
    @JavascriptInterface
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
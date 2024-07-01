package com.code.kembang_telon.view.payments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.code.kembang_telon.MainActivity
import com.code.kembang_telon.R
import com.midtrans.sdk.uikit.SdkUIFlowBuilder

class PaymentMidtransActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_midtrans)

        val webView = findViewById<WebView>(R.id.webview)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.e("klikitem", "Loading URL: $url")
                if (url != null) {
                    if (url.contains("Payment successful") || url.contains("completed")) {
                        val intent = Intent(this@PaymentMidtransActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                        return true
                    }
                }
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.e("klikitem", "Finished loading URL: $url")
            }
        }
        webView.settings.javaScriptEnabled = true
        val url = intent.getStringExtra("URL")
        Log.e("klikitem", "Loading URL BOS: $url")
        if (url != null) {
            webView.loadUrl(url)
        } else {
            Log.e("klikitem", "URL is null")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}


package com.example.senseiam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        WebView.setWebContentsDebuggingEnabled(true) // Enable WebView debugging

        webView = findViewById(R.id.webView)
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        // Ensure the correct setup of the JavaScript interface
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        webView.loadUrl("file:///android_asset/register.html")
    }

    class WebAppInterface(private val context: Context) {

        private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        @JavascriptInterface
        fun saveUserData(userData: String) {
            val editor = sharedPreferences.edit()
            editor.putString("userData", userData)
            editor.apply()
            // Start the LoginActivity
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}

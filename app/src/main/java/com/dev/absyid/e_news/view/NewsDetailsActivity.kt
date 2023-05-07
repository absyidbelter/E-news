package com.dev.absyid.e_news.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.dev.absyid.e_news.R

class NewsDetailsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_news)

        val webView = findViewById<WebView>(R.id.web_view)

        webView.settings.javaScriptEnabled = true

        val articleUrl = intent.getStringExtra("url")
        if (articleUrl != null) {
            loadUrlIntoWebView(articleUrl, webView)
        }
    }

    private fun loadUrlIntoWebView(url: String, webView: WebView) {
        webView.loadUrl(url)
    }
}

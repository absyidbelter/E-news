package com.dev.absyid.e_news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.absyid.e_news.R
import com.dev.absyid.e_news.adapter.NewsSourcesAdapter
import com.dev.absyid.e_news.controller.SourcesResponse
import com.dev.absyid.e_news.model.NewsServiceFactory
import com.dev.absyid.e_news.utils.key
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsSourcesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_sources)

        recyclerView = findViewById(R.id.news_sources_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val categoryName = intent.getStringExtra("categoryName") ?: ""
        title = "News Sources"

        val service = NewsServiceFactory.create()

        val callback = object : Callback<SourcesResponse> {
            override fun onResponse(call: Call<SourcesResponse>, response: Response<SourcesResponse>) {
                if (response.isSuccessful) {
                    val sources = response.body()?.sources ?: emptyList()
                    recyclerView.adapter = NewsSourcesAdapter(sources)
                } else {
                    showFailedMessage()
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                showFailedMessage()
            }
        }

        service.getSourcesByCategory(key.API_KEY, categoryName).enqueue(callback)
    }

    private fun showFailedMessage() {
        Toast.makeText(this@NewsSourcesActivity, "Failed to get sources", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

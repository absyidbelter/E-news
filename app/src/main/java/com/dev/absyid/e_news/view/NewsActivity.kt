package com.dev.absyid.e_news.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.absyid.e_news.controller.ArticlesResponse
import com.dev.absyid.e_news.R
import com.dev.absyid.e_news.adapter.NewsAdapter
import com.dev.absyid.e_news.model.NewsServiceFactory
import com.dev.absyid.e_news.utils.key
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    private var sourceId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.news_articles_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.article_search_edit_text)

        sourceId = intent.getStringExtra("sourceId").orEmpty()
        title = sourceId

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchArticles(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        val newsService = NewsServiceFactory.create()

        newsService.getArticlesBySource(key.API_KEY, sourceId)
            .enqueue(object : Callback<ArticlesResponse> {
                override fun onResponse(
                    call: Call<ArticlesResponse>,
                    response: Response<ArticlesResponse>
                ) {
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles.orEmpty()
                        recyclerView.adapter = NewsAdapter(articles)
                    } else {
                        showErrorToast()
                    }
                }

                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                    showErrorToast(t.localizedMessage ?: "Unknown error")
                }
            })

    }

    private fun searchArticles(query: String) {
        val newsService = NewsServiceFactory.create()

        newsService.searchArticles(key.API_KEY, query, sourceId)
            .enqueue(object : Callback<ArticlesResponse> {
                override fun onResponse(
                    call: Call<ArticlesResponse>,
                    response: Response<ArticlesResponse>
                ) {
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles.orEmpty()
                        recyclerView.adapter = NewsAdapter(articles)
                    } else {
                        showErrorToast(response.errorBody()?.string() ?: "Unknown error")
                    }
                }

                override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                    showErrorToast(t.localizedMessage ?: "Unknown error")
                }
            })
    }

    private fun showErrorToast(message: String = "Failed to load articles") {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

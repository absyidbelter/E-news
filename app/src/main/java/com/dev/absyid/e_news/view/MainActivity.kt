package com.dev.absyid.e_news.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.absyid.e_news.R
import com.dev.absyid.e_news.adapter.NewsCategoryAdapter
import com.dev.absyid.e_news.controller.NewsService
import com.dev.absyid.e_news.controller.SourcesResponse
import com.dev.absyid.e_news.model.Category
import com.dev.absyid.e_news.model.NewsServiceFactory
import com.dev.absyid.e_news.utils.key
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var categoryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "News Category"
        categoryRecyclerView = findViewById(R.id.category_recyclerview)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this)

        val newsService = NewsServiceFactory.create()
        loadCategories(newsService)
    }

    private fun loadCategories(newsService: NewsService) {
        newsService.getSources(key.API_KEY).enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(call: Call<SourcesResponse>, response: Response<SourcesResponse>) {
                if (response.isSuccessful) {
                    val sources = response.body()?.sources ?: emptyList()
                    val categories = sources.map { Category(it.id, it.category) }
                    val filteredCategories = removeDuplicateCategories(categories)
                    setCategoryRecyclerViewAdapter(filteredCategories)
                } else {
                    handleUnsuccessfulResponse(response)
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun removeDuplicateCategories(categories: List<Category>): List<Category> {
        val distinctCategories = categories.distinctBy { it.name }
        val filteredCategories = mutableListOf<Category>()

        for (category in distinctCategories) {
            if (!filteredCategories.any { it.id == category.id }) {
                filteredCategories.add(category)
            }
        }

        return filteredCategories
    }

    private fun setCategoryRecyclerViewAdapter(categories: List<Category>) {
        categoryRecyclerView.adapter = NewsCategoryAdapter(categories) { category ->
            showNewsSources(category.name)
        }
    }

    private fun handleUnsuccessfulResponse(response: Response<SourcesResponse>) {
        val errorMessage = "Error: ${response.code()} ${response.message()}"
        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showNewsSources(categoryName: String) {
        val intent = Intent(this, NewsSourcesActivity::class.java)
        intent.putExtra("categoryName", categoryName)
        startActivity(intent)
    }
}

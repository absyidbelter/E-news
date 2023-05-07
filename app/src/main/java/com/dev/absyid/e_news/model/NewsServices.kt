package com.dev.absyid.e_news.model

import com.dev.absyid.e_news.controller.NewsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsServiceFactory {
    fun create(): NewsService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NewsService::class.java)
    }
}
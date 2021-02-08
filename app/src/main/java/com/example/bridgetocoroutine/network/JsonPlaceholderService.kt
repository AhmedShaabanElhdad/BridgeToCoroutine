package com.example.bridgetocoroutine.network

import com.example.bridgetocoroutine.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceholderService {

    @GET("posts")
    fun getPostsWthExecute(): Call<List<Post>>


    @GET("posts")
    fun getPostsWthCallback(): Call<List<Post>>


}
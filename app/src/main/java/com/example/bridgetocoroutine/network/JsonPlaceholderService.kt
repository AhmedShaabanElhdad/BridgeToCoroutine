package com.example.bridgetocoroutine.network

import com.example.bridgetocoroutine.model.Post
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderService {

    @GET("/posts")
    fun getPostsWthCall(): Call<List<Post>>

    @GET("/posts")
    fun getPostsWthObservable(): Observable<List<Post>>

    //we now define our API call as a suspend function
    @GET("/posts")
    suspend fun getPostsWthCoroutine(): Response<List<Post>>

}
package com.example.bridgetocoroutine.tasks

import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.JsonPlaceholderService
import com.example.bridgetocoroutine.onResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


// The callback fix problem that occur as the Thread is Block despite
// the first request finish his work and remain still block while updating ui
// The loading for each repository can then be started before the result for the previous repository is received
// it divide the process into two part
// 1- processing response from api in the thread
// 2- handling result part should be extracted into a callback
fun loadPostInBackgroundWithCallback(service: JsonPlaceholderService, callback: (List<Post>?) -> Unit) {

//    service.getPostsWthExecute().enqueue(object : Callback<List<Post>> {
//        override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//            callback(response.body())
//        }
//
//        override fun onFailure(call: Call<List<Post>>, t: Throwable) {
//
//        }
//    })


    //here i make extension function to help me to reduce code and get rid of object expresion
    service.getPostsWthExecute().onResponse { response ->
        callback(response.body())
    }

}


// problem with callback hell here is
// 1- retrofit callback is work only on api not other function that need long time
// 2- you will write code than should be necessary
// 3- you will be nesting callbacks N layers deep where N equals the number of discrete tasks in a chunk of code
//every line of code must actually be encapsulated in a callback which is encapsulated in a callback
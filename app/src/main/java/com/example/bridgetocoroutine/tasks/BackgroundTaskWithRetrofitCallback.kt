package com.example.bridgetocoroutine.tasks

import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.JsonPlaceholderService
import com.example.bridgetocoroutine.onResponse


// The callback fix problem that occur as the Thread is Block despite
// the first request finish his work and remain still block while updating ui
// The loading for each repository can then be started before the result for the previous repository is received
// it divide the process into two part
// 1- processing response from api in the thread
// 2- handling result part should be extracted into a callback
// We're starting many requests concurrently which lets us decrease the total loading time.
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
    service.getPostsWthCall().onResponse { response ->
        callback(response.body())
    }

}


// problem with callback hell here is
// 1- retrofit callback is work only on api not other function that need long time
// 2- you will write code than should be necessary
// 3- you will be nesting callbacks N layers deep where N equals the number of discrete tasks in a chunk of code
// 4- every line of code must actually be encapsulated in a callback which is encapsulated in a callback
// 5- The results can come in any order if we call many request concurrently
// 6- callbacks might be non-trivial and error-prone, especially when there're several underlying threads and synchronization takes place.

// Scenario:- if we call many request concurrently and every one of this request make another request
// then update data in callback it may cause late in update ui so if this requests return list
// we should update it in last index but what if last index finish before previous one
// all the results for requests that take more time to process will be lost.

//Solution in Callback will
// - using a synchronized version of the list and AtomicInteger that represent increasing count
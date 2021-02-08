package com.example.bridgetocoroutine.tasks

import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.JsonPlaceholderService


// despite it free the main thread it is still block the thread until finish calling api and get data
// then update the ui so if there is another it will wait until update ui which run in main ui thread finish
// to do its task in background thread
fun loadPostInBackground(service: JsonPlaceholderService, callback: (List<Post>) -> Unit) {

    // use the thread function to start a new thread
    // the main ui thread now is free to do any thing
    Thread {
        callback(loadPostSynchronously(service))
    }
}


package com.example.bridgetocoroutine.tasks

import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.JsonPlaceholderService

fun loadPostSynchronously(service: JsonPlaceholderService) : List<Post> {


    // Execute make the service call function but it work synchronously
    val posts = service
        .getPostsWthCall()
        .execute()  // return Response
        .body() ?: listOf()  // we can change it in extension function

    return posts

}
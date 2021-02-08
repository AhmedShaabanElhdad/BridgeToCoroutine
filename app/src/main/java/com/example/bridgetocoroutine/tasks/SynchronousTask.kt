package com.example.bridgetocoroutine.tasks

import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.JsonPlaceholderService

fun loadPostSynchronously(service: JsonPlaceholderService) : List<Post> {


    // Execute make the service call function but it work synchronously
    val posts = service
        .getPostsWthExecute()
        .execute()
        .body() ?: listOf()

    return posts

}
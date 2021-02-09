package com.example.bridgetocoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.RetrofitBuilder
import com.example.bridgetocoroutine.tasks.loadPostInBackground
import com.example.bridgetocoroutine.tasks.loadPostInBackgroundWithCallback
import com.example.bridgetocoroutine.tasks.loadPostInBackgroundWithSuspend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var posts: List<Post> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RetrofitBuilder.makeRetrofitService()

        //First Task
        service.getPostsWthCall()

        //Second Task
        loadPostInBackground(service) { posts ->
            runOnUiThread {
                this.posts = posts
            }
        }

        //third Task
        loadPostInBackgroundWithCallback(service) { _posts ->
            _posts?.apply {
                posts = this
            }
        }

        //Forth Task
        loadPostInBackgroundWithCallback(service)?.apply {
            subscribe { _posts ->
                posts = _posts
            }
        }

        // fifth Task
        CoroutineScope(Dispatchers.IO).launch  {
            posts = loadPostInBackgroundWithSuspend(service)
        }


    }
}
package com.example.bridgetocoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.RetrofitBuilder
import com.example.bridgetocoroutine.tasks.loadPostInBackground
import com.example.bridgetocoroutine.tasks.loadPostInBackgroundUsingDeferred
import com.example.bridgetocoroutine.tasks.loadPostInBackgroundWithCallback
import com.example.bridgetocoroutine.tasks.loadPostInBackgroundWithSuspend
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    var posts: List<Post> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RetrofitBuilder.makeRetrofitService()

        // First Task
        service.getPostsWthCall()

        // Second Task
        loadPostInBackground(service) { posts ->
            runOnUiThread {
                this.posts = posts
            }
        }

        // third Task
        loadPostInBackgroundWithCallback(service) { _posts ->
            _posts?.apply {
                posts = this
                // update data here
            }
        }

        // Forth Task
        loadPostInBackgroundWithCallback(service)?.apply {
            subscribe { _posts ->
                posts = _posts
                // update data here
            }
        }

        // fifth Task
        CoroutineScope(Dispatchers.IO).launch  {
            posts = loadPostInBackgroundWithSuspend(service)
            // if you don't use live data will need go to main thread to update ui
            withContext(Dispatchers.Main) {
                // update data here
            }
        }

        // six Task
        CoroutineScope(Dispatchers.Main).launch{
            CoroutineScope(Dispatchers.IO).launch{
                posts = loadPostInBackgroundUsingDeferred(service)
                // update data here
            }.join()
        }



        // runBlocking {  posts = loadPostInBackgroundUsingDeferred(service) }
        // all the coroutines still run on the main UI thread.
        // although runBlock will block the thread but if there exist more than coroutine in coroutine scope will work concurrency



    }
}
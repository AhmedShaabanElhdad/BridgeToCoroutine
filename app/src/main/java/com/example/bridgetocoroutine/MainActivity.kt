package com.example.bridgetocoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.RetrofitBuilder
import com.example.bridgetocoroutine.tasks.loadPostInBackground
import com.example.bridgetocoroutine.tasks.loadPostInBackgroundWithCallback

class MainActivity : AppCompatActivity() {

    var posts:List<Post> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RetrofitBuilder.makeRetrofitService()

        //First Task
        service.getPostsWthExecute()



        //Second Task
        loadPostInBackground(service){ posts ->
            runOnUiThread{
                this.posts = posts
            }
        }

        //third Task
        loadPostInBackgroundWithCallback(service){ _posts ->
            _posts?.apply {
               posts = this
            }
        }



    }
}
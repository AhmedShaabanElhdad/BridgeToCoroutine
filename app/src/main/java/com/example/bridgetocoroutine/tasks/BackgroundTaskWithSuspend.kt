package com.example.bridgetocoroutine.tasks

import com.example.bridgetocoroutine.bodyList
import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.JsonPlaceholderService
import com.example.bridgetocoroutine.onResponse
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.flatMapIterable
import io.reactivex.schedulers.Schedulers

//Coroutine
    // Coroutines are light wight Thread that run on top of threads and can be suspended (computation can be paused).
    // Computation can be paused, removed from the thread, and stored in memory. Meanwhile,
    // the thread is free to be occupied with other activities:


// Steps to make Coroutine
    // 1- we now define our API call as a suspend function this is concern with retrofit
    // 2- returning the result wrapped in Response instead of returning a Call
    // 3- Suspend function should only be called from a coroutine or another suspend function
    // 4- To start a new coroutine we can just use one of the main "coroutine builders": launch, async, or runBlocking
            // - async  => starts a new coroutine and returns a Deferred object.
                    // Deferred ( Future or Promise like in javascript): it stores a computation, but it defers the moment you get the final result => it promises the result sometime in the future.
            // - launch => is used for starting a computation that isn't expected to return a specific result. launch returns Job,
                    // Job represents the coroutine. It is possible to wait until it completes by calling Job.join().
            // - runBlock => runBlocking is used as a bridge between regular and suspend functions, between blocking and non-blocking worlds.
                    // It works as an adaptor for starting the top-level main coroutine and is intended primarily to be used in tests.
    // 5- use CoroutineDispatcher => determines what thread or threads the corresponding coroutine should be run on.

//
//coroutine can be started on one thread from the thread pool and resumed on another
// 1- Dispatchers.Default => represents a shared pool of threads on JVM. This pool provides a means for parallel execution.
      // It consists of as many threads as there are CPU cores available, but still it has two threads if there's only one core.
// 2- Dispatchers.Main =>  run the coroutine only on the main UI thread
      // If the main thread is busy when we start a new coroutine on it, the coroutine becomes suspended and scheduled for execution on this thread. The coroutine will only resume when the thread becomes free.
// 3- Dispatchers.IO => represent background Thread


// For Android, it is common practice to use CoroutineDispatchers.Main by default for the top coroutine and
// then to explicitly put a different dispatcher when we need to run the code on a different thread.

// Note runBlock => That brings us approximately the same total loading time as the CALLBACKS version before.
    // But without needing any callbacks.

// what happen if we call this function in main thread will ui freeze ??!!
    // While the response is waiting to be received, the thread is free to be occupied with other tasks.
    // That's why when users are loaded via the COROUTINE option, the UI stays responsive, despite all the requests taking place on the main UI thread.
    // suspend functions treat the thread fairly and don't block it for "waiting", but it doesn't bring any concurrency to the picture.


// different between Job and Deferred
// Deferred is a generic type which extends Job. An async call can return a Deferred<T> depending on what the lambda returns (the last expression inside the lambda is the result).
// use await() on the Deferred instanceTo get the result of a coroutine.


// Coroutine Builder => they automatically create the corresponding scope. as they take lambda which represent Coroutine scope
//launch {
     /* this: CoroutineScope */
//}


// Coroutine scope is responsible for the structure and parent-child relationships between different coroutines.
    // We always start new coroutines inside a scope.
    // Coroutine context stores additional technical information used to run a given coroutine, like the dispatcher specifying the thread or threads the coroutine should be scheduled on.

//This "parent-child" relationship works through scopes: the child coroutine is started from the scope corresponding to the parent coroutine.



// New coroutines can only be started inside a scope.
// launch and async are declared as extensions to CoroutineScope,
// so an implicit or explicit receiver must always be passed when we call them.




suspend fun loadPostInBackgroundWithSuspend(service: JsonPlaceholderService): List<Post> {
    return service.getPostsWthCoroutine()
        .bodyList() //this is extension function to return list instead on nullable list
}
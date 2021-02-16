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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope


// Concurrency =>
// When starting each request in a new coroutine, all the requests are started asynchronously.
// A new request can be sent before the result for the previous one is received:

// powerful of coroutine with deferred ==> "Concurrency" :-
// we can only start a new coroutine inside a coroutine scope. So add code to the coroutineScope call, so that we can call async
    // 1-  add coroutine scope as it is only way to start new coroutine
    // 2- We wrap each request with async. That will create as many coroutines as number of repositories we have.
          // since it's inexpensive to create a new coroutine, it's not an issue. We can create as many as we need.
    // 3-  async returns Deferred<T> or usually Deferred<List<T>>
    // 4- deferred.await => call it will return List<T>
    // 5- in async If we don't specify CoroutineDispatcher as an argument, then async will use the dispatcher from the outer scope.

// you can change the thread that async use if it used inside runBlock => Specify Dispatchers.Default as the context argument for the async function

// Note Note that it's considered good practice to use the dispatcher from the outer scope rather than
// to explicitly specify it on each end-point.

// 1- withContext  => calls the given code with the specified coroutine context, suspends until it completes, and returns the result.
// 2- launch(context) { ... }.join() => start a new coroutine and explicitly wait (by suspending) until it completes.


// in suspend function without async if there return list and i want to make request to api for each item in list
// and return new type  i will need to use flatmap but with async i will use map and async inside map

// It is possible to create a new scope without starting a new coroutine.
// The coroutineScope function does this. When we need to start new coroutines in a structured way inside a suspend function without access to the outer scope,
// we can create a new coroutine scope which automatically becomes a child of the outer scope that this suspend function is called from.

// It's also possible to start a new coroutine from the global scope using GlobalScope.async or GlobalScope.launch.
// This will create a top-level "independent" coroutine.

// what drawback of global scopes and not in structured concurrency:
// When using GlobalScope.async
// - there is no structure that binds several coroutines to a smaller scope.
// - The coroutines started from the global scope are all independent;
// - their lifetime is limited only by the lifetime of the whole application.
// - It is possible to store a reference to the coroutine started from the global scope and wait for its completion or cancel it explicitly but it won't happen automatically as it would with a structured one.

// in structure concurrency
// - The scope automatically waits for completion of all the child coroutines.
        // if the scope corresponds to a coroutine, then the parent coroutine does not complete
        // until all the coroutines launched in its scope are complete.
// - The scope can automatically cancel child coroutines if something goes wrong or
        // if a user simply changes their mind and decides to revoke the operation.


// When we start new coroutines inside the given scope, it's much easier to ensure that all of them are run with the same context.
// And it's much easier to replace the context if needed.

// All the nested coroutines are automatically started with the inherited context; and the dispatcher is a part of this context.
// That's why all the coroutines started by async are started with the context of the default dispatcher:
// once, when we create a top-level coroutine. All the nested coroutines then inherit the context and modify it only if needed.


suspend fun loadPostInBackgroundUsingDeferred(service: JsonPlaceholderService) = coroutineScope {
    // this scope inherits the context from the outer scope

    val deferred: Deferred<List<Post>> = async {
        // nested coroutine started with the inherited context

        service.getPostsWthCoroutine()
            .bodyList()
    }

    deferred.await()
}




suspend fun loadPostInBackgroundUsingDeferredAndGlobalScope(service: JsonPlaceholderService):List<Post>{
    val deferred: Deferred<List<Post>> = GlobalScope.async {
        service.getPostsWthCoroutine()
            .bodyList()
    }

    return  deferred.await()
}
package com.example.bridgetocoroutine.tasks

import com.example.bridgetocoroutine.model.Post
import com.example.bridgetocoroutine.network.JsonPlaceholderService
import com.example.bridgetocoroutine.onResponse
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.flatMapIterable
import io.reactivex.schedulers.Schedulers


//Reactive is a programming paradigm and the oriented around data flow and propagation of data change.
//things can be stream variables such as user input, properties, and data structure

//The stream is a sequence of ongoing event ordered in time.
// A stream can emit three different things a Value, an Error, and Completed signal

// Rx depend on  observer design pattern + iterator pattern + function programming
// RxJava contain 3 item
// 1- an Observable emits a stream of data and event
// 2- subscribers consume those data.
// 3- Schedular tells observable and observers, on which thread they should run.

//Schedular has two method
// observeOn() – Is tell the observers, on which thread you should observe
// subscribeOn() – tell the observable, on which thread you should run.


//Reactive Programming
// 1- raises the level of abstraction and keep business logic interdependent of the code
// 2- the code is more concise and simply the ability to chain asynchronous operations. (problem in callback)
// 3- is exposed explicit way to define how concurrent operation should be operates. (many request in callback)
// 4- Rx programming provides also a simplified way of running different tasks in different threads. (problem in Thread)





fun loadPostInBackgroundWithCallback(service: JsonPlaceholderService): Observable<List<Post>>? {
    val io: Scheduler = Schedulers.io()

    return service.getPostsWthObservable()
        .observeOn(io)
        .subscribeOn(AndroidSchedulers.mainThread())
}


// problem with Rx is
// 1- problem is that it is not easy to understand. Especially, Functional reactive programming is very hard
// to understand when you come from Object-Oriented Programming
package com.ab.kotlin_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch { // launch a new coroutine in background and continue
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // print after delay
        }
        println("Hello,") // main thread continues while coroutine is delayed
        Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive

//        runBlocking {     // but this expression blocks the main thread
//            delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
//        }


        val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        runBlocking {
            job.join() // wait until child coroutine completes
        }


        // Structured Coroutines
        runBlocking { // this: CoroutineScope
            launch { // launch a new coroutine in the scope of runBlocking
                delay(1000L)
                println("World!")
            }
            println("Hello,")
        }

        runBlocking {
            launch { doWorld() }
            println("Hello,")
        }

        GlobalScope.launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(100L)
            }
        }
    }

    // this is your first suspending function
    suspend fun doWorld() {
        delay(1000L)
        println("World!")
    }
}

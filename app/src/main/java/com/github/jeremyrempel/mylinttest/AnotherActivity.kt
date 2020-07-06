package com.github.jeremyrempel.mylinttest

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity

class AnotherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.w("test", "I'm a log test")
    }
}

package com.github.jeremyrempel.mylinttest

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife

class MainActivity : AppCompatActivity() {

    @JvmField
    @BindView(R.id.txt_hello)
    var helloTxt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        // deprecated
        val fail = MyDeprecatedLibClass()

        val myResult = MyTest().doSomething()

        Log.d("testing", "hello world lint")

        Log.wtf("wtf", "this is another err")

        Toast.makeText(this, "hello world", Toast.LENGTH_SHORT)
    }
}
package com.github.jeremyrempel.mylinttest;

import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.function.Function;

import kotlin.jvm.functions.Function1;

public class MyTest extends AppCompatActivity {

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);

        // do something
    }

    public String doSomething() {
        return "hello";
    }

    public void doSomethingWithLamba(Function1<Void, String> callback, String param) {

    }
}

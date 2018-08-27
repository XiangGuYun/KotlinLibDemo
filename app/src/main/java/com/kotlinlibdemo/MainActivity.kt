package com.kotlinlibdemo

import android.os.Bundle
import com.kotlinlib.activity.KotlinActivity
import com.kotlinlib.other.LayoutId

@LayoutId(R.layout.activity_main)
class MainActivity : KotlinActivity() {

    override fun init(bundle: Bundle?) {
           go(MainActivity::class.java,"name" to "1")
    }

}

package com.kotlinlib.other

import android.widget.TextView

class Group(vararg anys: Any){
    var anys: Array<out Any> = anys

    fun text(vararg strs:String): Group {
        for ((i,it) in anys.withIndex()){
            (it as TextView).text = strs[i]
        }
        return this
    }

    fun textColor(vararg colors:Int): Group {
        for ((i,it) in anys.withIndex()){
            (it as TextView).setTextColor(colors[i])
        }
        return this
    }
}
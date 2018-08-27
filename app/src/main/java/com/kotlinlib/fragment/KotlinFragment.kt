package com.kotlinlib.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlinlib.other.BaseInterface
import com.kotlinlib.activity.KotlinActivity
import com.kotlinlib.other.LayoutId
import com.kotlinlib.other.DensityUtils

abstract class KotlinFragment:Fragment(), BaseInterface {
    
    lateinit var gson:Gson

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewInject = this::class.annotations[0] as LayoutId
        val view = inflater.inflate(viewInject.id,container,false)
        gson = KotlinActivity.gson
        init(view)
        return view
    }

    abstract fun init(view: View?)

    /**
     * 获取对象JSON字符串
     * @param any Any
     * @return String
     */
    fun jsonStr(any: Any):String{
        return gson.toJson(any)
    }

    /**
     * 土司提示
     * @param isLong 是否显示更长时间
     */
    fun Any.toast(isLong: Boolean=false){
        if(isLong)
            Toast.makeText(activity,this.toString(),
                    Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(activity,this.toString(),
                    Toast.LENGTH_LONG).show()
    }

    /**
     * 尺寸单位转换
     */
    fun Number.px2dp():Int{
        return DensityUtils.px2dip(this@KotlinFragment.activity as Context, this.toFloat())
    }

    fun Number.dp2px():Int{
        return DensityUtils.dip2px(this@KotlinFragment.activity as Context, this.toFloat())
    }

    fun Number.sp():Int{
        return DensityUtils.px2sp(this@KotlinFragment.activity as Context, this.toFloat())
    }

    fun Number.px():Int{
        return DensityUtils.sp2px(this@KotlinFragment.activity as Context, this.toFloat())
    }

    inner class JsonList<T>{
        fun transList(jsonStr:String): List<T> {
            return gson.fromJson(jsonStr, object : TypeToken<List<T>>(){}.type) as List<T>
        }

        fun transArrayList(jsonStr:String): ArrayList<T> {
            return gson.fromJson(jsonStr, object : TypeToken<ArrayList<T>>(){}.type) as ArrayList<T>
        }
    }

}
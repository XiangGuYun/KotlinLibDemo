package com.kotlinlib.other

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

interface StringUtils {

    val DEBUG: Int
        get() = 1
    val ERROR: Int
        get() = 3


    /*
    打印日志
    */
    fun Any.logD(tag:String="def",pre:String=""){
        Log.d(tag, pre+this.toString())
    }

    fun Any.logI(tag:String="def"){
        Log.i(tag, this.toString())
    }

    fun Any.logE(tag:String="def", pre:String="" ){
        Log.e(tag,pre+ this.toString())
    }

    fun Any.header(header:String):String{
        return "$header${this}"
    }

    /*
   获取颜色
    */
    fun String.color():Int{
        return Color.parseColor(this)
    }

    /*
    打印日期
     */
    @SuppressLint("SimpleDateFormat")
    fun Long.fmtDate(fmt:String):String{
        return SimpleDateFormat(fmt).format(Date(this))
    }

    fun String.fmtDate(fmt:String):String{
        return SimpleDateFormat(fmt).format(Date(this.toLong()*1000))
    }

    /*
    判断是否只有数字
     */
    fun String.isNum():Boolean{
        return TextUtils.isDigitsOnly(this)
    }

    /**
     * 转为UTF-8
     */
    fun String.utf8():String{
        try {
            return URLEncoder.encode(this, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("UnsupportedEncodingException occurred. ", e)
        }
    }

    fun String.htmlColor(color:String):String{
        return "<font color='$color'>$this</font>"
    }


}
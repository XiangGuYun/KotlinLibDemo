package com.kotlinlib.view.base

import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

interface ViewUtils {

    val MP:Int get() = ViewGroup.LayoutParams.MATCH_PARENT
    val WC:Int get() = ViewGroup.LayoutParams.WRAP_CONTENT

    /**
     * View类扩展
     */

    fun <T:View> T.hide():T {
        this.visibility=View.INVISIBLE
        return this
    }

    fun <T:View> T.gone():T {
        this.visibility=View.GONE
        return this
    }

    fun <T:View> T.showIfNot():T {
        if(visibility!=View.VISIBLE)
            visibility=View.VISIBLE
        return this
    }

    fun <T:View> T.show():T {
        this.visibility=View.VISIBLE
        return this
    }

    //实现监听设置的第一种方法
    fun <T:View> T.click(function: () -> Unit):T {
        setOnClickListener { function.invoke() }
        return this
    }

    //实现监听设置的第二种方法
    //使用函数类型作为形参类型
    fun <T:View> T.click1(func: (View)->Unit):T{
        setOnClickListener(func)
        return this
    }

    //使用函数类型作为返回值类型
    fun getFunType():(Int,Int)->Int{
        return fun(a:Int,b:Int):Int{
            return a+b
        }
    }

    fun <T:View> T.alpha(a:Float):T{
        alpha = a
        return this
    }

    fun <T:View> T.background(b:Drawable):T{
        background = b
        return this
    }

    fun <T:View> T.lp(params:ViewGroup.LayoutParams):T{
        layoutParams = params
        return this
    }

    fun  <T:View> T.child(childId:Int):T{
        return findViewById(childId)
    }

    fun  <T:View> T.child1(childId:Int):ViewGroup{
        return findViewById(childId)
    }

    fun <T:ViewGroup> T.ca(index:Int):View{
        return getChildAt(index)
    }


    data class SizeMode(var widthSize:Int,var widthMode:Int,var heightSize:Int,
                        var heightMode:Int)

    fun View.getSizeMode(widthSpec:Int,heightSpec:Int): SizeMode {
        var widthSize = View.MeasureSpec.getSize(widthSpec)
        var widthMode = View.MeasureSpec.getMode(widthSpec)
        var heightSize = View.MeasureSpec.getSize(heightSpec)
        var heightMode = View.MeasureSpec.getMode(heightSpec)
        return SizeMode(widthSize, widthMode, heightSize, heightMode)
    }

    fun View.getTypeArray(styleId:IntArray,attr:AttributeSet):TypedArray{
        return context.obtainStyledAttributes(attr,styleId)
    }

    fun View.getTypeArray(styleId:IntArray):TypedArray{
        return context.obtainStyledAttributes(styleId)
    }

    fun View.revertVisibility1(){
        visibility = if(visibility==View.INVISIBLE){
            View.VISIBLE
        }else{
            View.INVISIBLE
        }
    }

    fun View.revertVisibility2(){
        visibility = if(visibility==View.GONE){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

    fun RecyclerView.update(){
        this.adapter.notifyDataSetChanged()
    }

    fun EditText.empty(): Boolean {
        return text.toString().isEmpty()
    }

}
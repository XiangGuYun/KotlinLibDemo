package com.kotlinlib.view.textview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView

interface TextViewUtils {

    val TextView.textString: String get()=text.toString()

    fun <T:TextView> T.text(text:String): T {
        this.text = text
        return this
    }

    fun <T:TextView> T.color(color:Int): T {
        setTextColor(color)
        return this
    }

    fun <T:TextView> T.color(color:String): T {
        setTextColor(Color.parseColor(color))
        return this
    }

    fun <T:TextView> T.size(size:Int): T {
        textSize = size.toFloat()
        return this
    }

    fun <T:TextView> T.html(text:String):T{
        setText(Html.fromHtml(text))
        return this
    }

    /**
     * 获取TextView的Drawable
     */
    fun Context.tvDrawable(id:Int): Drawable {
        val drawable = resources.getDrawable(id)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        return drawable
    }

    fun TextView.setLeftTVDrawable(id:Int){
        this.setCompoundDrawables(context.tvDrawable(id),null,null,null)
    }

    fun TextView.setRightTVDrawable(id:Int){
        this.setCompoundDrawables(null,null,context.tvDrawable(id),null)
    }

    fun TextView.setTopTVDrawable(id:Int){
        this.setCompoundDrawables(null,context.tvDrawable(id),null,null)
    }

    fun TextView.setBtmTVDrawable(id:Int){
        this.setCompoundDrawables(null,null,null,context.tvDrawable(id))
    }

    fun TextView.setNullTVDrawable(){
        this.setCompoundDrawables(null,null,null,null)
    }

}
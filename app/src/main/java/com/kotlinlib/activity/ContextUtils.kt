package com.kotlinlib.activity

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import com.kotlinlib.fragment.FragPagerUtils
import com.kotlinlib.fragment.FragmentUtils
import com.kotlinlib.other.ResUtils


interface ContextUtils {

    val Context.inflater: LayoutInflater get()=LayoutInflater.from(this)
    
    val Context.SDPATH: String
        get() = Environment.getExternalStorageDirectory().toString()
    
    fun Context.drawable(id:Int): Drawable? {
        return resources.getDrawable(id)
    }

    fun Context.toggleKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun Activity.closeKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(imm.isActive&&currentFocus!=null){
            if(currentFocus.windowToken!= null){
                imm.hideSoftInputFromWindow(this.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

    /**
     * 将字符串进行复制
     * @receiver Context
     * @param text String
     */
    fun Context.copyText(text:String){
        val myClipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val  myClip: ClipData = ClipData.newPlainText("text", text)
        myClipboard.primaryClip = myClip
    }

    fun FragmentActivity.fragUtils(frag: Fragment, rootId:Int= ResUtils.getId(this, "root")): FragmentUtils {
        return FragmentUtils(this, frag, rootId)
    }

    fun FragmentActivity.fragUtils(list: ArrayList<Fragment>,rootId:Int= ResUtils.getId(this, "root")): FragmentUtils {
        return FragmentUtils(this, list, rootId)
    }

    fun <T:Fragment> FragmentActivity.fragPagerUtils(): FragPagerUtils<T> {
        return FragPagerUtils(this)
    }

    fun <T:Fragment> FragmentActivity.fragPagerUtils(vp:ViewPager,fragments:ArrayList<T>): FragPagerUtils<T> {
        return FragPagerUtils(this, vp, fragments)
    }

}
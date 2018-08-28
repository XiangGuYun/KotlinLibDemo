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

    //获取布局填充器
    val Context.inflater: LayoutInflater get()=LayoutInflater.from(this)

    //获取SD卡路径
    val Context.SDPATH: String get() = Environment.getExternalStorageDirectory().toString()

    /**
     * 获取Drawable对象
     * @receiver Context
     * @param id Int
     * @return Drawable?
     */
    fun Context.drawable(id:Int): Drawable? {
        return resources.getDrawable(id)
    }

    /**
     * 切换键盘
     * @receiver Context
     */
    fun Context.toggleKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 关闭键盘
     * @receiver Activity
     */
    fun Activity.closeKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(imm.isActive&&currentFocus!=null){
            if(currentFocus.windowToken!= null){
                imm.hideSoftInputFromWindow(this.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

    /**
     * 将字符串复制到剪贴板
     * @receiver Context
     * @param text String
     */
    fun Context.copyText(text:String){
        val myClipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val  myClip: ClipData = ClipData.newPlainText("text", text)
        myClipboard.primaryClip = myClip
    }

    /**
     * 创建FragmentUtils只包含一个Fragment
     * @receiver FragmentActivity
     * @param frag Fragment 单个Fragment
     * @param rootId 容器ID，默认是R.id.root
     * @return FragmentUtils
     */
    fun FragmentActivity.fragUtils(frag: Fragment, rootId:Int= ResUtils.getId(this, "root")): FragmentUtils {
        return FragmentUtils(this, frag, rootId)
    }

    /**
     * 创建FragmentUtils包含多个Fragment
     * @receiver FragmentActivity
     * @param list ArrayList<Fragment> Fragment集合
     * @param rootId Int 容器ID，默认是R.id.root
     * @return FragmentUtils
     */
    fun FragmentActivity.fragUtils(list: ArrayList<Fragment>,rootId:Int= ResUtils.getId(this, "root")): FragmentUtils {
        return FragmentUtils(this, list, rootId)
    }

    /**
     * 创建FragPagerUtils
     * @receiver FragmentActivity
     * @return FragPagerUtils<T>
     */
    fun <T:Fragment> FragmentActivity.fragPagerUtils(): FragPagerUtils<T> {
        return FragPagerUtils(this)
    }

    /**
     * 创建FragPagerUtils
     * @receiver FragmentActivity
     * @param vp ViewPager
     * @param fragments ArrayList<T>
     * @return FragPagerUtils<T>
     */
    fun <T:Fragment> FragmentActivity.fragPagerUtils(vp:ViewPager,fragments:ArrayList<T>): FragPagerUtils<T> {
        return FragPagerUtils(this, vp, fragments)
    }

}
package com.kotlinlib.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.widget.DrawerLayout
import android.view.View
import java.io.Serializable

interface ActivityUtils {
    /**
     * 将对象集合的某一字符串字段进行拼接
     * @param list ArrayList<T> 对象集合
     * @param regex String 分隔符
     * @param func (Int)->String 根据集合索引来获取字符串
     * @return String
     */
    fun <T> appendStr(list:ArrayList<T>,regex:String, func:(Int)->String): String {
        var build = StringBuilder()
        for (i in list.indices){
            if(i!=list.size-1){
                build.append(func.invoke(i)).append(regex)
            }else{
                build.append(func.invoke(i))
            }
        }
        return build.toString()
    }

    fun <T> appendStr(list:List<T>,regex:String, func:(Int)->String): String {
        var build = StringBuilder()
        for (i in list.indices){
            if(i!=list.size-1){
                build.append(func.invoke(i)).append(regex)
            }else{
                build.append(func.invoke(i))
            }
        }
        return build.toString()
    }

    /**
     * 获取屏幕宽高
     */
    fun Context.srnWidth():Int{
        return this.resources.displayMetrics.widthPixels
    }

    fun Context.srnHeight():Int{
        return this.resources.displayMetrics.heightPixels
    }


    /**
     * Activity类扩展
     */
    fun <T: Activity> Activity.go(cls:Class<T>, vararg pairs:Pair<String,Any>){
        val intent = Intent(this,cls)
        pairs.forEach {
            when(it.second::class){
                String::class->intent.putExtra(it.first,it.second.toString())
                Int::class->intent.putExtra(it.first,it.second as Int)
                Boolean::class->intent.putExtra(it.first,it.second as Boolean)
                Serializable::class->intent.putExtra(it.first,it.second as Serializable)
            }
        }
        startActivity(intent)
    }

    fun Activity.extraStr(name:String): String? {
        return intent.getStringExtra(name)
    }

    fun Activity.extraInt(pair:Pair<String,Int>): Int {
        return intent.getIntExtra(pair.first,pair.second)
    }

    fun Activity.extraBool(pair:Pair<String,Boolean>):Boolean{
        return intent.getBooleanExtra(pair.first,pair.second)
    }

    fun Activity.extraSerial(name:String): Serializable? {
        return intent.getSerializableExtra(name)
    }

    /**
     * 初始化侧滑菜单
     */
    fun initDrawer(drawerLayout: DrawerLayout, gravity:Int){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, gravity)
        drawerLayout.setDrawerListener(object : DrawerLayout.SimpleDrawerListener(){
            override fun onDrawerClosed(drawerView: View) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, gravity)
            }
        })
    }

    /**
     * 打开侧滑菜单
     */
    fun openDrawer(drawerLayout: DrawerLayout, gravity:Int){
        drawerLayout.openDrawer(gravity)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,gravity)
    }


}
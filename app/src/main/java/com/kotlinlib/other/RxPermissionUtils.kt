package com.kotlinlib.other

import android.app.Activity
import com.tbruyelle.rxpermissions2.RxPermissions


interface RxPermissionUtils {

    /**
     * 请求权限
     * @receiver Activity
     * @param yes ()->Unit 成功回调
     * @param no ()->Unit 失败回调
     * @param perm Array<out String> 权限可变数组
     */
    fun Activity.reqPermission(yes:()->Unit,no:()->Unit,vararg perm:String){
        var rx = RxPermissions(this)
//                .request(*perm)
                .request(*perm)
                .subscribe { granted ->
                    if (granted) {
                        yes.invoke()
                    } else {
                        no.invoke()
                    }
                }
    }

}
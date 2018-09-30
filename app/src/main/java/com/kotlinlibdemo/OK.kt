package com.kotlinlibdemo

import com.google.gson.Gson
import com.kotlinlib.other.StringUtils
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import java.lang.Exception

class NetUtils<T>: StringUtils {

    val gson = Gson()

    private val BaseUrl = "http://120.76.205.241:8000/news/toutiao"

    fun get(clazz: Class<T>, kw:String, pageToken:Int, onSuccess:(T)->Unit,
            onFailure:(String)->Unit){
        OkHttpUtils
                .get()
                .url(BaseUrl)
                .addParams("kw", kw)
                .addParams("pageToken", pageToken.toString())
                .addParams("apikey", "JHQm5BKMIG3iqzJodxYvdbdcgz7o9nDbWcNNmXJWZbjRKzfKAuIzZAVZ7ZuRhsbg")
                .build()
                .execute(object : StringCallback() {
                    override fun onError(call: Call?, e: Exception?, id: Int) {
                        onFailure.invoke(e?.message!!)
                        "网络请求错误：${e.message}".logE("NETUTILS")
                    }

                    override fun onResponse(response: String?, id: Int) {
                        "请求成功：$response".logD("NETUTILS")
                        val res = gson.fromJson<T>(response,clazz)
                        "解析结果:${gson.toJson(res)}".logD("NETUTILS")
                        onSuccess.invoke(res)
                    }
                })
    }

}
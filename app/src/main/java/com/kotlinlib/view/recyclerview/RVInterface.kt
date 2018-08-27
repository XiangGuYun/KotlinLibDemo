package com.kotlinlib.view.recyclerview

import com.kotlinlib.other.Holder
import android.text.Html
import android.widget.TextView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout

interface RVInterface {
    /**
     * 设置适配器
     * @receiver RVUtils
     * @param data ArrayList<T> 数据集合
     * @param fun1 (holder: EasyRVHolder, pos:Int)->Unit 绑定数据
     * @param itemId Int 列表项ID
     * @return RVUtils
     */
    fun <T> RVUtils.rvAdapter(data:ArrayList<T>,
                                                              fun1:(holder: Holder, pos:Int)->Unit,
                                                              itemId:Int): RVUtils {
        adapter(data, RVUtils.onBindData(fun1),
                RVUtils.SetMultiCellView { 0 },itemId)
        return this
    }

    /**
     * 设置多个列表项局部的适配器
     * @receiver RVUtils
     * @param data ArrayList<T>
     * @param fun1 (holder: com.kotlinlib.other.Holder, pos:Int)->Unit
     * @param fun2 (Int)->Int
     * @param itemId IntArray 传入可变长度的ID数组
     * @return RVUtils
     */
    fun <T> RVUtils.rvMultiAdapter(data:ArrayList<T>,
                                                                   fun1:(holder: Holder, pos:Int)->Unit,
                                                                   fun2:(pos:Int)->Int,
                                                                   vararg itemId:Int): RVUtils {
        adapter(data, RVUtils.onBindData(fun1),
                RVUtils.SetMultiCellView(fun2),*itemId)
        return this
    }

    fun Holder.htmlText(id:Int, html:String){
        getView<TextView>(id).text = Html.fromHtml(html)
    }

    fun RVUtils.pageConfig(currentPage:Int, pageCount:Int): RVUtils {
        this.currentPage = currentPage
        this.pageCount = pageCount
        return this
    }

    fun RVUtils.enableRefresh(refresh:SmartRefreshLayout, onRefresh:(RefreshLayout)->Unit,
                                                              onAddMore:(RefreshLayout)->Unit){
        refreshLayout = refresh
        refreshLayout.setOnRefreshListener {
            onRefresh.invoke(it)
        }
        refreshLayout.setOnLoadMoreListener {
            onRefresh.invoke(it)
        }
    }

    fun RVUtils.refresh(newList:List<*>, finish: () -> Unit){
        currentPage = 1
        dataList.clear()
        dataList = newList
        adapter.notifyDataSetChanged()
        finish.invoke()
    }

    fun RVUtils.checkMore(noMore:()->Unit, reqMore:(page:Int)->Unit){
        if(currentPage+1>pageCount){
            noMore.invoke()
            return
        }
        reqMore.invoke(++currentPage)
    }

    fun RVUtils.addMore(newData:List<*>, finish:()->Unit){
        dataList.addAll(newData)
        adapter.notifyDataSetChanged()
        refreshLayout.finishLoadMore()
        finish.invoke()
    }

}
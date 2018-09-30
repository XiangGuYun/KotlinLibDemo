package com.kotlinlibdemo

import android.os.Bundle
import android.os.Message
import android.view.View
import com.kotlinlib.other.LayoutId
import com.kotlinlib.other.TimeUtil
import com.kotlinlib.view.KotlinFragment
import com.kotlinlib.view.recyclerview.RVUtils
import com.kotlinlibdemo.R.id.rvNews
import kotlinx.android.synthetic.main.fragment_tou_tiao.*
import org.greenrobot.eventbus.EventBus
import java.util.*

@LayoutId(R.layout.fragment_tou_tiao)
class TouTiaoFragment:KotlinFragment() {

    var pageToken = 0
    var hasNext = false

    override fun init(view: View?) {

        reqNews()//初次请求

    }

    //如果在Fragment中控件发生空指针异常，则可以将控件放到onActivityCreated中
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refreshLayout.setOnRefreshListener {
            reqNews(true,false)//刷新
        }

        refreshLayout.setOnLoadMoreListener {
            reqNews(false,true)//加载
        }
    }

    private lateinit var data: ArrayList<TouTiaoNews.DataBean>

    fun reqNews(isRefresh:Boolean=false, isLoadMore:Boolean=false){
        if(isRefresh){
            pageToken = 0
        }else if(isLoadMore){
            if(hasNext){
                pageToken++
            }else{
                "没有更多数据了".toast()
                return
            }
        }
        EventBus.getDefault().post(Message.obtain().setWhat(SHOW_PROGRESSING_DIALOG))
        //请求网络数据
        NetUtils<TouTiaoNews>().get(TouTiaoNews::class.java,
                KEYWORD, pageToken, {
            EventBus.getDefault().post(Message.obtain().setWhat(HIDE_PROGRESSING_DIALOG))
            hasNext = it.isHasNext
            if(!isRefresh&&!isLoadMore){
                data = ArrayList(it.data)
                //显示新闻列表
                RVUtils(rvNews).rvMultiAdapter(data, {
                    holder, pos ->
                    holder.getItemView().click {
                        activity!!.go(NewsDetailActivity::class.java, WEB_URL to data[pos].url)
                    }
                    holder.setText(R.id.tvTitle, data[pos].title)//标题
                    holder.setText(R.id.tvSource, data[pos].posterScreenName)//来源
                    holder.setText(R.id.tvPublishTime, TimeUtil.getTimeFormatText(Date(data[pos].publishDate.toLong()*1000),
                            "yyyy-MM-dd"))//来源
                    //插图
                    when(data[pos].imageUrls.size){
                        1-> showBmp(data[pos].imageUrls[0], holder.getView(R.id.iv1))
                        else -> {
                            showBmp(data[pos].imageUrls[0], holder.getView(R.id.iv1))
                            showBmp(data[pos].imageUrls[1], holder.getView(R.id.iv2))
                            showBmp(data[pos].imageUrls[2], holder.getView(R.id.iv3))
                        }
                    }
                },{
                    //根据返回的imgUrl数量来决定采用哪种布局
                    when(data[it].imageUrls.size){
                        1->0
                        else -> 1
                    }
                },R.layout.item_news1,//单插图布局
                        R.layout.item_news3//三插图布局
                )
            }else if(isRefresh){
                data = ArrayList(it.data)
                rvNews.update()
                refreshLayout.finishRefresh()
                "刷新成功".toast()
            }else{
                data.addAll(ArrayList(it.data))
                rvNews.update()
                refreshLayout.finishLoadMore()
                "加载成功".toast()
            }
        }, {

        })
    }
}
package com.kotlinlibdemo

import android.os.Bundle
import android.os.Message
import android.support.design.widget.TabLayout
import com.kotlinlib.activity.KotlinActivity
import com.kotlinlib.fragment.FragPagerUtils
import com.kotlinlib.other.LayoutId
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@LayoutId(R.layout.activity_main)
class MainActivity : KotlinActivity() {

    @Subscribe
    fun handleEvent(msg:Message){
        when(msg.what){
            SHOW_PROGRESSING_DIALOG->{
                ring.show()
                ring.startAnim()
            }
            HIDE_PROGRESSING_DIALOG->{
                ring.stopAnim()
                ring.hide()
            }
        }
    }

    override fun init(bundle: Bundle?) {
        EventBus.getDefault().register(this)
        FragPagerUtils<TouTiaoFragment>(this, vpTouTiao, listOf(TouTiaoFragment()))
                .addTabLayout(tlTouTiao, true, false) { tab, index -> tab.text = "item${index + 1}" }
        ring.setViewColor("#666666".color())
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}

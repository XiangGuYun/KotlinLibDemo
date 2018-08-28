package com.kotlinlibdemo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.kotlinlib.activity.KotlinActivity
import com.kotlinlib.other.LayoutId
import com.kotlinlib.other.TV
import com.kotlinlib.view.recyclerview.RVUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@LayoutId(R.layout.activity_main)
class MainActivity : KotlinActivity() {

    override fun init(bundle: Bundle?) {
        val list = ArrayList<String>()
        for (i in 1..100){
            list.add("ITEM"+(i+1))
        }
        val rvUtils = RVUtils(rv)
        val heights = ArrayList<Int>()
        for (i in 1..100){
            heights.add((100 + Math.random() * 300).toInt())
        }
        rvUtils.staggerManager(4,true)
                .animator(null)
                .enableDraggableItem(list,true)
                .rvAdapter(list, { holder, pos ->
                    val tv = holder.getView<TV>(R.id.tv_cell)
                    val lp = tv.layoutParams
                    lp.height = heights[pos]
                    holder.setText(R.id.tv_cell, list[pos])
                    holder.getItemView().click {
                        rvUtils.doDelete(pos, list.size)
                    }
                    holder.getItemView().setOnLongClickListener {
                        if(pos!=0){
                            rvUtils.getmItemTouchHelper().startDrag(holder)
                        }
                        return@setOnLongClickListener true
                    }
                    tv.setBackgroundColor("#${getRandColorCode()}".color())
                },R.layout.cell)
    }



}

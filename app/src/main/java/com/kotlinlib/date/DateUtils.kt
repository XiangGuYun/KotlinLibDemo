package com.kotlinlib.date

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

interface DateUtils {

     val days31:ArrayList<Int> get() = arrayListOf(1, 3, 5, 7, 8, 10, 12)

    /**
     * 获取日期的星期索引
     * @param date
     * @return
     */
    fun getWeekIndex(date: Date): Int {
        val weekDays = intArrayOf(0, 1, 2, 3, 4, 5, 6)
        val cal = Calendar.getInstance()
        cal.time = date
        var w = cal.get(Calendar.DAY_OF_WEEK) - 1
        if (w < 0)
            w = 0
        return weekDays[w]
    }


    /**
     * EEEE：星期
     * EEE：周
     * @param date
     * @return
     */
    fun getWeekDay(date: Date): String {
        val dateFm = SimpleDateFormat("EEE")
        return dateFm.format(date)
    }

    /**
     * 判断是否是闰年
     * @param year
     * @return
     */
    fun isRunYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    /**
     * 获取一个月有多少天
     * @param month
     * @param year
     * @return
     */
    fun getMonthDays(month: Int, year: Int): Int {
        return if (month == 2) {
            if (isRunYear(year)) 29 else 28
        } else if (days31.contains(month)) {
            31
        } else {
            30
        }
    }

    /**
     * 获取当前月份是第几季度
     * @param currMonth Int
     * @return Int
     */
    fun getSeason(currMonth: Int): Int {
        if (currMonth == 1 || currMonth == 2 || currMonth == 3)
            return 1
        if (currMonth == 4 || currMonth == 5 || currMonth == 6)
            return 2
        if (currMonth == 7 || currMonth == 8 || currMonth == 9)
            return 3
        return if (currMonth == 10 || currMonth == 11 || currMonth == 12) 4 else 1
    }
}
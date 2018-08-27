package com.kotlinlib.other

import com.kotlinlib.activity.ActivityUtils
import com.kotlinlib.activity.ContextUtils
import com.kotlinlib.bitmap.BmpUtils
import com.kotlinlib.listener.OnPageChange
import com.kotlinlib.listener.OnTabSelected
import com.kotlinlib.net.NetUtils
import com.kotlinlib.persistence.SPUtils
import com.kotlinlib.transfer.IOUtils
import com.kotlinlib.transfer.MessageUtils
import com.kotlinlib.view.base.ViewUtils
import com.kotlinlib.view.recyclerview.RVInterface
import com.kotlinlib.view.textview.TextViewUtils

interface BaseInterface: SPUtils, TextViewUtils, StringUtils,
        DensityUtils, ViewUtils, BmpUtils, RxPermissionUtils, NetUtils,
        RVInterface, IOUtils, ContextUtils, MessageUtils, OnPageChange, OnTabSelected, ActivityUtils
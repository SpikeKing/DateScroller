package org.wangchenlong.datescroller.widget.config;


import android.support.annotation.ColorRes;

import org.wangchenlong.datescroller.widget.data.Type;
import org.wangchenlong.datescroller.widget.data.WheelCalendar;
import org.wangchenlong.datescroller.widget.listener.OnDateSetListener;

import static org.wangchenlong.datescroller.widget.config.DefaultConfig.MAX_LINE;

/**
 * 滚动配置
 */
public class ScrollerConfig {
    public Type mType = DefaultConfig.TYPE;
    @ColorRes public int mToolbarBkgColor = DefaultConfig.TOOLBAR_BKG_COLOR; // 背景颜色
    @ColorRes public int mItemSelectorLine = DefaultConfig.ITEM_SELECTOR_LINE; // 选中线颜色
    @ColorRes public int mItemSelectorRect = DefaultConfig.ITEM_SELECTOR_RECT; // 选中框颜色

    public String mCancelString = DefaultConfig.CANCEL; // 取消
    public String mSureString = DefaultConfig.SURE; // 确认
    public String mTitleString = DefaultConfig.TITLE; // 标题
    public int mToolBarTVColor = DefaultConfig.TOOLBAR_TV_COLOR; // ToolBar的颜色

    public int mWheelTVNormalColor = DefaultConfig.TV_NORMAL_COLOR; // 滚轮默认颜色
    public int mWheelTVSelectorColor = DefaultConfig.TV_SELECTOR_COLOR; // 滚轮选中颜色
    public int mWheelTVSize = DefaultConfig.TV_SIZE; // 文字默认大小
    public boolean cyclic = DefaultConfig.CYCLIC; // 是否循环

    public String mYear = DefaultConfig.YEAR; // 年单位
    public String mMonth = DefaultConfig.MONTH; // 月单位
    public String mDay = DefaultConfig.DAY; // 日单位
    public String mHour = DefaultConfig.HOUR; // 小时单位
    public String mMinute = DefaultConfig.MINUTE; // 分钟单位

    public WheelCalendar mMinCalendar = new WheelCalendar(0); // 最小日期
    public WheelCalendar mMaxCalendar = new WheelCalendar(0); // 最大日期
    public WheelCalendar mCurCalendar = new WheelCalendar(System.currentTimeMillis()); // 当前日期

    public OnDateSetListener mCallback; // 回调

    public int mMaxLines = MAX_LINE; // 最大行数, 依据控件样式
}

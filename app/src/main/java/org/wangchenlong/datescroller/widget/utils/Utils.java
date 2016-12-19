package org.wangchenlong.datescroller.widget.utils;

import android.content.Context;
import android.view.View;

import org.wangchenlong.datescroller.widget.data.WheelCalendar;


/**
 * 工具类
 *
 * @author C.L.Wang
 */
public class Utils {

    /**
     * 判断时间是否相等
     *
     * @param calendar 日期
     * @param params   参数
     * @return 时间是否相等
     */
    public static boolean isTimeEquals(WheelCalendar calendar, int... params) {
        switch (params.length) {
            case 1:
                return calendar.year == params[0];
            case 2:
                return calendar.year == params[0] &&
                        calendar.month == params[1];
            case 3:
                return calendar.year == params[0] &&
                        calendar.month == params[1] &&
                        calendar.day == params[2];
            case 4:
                return calendar.year == params[0] &&
                        calendar.month == params[1] &&
                        calendar.day == params[2] &&
                        calendar.hour == params[3];
        }
        return false;
    }

    /**
     * 全部隐藏视图
     *
     * @param views 视图
     */
    public static void hideViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static int px2dp(Context context, float pxValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }
}

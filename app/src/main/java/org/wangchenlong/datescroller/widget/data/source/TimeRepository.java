package org.wangchenlong.datescroller.widget.data.source;


import org.wangchenlong.datescroller.widget.config.ScrollerConfig;
import org.wangchenlong.datescroller.widget.data.WheelCalendar;
import org.wangchenlong.datescroller.widget.utils.DateConstants;
import org.wangchenlong.datescroller.widget.utils.Utils;

import java.util.Calendar;

/**
 * 时间数据集合, 代理WheelCalendar和DateConstants
 *
 * @author C.L. Wang
 */
public class TimeRepository implements TimeDataSource {
    private ScrollerConfig mScrollerConfig;

    private WheelCalendar mCalendarMin, mCalendarMax;

    private boolean mIsMinNoRange, mIsMaxNoRange;

    public TimeRepository(ScrollerConfig scrollerConfig) {
        mScrollerConfig = scrollerConfig;
        mCalendarMin = scrollerConfig.mMinCalendar;
        mCalendarMax = scrollerConfig.mMaxCalendar;

        mIsMinNoRange = mCalendarMin.isNoRange();
        mIsMaxNoRange = mCalendarMax.isNoRange();
    }

    @Override
    public int getMinYear() {
        if (mIsMinNoRange)
            return DateConstants.DEFAULT_MIN_YEAR;
        else
            return mCalendarMin.year;
    }

    @Override
    public int getMaxYear() {
        if (mIsMaxNoRange)
            return getMinYear() + DateConstants.YEAR_COUNT;

        return mCalendarMax.year;
    }

    @Override
    public int getMinMonth(int year) {
        if (!mIsMinNoRange && Utils.isTimeEquals(mCalendarMin, year))
            return mCalendarMin.month;

        return DateConstants.MIN_MONTH;
    }

    @Override
    public int getMaxMonth(int year) {
        if (!mIsMaxNoRange && Utils.isTimeEquals(mCalendarMax, year))
            return mCalendarMax.month;

        return DateConstants.MAX_MONTH;
    }

    @Override
    public int getMinDay(int year, int month) {
        if (!mIsMinNoRange && Utils.isTimeEquals(mCalendarMin, year, month))
            return mCalendarMin.day;

        return DateConstants.MIN_DAY;
    }

    @Override
    public int getMaxDay(int year, int month) {
        if (!mIsMaxNoRange && Utils.isTimeEquals(mCalendarMax, year, month))
            return mCalendarMax.day;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, month - 1);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getMinHour(int year, int month, int day) {
        if (!mIsMinNoRange && Utils.isTimeEquals(mCalendarMin, year, month, day))
            return mCalendarMin.hour;
        else
            return DateConstants.MIN_HOUR;
    }

    @Override
    public int getMaxHour(int year, int month, int day) {
        if (!mIsMaxNoRange && Utils.isTimeEquals(mCalendarMax, year, month, day))
            return mCalendarMax.hour;
        else
            return DateConstants.MAX_HOUR;
    }

    @Override
    public int getMinMinute(int year, int month, int day, int hour) {
        if (!mIsMinNoRange && Utils.isTimeEquals(mCalendarMin, year, month, day, hour))
            return mCalendarMin.minute + 1;
        else
            return DateConstants.MIN_MINUTE;
    }

    @Override
    public int getMaxMinute(int year, int month, int day, int hour) {
        if (!mIsMaxNoRange && Utils.isTimeEquals(mCalendarMax, year, month, day, hour))
            return mCalendarMax.minute;

        return DateConstants.MAX_MINUTE;
    }

    @Override
    public boolean isMinYear(int year) {
        return Utils.isTimeEquals(mCalendarMin, year);
    }

    @Override
    public boolean isMinMonth(int year, int month) {
        return Utils.isTimeEquals(mCalendarMin, year, month);
    }

    @Override
    public boolean isMinDay(int year, int month, int day) {
        return Utils.isTimeEquals(mCalendarMin, year, month, day);
    }

    @Override
    public boolean isMinHour(int year, int month, int day, int hour) {
        return Utils.isTimeEquals(mCalendarMin, year, month, day, hour);
    }


    @Override
    public WheelCalendar getDefaultCalendar() {
        return mScrollerConfig.mCurCalendar;
    }
}

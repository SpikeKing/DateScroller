package org.wangchenlong.datescroller.widget;

import android.content.Context;
import android.view.View;

import org.wangchenlong.datescroller.R;
import org.wangchenlong.datescroller.widget.adapters.NumericWheelAdapter;
import org.wangchenlong.datescroller.widget.config.ScrollerConfig;
import org.wangchenlong.datescroller.widget.data.source.TimeRepository;
import org.wangchenlong.datescroller.widget.utils.DateConstants;
import org.wangchenlong.datescroller.widget.utils.Utils;
import org.wangchenlong.datescroller.widget.wheel.OnWheelChangedListener;
import org.wangchenlong.datescroller.widget.wheel.WheelView;

import java.util.Calendar;

/**
 * 时间滚轮, 用于控制时间数据
 */
class TimeWheel {
    private Context mContext;

    private WheelView mYearView, mMonthView, mDayView, mHourView, mMinuteView; // 滚动视图
    private NumericWheelAdapter mYearAdapter, mMonthAdapter, mDayAdapter, mHourAdapter, mMinuteAdapter;

    private ScrollerConfig mScrollerConfig;
    private TimeRepository mRepository;

    private OnWheelChangedListener mYearListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            updateMonths();
        }
    };
    private OnWheelChangedListener mMonthListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            updateDays();
        }
    };
    private OnWheelChangedListener mDayListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            updateHours();
        }
    };
    private OnWheelChangedListener mHourListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            updateMinutes();
        }
    };

    /**
     * 设置视图与参数
     *
     * @param view           视图
     * @param scrollerConfig 滚动参数
     */
    TimeWheel(View view, ScrollerConfig scrollerConfig) {
        mScrollerConfig = scrollerConfig;
        mRepository = new TimeRepository(scrollerConfig);
        mContext = view.getContext();
        initialize(view);
    }

    /**
     * 初始化与设置视图
     *
     * @param view 视图
     */
    private void initialize(View view) {
        initView(view); // 初始化视图

        initYearView();
        initMonthView();
        initDayView();
        initHourView();
        initMinuteView();
    }

    /**
     * 初始化视图
     *
     * @param view 视图
     */
    private void initView(View view) {
        mYearView = (WheelView) view.findViewById(R.id.year);
        mMonthView = (WheelView) view.findViewById(R.id.month);
        mDayView = (WheelView) view.findViewById(R.id.day);
        mHourView = (WheelView) view.findViewById(R.id.hour);
        mMinuteView = (WheelView) view.findViewById(R.id.minute);

        // 根据类型, 设置隐藏位置
        switch (mScrollerConfig.mType) {
            case ALL:
                break;
            case YEAR_MONTH_DAY:
                Utils.hideViews(mHourView, mMinuteView);
                break;
            case YEAR_MONTH:
                Utils.hideViews(mDayView, mHourView, mMinuteView);
                break;
            case MONTH_DAY_HOUR_MIN:
                Utils.hideViews(mYearView);
                break;
            case HOURS_MINS:
                Utils.hideViews(mYearView, mMonthView, mDayView);
                break;
            case YEAR:
                Utils.hideViews(mMonthView, mDayView, mHourView, mMinuteView);
                break;
        }

        mYearView.addChangingListener(mYearListener);
        mYearView.addChangingListener(mMonthListener);
        mYearView.addChangingListener(mDayListener);
        mYearView.addChangingListener(mHourListener);

        mMonthView.addChangingListener(mMonthListener);
        mMonthView.addChangingListener(mDayListener);
        mMonthView.addChangingListener(mHourListener);

        mDayView.addChangingListener(mDayListener);
        mDayView.addChangingListener(mHourListener);

        mHourView.addChangingListener(mHourListener);
    }

    /**
     * 初始化年视图, 年是最高级
     */
    private void initYearView() {
        int minYear = mRepository.getMinYear();
        int maxYear = mRepository.getMaxYear();

        mYearAdapter = new NumericWheelAdapter(mContext, minYear, maxYear,
                DateConstants.FORMAT, mScrollerConfig.mYear);
        mYearAdapter.setConfig(mScrollerConfig);
        mYearView.setViewAdapter(mYearAdapter);
        mYearView.setCurrentItem(mRepository.getDefaultCalendar().year - minYear);
    }

    /**
     * 初始化月视图
     */
    private void initMonthView() {
        updateMonths();
        int curYear = getCurrentYear();
        int minMonth = mRepository.getMinMonth(curYear);
        mMonthView.setCurrentItem(mRepository.getDefaultCalendar().month - minMonth);
    }

    /**
     * 初始化日视图
     */
    private void initDayView() {
        updateDays();
        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();

        int minDay = mRepository.getMinDay(curYear, curMonth);
        mDayView.setCurrentItem(mRepository.getDefaultCalendar().day - minDay);
    }

    /**
     * 初始化小时视图
     */
    private void initHourView() {
        updateHours();
        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();
        int curDay = getCurrentDay();

        int minHour = mRepository.getMinHour(curYear, curMonth, curDay);
        mHourView.setCurrentItem(mRepository.getDefaultCalendar().hour - minHour);
    }

    /**
     * 初始化分钟视图
     */
    private void initMinuteView() {
        updateMinutes();
        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();
        int curDay = getCurrentDay();
        int curHour = getCurrentHour();
        int minMinute = mRepository.getMinMinute(curYear, curMonth, curDay, curHour);

        mMinuteView.setCurrentItem(mRepository.getDefaultCalendar().minute - minMinute);
    }

    /**
     * 更新月份
     */
    private void updateMonths() {
        if (mMonthView.getVisibility() == View.GONE)
            return;

        int curYear = getCurrentYear();
        int minMonth = mRepository.getMinMonth(curYear);
        int maxMonth = mRepository.getMaxMonth(curYear);
        mMonthAdapter = new NumericWheelAdapter(mContext, minMonth, maxMonth,
                DateConstants.FORMAT, mScrollerConfig.mMonth);
        mMonthAdapter.setConfig(mScrollerConfig);
        mMonthView.setViewAdapter(mMonthAdapter);
        mMonthView.setCyclic(mScrollerConfig.cyclic);

        // 当滚动数量不足时, 需要避免循环
        if (maxMonth - minMonth < mScrollerConfig.mMaxLines) {
            mMonthView.setCyclic(false);
        }

        if (mRepository.isMinYear(curYear)) {
            mMonthView.setCurrentItem(0, false);
        }
    }

    /**
     * 更新日
     */
    private void updateDays() {
        if (mDayView.getVisibility() == View.GONE)
            return;

        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + mYearView.getCurrentItem());
        calendar.set(Calendar.MONTH, curMonth);

        int maxDay = mRepository.getMaxDay(curYear, curMonth);
        int minDay = mRepository.getMinDay(curYear, curMonth);
        mDayAdapter = new NumericWheelAdapter(mContext, minDay, maxDay, DateConstants.FORMAT, mScrollerConfig.mDay);
        mDayAdapter.setConfig(mScrollerConfig);
        mDayView.setViewAdapter(mDayAdapter);
        mDayView.setCyclic(mScrollerConfig.cyclic); // 是否循环

        // 当滚动数量不足时, 需要避免循环
        if (maxDay - minDay < mScrollerConfig.mMaxLines) {
            mDayView.setCyclic(false);
        }

        if (mRepository.isMinMonth(curYear, curMonth)) {
            mDayView.setCurrentItem(0, true);
        }

        int dayCount = mDayAdapter.getItemsCount();
        if (mDayView.getCurrentItem() >= dayCount) {
            mDayView.setCurrentItem(dayCount - 1, true);
        }
    }

    /**
     * 更新小时
     */
    private void updateHours() {
        if (mHourView.getVisibility() == View.GONE)
            return;

        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();
        int curDay = getCurrentDay();

        int minHour = mRepository.getMinHour(curYear, curMonth, curDay);
        int maxHour = mRepository.getMaxHour(curYear, curMonth, curDay);

        mHourAdapter = new NumericWheelAdapter(mContext, minHour, maxHour, DateConstants.FORMAT, mScrollerConfig.mHour);
        mHourAdapter.setConfig(mScrollerConfig);
        mHourView.setViewAdapter(mHourAdapter);
        mHourView.setCyclic(mScrollerConfig.cyclic);

        // 当滚动数量不足时, 需要避免循环
        if (maxHour - minHour < mScrollerConfig.mMaxLines) {
            mHourView.setCyclic(false);
        }

        if (mRepository.isMinDay(curYear, curMonth, curDay))
            mHourView.setCurrentItem(0, false);
    }

    /**
     * 更新分钟
     */
    private void updateMinutes() {
        if (mMinuteView.getVisibility() == View.GONE)
            return;

        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();
        int curDay = getCurrentDay();
        int curHour = getCurrentHour();

        int minMinute = mRepository.getMinMinute(curYear, curMonth, curDay, curHour);
        int maxMinute = mRepository.getMaxMinute(curYear, curMonth, curDay, curHour);

        mMinuteAdapter = new NumericWheelAdapter(mContext, minMinute, maxMinute, DateConstants.FORMAT, mScrollerConfig.mMinute);
        mMinuteAdapter.setConfig(mScrollerConfig);
        mMinuteView.setViewAdapter(mMinuteAdapter);
        mMinuteView.setCyclic(mScrollerConfig.cyclic);

        // 当滚动数量不足时, 需要避免循环
        if (maxMinute - minMinute < mScrollerConfig.mMaxLines) {
            mMinuteView.setCyclic(false);
        }

        if (mRepository.isMinHour(curYear, curMonth, curDay, curHour))
            mMinuteView.setCurrentItem(0, false);
    }

    int getCurrentYear() {
        return mYearView.getCurrentItem() + mRepository.getMinYear();
    }

    int getCurrentMonth() {
        int curYear = getCurrentYear();
        return mMonthView.getCurrentItem() + +mRepository.getMinMonth(curYear);
    }

    int getCurrentDay() {
        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();
        return mDayView.getCurrentItem() + mRepository.getMinDay(curYear, curMonth);
    }

    int getCurrentHour() {
        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();
        int curDay = getCurrentDay();
        return mHourView.getCurrentItem() + mRepository.getMinHour(curYear, curMonth, curDay);
    }

    int getCurrentMinute() {
        int curYear = getCurrentYear();
        int curMonth = getCurrentMonth();
        int curDay = getCurrentDay();
        int curHour = getCurrentHour();

        return mMinuteView.getCurrentItem() + mRepository.getMinMinute(curYear, curMonth, curDay, curHour);
    }
}

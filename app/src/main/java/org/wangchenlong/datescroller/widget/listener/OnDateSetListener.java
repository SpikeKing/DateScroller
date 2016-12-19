package org.wangchenlong.datescroller.widget.listener;


import org.wangchenlong.datescroller.widget.DateScrollerDialog;

/**
 * 日期设置的监听器
 *
 * @author C.L. Wang
 */
public interface OnDateSetListener {
    void onDateSet(DateScrollerDialog timePickerView, long milliseconds);
}

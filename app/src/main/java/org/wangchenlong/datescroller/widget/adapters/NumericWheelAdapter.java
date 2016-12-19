/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wangchenlong.datescroller.widget.adapters;

import android.content.Context;
import android.text.TextUtils;

import org.wangchenlong.datescroller.R;

/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapter extends AbstractWheelTextAdapter {

    private static final int DEFAULT_MAX_VALUE = 9; // 默认的数字最大值
    private static final int DEFAULT_MIN_VALUE = 0; // 默认的数字最小值

    // Values
    private int minValue;
    private int maxValue;

    private String format; // format
    private String unit; //unit

    /**
     * 构造器
     *
     * @param context 当前上下文
     */
    public NumericWheelAdapter(Context context) {
        this(context, DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }


    /**
     * 构造器
     *
     * @param context  上下文
     * @param minValue 最小值
     * @param maxValue 最大值
     */
    public NumericWheelAdapter(Context context, int minValue, int maxValue) {
        this(context, minValue, maxValue, null);
    }

    /**
     * 构造器
     *
     * @param context  上下文
     * @param minValue 最小值
     * @param maxValue 最大值
     * @param format   格式
     */
    public NumericWheelAdapter(Context context, int minValue, int maxValue, String format) {
        this(context, minValue, maxValue, format, null);
    }

    /**
     * 构造器
     *
     * @param context  上下文
     * @param minValue 最小值
     * @param maxValue 最大值
     * @param format   格式化
     * @param unit     滚轮单位
     */
    public NumericWheelAdapter(Context context, int minValue, int maxValue, String format, String unit) {
        super(context);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.format = format;
        this.unit = unit;
        this.itemResourceId = R.layout.tv_item; // 默认样式
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            String text = !TextUtils.isEmpty(format) ? String.format(format, value) : Integer.toString(value);
            text = TextUtils.isEmpty(unit) ? text : text + unit;

            return text;
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }
}

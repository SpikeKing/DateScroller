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

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * 抽象滚轮适配器
 */
abstract class AbstractWheelAdapter implements WheelViewAdapter {
    private List<DataSetObserver> mDatasetObservers; // 数据集观察者

    @Override
    public View getEmptyItem(View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        if (mDatasetObservers == null) {
            mDatasetObservers = new LinkedList<>();
        }
        mDatasetObservers.add(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (mDatasetObservers != null) {
            mDatasetObservers.remove(observer);
        }
    }

    /**
     * 通知观察者数据改变
     */
    protected void notifyDataChangedEvent() {
        if (mDatasetObservers != null) {
            for (DataSetObserver observer : mDatasetObservers) {
                observer.onChanged();
            }
        }
    }

    /**
     * 通知观察者无效数据
     */
    protected void notifyDataInvalidatedEvent() {
        if (mDatasetObservers != null) {
            for (DataSetObserver observer : mDatasetObservers) {
                observer.onInvalidated();
            }
        }
    }

}

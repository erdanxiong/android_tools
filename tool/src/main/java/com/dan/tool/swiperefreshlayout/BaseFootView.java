package com.dan.tool.swiperefreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
/**
 * Created by zj on 2017/9/9
 * 最基础的footview 页面
 */
public abstract class BaseFootView extends FrameLayout implements FootViewListener {
    public BaseFootView(Context context) {
        super(context);
    }

    public BaseFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseFootView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }
}

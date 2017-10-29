package com.dan.tool.swiperefreshlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan.tool.R;


/**
 * Created by zj on 2017/9/9.
 */
public class RecyclerFootView extends BaseFootView {
    private LayoutInflater layoutInflater ;
    //footView 内容
    private View footView;

    public RecyclerFootView(Context context) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
        footView = layoutInflater.inflate(R.layout.footview,null);
        //设置子view在父布局中的位置
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //放入父布局
        addView(footView);
    }

    //加载更多时进度条显示，提示文字显示
    @Override
    public void upLoadMore() {
        footView.setVisibility(View.VISIBLE);
    }

    //加载更多时进度条,文字都不显示
    @Override
    public void downRefresh() {
        footView.setVisibility(View.GONE);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }
}

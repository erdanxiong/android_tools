package com.dan.tool.swiperefreshlayout;
/**
 * Created by zj on 2017/9/9.
 *  用来控制底部view的显示和隐藏
 */
public interface FootViewListener {

    //上拉加载时footView 显示
    public void upLoadMore();

    //下拉刷新时隐藏
    public void downRefresh();
}

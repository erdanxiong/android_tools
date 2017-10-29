package com.dan.tool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.dan.tool.R;
import com.dan.tool.swiperefreshlayout.SwipeRecycler;

import java.util.ArrayList;

/**
 * Created by zj on 2017/10/29.
 */
public class SwipeContentActivity extends Activity {
    int number = 0 ;
    SwipeRecycler  swipe;
    private ArrayList<String>  datas  = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipecontent);
        swipe = (SwipeRecycler) findViewById(R.id.swipe);
        init();
    }

    public void init(){
        init(++number);
        swipe.initView(R.layout.swiperecyclerview);
        swipe.setCustomLayout(R.layout.swipecontent_item);
        swipe.setFootViewBaseCount(15);
        //adapter 回传值
        swipe.setOnBindRecyclerViewData(new SwipeRecycler.OnBindRecyclerViewData() {
            @Override
            public void onBindViewData(SwipeRecycler.InnerAdapter.ViewHolder viewHolder, Object data) {
                    View view =viewHolder.itemView;
                    TextView textView = (TextView) view.findViewById(R.id.content_text);
                    textView.setText((String)data);
            }
        });
        //实现数据的加载
        swipe.setSwipeRecyclerRefreshListener(new SwipeRecycler.SwipeRecyclerRefreshListener() {
            @Override
            public void downRefresh() {
                swipe.addCustomerDatas(addData(number=1));
            }

            @Override
            public void upLoadMore() {
               swipe.loadData( addData(++number));
            }
        });
        swipe.setCustomerDatas(datas);
        LinearLayoutManager linearLayoutManager  =   new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        swipe.setLinearLayoutManager(linearLayoutManager);
        swipe.setAdapter();
    }

    public void init(int number ){
        int i = 0 ;
        for(i=0;i<20;i++){
            datas.add(""+i);
        }
    }

    public ArrayList<String> addData(int number){
        ArrayList<String>  list  = new ArrayList<String>();
        int i = number;
        for(i=(number-1)*20;i<number*20;i++){
            list.add(""+i);
        }
        return list;
    }
}

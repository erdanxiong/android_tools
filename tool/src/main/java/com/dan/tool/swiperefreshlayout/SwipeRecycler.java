package com.dan.tool.swiperefreshlayout;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.dan.tool.R;
import java.util.List;
/**
 * Created by zj on 2017/8/30.
 * SwipeRefreshLayout ， RecyclerView 组合体， 下拉有旋转按钮，上拉有加载更多的功能
 */
public class SwipeRecycler extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    private final static int FOOTVIEW= 1;

    private final static int NORMAL= 2;

    private FootViewHolder footViewHolder = null;

    private Handler handler;

    private LayoutInflater layoutInflater;

    //下拉刷新的头
    private SwipeRefreshLayout swipeRefreshLayout;

    //盛放数据的列表
    private RecyclerView recyclerView;

    //底部的footview
    private BaseFootView baseFootView;

    //是否开启上拉加载功能 默认是开启的
    public boolean upLoadMoreEnable = true;

    //swipeRefreshLayout  与 RecyclerView的结合体
    private View swipeRecycler = null;

    //用户自定义item布局
    private int customLayout;

    //SwipeRecycleView 滚动监听
    private SwipeRecyclerRefreshListener swipeRecyclerRefreshListener;

    //item回传绑定数据
    private OnBindRecyclerViewData onBindRecyclerViewData;

    //用户要填充的数据
    private List<Object> customerDatas;

    //footView 加载的基础数据   只有达到这个数据条数的时候才显示,默认是15条
    private int footViewBaseCount = 15;

    //布局管理
    private LinearLayoutManager linearLayoutManager;
    //适配器
    private InnerAdapter innerAdapter;

    public SwipeRecycler(Context context) {
        super(context);
    }

    public SwipeRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRecycler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 外面穿进来的布局
     * @param swipeRereshLayoutID
     */
    public void initView(int swipeRereshLayoutID) {
        baseFootView = new RecyclerFootView(getContext());

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 1) {
                    baseFootView.downRefresh();
                }
            }
        };
        layoutInflater = LayoutInflater.from(getContext());
        swipeRecycler = layoutInflater.inflate(swipeRereshLayoutID, null);
        //TODO 绑定组件
        swipeRefreshLayout = (SwipeRefreshLayout) swipeRecycler.findViewById(R.id.swipe);
        recyclerView = (RecyclerView) swipeRecycler.findViewById(R.id.recyclerView);
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        addView(swipeRecycler);
        //swipeRefreshLayout设置颜色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        setScrollListenerForRecycleView();
    }


    //为recycleView 设置滑动监听器
    public void setScrollListenerForRecycleView() {
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    baseFootView.upLoadMore();
                    int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (upLoadMoreEnable) {
                        if (lastVisiblePosition + 1== innerAdapter.getItemCount()) {
                            //TODO 上拉加载功能
                            swipeRecyclerRefreshListener.upLoadMore();
                        }
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }


    public void setBaseFootView(BaseFootView baseFootView) {
        this.baseFootView = baseFootView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public void setUpLoadMoreEnable(boolean upLoadMoreEnable) {
        this.upLoadMoreEnable = upLoadMoreEnable;
    }

    public void setCustomLayout(int customLayout) {
        this.customLayout = customLayout;
    }


    public void setSwipeRecyclerRefreshListener(SwipeRecyclerRefreshListener swipeRecyclerRefreshListener) {
        this.swipeRecyclerRefreshListener = swipeRecyclerRefreshListener;
    }

    public void setOnBindRecyclerViewData(OnBindRecyclerViewData onBindRecyclerViewData) {
        this.onBindRecyclerViewData = onBindRecyclerViewData;
    }

    public void setCustomerDatas(List<? extends Object> customerDatas) {
        this.customerDatas = (List<Object>) customerDatas;
    }


    public void addCustomerDatas(List<? extends Object> customerDatas) {
        this.customerDatas.clear();
        this.customerDatas.addAll(customerDatas);
        this.innerAdapter.notifyDataSetChanged();
    }


    public void setFootViewBaseCount(int footViewBaseCount) {
        this.footViewBaseCount = footViewBaseCount;
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        swipeRecyclerRefreshListener.downRefresh();
        swipeRefreshLayout.setRefreshing(false);
    }


    public interface SwipeRecyclerRefreshListener {
        //下拉刷新
        public void downRefresh();

        //上拉加载更多
        public void upLoadMore();
    }

    //RecylerView的adapter用来绑定用户自定义界面数据
    public interface OnBindRecyclerViewData {
        public void onBindViewData(InnerAdapter.ViewHolder viewHolder, Object data);
    }

    //当前RecyclerView 适配器
    public class InnerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public ViewHolder(View itemView) {
                super(itemView);
                this.view = itemView;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //添加footView
            if (FOOTVIEW==viewType) {
                footViewHolder = new FootViewHolder(baseFootView);
                return footViewHolder;
            }
            View customItemView = layoutInflater.inflate(customLayout, parent, false);
            return new ViewHolder(customItemView);
        }

        //绑定用户数据
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewHolder) {
                    Object data = customerDatas.get(position);
                    //通过钩子回传给view进行数据分发
                    onBindRecyclerViewData.onBindViewData((ViewHolder) holder, data);
            }
        }

        @Override
        public int getItemCount() {
            if (customerDatas == null) {
                throw new RuntimeException("用户自定数据为空");
            }
            if (upLoadMoreEnable && customerDatas.size() > footViewBaseCount) {
                return customerDatas.size() + 1;
            }
            return customerDatas.size();
        }

        @Override
        public int getItemViewType(int position) {
          if(getItemCount()>=footViewBaseCount){
              if(upLoadMoreEnable&&position==getItemCount()-1){
                  return FOOTVIEW ;
              }
          }
            return  NORMAL;
        }
    }

    //TODO 前端调用,进行适配
    public void setAdapter() {
        innerAdapter = new InnerAdapter();
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setAdapter(innerAdapter);
    }

    //TODO 返回footView 用来控制footView，主要目的是用来控制footView的显示和隐藏
    public BaseFootView callBackFootView() {
        return this.baseFootView;
    }

    //刷新数据
    public void loadData(List<? extends Object> data) {
        //加载完成后 boot 在 500毫秒后消失
        Message message = handler.obtainMessage();
        message.what = 1;
        this.customerDatas.addAll(data);
        innerAdapter.notifyDataSetChanged();
        handler.sendMessageDelayed(message, 500);
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }
}

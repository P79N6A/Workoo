package com.yc.love.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.yc.love.R;
import com.yc.love.adaper.rv.NoThingAdapter;
import com.yc.love.adaper.rv.holder.BaseViewHolder;
import com.yc.love.adaper.rv.holder.StringBeanViewHolder;
import com.yc.love.model.bean.StringBean;
import com.yc.love.ui.activity.base.BaseSameActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 入门秘籍
 */
public class LoveIntroductionActivity extends BaseSameActivity {

    private String mActivityTitle;
    private RecyclerView mRecyclerView;
    private NoThingAdapter<StringBean> mAdapter;
    private List<StringBean> mDatas;
    private int PAGE_NUM = 10;
    private boolean loadMoreEnd;
    private boolean loadDataEnd;
    private boolean showProgressBar = false;
    private int num = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_introduction);
        initViews();
        initDatas();
    }

    protected void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.love_introduction_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            StringBean stringBean = new StringBean("name " + i);
            mDatas.add(stringBean);
        }
        mAdapter = new NoThingAdapter<StringBean>(mDatas, mRecyclerView) {
            @Override
            public BaseViewHolder getHolder(ViewGroup parent) {
                return new StringBeanViewHolder(LoveIntroductionActivity.this, null, parent);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        if (mDatas.size() < PAGE_NUM) {
            Log.d("ssss", "loadMoreData: data---end");
        } else {
            mAdapter.setOnMoreDataLoadListener(new NoThingAdapter.OnLoadMoreDataListener() {
                @Override
                public void loadMoreData() {
                    if (loadDataEnd == false) {
                        return;
                    }
                    if (showProgressBar == false) {
                        //加入null值此时adapter会判断item的type
                        mDatas.add(null);
                        mAdapter.notifyDataSetChanged();
                        showProgressBar = true;
                    }
                    if (!loadMoreEnd) {
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                /*if (num >= 41) {
//                                    progressBarViewHolder.removePbChangDes("已加载全部数据");
                                    return;
                                }*/

                                List<StringBean> netLoadMoreData = new ArrayList<>();
                                for (int i = 0; i < 10; i++) {
                                    netLoadMoreData.add(new StringBean("name " + (i + num)));
                                }
                                num += 10;
                                showProgressBar = false;
                                mDatas.remove(mDatas.size() - 1);
                                mAdapter.notifyDataSetChanged();
                                if (netLoadMoreData.size() < PAGE_NUM) {
                                    loadMoreEnd = true;
                                }
                                mDatas.addAll(netLoadMoreData);
                                mAdapter.notifyDataSetChanged();
                                mAdapter.setLoaded();
                            }
                        }, 1000);
                    } else {
                        Log.d("mylog", "loadMoreData: loadMoreEnd end 已加载全部数据 ");
                        mDatas.remove(mDatas.size() - 1);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        loadDataEnd = true;
    }

    @Override
    protected void initIntentData() {
        Intent intent = getIntent();
        mActivityTitle = intent.getStringExtra("title");
    }

    public static void startLoveIntroductionActivity(Context context, String title) {
        Intent intent = new Intent(context, LoveIntroductionActivity.class);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected String offerActivityTitle() {
        return mActivityTitle;
    }
}

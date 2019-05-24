package com.yc.love.ui.frament.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mayn on 2019/4/24.
 */

public abstract class BaseLazyFragment extends BaseFragment {

    protected View rootView;
    private boolean isInitView = false;
    private boolean isVisible = false;

    @Override
    protected View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(setContentView(), container, false);
        initViews();
        isInitView = true;
        isCanLoadData();
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if (isVisibleToUser) {
            isVisible = true;
            isCanLoadData();
        } else {
            isVisible = false;
        }
    }

    private void isCanLoadData() {
        //所以条件是view初始化完成并且对用户可见
        if (isInitView && isVisible) {
            lazyLoad();

            //防止重复加载数据
            isInitView = false;
            isVisible = false;
        }
    }

    /**
     * 加载页面布局文件
     */
    protected abstract int setContentView();

    /**
     * 让布局中的view与fragment中的变量建立起映射
     */
    protected abstract void initViews();

    /**
     * 加载要显示的数据
     */
    protected abstract void lazyLoad();


}

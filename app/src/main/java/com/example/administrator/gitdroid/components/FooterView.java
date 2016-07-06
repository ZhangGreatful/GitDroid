package com.example.administrator.gitdroid.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class FooterView extends FrameLayout {

    //    代表视图的三种状态的静态常量值
    private static final int STATE_LOADING  = 0;
    private static final int STATE_COMPLETE = 1;
    private static final int STATE_ERROR    = 2;


    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_complete)
    TextView    tvComplete;
    @Bind(R.id.tv_error)
    TextView    tvError;

    //    设置默认状态为加载中
    private int state = STATE_LOADING;

    public FooterView(Context context) {
        this(context, null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.content_load_footer, this, true);
        init();

    }

    private void init() {
        ButterKnife.bind(this);
    }

    //    显示加载中
    public void showLoading() {
        state = STATE_LOADING;
        progressBar.setVisibility(VISIBLE);
        tvComplete.setVisibility(GONE);
        tvError.setVisibility(GONE);
    }

    //    显示加载完成
    public void showComplete() {
        state = STATE_COMPLETE;
        progressBar.setVisibility(GONE);
        tvComplete.setVisibility(VISIBLE);
        tvError.setVisibility(GONE);
    }

    //    显示加载错误
    public void showError(String message) {
        state = STATE_ERROR;
        progressBar.setVisibility(GONE);
        tvComplete.setVisibility(GONE);
        tvError.setVisibility(VISIBLE);
    }

    public boolean isLoading() {
        return state == STATE_LOADING;
    }

    public boolean isComplete() {
        return state == STATE_COMPLETE;
    }

    public void setErrorClickListener(OnClickListener onClickListener) {
        tvError.setOnClickListener(onClickListener);
    }
}

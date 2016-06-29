package com.example.administrator.gitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.administrator.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class Pager2 extends FrameLayout {
//    绑定视图id
    @Bind(R.id.ivBubble1)
    ImageView ivBubble1;
    @Bind(R.id.ivBubble2) ImageView ivBubble2;
    @Bind(R.id.ivBubble3) ImageView ivBubble3;
    public Pager2(Context context) {
        super(context);
        init();
    }

    public Pager2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2, this, true);
        ButterKnife.bind(this);
        ivBubble1.setVisibility(GONE);
        ivBubble2.setVisibility(GONE);
        ivBubble3.setVisibility(GONE);

    }
//    显示当前页面的三个图像控件进入动画效果,只会显示一次
    public void showAnimation(){
        if(ivBubble1.getVisibility()!=VISIBLE){
         postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivBubble1.setVisibility(VISIBLE);
                    YoYo.with(Techniques.FadeInLeft).duration(300).playOn(ivBubble1);
                }
            },50);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivBubble2.setVisibility(VISIBLE);
                    YoYo.with(Techniques.FadeInLeft).duration(300).playOn(ivBubble1);
                }
            },550);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivBubble3.setVisibility(VISIBLE);
                    YoYo.with(Techniques.FadeInLeft).duration(300).playOn(ivBubble1);
                }
            },1050);
        }


    }
}

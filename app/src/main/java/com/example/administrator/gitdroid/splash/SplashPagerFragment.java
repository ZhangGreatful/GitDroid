package com.example.administrator.gitdroid.splash;

import android.support.v4.app.Fragment;
import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.administrator.gitdroid.R;
import com.example.administrator.gitdroid.splash.pager.Pager2;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class SplashPagerFragment extends Fragment {

    //    绑定id
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @BindColor(R.color.colorGreen)
    int colorGreen;
    @BindColor(R.color.colorRed)
    int colorRed;
    @BindColor(R.color.colorYellow)
    int colorYellow;
    @Bind(R.id.content) FrameLayout framLayout;

    private SplashPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_pager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new SplashPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
//        给ViewPager设置监听
        viewPager.addOnPageChangeListener(pagerChangeListener);
        indicator.setViewPager(viewPager);

    }

    //      此监听器主要负责监听页面背景颜色的变化,和最后一个页面视图动画的显示
    private final ViewPager.OnPageChangeListener pagerChangeListener = new ViewPager.OnPageChangeListener() {
        //      ARGB取值器
        final ArgbEvaluator evaluator = new ArgbEvaluator();

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            第一个页面到第二个页面之间
            if (position == 0) {
//                第一个参数:    颜色绿色到红色的中间值[0-1]
//                第二个参数:    初始颜色
//                第三个参数:    结束颜色
                int color = (int) evaluator.evaluate(positionOffset, colorGreen, colorRed);
//                将颜色设置为FramLayout的背景颜色
                framLayout.setBackgroundColor(color);
                return;
            }
//            第二个页面到第三个页面之间
            if (position == 1) {
                int color = (int) evaluator.evaluate(positionOffset, colorRed, colorYellow);
                framLayout.setBackgroundColor(color);
                return;
            }

        }
//      当进入第三张页面时,添加动画
        @Override
        public void onPageSelected(int position) {
            if (position==2){
                Pager2 pager2= (Pager2) adapter.getView(position);
                pager2.showAnimation();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

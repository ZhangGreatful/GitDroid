package com.example.administrator.gitdroid.splash;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
    private static final String TAG = "SplashPagerFragment";

    //    绑定id
    @Bind(R.id.viewPager)
    ViewPager       viewPager;
    @Bind(R.id.indicator)
    CircleIndicator indicator;
    @BindColor(R.color.colorGreen)
    int             colorGreen;//VeiwPager页面对应的背景颜色
    @BindColor(R.color.colorRed)
    int             colorRed;//VeiwPager页面对应的背景颜色
    @BindColor(R.color.colorYellow)
    int             colorYellow;//VeiwPager页面对应的背景颜色
    @Bind(R.id.content)
    FrameLayout     framLayout;//手机Layout
    @Bind(R.id.layoutPhone)
    FrameLayout     layoutPhone;
    @Bind(R.id.ivPhoneFont)
    ImageView       ivPhoneFont;

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
        viewPager.addOnPageChangeListener(pageColorChangeListener);
        indicator.setViewPager(viewPager);

    }

    //      该监听器主要监听viewPager在Scroll过程中,
// 当前布局上的LayoutPhone布局的平移,缩放,渐变的处理
    private final ViewPager.OnPageChangeListener pageColorChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
//            VeiwPager在第一个和第二个页面之间
            if (position == 0) {
                float scale = 0.3f + positionOffset * 0.7f;
                layoutPhone.setScaleX(scale);
                layoutPhone.setScaleY(scale);
//                在平移过程中,fone实时的变化
                ivPhoneFont.setAlpha(positionOffset);
//                在平移的过程中有一个平移的动画
                int scroll = (int) (-400+400*positionOffset);
                int scrollY= (int) (-110*positionOffset);
                layoutPhone.setTranslationX(scroll);
//                layoutPhone.setTranslationY(scrollY);
                return;
            }
//              当ViewPager在第二个页面和第三个页面之间时,手机要和ViewPager一起移
            if (position == 1) {
                layoutPhone.setTranslationX(-positionOffsetPixels);
                return;
            }
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    //      此监听器主要负责监听页面背景颜色的变化,和最后一个页面视图动画的显示
    private final ViewPager.OnPageChangeListener pagerChangeListener     = new ViewPager.OnPageChangeListener() {
        //      ARGB取值器
        final ArgbEvaluator evaluator = new ArgbEvaluator();

        /**
         *
         * @param position  当前页面位置
         * @param positionOffset  位置偏移量
         * @param positionOffsetPixels  像素偏移量
         */
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
//            显示最后一个视图的动画
            if (position == 2) {
                Pager2 pager2 = (Pager2) adapter.getView(position);
                pager2.showAnimation();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

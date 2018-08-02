package com.hzyc.yy.rollviewpage_demo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.jude.rollviewpager.hintview.TextHintView;

public class MainActivity extends AppCompatActivity {

    private RollPagerView mRollPagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRollPagerView = (RollPagerView) findViewById(R.id.roll_view_pager);

        mRollPagerView.setPlayDelay(2000);
        mRollPagerView.setAnimationDurtion(1000);
        mRollPagerView.setAdapter(new ToastNormalAdapter());


        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollPagerView.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollPagerView.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
        //mRollPagerView.setHintView(new TextHintView(this));
        //mRollPagerView.setHintView(null);


    }


    class ToastNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}

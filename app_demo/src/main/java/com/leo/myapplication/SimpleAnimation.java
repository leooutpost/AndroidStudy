package com.leo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class SimpleAnimation extends AppCompatActivity implements View.OnClickListener{

    //在代码中定义 动画实例对象
    private Animation myAnimation_Alpha;
    private ImageView imgPic;
    private Button btnAlpha, btnScale, btnTranslate, btnRotate,goMenu;
    private Animation myAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_animation);

        intiView();

        initData();

    }

    /**
     * 初始化组件
     */
    private void intiView() {
        imgPic = (ImageView) findViewById(R.id.rotateKing);
        btnAlpha = (Button) findViewById(R.id.alphaButton);
        btnScale = (Button) findViewById(R.id.scaleButton);
        btnTranslate = (Button) findViewById(R.id.translationButton);
        btnRotate = (Button) findViewById(R.id.rotateButton);
        goMenu=(Button)findViewById(R.id.goMenuFromAnim);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        btnAlpha.setOnClickListener(this);
        btnScale.setOnClickListener(this);
        btnTranslate.setOnClickListener(this);
        btnRotate.setOnClickListener(this);
        goMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alphaButton:
                /**
                 * 使用XML中的动画效果 第一个参数Context为程序的上下文 第二个参数id为动画XML文件的引用
                 */
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.my_anim_alpha);
                imgPic.startAnimation(myAnimation);
                break;

            case R.id.scaleButton:
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.my_anim_scale);
                imgPic.startAnimation(myAnimation);
                break;

            case R.id.translationButton:
                myAnimation = AnimationUtils.loadAnimation(this,
                        R.anim.my_anim_translate);
                imgPic.startAnimation(myAnimation);
                break;

            case R.id.rotateButton:
                myAnimation = AnimationUtils
                        .loadAnimation(this, R.anim.my_anim_rotate);
                imgPic.startAnimation(myAnimation);
                break;
            case R.id.goMenuFromAnim:
                Intent i=new Intent(SimpleAnimation.this,Menu1.class);
                startActivity(i);
                break;

        }
    }

}


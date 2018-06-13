package com.txd.hzj.wjlp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/12 0012 17:06
 * 功能描述：封装分销title选择器
 * 联系方式：无
 */
public class MyShopTitleView extends LinearLayout implements View.OnClickListener {

    private TextView shopkeeper;
    private TextView ordinary;
    public String leftName="";



    public String rightName="";
    private LeftContent left;



    public interface LeftContent {
        void getLeft(String string);
    }



    public MyShopTitleView(Context context) {
        super(context);
        initView(context);
    }

    public MyShopTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyShopTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_shop_title_view, this, true);
        shopkeeper = inflate.findViewById(R.id.shop_shopkeeper);
        ordinary = inflate.findViewById(R.id.shop_person);
        shopkeeper.setOnClickListener(this);
        ordinary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_shopkeeper:
                shopkeeper.setBackground(getResources().getDrawable(R.drawable.shop_title_choice_left));
                shopkeeper.setTextColor(getResources().getColor(R.color.white));
                ordinary.setBackground(getResources().getDrawable(R.drawable.shop_title_unchoice_right));
                ordinary.setTextColor(getResources().getColor(R.color.red_tv_back));
                left.getLeft("0");
                break;
            case R.id.shop_person:
                shopkeeper.setBackground(getResources().getDrawable(R.drawable.shop_title_unchoice_left));
                shopkeeper.setTextColor(getResources().getColor(R.color.red_tv_back));
                ordinary.setBackground(getResources().getDrawable(R.drawable.shop_title_choice_right));
                ordinary.setTextColor(getResources().getColor(R.color.white));
                left.getLeft("1");
                break;
        }
    }
    public String getLeftName() {
        return leftName;
    }

    public String getRightName() {
        return rightName;
    }
    public void setLeftName(String leftName) {
        this.leftName = leftName;
        shopkeeper.setText(getLeftName());

    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
        ordinary.setText(getRightName());
    }

    public void setleftListener(LeftContent left) {
        this.left = left;

    }



}

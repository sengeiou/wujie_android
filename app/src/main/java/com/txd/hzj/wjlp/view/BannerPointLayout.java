package com.txd.hzj.wjlp.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.txd.hzj.wjlp.R;


/**
 * Created by OTKJ on 2018/3/30.
 */

public class BannerPointLayout extends RelativeLayout {
    private String styleBannerPointLayout;
    private int pointSize;
    private int pointDimen;
    private LinearLayout pointLayout;
    private ImageView pointerMoveView;
    private String styleMovePoint, styleNoPoint;
    private Drawable unFocusDrawable, focusDrawable;

    public BannerPointLayout(Context context) {
        this(context, null);
    }

    public BannerPointLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerPointLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Resources res = getResources();
        styleMovePoint = res.getString(R.string.pointer_style_has_move_point);
        styleNoPoint = res.getString(R.string.pointer_style_no_move_point);
        float defaultSizeDimen = context.getResources().getDimension(R.dimen.nine);
        float defaultMarginDimen = context.getResources().getDimension(R.dimen.nine);
        LayoutInflater inflater = LayoutInflater.from(context);
        View rootView = inflater.inflate(R.layout.pointer_layout, BannerPointLayout.this, false);
        addView(rootView);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.BannerPointLayout);
        // 获取自定义属性和默认值
        styleBannerPointLayout = mTypedArray.getString(R.styleable.BannerPointLayout_styleBannerPointLayout);
        pointSize = (int) mTypedArray.getDimension(R.styleable.BannerPointLayout_pointerSize, defaultSizeDimen);
        pointDimen = (int) mTypedArray.getDimension(R.styleable.BannerPointLayout_pointerLMargin, defaultMarginDimen);
        int unPointRes = mTypedArray.getResourceId(R.styleable.BannerPointLayout_unPointDrawable, R.drawable.round_nine_grey);
        unFocusDrawable = res.getDrawable(unPointRes);
        int pointRes = mTypedArray.getResourceId(R.styleable.BannerPointLayout_pointDrawable, R.drawable.round_nine_w);
        focusDrawable = res.getDrawable(pointRes);
        mTypedArray.recycle();
        //白点设置
        pointerMoveView = (ImageView) rootView.findViewById(R.id.whitePointImg);
        pointerMoveView.setImageDrawable(focusDrawable);
        if (null != styleBannerPointLayout)
            if (styleBannerPointLayout.equals(styleMovePoint)) {
                pointerMoveView.setVisibility(View.VISIBLE);
                LayoutParams params = (LayoutParams) pointerMoveView.getLayoutParams();
                params.width = pointSize;
                params.height = pointSize;
            } else if (styleBannerPointLayout.equals(styleNoPoint)) {
//                pointerMoveView.setVisibility(View.GONE);
            }
        //默认显示点设置

    }


    public void setPointNum(int pointNum) {
        if (pointNum > 1) {
            View rootView = getChildAt(0);
            pointLayout = (LinearLayout) rootView.findViewById(R.id.pointLayout);
            for (int i = 0; i < pointNum; i++) {
                ImageView unRoundView = new ImageView(getContext());
                unRoundView.setImageDrawable(unFocusDrawable);
                pointLayout.addView(unRoundView);
                LinearLayout.LayoutParams unParams = (LinearLayout.LayoutParams) unRoundView.getLayoutParams();
                unParams.width = pointSize;
                unParams.height = pointSize;
                if (i > 0) {
                    unParams.setMarginStart(pointDimen);
                }
            }
        } else {//一个点，没有必要显示
            this.setVisibility(View.GONE);
        }

    }

    private int lastLeft;
    private int lastPostion;

    public void toPosition(int position) {
        Log.i("i", String.valueOf(position));
        if (styleBannerPointLayout.equals(styleMovePoint)) {
            View toView = null;
            //TODO 逻辑处理
            toView = pointLayout.getChildAt(position);
            if (null != toView) {
                ObjectAnimator translationX = new ObjectAnimator().ofFloat(pointerMoveView, "translationX", lastLeft, toView.getLeft());
                ObjectAnimator translationY = new ObjectAnimator().ofFloat(pointerMoveView, "translationY", 0, 0);
                AnimatorSet animatorSet = new AnimatorSet();  //组合动画
                animatorSet.playTogether(translationX, translationY); //设置动画
                animatorSet.setDuration(500);  //设置动画时间
                animatorSet.start(); //启动
                lastLeft = toView.getLeft();
            }
        } else if (styleBannerPointLayout.equals(styleNoPoint)) {
            if (position % pointLayout.getChildCount() == 0) {
                pointerMoveView.setVisibility(View.VISIBLE);
            } else {
                pointerMoveView.setVisibility(View.GONE);
            }

            ImageView currentView = (ImageView) pointLayout.getChildAt(position);
            ImageView lastView = (ImageView) pointLayout.getChildAt(lastPostion);
            lastView.setImageDrawable(unFocusDrawable);
            currentView.setImageDrawable(focusDrawable);
            lastPostion = position;
        }

    }

}

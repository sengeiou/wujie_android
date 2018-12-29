package com.txd.hzj.wjlp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ListView;

import com.txd.hzj.wjlp.R;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class ScrollListView extends ListView {

    public ScrollListView(Context context) {
        super(context);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    //    private static final float DEFAULT_MAX_RATIO = 0.6f;
//    private static final float DEFAULT_MAX_HEIGHT = 0f;
//
//    private float mMaxRatio = DEFAULT_MAX_RATIO;// 优先级高
//    private float mMaxHeight = DEFAULT_MAX_HEIGHT;// 优先级低
//
//    public ScrollListView(Context context) {
//        super(context);
//    }
//
//    public ScrollListView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initAttrs(context, attrs);
//        init();
//    }
//
//    private void initAttrs(Context context, AttributeSet attrs) {
//        if (attrs == null) {
//            return;
//        }
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightView);
//        int count = typedArray.getIndexCount();
//        for (int i = 0; i < count; i++) {
//            int attr = typedArray.getIndex(i);
//            if (attr == R.styleable.MaxHeightView_mhv_HeightRatio) {
//                mMaxRatio = typedArray.getFloat(attr, DEFAULT_MAX_RATIO);
//            } else if (attr == R.styleable.MaxHeightView_mhv_HeightDimen) {
//                mMaxHeight = typedArray.getFloat(attr, DEFAULT_MAX_HEIGHT);
//            }
//        }
//        typedArray.recycle();
//    }
//
//    private void init() {
//        if (mMaxHeight <= 0) {
//            mMaxHeight = mMaxRatio * (float) getScreenHeight(getContext());
//        } else {
//            mMaxHeight = Math.min(mMaxHeight, mMaxRatio * (float) getScreenHeight(getContext()));
//        }
//    }
//
//    /**
//     * 获取屏幕高度
//     *
//     * @param context
//     */
//    private int getScreenHeight(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        return wm.getDefaultDisplay().getHeight();
//    }
//
//    /**
//     * 重写该方法，达到使ListView适应ScrollView的效果
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
//        }
//
//        if (heightMode == MeasureSpec.UNSPECIFIED) {
//            heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
//        }
//        if (heightMode == MeasureSpec.AT_MOST) {
//            heightSize = heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight;
//        }
//        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
//        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
//    }

}

package com.txd.hzj.wjlp.catchDoll.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：描边文字
 */
@SuppressLint("AppCompatCustomView")
public class StrokeTextView extends TextView {

    private TextView outLineTextView = null;

    private int strokeWidth = 3; // 描边宽度，默认为3
    private int strokeColor = Color.WHITE;

    public StrokeTextView(Context context) {
        super(context);
        outLineTextView = new TextView(context);
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        outLineTextView = new TextView(context, attrs);
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        outLineTextView = new TextView(context, attrs, defStyleAttr);
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeColor(String strokeColorStr) {
        this.strokeColor = Color.parseColor(strokeColorStr);
    }

    private void init() {
        TextPaint paint = outLineTextView.getPaint();
        paint.setStrokeWidth(strokeWidth); // 设置描边宽度
        paint.setStyle(Paint.Style.STROKE);
        outLineTextView.setTextColor(strokeColor); // 描边颜色
        outLineTextView.setGravity(getGravity());
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        outLineTextView.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置轮廓文字
        CharSequence outLineText = outLineTextView.getText();
        if (outLineText == null || !outLineText.equals(this.getText())) {
            outLineTextView.setText(getText());
            postInvalidate();
        }
        outLineTextView.measure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        outLineTextView.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        outLineTextView.draw(canvas);
        super.onDraw(canvas);
    }
}

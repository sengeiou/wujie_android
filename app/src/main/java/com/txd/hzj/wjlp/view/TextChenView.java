package com.txd.hzj.wjlp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.txd.hzj.wjlp.R;

/**
 * 作者：chen
 * 日期：2017/7/13 0007
 * 时间：下午17:55
 * 描述： 文字跑马灯
 */

public class TextChenView extends View {
    Paint paint=new Paint();

    private int scrollTextSize=30;//文字尺寸
    private int scrollTextColor=getResources().getColor(R.color.black);//文字颜色
    private String scrollText="测试文字";//默认文字
    private int rx=0;//绘制文字x坐标
    private MyThread thread;




    public TextChenView(Context context) {
        super(context);
    }

    public TextChenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.ViewTwo);
        scrollTextSize=ta.getDimensionPixelSize(R.styleable.ViewTwo_scrollTextSize,30);
        scrollTextColor=ta.getInt(R.styleable.ViewTwo_scrollTextColor,R.color.black);
        scrollText=ta.getString(R.styleable.ViewTwo_scrollText);
        ta.recycle();//释放
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setTextSize(scrollTextSize);//文字大小
        paint.setColor(scrollTextColor);//文字颜色
        paint.setAntiAlias(true);
        canvas.drawText(scrollText,rx,50,paint);//绘制文字
        /**
         * 开启线程
         */
        if(thread==null){
            thread=new MyThread();
            thread.start();//开启线程
        }

    }

    /**
     * 线程
     */
    class MyThread extends Thread{
        @Override
        public void run(){
            while (true){

                rx=rx+2;
                postInvalidate();
                if(rx>getWidth()){
                    rx= (int) -paint.measureText(scrollText);
                }

                /**
                 * 让线程睡眠30毫秒
                 */
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

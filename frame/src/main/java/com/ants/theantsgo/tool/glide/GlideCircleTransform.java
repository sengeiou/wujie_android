package com.ants.theantsgo.tool.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/10/9 0009
 * 时间：11:27
 * 描述：将图片转换成圆角(可以设置边框及其颜色)
 * ===============Txunda===============
 */

class GlideCircleTransform extends BitmapTransformation {
    private Paint mBorderPaint;
    /**
     * 边框粗细 单位px
     */
    private float mBorderWidth = 0f;

    GlideCircleTransform(Context context) {
        super(context);
    }

    /**
     * 带边框的圆形头像
     *
     * @param context     上下文
     * @param borderWidth 边框宽度(该值表示为dp大小)
     * @param borderColor 边框颜色
     */
    GlideCircleTransform(Context context, int borderWidth, int borderColor) {
        super(context);
        mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }


    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, source.getConfig());
        if (result == null) {
            result = Bitmap.createBitmap(size, size, source.getConfig());
        }

        if (squared != source) {
            source.recycle();
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);// 设置画笔抗锯齿
        float r = size / 2f;// 计算圆的半径
        canvas.drawCircle(r, r, r, paint);// 画圆
        if (mBorderPaint != null) {// 如果边界的画笔不为空
            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        // 回收squared这个BitMap
        squared.recycle();
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}

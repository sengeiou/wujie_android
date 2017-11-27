package com.txd.hzj.wjlp.txunda_lh;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Glide加载圆角
 * by Txunda_LH on 2017/11/25.
 */

public class RoundTransformation extends BitmapTransformation {
    private static float radius = 0f;

    public RoundTransformation(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    public RoundTransformation(Context context) {
        super(context);
    }

    public RoundTransformation(Context context, int radius) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * radius;
    }



    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap toTransform) {
        if (toTransform == null) return null;

        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, toTransform.getWidth(), toTransform.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }



    @Override
    public String getId() {
        return getClass().getName().concat(String.valueOf(radius));
    }
}
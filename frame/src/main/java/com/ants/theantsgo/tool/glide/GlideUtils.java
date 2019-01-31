package com.ants.theantsgo.tool.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.ants.theantsgo.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/6/5 0005
 * 时间：10:03
 * 描述：Glide加载图片工具类(可以加载网络图片和本地图片，圆形图片或者圆角图片)
 */

public class GlideUtils {

    /**
     * 简单的加载网络图片，不设置宽高
     *
     * @param context 上下文
     * @param urlStr  图片Url
     * @param imgv    ImageView
     */
    public static void loadUrlImg(Context context, String urlStr, ImageView imgv) {
        Glide.with(context)
                .load(urlStr)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .into(imgv);
    }

    /**
     * 原生API (中间显示图片，可能显示不全)
     *
     * @param url 网络路径
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void urlCenterCrop(String url, int w, int h, ImageView iv) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .override(w, h)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 原生API (全部显示图片，可能不能沾满宽高)
     *
     * @param url 网络路径
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void urlFitCenter(String url, int w, int h, ImageView iv) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .override(w, h)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 原生API (中间显示图片，可能显示不全)
     *
     * @param url 文件
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void FileCenterCrop(File url, int w, int h, ImageView iv) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .override(w, h)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 原生API (全部显示图片，可能不能沾满宽高)
     *
     * @param url 图片文件
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void fileFitCenter(File url, int w, int h, ImageView iv) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .override(w, h)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 圆形图片
     *
     * @param url 网络路径
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void urlCirclePic(String url, int w, int h, ImageView iv) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .transform(new CenterCrop(iv.getContext()), new GlideCircleTransform(iv.getContext()))
                .override(w, h)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 加载圆形图片不带背景颜色的
     *
     * @param picUrl
     * @param w
     * @param h
     * @param iv
     */
    public static void urlCirclePicNoBg(String picUrl, int w, int h, final ImageView iv) {
        Glide.with(iv.getContext()).load(picUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(iv) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(iv.getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                iv.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 圆形图片
     *
     * @param url 图片文件
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void fileCirclePic(File url, int w, int h, ImageView iv) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .transform(new CenterCrop(iv.getContext()), new GlideCircleTransform(iv.getContext()))
                .override(w, h)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 圆形图片带边框
     *
     * @param url 网络路径
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void urlCirclePicWithBorder(String url, int w, int h, int borderWidth, int borderColor, ImageView
            iv) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .transform(new CenterCrop(iv.getContext()),
                        new GlideCircleTransform(iv.getContext(), borderWidth, borderColor))
                .override(w, h)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 圆形图片带边框
     *
     * @param url 图片文件
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void fileCirclePicWithBorder(File url, int w, int h, int borderWidth, int borderColor, ImageView iv) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .transform(new CenterCrop(iv.getContext()),
                        new GlideCircleTransform(iv.getContext(), borderWidth, borderColor))
                .override(w, h)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 圆角图片
     *
     * @param url 网络路径
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void urlRoundPic(String url, int w, int h, ImageView iv, int rdp) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .transform(new CenterCrop(iv.getContext()), new GlideRoundTransform(iv.getContext(), rdp))
                .override(w, h)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }

    /**
     * 圆角图片
     *
     * @param url 图片文件
     * @param w   宽
     * @param h   高
     * @param iv  ImageView
     */
    public static void fileRoundPic(File url, int w, int h, ImageView iv, int rdp) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .transform(new CenterCrop(iv.getContext()), new GlideRoundTransform(iv.getContext(), rdp))
                .override(w, h)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(iv);
    }


    /**
     * 将Glide缓存图片的路径返回
     * <p>注：该方法需要在新线程中使用
     *
     * @param context 上下文
     * @param url     加载图片的URL
     * @return String路径
     */
    public static String getGlideFilePath(Context context, String url) {
        String absolutePath = "";
        FutureTarget<File> future = Glide.with(context)
                .load(url)
                .downloadOnly(100, 100);
        try {
            File file = future.get();
            absolutePath = file.getCanonicalPath();
            return absolutePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return absolutePath;
    }


}

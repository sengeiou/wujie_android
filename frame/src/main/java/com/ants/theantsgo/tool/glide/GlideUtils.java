package com.ants.theantsgo.tool.glide;

import android.content.Context;
import android.widget.ImageView;

import com.ants.theantsgo.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/6/5 0005
 * 时间：10:03
 * 描述：Glide加载图片工具类(可以加载网络图片和本地图片，圆形图片或者圆角图片)
 *
 */

public class GlideUtils {
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
    public static void urlRoundPic(String url, int w, int h, ImageView iv,int rdp) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .transform(new CenterCrop(iv.getContext()), new GlideRoundTransform(iv.getContext(),rdp))
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
    public static void fileRoundPic(File url, int w, int h, ImageView iv,int rdp) {
        Glide.with(iv.getContext()).load(url)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .transform(new CenterCrop(iv.getContext()), new GlideRoundTransform(iv.getContext(),rdp))
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
            absolutePath = file.getAbsolutePath();
            return absolutePath;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return absolutePath;
    }



}

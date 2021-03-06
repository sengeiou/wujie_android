package com.ants.theantsgo.imageLoader;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.ants.theantsgo.R;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：该类是用于加载图片选择控件的图片的类
 * 修订历史：
 * ================================================
 */
public class GlideImageLoader implements ImageLoader {

    /**
     * 显示选择的图像
     *
     * @param activity  Activity
     * @param path      图片路径
     * @param imageView 显示的控件
     * @param width     宽度
     * @param height    高度
     */
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        try {
            Glide.with(activity)                                //配置上下文
                    .load(Uri.fromFile(new File(path)))         //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(R.drawable.ic_default)               //设置错误图片
                    .placeholder(R.drawable.ic_default)         //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)   //缓存全尺寸
                    .into(imageView);
        } catch (Exception e) {
            Toast.makeText(activity, "图片异常，无法显示该图片", Toast.LENGTH_SHORT).show();
            L.e("GlideImageLoader.displayImage Exception:" + e.toString());
        }
    }

    @Override
    public void displayImagePreview(Activity activity, String s, ImageView imageView, int i, int i1) {

    }

    @Override
    public void clearMemoryCache() {
    }
}

package com.txd.hzj.wjlp.tool;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * 创建者：wjj
 * 创建时间：2018/05/18 17:25
 * 功能描述：Glide图片加载工具类
 * 联系方式：jingjie.office@qq.com
 */
public class GlideUtil {

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

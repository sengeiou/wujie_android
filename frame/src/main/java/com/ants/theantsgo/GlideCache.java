package com.ants.theantsgo;

import android.content.Context;
import android.os.Environment;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 2:52
 * 描述：Glide缓存管理
 * ===============Txunda===============
 */
public class GlideCache implements GlideModule {



    @Override
    public void applyOptions(Context context, GlideBuilder builder) {



        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));

//        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
//        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));//设置内存缓存大小
//
//        // 设置图片的显示格式ARGB_8888(指图片大小为32bit)
//        Bitmap Format用于设置全局缺省首选Bitmap规格，设置方法：GlideBuilder.setDecodeFormat()
//        默认采用RGB_565(比ARGB_8888节省一半的内存)，但不支持透明度。
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        String downloadDirectoryPath = Settings.cacheCompressPath;// 设置缓存路径
//        int cacheSize = 100*1024*1024;// 设置缓存的大小为100M(100*1024*1024)
//        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));


        //builder 内的所有方法你都可以设置。
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context));
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
//         builder.setDiskCache(new DiskCache.Factory() {
//            @Override
//            public DiskCache build() {
//                File imgFile = new File(Environment.getExternalStorageDirectory()+"/Android/data/com.leying365");
//                return DiskLruCacheWrapper.get(imgFile,DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
//            }
//        });

        L.e("=====设置的缓存路径=====", downloadDirectoryPath);
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, downloadDirectoryPath, cacheSize));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, downloadDirectoryPath, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE));

//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, "glideCache", cacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

}

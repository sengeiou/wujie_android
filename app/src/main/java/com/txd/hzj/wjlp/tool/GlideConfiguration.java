package com.txd.hzj.wjlp.tool;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * by Txunda_LH on 2017/11/3.
 */

public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        //builder 内的所有方法你都可以设置。
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context));
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
      /*  builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                File imgFile = new File(Environment.getExternalStorageDirectory()+"/Android/data/com.leying365");
                return DiskLruCacheWrapper.get(imgFile,DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
            }
        });*/
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
        builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }


}

package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.youth.banner.loader.ImageLoader;

/**
 * 创建者：Qyl
 * 创建时间：2018/7/23 0023 15:27
 * 功能描述：
 * 联系方式：无
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage( Context context, final Object path, final ImageView imageView) {


        Glide.with(context).load(path).transform(new CenterCrop(context),new GlideRoundTransform(context,15)).into(imageView);


        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }

}

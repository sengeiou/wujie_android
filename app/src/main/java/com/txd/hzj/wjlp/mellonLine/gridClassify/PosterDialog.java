package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.config.Settings;
import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.tool.BitmapUtils;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/10/29 15:20
 * 功能描述：
 */
public class PosterDialog extends Dialog{
    private Context mContext;
    private String url;
    private ImageView mImageView;

    public PosterDialog(@NonNull Context context) {
        this(context, R.style.Ticket_Dialog);
    }

    public PosterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        init();
    }

    public void setUrl(final String url) {
        this.url = url;
        if (null != mImageView){
            Glide.with(mContext).load(url).asBitmap().fitCenter().into(mImageView);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BitmapUtils.gainInstance().savePic(mContext, url, "poster", new BitmapUtils.Listener() {
                        @Override
                        public void saveSuccess() {
                            ((Activity)mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mContext, "已成功保存到相册！", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                }
            });
        }

    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_poster, null);
        setContentView(view);
        mImageView = view.findViewById(R.id.img);
        TextView sure_tv=view.findViewById(R.id.sure_tv);
        sure_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
    }

    private void initDialog() {
        Window window = getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (Settings.displayWidth*0.9);
        layoutParams.height= (int) (Settings.displayHeight*0.8);
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
    }
}

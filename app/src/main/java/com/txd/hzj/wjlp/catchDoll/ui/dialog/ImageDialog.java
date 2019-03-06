package com.txd.hzj.wjlp.catchDoll.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.base.BaseDialog;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：显示纯图片的弹窗
 */
public class ImageDialog extends BaseDialog {

    public ImageDialog(@NonNull Context context) {
        super(context);
    }

    public ImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        Context context;
        String urlPath; // 图片路径
        boolean isNetwork = true; // 是否是加载网络图片，默认加载网络图片

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setUrlPath(String urlPath) {
            this.urlPath = urlPath;
            return this;
        }

        public Builder setIsNetwork(boolean isNetwork) {
            this.isNetwork = isNetwork;
            return this;
        }

        public ImageDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final ImageDialog imageDialog = new ImageDialog(context, R.style.Dialog);
            View view = inflater.inflate(R.layout.dialog_image_show, null);

            ImageView content_imgv = view.findViewById(R.id.dialogImageShow_content_imgv);
            ImageView close_imgv = view.findViewById(R.id.dialogImageShow_close_imgv);

            GlideUtils.loadUrlImg(context, urlPath, content_imgv);

            close_imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageDialog.isShowing()) {
                        imageDialog.dismiss();
                    }
                }
            });

            int screenWidth = Config.getScreenWidth((Activity) context);
            Window window = imageDialog.getWindow(); // 这部分是设置dialog宽高
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = screenWidth / 6 * 5;
//            lp.height = screenWidth / 4 * 3;
            window.setAttributes(lp);

            imageDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageDialog.setCancelable(true);
            return imageDialog;
        }
    }
}

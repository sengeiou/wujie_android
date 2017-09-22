package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;

import java.util.Map;

public class ShareToFriendsAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private int img_h = 0;
    private UserPst userPst;

    @ViewInject(R.id.share_frind_iv)
    private ImageView share_frind_iv;

    @ViewInject(R.id.share_title_tv)
    private TextView share_title_tv;

    @ViewInject(R.id.share_context_ev)
    private EditText share_context_ev;

    private String share_title = "";
    private String share_url = "";
    private String share_img = "";
    private String share_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("分享好友");

        img_h = Settings.displayWidth / 2;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Settings.displayWidth, img_h);
        share_frind_iv.setLayoutParams(params);
    }

    @Override
    @OnClick({R.id.to_share_friends_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_share_friends_tv:// 分享
                String context = share_context_ev.getText().toString().trim();
                toShare(share_title, share_img, share_url, context, share_id, "5");
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_shre_to_friends;
    }

    @Override
    protected void initialized() {
        userPst = new UserPst(this);
    }

    @Override
    protected void requestData() {
        userPst.shareFriend();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("shareFriend")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            share_id = data.get("share_id");
            share_img = data.get("share_img");
            Glide.with(this).load(share_img)
                    .override(Settings.displayWidth, img_h)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(share_frind_iv);
            share_title = data.get("share_title");
            share_url = data.get("share_url");
            share_title_tv.setText(share_title);
        }

    }
}

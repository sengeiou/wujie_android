package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

public class ShareToFriendsAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("分享好友");
    }

    @Override
    @OnClick({R.id.to_share_friends_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_share_friends_tv:// 分享
                toShare();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_shre_to_friends;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}

package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.OnePeopleDollAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.OnePeopleDollBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：TA的娃娃
 */
public class OnePeoplesDollActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv)
    private TextView titleView_title_tv;
    @ViewInject(R.id.onePropleDoll_header_imgv)
    public ImageView onePropleDoll_header_imgv;
    @ViewInject(R.id.onePropleDoll_name_tv)
    public TextView onePropleDoll_name_tv;
    @ViewInject(R.id.onePropleDoll_catchCount_tv)
    public TextView onePropleDoll_catchCount_tv;
    @ViewInject(R.id.onePropleDoll_data_rlv)
    public RecyclerView onePropleDoll_data_rlv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_one_peoples_doll;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("TA的娃娃");
        GlideUtils.urlCirclePicNoBg("http://h.hiphotos.baidu.com/zhidao/pic/item/060828381f30e92487ee73f74e086e061c95f7aa.jpg", 43, 43, onePropleDoll_header_imgv);
        onePropleDoll_name_tv.setText("会飞的鱼");
        onePropleDoll_catchCount_tv.setText("共抓中10个");

        initListData();

    }

    /**
     * 初始化列表界面
     */
    private void initListData() {
        List<OnePeopleDollBean> list = new ArrayList<>();
        OnePeopleDollBean onePeopleDollBean;
        for (int i = 0; i < 20; i++) {
            onePeopleDollBean = new OnePeopleDollBean();
            onePeopleDollBean.setImageUrl("http://b-ssl.duitang.com/uploads/item/201603/26/20160326232537_Cz4mZ.jpeg");
            onePeopleDollBean.setName("什么什么娃娃");
            onePeopleDollBean.setTime(System.currentTimeMillis());
            list.add(onePeopleDollBean);
        }

        OnePeopleDollAdapter adapter = new OnePeopleDollAdapter(this, list);
        onePropleDoll_data_rlv.setLayoutManager(new GridLayoutManager(this, 2));
        onePropleDoll_data_rlv.setAdapter(adapter);

    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.titleView_goback_imgv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
        }
    }
}

package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.GranTheRecordAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.GrabTheRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：抓中娃娃记录界面
 */
public class GrabTheRecordActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv) // 标题
    public TextView titleView_title_tv;
    @ViewInject(R.id.list_nullData_llayout)
    public LinearLayout list_nullData_llayout;
    @ViewInject(R.id.list_nullDataImg_imgv)
    public ImageView list_nullDataImg_imgv;
    @ViewInject(R.id.list_nullDataMsg_tv)
    public TextView list_nullDataMsg_tv;

    @ViewInject(R.id.list_show_reView)
    public RecyclerView list_show_reView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        titleView_title_tv.setText("抓中记录");
        setData();
    }

    /**
     * 设置展示数据
     */
    private void setData() {
        List<GrabTheRecordBean> grabTheRecordBeans = new ArrayList<>();
        GrabTheRecordBean grabTheRecordBean;
        for (int i = 0; i < 10; i++) {
            grabTheRecordBean = new GrabTheRecordBean();
            grabTheRecordBean.setHeadUrl("http://img4.duitang.com/uploads/item/201407/26/20140726141237_32faQ.png");
            grabTheRecordBean.setUserName("谁知道叫啥名呢");
            grabTheRecordBean.setTime(1545709088 * 1000L); // 毫秒 2018-12-25 11:38:08
            grabTheRecordBean.setContent("那谁抓到娃娃了！！！！！！！！！！！！！！");
            grabTheRecordBean.setRoomNumber("121");
            grabTheRecordBean.setVideoUrl("http://img4.duitang.com/uploads/item/201407/26/20140726141237_32faQ.png");
            grabTheRecordBeans.add(grabTheRecordBean);
        }
        if (grabTheRecordBeans == null || grabTheRecordBeans.size() == 0) {
            // TODO 设置空数据展示视图
            showNullData(list_show_reView, list_nullData_llayout, list_nullDataImg_imgv, list_nullDataMsg_tv, R.mipmap.icon_my_select, "暂无抓中记录哦~");
        } else {
            GranTheRecordAdapter adapter = new GranTheRecordAdapter(grabTheRecordBeans, this);
            list_show_reView.setLayoutManager(new LinearLayoutManager(this));
            list_show_reView.setAdapter(adapter);
        }
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

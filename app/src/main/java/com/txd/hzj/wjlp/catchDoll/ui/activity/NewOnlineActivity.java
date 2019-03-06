package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.adapter.RoomListAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.RoomBean;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：type: 1新品上线 2高价精品 3只爱娃娃 4美女专场 5实用专区
 */
public class NewOnlineActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.list_show_reView)
    public RecyclerView list_show_reView;

    List<RoomBean> roomList;
    private int type; // 数据加载类别：1新品上线 2高价精品 3只爱娃娃 4美女专场 5实用专区

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        Intent intent = getIntent();
        type = intent.getExtras().getInt("type");
        switch (type) {
            case 1:
                titleView_title_tv.setText("最新上线");
                break;
            case 2:
                titleView_title_tv.setText("高价精品");
                break;
            case 3:
                titleView_title_tv.setText("只爱娃娃");
                break;
            case 4:
                titleView_title_tv.setText("美女专场");
                break;
            case 5:
                titleView_title_tv.setText("实用专区");
                break;
        }
        setRoomData();
    }

    /**
     * 设置房间列表
     */
    private void setRoomData() {
        roomList = new ArrayList<>();
//        roomList.add(new RoomBean(1, "http://h.hiphotos.baidu.com/zhidao/pic/item/2f738bd4b31c87018e126740237f9e2f0608fff4.jpg", "测试房间1", 10, 0));
//        roomList.add(new RoomBean(2, "http://c.hiphotos.baidu.com/zhidao/pic/item/4034970a304e251f27d9d254a586c9177f3e536b.jpg", "测试房间2", 10, 1));
//        roomList.add(new RoomBean(3, "http://b-ssl.duitang.com/uploads/blog/201404/23/20140423205758_FJ4NN.jpeg", "测试房间3", 10, 1));
//        roomList.add(new RoomBean(4, "http://c.hiphotos.baidu.com/zhidao/pic/item/cdbf6c81800a19d8265bbb793bfa828ba61e4675.jpg", "测试房间4", 10, 1));
//        roomList.add(new RoomBean(5, "http://img.zcool.cn/community/0165c75a602e0ba8012113c7d220e7.jpg@2o.jpg", "测试房间5", 10, 0));
//        roomList.add(new RoomBean(6, "http://life.southmoney.com/tuwen/UploadFiles_6871/201809/20180919114327818.jpg", "测试房间6", 10, 0));
//        roomList.add(new RoomBean(7, "http://ccstatic-1252317822.file.myqcloud.com/portraitimg/2018-02-07/5a7a8526367d2.jpg", "测试房间7", 10, 0));
//        roomList.add(new RoomBean(8, "http://img.xspic.com/img/116/194/574068_5.jpg", "测试房间8", 10, 0));
//        roomList.add(new RoomBean(9, "http://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/314e251f95cad1c8fa989c4b783e6709c93d51be.jpg", "测试房间9", 10, 1));
//        roomList.add(new RoomBean(10, "http://life.southmoney.com/tuwen/UploadFiles_6871/201809/20180919114327377.jpg", "测试房间10", 10, 0));
//        roomList.add(new RoomBean(11, "http://img1.imgtn.bdimg.com/it/u=165501638,2373619033&fm=27&gp=0.jpg", "测试房间11", 10, 0));
//        roomList.add(new RoomBean(12, "http://hbimg.b0.upaiyun.com/afd558e0130d4141116a66a282a0d9267f4602721614e-kb2Tjj_fw658", "测试房间12", 10, 1));
//        roomList.add(new RoomBean(13, "http://kanimg.9ku.com/kanqq/pic/upload/2018/0530/b78eea8df704a1e831fb6c8778a618cb.jpg", "测试房间13", 10, 0));
        RoomListAdapter adapter = new RoomListAdapter(roomList, this);
        list_show_reView.setLayoutManager(new GridLayoutManager(this, 2));
//        list_show_reView.setNestedScrollingEnabled(false); // 重新设置外层Scroll滑动阻尼效果
        list_show_reView.setAdapter(adapter);
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

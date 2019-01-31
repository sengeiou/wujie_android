package com.txd.hzj.wjlp.catchDoll.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.MyDollItemAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseFgt;
import com.txd.hzj.wjlp.catchDoll.bean.MyDollBean;
import com.txd.hzj.wjlp.catchDoll.view.NoScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：我的娃娃界面Fragment
 */
@SuppressLint("ValidFragment")
public class MyDollFragment extends BaseFgt {

    @ViewInject(R.id.myDollPage_prompt_llayout)
    public LinearLayout myDollPage_prompt_llayout;
    @ViewInject(R.id.myDollPage_prompt_tv)
    public TextView myDollPage_prompt_tv;
    @ViewInject(R.id.myDollPage_list_rlView)
    public NoScrollRecyclerView myDollPage_list_rlView;

    private int type;
    private List<MyDollBean> list;

    public MyDollFragment(int type) {
        this.type = type;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_doll_page;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        initData();
    }

    @Override
    protected void immersionInit() {
    }

    private void initData() {
        if (type == 0) {
            myDollPage_prompt_llayout.setVisibility(View.VISIBLE);
            myDollPage_prompt_tv.setText("娃娃的寄存期为7天时间，寄存到期后将自动兑换成相应银两。");
        }

        list = new ArrayList<>();
        MyDollBean myDollBean;
        for (int i = 0; i < 20; i++) {
            myDollBean = new MyDollBean();
            myDollBean.setImageUrl("http://img4.duitang.com/uploads/item/201407/26/20140726141237_32faQ.png");
            myDollBean.setMaturityTime(90000);
            myDollBean.setName("那啥那啥啥");
            myDollBean.setTime(System.currentTimeMillis());
            myDollBean.setConvertible(8);
            list.add(myDollBean);
        }
        MyDollItemAdapter adapter = new MyDollItemAdapter(list, getActivity());
        myDollPage_list_rlView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        myDollPage_list_rlView.setNestedScrollingEnabled(false); // 重新设置外层Scroll滑动阻尼效果
        myDollPage_list_rlView.setAdapter(adapter);

    }
}

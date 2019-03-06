package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.glide.GlideUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.adapter.MyDollViewPagerAdapter;
import com.txd.hzj.wjlp.catchDoll.ui.fragment.MyDollFragment;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：我的娃娃主界面
 */
public class MyDollActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.myDoll_header_imgv)
    public ImageView myDoll_header_imgv;
    @ViewInject(R.id.myDoll_name_tv)
    public TextView myDoll_name_tv;
    @ViewInject(R.id.myDoll_count_tv)
    public TextView myDoll_count_tv;
    @ViewInject(R.id.myDoll_table_tablayout)
    public TabLayout myDoll_table_tablayout;
    @ViewInject(R.id.myDoll_content_vp)
    public ViewPager myDoll_content_vp;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_doll;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("我的娃娃");
    }

    private void initPageData(List<String> titleList) {
        if (titleList != null && titleList.size() > 0) {
            this.titleList = titleList;

            fragmentList = new ArrayList<>();
            for (int i = 0; i < this.titleList.size(); i++) {
                fragmentList.add(new MyDollFragment(i + 1));
            }
            myDoll_content_vp.setAdapter(new MyDollViewPagerAdapter(getSupportFragmentManager(), fragmentList));

            for (int i = 0; i < titleList.size(); i++) {
                myDoll_table_tablayout.addTab(myDoll_table_tablayout.newTab());
            }

            myDoll_table_tablayout.setupWithViewPager(myDoll_content_vp); // 关联viewpager

            for (int i = 0; i < titleList.size(); i++) {
                myDoll_table_tablayout.getTabAt(i).setText(titleList.get(i));
            }
        }
    }

    @Override
    protected void requestData() {
        Catcher.myAward(this);
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

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject jsonData = jsonObject.getJSONObject("data");

            // 设置类别中的数量
            JSONObject countJson = jsonData.getJSONObject("count");
            String deposit = countJson.getString("deposit");// 寄存
            String wait = countJson.getString("wait");// 待邮寄
            String alread = countJson.getString("already");// 已发货
            String exchange = countJson.getString("exchange"); // 已兑换
            List<String> tempTitleList = new ArrayList<>();
            tempTitleList.add(new StringBuffer().append("寄存中(").append(deposit).append(")").toString());
            tempTitleList.add(new StringBuffer().append("待邮寄(").append(wait).append(")").toString());
            tempTitleList.add(new StringBuffer().append("已发货(").append(alread).append(")").toString());
            tempTitleList.add(new StringBuffer().append("已兑换(").append(exchange).append(")").toString());
            initPageData(tempTitleList); // 设置界面上列表类别的数量和显示

            // 设置个人信息
            JSONObject detailsJson = jsonData.getJSONObject("details");
            String id = detailsJson.getString("id");
            String nickname = detailsJson.getString("nickname");
            String head_pic = detailsJson.getString("head_pic");
            String chance_num = detailsJson.getString("chance_num");
            String nums = detailsJson.getString("nums");

            GlideUtils.urlCirclePic(head_pic, 43, 43, myDoll_header_imgv);
            myDoll_name_tv.setText(nickname);
            myDoll_count_tv.setText(new StringBuffer().append("共抓中").append(nums).append("个"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.txd.hzj.wjlp.catchDoll.ui.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.catchDoll.adapter.MyDollItemAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.MyDollBean;
import com.txd.hzj.wjlp.catchDoll.view.NoScrollRecyclerView;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private int type; // 1 寄存 2 待邮寄 3已发货  4已兑换

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
        Catcher.myList(type, this);
    }

    @Override
    protected void immersionInit() {
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        try {
            int s = type;
            JSONObject jsonObject = new JSONObject(jsonStr);
            if (requestUrl.endsWith("Mylist")) { // TODO 此处还存在一些问题，寄存的为商品，MyDoll中属性都有，但是待邮寄，已发货和已兑换是订单，没办法按照寄存的处理
                JSONObject jsonData = jsonObject.getJSONObject("data");
                JSONArray jsonArrrayDeposit = jsonData.getJSONArray("deposit");
                List<MyDollBean> myDollBeanList = new ArrayList<>();
                for (int i = 0; i < jsonArrrayDeposit.length(); i++) {
                    JSONObject jsonObject1 = jsonArrrayDeposit.getJSONObject(i);
                    MyDollBean myDollBean = GsonUtil.GsonToBean(jsonObject1.toString(), MyDollBean.class);
                    myDollBeanList.add(myDollBean);
                }
                if (myDollBeanList != null && myDollBeanList.size()>0){
                    MyDollItemAdapter adapter = new MyDollItemAdapter(myDollBeanList, type, getActivity());
                    myDollPage_list_rlView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    myDollPage_list_rlView.setNestedScrollingEnabled(false); // 重新设置外层Scroll滑动阻尼效果
                    myDollPage_list_rlView.setAdapter(adapter);
                }


                if (jsonData.has("details") && !jsonData.getString("details").isEmpty()) {
                    myDollPage_prompt_llayout.setVisibility(View.VISIBLE); // 如果提示字段存在并且不为空则显示温馨提示
                    myDollPage_prompt_tv.setText(jsonData.getString("details")); // 设置显示内容
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

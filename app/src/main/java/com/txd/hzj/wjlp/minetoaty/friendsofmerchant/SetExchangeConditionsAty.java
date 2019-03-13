package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.addres.CityForTxd;
import com.txd.hzj.wjlp.bean.addres.DistrictsForTxd;
import com.txd.hzj.wjlp.bean.addres.ProvinceForTxd;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/18 15:23
 * 功能描述：设置互换条件
 */
public class SetExchangeConditionsAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.chooseShopkeeperLayout)
    private RelativeLayout chooseShopkeeperLayout;

    @ViewInject(R.id.nameTv)
    private TextView nameTv;

    @ViewInject(R.id.sexTv)
    private TextView sexTv;

    @ViewInject(R.id.ageTv)
    private TextView ageTv;

    @ViewInject(R.id.gradeTv)
    private TextView gradeTv;

    @ViewInject(R.id.addressTv)
    private TextView addressTv;


    private String[] titles = {"性别选择", "年龄选择", "等级选择", "请选择区域"};


    private AddressPst mAddressPst;
    private ArrayList<ProvinceForTxd> options1Items = new ArrayList<>();//临时数据存储省份
    private ArrayList<ArrayList<CityForTxd>> options2Items = new ArrayList<>();//临时数据存储城市
    private ArrayList<ArrayList<ArrayList<DistrictsForTxd>>> options3Items = new ArrayList<>();//临时数据存储区域
    private String mSta_mid;
    private String mShopkeeper_id;
    private String mCity_id;
    private String mC_id;
    private int mSexNum;
    private int mAgeNum;
    private int mGradeNum;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_set_exchange_conditions;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("设置互换条件");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        mC_id = getIntent().getStringExtra("c_id");
        if (mC_id!= null){
            chooseShopkeeperLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        String label = event.getLabel();
        String message = event.getMessage();
        if (label.equals("ChooseShopKeeperAty")) {
            String[] split = message.split(";");
            nameTv.setText(split[0]);
            mShopkeeper_id = split[1];
        }
    }

    @Override
    protected void requestData() {
        mAddressPst = new AddressPst(this);
        mAddressPst.androidAddress();
    }

    void app_exchange_user(String sta_mid, String shopkeeper_id, String sex, String age, String member_coding, String city_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("shopkeeper_id", shopkeeper_id);
        if (!TextUtils.isEmpty(sex)) {
            params.addBodyParameter("sex", sex);
        }
        if (!TextUtils.isEmpty(age)) {
            params.addBodyParameter("age", age);
        }
        if (!TextUtils.isEmpty(member_coding)) {
            params.addBodyParameter("member_coding", member_coding);
        }
        if (!TextUtils.isEmpty(city_id)) {
            params.addBodyParameter("city_id", city_id);
        }
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_exchange_user", params, baseView);
    }

    void exchange_log(String sta_mid, String status,String c_id, String sex,String age, String member_coding,String city_id,BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("status", status);
        params.addBodyParameter("c_id", c_id);
        if (!TextUtils.isEmpty(sex)){
            params.addBodyParameter("sex", sex);
        }
        if (!TextUtils.isEmpty(age)){
            params.addBodyParameter("age", age);
        }
        if (!TextUtils.isEmpty(member_coding)){
            params.addBodyParameter("member_coding", member_coding);
        }
        if (!TextUtils.isEmpty(city_id)){
            params.addBodyParameter("city_id", city_id);
        }
        apiTool2.postApi(Config.BASE_URL + "OsManager/exchange_log", params, baseView);
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("androidAddress")) {
            options1Items = JSONUtils.parseKeyAndValueToMapList(ProvinceForTxd.class, map.get("data"));
            for (int i = 0; i < options1Items.size(); i++) {
                ProvinceForTxd provinceForTxd = options1Items.get(i);
                ArrayList<CityForTxd> cities = (ArrayList<CityForTxd>) provinceForTxd.getCities();
                options2Items.add(cities);
                ArrayList<ArrayList<DistrictsForTxd>> hh = new ArrayList<>();
                for (int i1 = 0; i1 < cities.size(); i1++) {
                    CityForTxd cityForTxd = cities.get(i1);
                    ArrayList<DistrictsForTxd> districts = (ArrayList<DistrictsForTxd>) cityForTxd.getDistricts();
                    hh.add(districts);
                }
                options3Items.add(hh);
            }
            return;
        }

        if (requestUrl.endsWith("app_exchange_user") || requestUrl.endsWith("exchange_log")){
            showToast(map.get("message"));
            if (Integer.parseInt(map.get("code"))==1){
                finish();
            }
        }


    }

    @Override
    @OnClick({R.id.chooseShopkeeperLayout, R.id.sexLayout, R.id.ageLayout, R.id.gradeLayout, R.id.addressLayout, R.id.confirmTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chooseShopkeeperLayout:
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid", mSta_mid);
                startActivity(ChooseShopKeeperAty.class, bundle);
                break;
            case R.id.sexLayout:
                showPickerView(titles[0]);
                break;
            case R.id.ageLayout:
                showPickerView(titles[1]);
                break;
            case R.id.gradeLayout:
                showPickerView(titles[2]);
                break;
            case R.id.addressLayout:
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        addressTv.setText(options1Items.get(options1).getProvincename() + options2Items.get(options1).get(option2).getCityname() + options3Items.get(options1).get(option2).get(options3).getDistrictname());
                        mCity_id = options3Items.get(options1).get(option2).get(options3).getDistrict_id();
                    }
                })
                        .setTitleText(titles[3])
                        .setOutSideCancelable(false)
                        .setCancel(new OptionsPickerView.OnOptionsCancelListener() {
                            @Override
                            public void onOptionsCancel(View v) {
                                addressTv.setText("不限");
                                mCity_id = null;
                            }
                        })
                        .build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);
                pvOptions.show();
                break;
            case R.id.confirmTv:
                if (mC_id == null) {
                    if (mShopkeeper_id == null) {
                        showToast("请选择店主");
                        return;
                    }
                    app_exchange_user(mSta_mid, mShopkeeper_id, String.valueOf(mSexNum), String.valueOf(mAgeNum), String.valueOf(mGradeNum), mCity_id, this);
                }else {
                    exchange_log(mSta_mid,"2",mC_id,sexTv.getText().toString(), ageTv.getText().toString(), gradeTv.getText().toString(), mCity_id,this);
                }
                break;
        }
    }


    private void showPickerView(String title) {
        if (title.equals(titles[0])) {
            final List<String> mOptionsItems = new ArrayList<>();
            mOptionsItems.add("不限");
            mOptionsItems.add("男");
            mOptionsItems.add("女");
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    mSexNum = options1;
                    sexTv.setText(mOptionsItems.get(options1));
                }
            })
                    .setTitleText(title)
                    .setOutSideCancelable(false)
                    .setCancel(new OptionsPickerView.OnOptionsCancelListener() {
                        @Override
                        public void onOptionsCancel(View v) {
                            mSexNum = 0;
                            sexTv.setText("不限");
                        }
                    })
                    .build();
            pvOptions.setPicker(mOptionsItems);
            pvOptions.show();
        } else if (title.equals(titles[1])) {
            final List<String> mOptionsItems = new ArrayList<>();
            mOptionsItems.add("不限");
            mOptionsItems.add("18岁以下");
            mOptionsItems.add("18-22岁");
            mOptionsItems.add("23-26岁");
            mOptionsItems.add("27-35岁");
            mOptionsItems.add("35岁以上");
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    mAgeNum = options1;
                    ageTv.setText(mOptionsItems.get(options1));
                }
            })
                    .setTitleText(title)
                    .setOutSideCancelable(false)
                    .setCancel(new OptionsPickerView.OnOptionsCancelListener() {
                        @Override
                        public void onOptionsCancel(View v) {
                            mAgeNum = 0;
                            ageTv.setText("不限");
                        }
                    })
                    .build();
            pvOptions.setPicker(mOptionsItems);
            pvOptions.show();
        } else if (title.equals(titles[2])) {
            final List<String> mOptionsItems = new ArrayList<>();
            mOptionsItems.add("不限");
            mOptionsItems.add("无界会员");
            mOptionsItems.add("无忧会员");
            mOptionsItems.add("优享会员");
            OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    mGradeNum = options1;
                    gradeTv.setText(mOptionsItems.get(options1));
                }
            })
                    .setTitleText(title)
                    .setOutSideCancelable(false)
                    .setCancel(new OptionsPickerView.OnOptionsCancelListener() {
                        @Override
                        public void onOptionsCancel(View v) {
                            mGradeNum = 0;
                            gradeTv.setText("不限");
                        }
                    })
                    .build();
            pvOptions.setPicker(mOptionsItems);
            pvOptions.show();
        }

    }

}

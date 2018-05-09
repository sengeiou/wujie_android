package com.txd.hzj.wjlp.tool.proUrbArea;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.txd.hzj.wjlp.bean.addres.CityForTxd;
import com.txd.hzj.wjlp.bean.addres.DistrictsForTxd;
import com.txd.hzj.wjlp.bean.addres.ProvinceForTxd;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.new_wjyp.http.Freight;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by OTKJ on 2018/5/9.
 */

public class ProUrbAreaUtil implements BaseView {
    private ArrayList<ProvinceForTxd> options1Items = new ArrayList<>();//临时数据存储省份
    private ArrayList<ArrayList<CityForTxd>> options2Items = new ArrayList<>();//临时数据存储城市
    private ArrayList<ArrayList<ArrayList<DistrictsForTxd>>> options3Items = new ArrayList<>();//临时数据存储区域
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    private WeApplication application;
    private static ProUrbAreaUtil proUrbAreaUtil;

    public static ProUrbAreaUtil gainInstance() {
        synchronized (ProUrbAreaUtil.class) {
            if (null == proUrbAreaUtil) {
                proUrbAreaUtil = new ProUrbAreaUtil();
            }
        }
        return proUrbAreaUtil;
    }

    public void checkData(WeApplication application) {
        this.application = application;
        String data = application.getCityProvienceJson();
        if (TextUtils.isEmpty(data)) {
            //获取网络数据
            AddressPst addressPst = new AddressPst(this);
            addressPst.androidAddress();
        }
    }

    private class ProHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_LOAD_SUCCESS: {
//                    专门为滚轮准备
                }
            }

        }
    }

    private void dealJsonData(final String jsonData, final Handler handler) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
   /*
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         */
//        String JsonData = new GetJsonDataUtil().getJson(this, "provinceFotTxd.json");//获取assets目录下的json文件数据
                ArrayList<ProvinceForTxd> jsonBean = parseData(jsonData);//用Gson 转成实体
        /*
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
                options1Items = jsonBean;

                for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
                    ArrayList<CityForTxd> CityList = new ArrayList<>();//该省的城市列表（第二级）
                    ArrayList<ArrayList<DistrictsForTxd>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                    for (int c = 0; c < jsonBean.get(i).getCities().size(); c++) {//遍历该省份的所有城市

                        CityList.add(jsonBean.get(i).getCities().get(c));//添加城市

                        ArrayList<DistrictsForTxd> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                        //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                        if (jsonBean.get(i).getCities().get(c).getDistricts() == null
                                || jsonBean.get(i).getCities().get(c).getDistricts().size() == 0) {
                            City_AreaList.add(new DistrictsForTxd("", ""));
                        } else {
                            for (int d = 0; d < jsonBean.get(i).getCities().get(c).getDistricts().size(); d++) {//该城市对应地区所有数据
                                DistrictsForTxd AreaName = jsonBean.get(i).getCities().get(c).getDistricts().get(d);
                                City_AreaList.add(AreaName);//添加该城市所有地区数据
                            }
                        }
                        Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                    }
            /*
             * 添加城市数据
             */
                    options2Items.add(CityList);
            /*
             * 添加地区数据
             */
                    options3Items.add(Province_AreaList);

                }
                if (null != handler)
                    handler.sendEmptyMessage(MSG_LOAD_SUCCESS);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    private ArrayList<ProvinceForTxd> parseData(String result) { // Gson 解析
        ArrayList<ProvinceForTxd> detail = new ArrayList<>();
        JSONArray data = null;
        try {
            data = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < data.length(); i++) {
            ProvinceForTxd entity = GsonUtil.GsonToBean(data.optJSONObject(i).toString(), ProvinceForTxd.class);
            detail.add(entity);
        }
        return detail;
    }

    public  void ShowPickerView(final TextView tv_chose_ads, final String goods_id) {// 弹出选择器
        String data = application.getCityProvienceJson();
        dealJsonData(data, new ProHandler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_LOAD_SUCCESS: {
//                    专门为滚轮准备
                        OptionsPickerView pvOptions = new OptionsPickerView.Builder(application.getApplicationContext(), new OptionsPickerView
                                .OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                // 省
                                String province = options1Items.get(options1).getPickerViewText();
                                String province_id = options1Items.get(options1).getProvince_id();
                                // 市
                                String city = options2Items.get(options1).get(options2).getPickerViewText();
                                String city_id = options2Items.get(options1).get(options2).getCity_id();
                                // 区
                                String area = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                                String area_id = options3Items.get(options1).get(options2).get(options3).getDistrict_id();
                                // 设置省市区
                                String tx = province + "," + city + "," + area;
                                tv_chose_ads.setText(tx);
                                //选好城市区域之后,从服务器获取运费
                                Freight.freight(goods_id, tx, ProUrbAreaUtil.this);
                            }
                        }).setTitleText("城市选择")
                                .setDividerColor(Color.BLACK)
                                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                                .setContentTextSize(20)
                                .setOutSideCancelable(false)// default is true
                                .build();
                        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                        pvOptions.show();
                    }
                }
            }
        });
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void showDialog(String text) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void removeDialog() {

    }

    @Override
    public void removeContent() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {

    }

    @Override
    public void onException(Exception exception) {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        if (requestUrl.contains("androidAddress")) {
            Log.e(ProUrbAreaUtil.this.getClass().getSimpleName(), jsonStr);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonStr);
                String data = jsonObject.getString("data");
                application.setCityProvience(data);
//                dealJsonData(data, null);
            } catch (JSONException e) {
                e.printStackTrace();

            }
        } else if (requestUrl.contains("freight")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            callBack.freightGetEd(map);
//            freight_tv.setText(map.get("pay"));
//            freight_tv.setTextColor(Color.parseColor("#FD8214"));
//            tv_freight.setText(map.get("pay"));
        }

    }

    CallBack callBack;

    public interface CallBack {
        /**
         * 得到运费
         *
         * @param map
         */
        void freightGetEd(Map<String, String> map);
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {

    }

    @Override
    public void onErrorTip(String tips) {

    }
}

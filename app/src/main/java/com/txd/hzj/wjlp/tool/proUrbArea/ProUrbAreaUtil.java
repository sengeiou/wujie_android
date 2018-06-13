package com.txd.hzj.wjlp.tool.proUrbArea;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.base.BaseActivity;
import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.txd.hzj.wjlp.bean.addres.CityForTxd;
import com.txd.hzj.wjlp.bean.addres.DistrictsForTxd;
import com.txd.hzj.wjlp.bean.addres.ProvinceForTxd;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.http.Freight;
import com.txd.hzj.wjlp.wjyp.BaseAty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * 解析地址库并弹出选择器
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
    private CallBack callBack;

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
                if (null != options1Items && options1Items.size() > 0 && null != options2Items && options2Items.size() > 0 && null != options3Items && options3Items.size() > 0) {
                    if (null != handler)
                        handler.sendEmptyMessage(MSG_LOAD_SUCCESS);
                } else {
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

    private BaseActivity baseActivity;
    /**
     * 丢失数据了
     */
    private boolean getWrong = false;
    private TextView tv_chose_ads;
    private String goods_id;

    public void showPickerView(final TextView tv_chose_ads, final String goods_id, BaseActivity activity, CallBack callback) {// 弹出选择器
        this.baseActivity = activity;
        showDialog();
        if(null==application){
            application= (WeApplication) activity.getApplication();
        }
        String data = application.getCityProvienceJson();
        this.tv_chose_ads = tv_chose_ads;
        this.goods_id = goods_id;
        this.callBack = callback;
        if (!TextUtils.isEmpty(data)) {
            getWrong = false;
            dealJsonData(data, new AreaHandler(activity, tv_chose_ads, goods_id));
        } else {
            getWrong = true;
            checkData(application);
        }

    }

    //记录选中的位置
    private int record_option1, record_option2, record_option3;
    private String province_id, city_id, area_id; // 省、市、区ID
    private String province, city, area; // 省、市、区名称

    private class AreaHandler extends Handler {
        private BaseActivity activity;
        private TextView tv_chose_ads;
        private String goods_id;

        AreaHandler(BaseActivity activity, TextView tv_chose_ads, String goods_id) {
            this.activity = activity;
            this.tv_chose_ads = tv_chose_ads;
            this.goods_id = goods_id;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            removeDialog();
            switch (msg.what) {
                case MSG_LOAD_SUCCESS: {
//                    专门为滚轮准备
                    OptionsPickerView pvOptions = new OptionsPickerView.Builder(activity, new OptionsPickerView
                            .OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            // 省
                            province = options1Items.get(options1).getPickerViewText();
                            province_id = options1Items.get(options1).getProvince_id();
                            // 市
                            city = options2Items.get(options1).get(options2).getPickerViewText();
                            city_id = options2Items.get(options1).get(options2).getCity_id();
                            // 区
                            area = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                            area_id = options3Items.get(options1).get(options2).get(options3).getDistrict_id();
                            // 设置省市区
                            StringBuffer tx = new StringBuffer();
                            tx.append(province);
                            tx.append(",");
                            tx.append(city);
                            tx.append(",");
                            tx.append(area);
                            //文字过长的处理
                            tv_chose_ads.setText(tx);
                            record_option1 = options1;
                            record_option2 = options2;
                            record_option3 = options3;
                            //选好城市区域之后,从服务器获取运费
                            if (!TextUtils.isEmpty(goods_id))
                                Freight.freight(goods_id, tx.toString(), ProUrbAreaUtil.this);
                            if (null != getData) {
                                getData.getAddress();
                            }
                        }
                    }).setTitleText("城市选择")
                            .setDividerColor(Color.BLACK)
                            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                            .setContentTextSize(20)
                            .setOutSideCancelable(false)// default is true
                            .build();
                    pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                    pvOptions.setSelectOptions(record_option1, record_option2, record_option3);
                    pvOptions.show();
                    pvOptions.setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(Object o) {
                            removeDialog();
                        }
                    });
                }
            }
        }
    }


    public void setGetData(GetData getData) {
        this.getData = getData;
    }

    private GetData getData;

    public interface GetData {
        public void getAddress();
    }

    /**
     * 获取省ID
     *
     * @return
     */
    public String getProvince_id() {
        return (province_id == null || province_id.isEmpty()) ? "" : province_id;
    }

    /**
     * 设置省ID
     *
     * @param province_id
     */
    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    /**
     * 获取市ID
     *
     * @return
     */
    public String getCity_id() {
        return (city_id == null || city_id.isEmpty()) ? "" : city_id;
    }

    /**
     * 设置市ID
     *
     * @param city_id
     */
    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    /**
     * 获取区县ID
     *
     * @return
     */
    public String getArea_id() {
        return (area_id == null || area_id.isEmpty()) ? "" : area_id;
    }

    /**
     * 设置区县ID
     *
     * @param area_id
     */
    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    /**
     * 获取省名称
     *
     * @return
     */
    public String getProvince() {
        return (province == null || province.isEmpty()) ? "" : province;
    }

    /**
     * 设置省名称
     *
     * @param province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取市名称
     *
     * @return
     */
    public String getCity() {
        return (city == null || city.isEmpty()) ? "" : city;
    }

    /**
     * 设置市名称
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取区县名称
     *
     * @return
     */
    public String getArea() {
        return (area == null || area.isEmpty()) ? "" : area;
    }

    /**
     * 设置区县名称
     *
     * @param area
     */
    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public void showDialog() {
        if (null != baseActivity)
            baseActivity.showDialog();
    }

    @Override
    public void showDialog(String text) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void removeDialog() {
        if (null != baseActivity)
            baseActivity.removeProgressDialog();
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
                if (getWrong&&null!=baseActivity&&null!=tv_chose_ads) {
                    dealJsonData(data, new AreaHandler(baseActivity, tv_chose_ads, goods_id) );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (requestUrl.contains("freight")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (null != callBack)
                callBack.freightGetEd(map);
//            freight_tv.setText(map.get("pay"));
//            freight_tv.setTextColor(Color.parseColor("#FD8214"));
//            tv_freight.setText(map.get("pay"));
        }

    }


    @Override
    public void onError(String requestUrl, Map<String, String> error) {

    }

    @Override
    public void onErrorTip(String tips) {

    }

    public interface CallBack {
        /**
         * 得到运费
         *
         * @param map
         */
        void freightGetEd(Map<String, String> map);
    }
}

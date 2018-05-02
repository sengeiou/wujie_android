package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.new_wjyp.http.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 积分兑换首页
 * 网络框架：RxJAVA
 */
public class IntegralAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.layout_bottom_tv)
    private TextView layout_bottom_tv;
    @ViewInject(R.id.tv_date)
    private TextView tv_date;
    @ViewInject(R.id.tv_point_num)
    TextView tv_point_num;
    @ViewInject(R.id.tv1)
    TextView tv1;
    @ViewInject(R.id.tv2)
    TextView tv2;
    @ViewInject(R.id.cb)
    CheckBox cb;
    @ViewInject(R.id.exchange_money_tv)
    TextView exchange_money_tv;
    @ViewInject(R.id.layout_show_down)
    private LinearLayout layout_show_down;
    @ViewInject(R.id.layout_zidongduihuan)
    private LinearLayout layout_zidongduihuan;
    private boolean isYouXiang; // 当前是否是优享会员

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("积分");
        download();
        setDownVisibility();

    }

    /**
     * 访问网络获取数据，RxJava框架
     */
    private void download() {

        Map<String, Object> parameters = new HashMap<String, Object>();
//        parameters.addHeader("token", Config.getToken());
        parameters.put("token", Config.getToken());
        new Novate.Builder(this)
                .baseUrl(Config.BASE_URL)
                .addHeader(parameters)
                .build()
                .rxPost("User/myIntegral", parameters, new RxStringCallback() {

                    @Override
                    public void onNext(Object tag, String response) {
                        L.e("wang", "==========>>>>>>>IntegralAty===response:" + response);
//                        Toast.makeText(SortCityActivity.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");

                            JSONObject jsonObject2 = new JSONObject(data);
                            String my_integral = jsonObject2.getString("my_integral");
                            String integral_percentage = jsonObject2.getString("integral_percentage");
                            layout_bottom_tv.setText(my_integral);
                            String time_out_status = jsonObject2.getString("time_out_status");
                            String exchange_status = jsonObject2.getString("exchange_status");
                            if (time_out_status.equals("1")) {
                                exchange_money_tv.setText("暂时不可兑换");
                                exchange_money_tv.setEnabled(false);
                                exchange_money_tv.setBackgroundResource(R.drawable.shape2);
                            } else if (time_out_status.equals("0") && exchange_status.equals("1")) {
                                exchange_money_tv.setText("今日不可兑换");
                                exchange_money_tv.setEnabled(false);
                                exchange_money_tv.setBackgroundResource(R.drawable.shape2);
                            } else if (time_out_status.equals("0") && exchange_status.equals("0")) {
                                exchange_money_tv.setText("今日可以兑换");
                                exchange_money_tv.setEnabled(true);
                            }

                            String status = jsonObject2.getString("status");
                            if (status.equals("1")){
                                // 如果是优享会员并且自动兑换状态为打开
                                cb.setChecked(true);
                            }
//                            if (status.equals("1")) {
//                                cb.setChecked(true);
////                                cb.setBackgroundResource(R.drawable.icon_auto_open_hzj);
//                            } else {
//                                cb.setChecked(false);
////                                cb.setBackgroundResource(R.drawable.icon_auto_close_hzj);
//                            }
                            JSONArray jsonArray = jsonObject2.getJSONArray("point_list");
                            ArrayList<String> list = new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String my_change_integral = jsonObject1.getString("my_change_integral");

                                String point_num = jsonObject1.getString("point_num");
                                String change = jsonObject1.getString("change");
                                String date = jsonObject1.getString("date");
                                String point_desc = jsonObject1.getString("point_desc");

                                date.replace("~", "-");
//                                layout_bottom_tv.setText(my_change_integral);
                                tv_point_num.setText("无界指数：" + point_num);
                                tv1.setText(point_desc == null ? "" : point_desc);
//                                tv1.setText("积分兑换额度=积分总额*无界指数，实际可兑换额度按10的倍数向下取整，最低兑换额度不小于10积分。");
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
                                Date date1 = new Date(System.currentTimeMillis());
                                String time = simpleDateFormat.format(date1);
                                tv_date.setText(time);
                                tv2.setText("提示：今日若不兑换，此兑换机会将作废" + "\n兑换时间：" + date);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {

                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {

                    }

                });

    }

    /**
     * 获取后台个人信息，设置控件的显示隐藏
     * <p>
     * 访问网络获取数据，RxJava框架
     */
    private void setDownVisibility() {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("token", Config.getToken());
        new Novate.Builder(this)
                .baseUrl(Config.BASE_URL)
                .addHeader(parameters)
                .build()
                .rxPost("User/userCenter", parameters, new RxStringCallback() {

                    @Override
                    public void onNext(Object tag, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject data = jsonObject.getJSONObject("data");
                            int user_card_type = Integer.parseInt(data.getString("user_card_type"));
                            switch (user_card_type) {
                                case 1:
                                    layout_show_down.setVisibility(View.GONE);
                                    break;
                                case 2:
                                    layout_zidongduihuan.setVisibility(View.GONE);
                                    break;
                                case 3:
                                    isYouXiang = true;
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {
                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {
                    }

                });

    }

    @Override
    @OnClick({R.id.check_details_tv, R.id.exchange_money_tv, R.id.cb})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.check_details_tv:// 积分明细
                Bundle bundle = new Bundle();
                bundle.putInt("from", 2);
                startActivity(ParticularsUsedByTricketAty.class, bundle);
                break;
            case R.id.exchange_money_tv:// 确认兑换
                bundle = new Bundle();
                bundle.putInt("to", 1);
                startActivity(ExchangeMoneyAty.class, bundle);
                break;
            case R.id.cb:
//                cb.setChecked(!cb.isClickable());
                User.changeIntegralStatus(IntegralAty.this, cb.isChecked() ? "1" : "0");
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_integral;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
    }

    Map<String, String> map;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {

        super.onComplete(requestUrl, jsonStr);
        L.e(jsonStr);

        L.e("wang", "==========jsonStr>>>>>>>IntegralAty===response:" + jsonStr);

        if (requestUrl.contains("myIntegral")) {
            map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            String time_out_status = map.get("time_out_status");
            String exchange_status = map.get("exchange_status");

            String status = map.get("status");
            if (isYouXiang && status.equals("1")){
                // 如果是优享会员并且自动兑换状态为打开
                cb.setChecked(true);
            }

            if (time_out_status.equals("1")) {

            }
//            layout_bottom_tv.setText(map.get("my_integral"));
            map = JSONUtils.parseKeyAndValueToMap(map.get("point_list"));

            tv_date.setText(map.get("date"));

        }

        if (requestUrl.contains("changeIntegralStatus")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if (map.get("code").equals("1")) {
                Toast.makeText(IntegralAty.this, map.get("message"), Toast.LENGTH_SHORT).show();
            } else {
                cb.setChecked(!cb.isChecked());
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("积分");
        download();
        setDownVisibility();
    }
}

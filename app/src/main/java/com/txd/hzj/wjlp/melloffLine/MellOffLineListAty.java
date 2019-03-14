package com.txd.hzj.wjlp.melloffLine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.util.PreferencesUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.DemoApplication;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;
import com.txd.hzj.wjlp.bean.offline.OffLineListBean;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.melloffLine.adapter.MellOffLineListAdapter;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/31 11:35
 * 功能描述：线下店铺列表页
 */
public class MellOffLineListAty extends BaseAty{

    @ViewInject(R.id.search_title_layout)
    private RelativeLayout search_title_layout;

    @ViewInject(R.id.search_title_be_back_iv)
    public ImageView search_title_be_back_iv;

    @ViewInject(R.id.title_search_tv)
    public TextView title_search_tv;

    @ViewInject(R.id.search_lin_layout)
    public LinearLayout search_lin_layout;

    @ViewInject(R.id.search_title_right_tv)
    public TextView search_title_right_tv;
    @ViewInject(R.id.search_type_tv)
    public TextView search_type_tv;

    @ViewInject(R.id.message_layout)
    public FrameLayout message_layout;

    @ViewInject(R.id.title_search_ev)
    public EditText title_search_ev;

    /**
     * 商家列表
     */
    @ViewInject(R.id.mell_lv)
    private PullToRefreshListView mell_lv;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    //店铺名称
    private String mKeyword;
    private int p;
    private String his_str;
    private StringBuilder sb;
    private int allNum = 0;
    private List<OffLineListBean.DataBean> mells;

    private MellOffLineListAdapter mlAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.search_title_layout);
        forTitle();

        mell_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;

                OfflineStore.offlinesearch(mKeyword, String.valueOf(p),MellOffLineListAty.this);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (allNum <= mells.size()) {
                    mell_lv.onRefreshComplete();
                    return;
                }
                p++;
                OfflineStore.offlinesearch(mKeyword, String.valueOf(p),MellOffLineListAty.this);
            }
        });

        mell_lv.setEmptyView(no_data_layout);

        forKeyboardSearch();
        mell_lv.setAdapter(mlAdapter);
    }

    private void forTitle() {
        search_title_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        search_title_be_back_iv.setVisibility(View.VISIBLE);
        title_search_tv.setVisibility(View.GONE);
        search_lin_layout.setVisibility(View.VISIBLE);
        message_layout.setVisibility(View.GONE);
        search_title_right_tv.setVisibility(View.VISIBLE);
        title_search_ev.setText(mKeyword);
        search_type_tv.setText("商品");
    }

    /**
     * 键盘搜索
     */
    private void forKeyboardSearch() {
        title_search_ev.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    forSearch();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_list;
    }

    @Override
    protected void initialized() {
        title_search_tv.setHint("搜索商家");
        mKeyword = getIntent().getStringExtra("keyword");
        p=1;
    }

    @Override
    protected void requestData() {
        OfflineStore.offlinesearch(mKeyword, String.valueOf(p),this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        OffLineListBean offLineListBean = JSON.parseObject(jsonStr, OffLineListBean.class);
        if (offLineListBean.getCode().equals("1")){
            mells=offLineListBean.getData();
            allNum = Integer.parseInt(offLineListBean.getNums());
            mlAdapter = new MellOffLineListAdapter(MellOffLineListAty.this, mells);
            mlAdapter.setOnItemClickListener(new MellOffLineListAdapter.OnItemClickListener() {
                @Override
                public void itemClick(int position) {
                    OffLineListBean.DataBean dataBean = mells.get(position);
                    Intent intent = new Intent(MellOffLineListAty.this, ShopMallDetailsAty.class);
                    String goods_num = dataBean.getGoods_num();
                    Bundle bundle = new Bundle();
                    if (!TextUtils.isEmpty(goods_num) && Integer.parseInt(goods_num) > 0) {
                        StringBuffer stringBuffer = new StringBuffer();
                        if (Config.OFFICIAL_WEB.contains("api")) {
                            stringBuffer.append("http://www.wujiemall.com/");
                        } else {
                            stringBuffer.append(Config.OFFICIAL_WEB);
                        }
                        stringBuffer.append("Wap/OfflineStore/offlineShop/os_type/1/merchant_id/");
                        stringBuffer.append(dataBean.getS_id());
                        if (Config.isLogin()) {
                            stringBuffer.append("/invite_code/");
                            stringBuffer.append(PreferencesUtils.getString(MellOffLineListAty.this, "invite_code"));
                        }
                        stringBuffer.append(".html");

                        bundle.putString("url", stringBuffer.toString());
                        startActivity(WebViewAty.class, bundle);
                    } else {
                        OffLineDataBean offLineDataBea = new OffLineDataBean();
                        offLineDataBea.setS_id(dataBean.getMerchant_id());
                        offLineDataBea.setMerchant_name(dataBean.getMerchant_name());
                        offLineDataBea.setMerchant_desc(dataBean.getMerchant_desc());
                        offLineDataBea.setLogo(dataBean.getLogo());
                        offLineDataBea.setScore(dataBean.getScore());
                        offLineDataBea.setMonths_order(dataBean.getMonths_order());
                        offLineDataBea.setDistance("");
                        Map<String, String> locInfo = DemoApplication.getInstance().getLocInfo();
                        offLineDataBea.setLat(locInfo.containsKey("lat") ? locInfo.get("lat") : "");
                        offLineDataBea.setLng(locInfo.containsKey("lon") ? locInfo.get("lon") : "");
                        offLineDataBea.setProportion("");
                        offLineDataBea.setShow(false);
                        offLineDataBea.setTicket(null);
                        offLineDataBea.setUser_id("");
                        bundle.putSerializable("mellInfo", offLineDataBea);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            });
            mell_lv.setAdapter(mlAdapter);
            mell_lv.onRefreshComplete();
        }

    }


    @Override
    @OnClick({R.id.search_title_right_tv, R.id.search_type_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.search_title_right_tv:
                forSearch();
                break;
            case R.id.search_type_tv:
                finish();
                break;
        }
    }

    // 搜索
    private void forSearch() {
        hideKeyBoard();
        String key = title_search_ev.getText().toString();
        if (key.equals("")) {
            showErrorTip("请输入搜索关键词");
            return;
        }
        his_str = PreferencesUtils.getString(this, "offlinehistory", "");
        if (!his_str.contains(key)) {
            sb = new StringBuilder();
            sb.append(key).append(",").append(his_str);
            PreferencesUtils.putString(this, "offlinehistory", sb.toString());
        }
        mKeyword = key;
        p = 1;
        requestData();
    }
}

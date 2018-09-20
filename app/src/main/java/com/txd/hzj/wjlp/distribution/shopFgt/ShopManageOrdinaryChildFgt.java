package com.txd.hzj.wjlp.distribution.shopFgt;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.AppManager;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopManageOrdinaryAdapter;
import com.txd.hzj.wjlp.distribution.bean.DistributionGoodsBean;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;
import com.txd.hzj.wjlp.distribution.shopaty.ShopGoodsManage;
import com.txd.hzj.wjlp.mellonLine.gridClassify.ToShareAty;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：wjj
 * 创建时间：2018-06-11 上午 11:02
 * 功能描述： 普通商品管理界面
 */
@SuppressLint("ValidFragment")
public class ShopManageOrdinaryChildFgt extends BaseFgt implements View.OnClickListener {

    /**
     * Fragment标示
     * 0 为出售中
     * 1 为已下架
     * 2 是已售罄
     */
    private int from;
    private List<DistributionGoodsBean.DataBean> list;
    private ShopManageOrdinaryAdapter adapter;

    @ViewInject(R.id.empty_layout)
    private FrameLayout emptyView;
    @ViewInject(R.id.shopManageOrdinaryChild_data_lv)
    private ListView shopManageOrdinaryChild_data_lv;
    @ViewInject(R.id.shopManageOrdinaryChild_batchManagement_tv)
    private TextView shopManageOrdinaryChild_batchManagement_tv;

    // 全选布局
    @ViewInject(R.id.shopManageOrdinaryChild_edit_lLayout)
    private LinearLayout shopManageOrdinaryChild_edit_lLayout;
    // 全选复选框
    @ViewInject(R.id.shopManageOrdinaryChild_selectAll_cbox)
    private CheckBox shopManageOrdinaryChild_selectAll_cbox;
    // 上架产品
    @ViewInject(R.id.shopManageOrdinaryChild_addForShelves_lLayout)
    private LinearLayout shopManageOrdinaryChild_addForShelves_lLayout;
    // 下架产品
    @ViewInject(R.id.shopManageOrdinaryChild_removeForShelves_lLayout)
    private LinearLayout shopManageOrdinaryChild_removeForShelves_lLayout;
    // 删除
    @ViewInject(R.id.shopManageOrdinaryChild_delete_lLayout)
    private LinearLayout shopManageOrdinaryChild_delete_lLayout;

    // 列表刷新加载布局
    @ViewInject(R.id.shopManageOrdinaryChild_sr_layout)
    private SuperSwipeRefreshLayout shopManageOrdinaryChild_sr_layout;

    private int p = 1; // 请求的分页
    // Header View
    private RelativeLayout head_container;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;
    private ShopExhibitPst mExhibitPst;
    private String mShop_id;

    //是否是上架下架接口
    private boolean isManage = false;

    public ShopManageOrdinaryChildFgt(int index) {
        from = index;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_shopmanage_ordinary_child;
    }

    @Override
    protected void immersionInit() {
        if (isVisible()) {
            shopManageOrdinaryChild_batchManagement_tv.setVisibility(View.VISIBLE);
            shopManageOrdinaryChild_selectAll_cbox.setChecked(false);
        }
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        shopManageOrdinaryChild_sr_layout.setRefreshing(true);
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        mExhibitPst = new ShopExhibitPst(this);
        if (PreferencesUtils.containKey(getActivity(), "shop_id")) {
            mShop_id = PreferencesUtils.getString(getActivity(), "shop_id");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shopManageOrdinaryChild_sr_layout.setHeaderView(createHeaderView());// add headerView
        shopManageOrdinaryChild_sr_layout.setFooterView(createFooterView());
        shopManageOrdinaryChild_sr_layout.setHeaderViewBackgroundColor(Color.WHITE);
        shopManageOrdinaryChild_sr_layout.setTargetScrollWithLayout(true);
        shopManageOrdinaryChild_sr_layout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                getData();
                if (shopManageOrdinaryChild_batchManagement_tv.getVisibility()==View.GONE){
                    shopManageOrdinaryChild_batchManagement_tv.setVisibility(View.VISIBLE);
                    shopManageOrdinaryChild_selectAll_cbox.setChecked(false);
                    ShopGoodsManage shopGoodsManage = (ShopGoodsManage) getActivity();
                    shopGoodsManage.setTitltRightVisibility(false);
                }
            }
            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        shopManageOrdinaryChild_sr_layout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                getData();
                if (shopManageOrdinaryChild_batchManagement_tv.getVisibility()==View.GONE){
                    shopManageOrdinaryChild_batchManagement_tv.setVisibility(View.VISIBLE);
                    shopManageOrdinaryChild_selectAll_cbox.setChecked(false);
                    ShopGoodsManage shopGoodsManage = (ShopGoodsManage) getActivity();
                    shopGoodsManage.setTitltRightVisibility(false);
                    adapter.setShowCbox(false);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onPushDistance(int distance) {
            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });
    }

    @Override
    protected void requestData() {
        shopManageOrdinaryChild_batchManagement_tv.setOnClickListener(this);

        switch (from) {
            case 0: // 出售中
                shopManageOrdinaryChild_addForShelves_lLayout.setVisibility(View.GONE); // 上架按钮隐藏
                break;
            case 1: // 已下架
                shopManageOrdinaryChild_removeForShelves_lLayout.setVisibility(View.GONE); // 下架按钮隐藏
                break;
            case 2: // 已售罄
                shopManageOrdinaryChild_addForShelves_lLayout.setVisibility(View.GONE); // 上架按钮隐藏
                shopManageOrdinaryChild_removeForShelves_lLayout.setVisibility(View.GONE); // 下架按钮隐藏
                break;
        }
        if (isVisible()) {
            p=1;
            getData();
        }

    }

    public void sendMessage(){
        if (isVisible()) {
            p=1;
            getData();
        }
    }

    private void getData() {
        if (null != mExhibitPst && !TextUtils.isEmpty(mShop_id)) {
            if (from == 2) {
                from += 1;
            }
            mExhibitPst.getGoodsList("", String.valueOf(p), mShop_id, String.valueOf(from));
            isManage = false;
        }
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (!isManage) {
            DistributionGoodsBean distributionGoodsBean = JSONObject.parseObject(jsonStr, DistributionGoodsBean.class);
            if (200 == distributionGoodsBean.getCode()) {
                if (p == 1) {
                    list.clear();
                    list.addAll(distributionGoodsBean.getData());
                    if (null != list && list.size() > 0) {
                        emptyView.setVisibility(View.GONE);
                        shopManageOrdinaryChild_sr_layout.setVisibility(View.VISIBLE);
                        adapter = new ShopManageOrdinaryAdapter(getActivity(), list, shopManageOrdinaryChild_selectAll_cbox);
                        adapter.setOnImageClickListener(new ShopManageOrdinaryAdapter.ImageClick() {
                            @Override
                            public void onImageClick(View view, int position) {
                                //http://test3.wujiemall.com/Distribution/DistributionShop/shop/g_id/676/shop_id/ZAAADU.html
                                DistributionGoodsBean.DataBean goodsBean = list.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putString("title", goodsBean.getGoods_name());
                                bundle.putString("pic", goodsBean.getGoods_img());
                                String shop_id_jiami = PreferencesUtils.getString(AppManager.getInstance().getTopActivity(), "shop_id_jiami");
                                bundle.putString("url", Config.SHARE_URL + "Distribution/DistributionShop/shop/g_id/" + goodsBean.getGoods_id() + "/shop_id/" + shop_id_jiami + ".html");
                                bundle.putString("context", goodsBean.getGoods_brief());
                                bundle.putString("id", goodsBean.getDsg_id());
                                bundle.putString("Shapetype", "6");
                                startActivity(ToShareAty.class, bundle);
                            }
                        });
                        shopManageOrdinaryChild_data_lv.setAdapter(adapter);
                    } else {
                        emptyView.setVisibility(View.VISIBLE);
                        shopManageOrdinaryChild_sr_layout.setVisibility(View.GONE);
                    }
                } else {
                    list.addAll(distributionGoodsBean.getData());
                    adapter.notifyDataSetChanged();
                }


            }
        } else {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            if (jsonObject.containsKey("code") && "200".equals(jsonObject.getString("code"))) {
                showToast("操作成功");
            }
        }
        refreshComplete();
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        removeProgressDialog();
        refreshComplete();
        emptyView.setVisibility(View.VISIBLE);
        shopManageOrdinaryChild_sr_layout.setVisibility(View.GONE);
    }

    private void refreshComplete() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            shopManageOrdinaryChild_sr_layout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility() == View.VISIBLE) {
            shopManageOrdinaryChild_sr_layout.setLoadMore(false);
            progressBar.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.shopManageOrdinaryChild_batchManagement_tv, R.id.shopManageOrdinaryChild_edit_lLayout, R.id.shopManageOrdinaryChild_selectAll_cbox,
            R.id.shopManageOrdinaryChild_addForShelves_lLayout, R.id.shopManageOrdinaryChild_removeForShelves_lLayout, R.id.shopManageOrdinaryChild_delete_lLayout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.shopManageOrdinaryChild_batchManagement_tv: // 批量管理
                ShopGoodsManage shopGoodsManage = (ShopGoodsManage) getActivity();
                shopGoodsManage.setTitltRightText("完成"); // 设置显示字体
                // 设置右侧按钮的显隐
                final TextView titleRightTv = shopGoodsManage.setTitltRightVisibility(true); // 设置显示之后返回设置的控件
                titleRightTv.setOnClickListener(new View.OnClickListener() { // 对返回的控件进行点击处理
                    @Override
                    public void onClick(View v) {
                        titleRightTv.setVisibility(View.GONE);
                        shopManageOrdinaryChild_batchManagement_tv.setVisibility(View.VISIBLE); // 显示批量管理按钮
                        adapter.setShowCbox(false); // 隐藏列表中的复选框
                        adapter.notifyDataSetChanged(); // 刷新Adapter数据
                    }
                });
                shopManageOrdinaryChild_batchManagement_tv.setVisibility(View.GONE); // 隐藏批量管理按钮
                adapter.setShowCbox(true); // 显示列表中的复选框
                adapter.notifyDataSetChanged(); // 刷新Adapter数据
                break;
            case R.id.shopManageOrdinaryChild_edit_lLayout: // 全选
            case R.id.shopManageOrdinaryChild_selectAll_cbox: // 全选
                for (DistributionGoodsBean.DataBean distributionGoodsBean : list) {
                    distributionGoodsBean.setChecked(shopManageOrdinaryChild_selectAll_cbox.isChecked());
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.shopManageOrdinaryChild_addForShelves_lLayout: // 上架
                showOperation("确定要上架选择的商品吗？", "提示", "确认", "取消", 1);
                break;
            case R.id.shopManageOrdinaryChild_removeForShelves_lLayout: // 下架
                showOperation("确定要下架选择的商品吗？", "提示", "确认", "取消", 2);
                break;
            case R.id.shopManageOrdinaryChild_delete_lLayout: // 删除
                showOperation("确定要删除选择的商品吗？", "提示", "确认", "取消", 3);
                break;
        }
    }


    /**
     * 上下架产品及删除产品对话框
     *
     * @param messageStr 显示的正文消息
     * @param titleStr   显示的标题
     * @param okStr      确认按钮显示文字
     * @param noStr      取消按钮显示的文字
     * @param type       1：上架；2：下架；3：删除
     */
    private void showOperation(String messageStr, String titleStr, String okStr, String noStr, final int type) {
        new MikyouCommonDialog(getActivity(), messageStr, titleStr, okStr, noStr, true)
                .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                        switch (btnType) {
                            case MikyouCommonDialog.OK: { // 上架，下架，删除
                                List<DistributionGoodsBean.DataBean> chuLiList = new ArrayList<>();
                                List<String> ids = new ArrayList<>();
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).isChecked()) {
                                        chuLiList.add(list.get(i));
                                        ids.add(list.get(i).getDsg_id());
                                    }
                                }
                                if (null != ids && ids.size() > 0) {
                                    list.removeAll(chuLiList);
                                    if (1 == type) {
                                        // 上架商品
                                        chuliGoods(ids, "0");
                                    } else if (2 == type) {
                                        // 下架商品
                                        chuliGoods(ids, "1");
                                    } else if (3 == type) {
                                        // 删除商品
                                        chuliGoods(ids, "9");
                                    }
                                    if (list.size() > 0) {
                                        emptyView.setVisibility(View.GONE);
                                        shopManageOrdinaryChild_sr_layout.setVisibility(View.VISIBLE);
                                        adapter.setCheckedCount();
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                p = 1;
                                                getData();
                                            }
                                        },200);
                                        if (shopManageOrdinaryChild_batchManagement_tv.getVisibility()==View.GONE){
                                            shopManageOrdinaryChild_batchManagement_tv.setVisibility(View.VISIBLE);
                                            shopManageOrdinaryChild_selectAll_cbox.setChecked(false);
                                            ShopGoodsManage shopGoodsManage = (ShopGoodsManage) getActivity();
                                            shopGoodsManage.setTitltRightVisibility(false);
                                        }
                                    }

                                }

                            }
                            break;
                            case MikyouCommonDialog.NO: {
                            }
                            break;
                        }
                    }
                }).showDialog();
    }

    private void chuliGoods(List<String> ids, String type) {
        if (null != mExhibitPst) {
            isManage = true;
            mExhibitPst.goodsManage(ids, type);
        }
    }

    /**
     * 创建底部加载布局
     *
     * @return
     */
    private View createFooterView() {
        View footerView = LayoutInflater.from(shopManageOrdinaryChild_sr_layout.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    /**
     * 创建头部加载布局
     *
     * @return
     */
    private View createHeaderView() {
        View headerView = LayoutInflater.from(shopManageOrdinaryChild_sr_layout.getContext()).inflate(R.layout.layout_head, null);
        head_container = headerView.findViewById(R.id.head_container);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

}

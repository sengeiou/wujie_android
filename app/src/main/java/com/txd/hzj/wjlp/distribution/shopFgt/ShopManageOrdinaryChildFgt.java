package com.txd.hzj.wjlp.distribution.shopFgt;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopManageOrdinaryAdapter;
import com.txd.hzj.wjlp.distribution.bean.DistributionGoodsBean;
import com.txd.hzj.wjlp.distribution.shopAty.ShopGoodsManage;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.ToShareAty;
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
    private List<DistributionGoodsBean> list;
    private ShopManageOrdinaryAdapter adapter;

    @ViewInject(R.id.emptyView)
    private View emptyView;
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

    public ShopManageOrdinaryChildFgt(int index) {
        from = index;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_shopmanage_ordinary_child;
    }
    @Override
    protected void immersionInit() {
        shopManageOrdinaryChild_data_lv.setEmptyView(emptyView);
        if (isVisible()){
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

    }


    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        removeProgressDialog();
        shopManageOrdinaryChild_sr_layout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerProgressBar.setVisibility(View.GONE);
        shopManageOrdinaryChild_sr_layout.setLoadMore(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        // TODO 模拟数据，后续请求数据改动的话需要一并修改的文件有：DistributionGoodsBean（商品对象）、ShopManageOrdinaryAdapter（List的Adapter）
        list = new ArrayList<>();
        DistributionGoodsBean distributionGoodsBean;
        for (int i = 0; i < 10; i++) {
            distributionGoodsBean = new DistributionGoodsBean();
            distributionGoodsBean.set_id(i);
            distributionGoodsBean.setImageUrl("https://gd1.alicdn.com/imgextra/i1/646527539/TB2goIfbiMnBKNjSZFCXXX0KFXa_!!646527539.jpg_400x400.jpg");
            distributionGoodsBean.setGoodsName((from == 0 ? "出售中" : from == 1 ? "已下架" : "已售罄") + i);
            distributionGoodsBean.setDaijinquan("最多可用50%代金券");
            distributionGoodsBean.setMeny("1380.00");
            distributionGoodsBean.setJifen("10.00");
            distributionGoodsBean.setChecked(false);
            list.add(distributionGoodsBean);
        }
        adapter = new ShopManageOrdinaryAdapter(getActivity(), list, shopManageOrdinaryChild_selectAll_cbox);
        adapter.setOnImageClickListener(new ShopManageOrdinaryAdapter.ImageClick() {
            @Override
            public void onImageClick(View view, int position) {
                //分享功能，可以使用ToShareAty  toShare("无界优品", share_img, share_url, share_content, goods_id, "1");
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                DistributionGoodsBean goodsBean = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("title", goodsBean.getGoodsName());
                bundle.putString("pic", goodsBean.getImageUrl());
                bundle.putString("url", "1");
                bundle.putString("context", goodsBean.getGoodsName());
                bundle.putString("id", "1");
                bundle.putString("Shapetype", "1");
                startActivity(ToShareAty.class, bundle);
            }
        });
        shopManageOrdinaryChild_data_lv.setAdapter(adapter);

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
                if (from == 0) {
                    // TODO 请求接口
                } else if (from == 1) {
                    // TODO 请求接口
                } else if (from == 2) {
                    // TODO 请求接口
                }

//                shopManageOrdinaryChild_sr_layout.setRefreshing(false);
//                progressBar.setVisibility(View.GONE);

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
                if (from == 0) {
                    // TODO 请求接口
                } else if (from == 1) {
                    // TODO 请求接口
                } else if (from == 2) {
                    // TODO 请求接口
                }
                shopManageOrdinaryChild_sr_layout.setLoadMore(false);
                progressBar.setVisibility(View.GONE);
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

        L.e(from == 0 ? "出售中" : from == 1 ? "已下架" : "已售罄");
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
                for (DistributionGoodsBean distributionGoodsBean : list) {
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
                                // TODO ================================ 在此进行数据商品上下架以及删除数据的后台请求 ================================
                                for (DistributionGoodsBean distributionGoodsBean : list) {
                                    L.e(distributionGoodsBean.toString());
                                }
                                switch (type) {
                                    case 1: // 上架商品
                                        break;
                                    case 2: // 下架商品
                                        break;
                                    case 3: // 删除商品
                                        List<DistributionGoodsBean> deleteList=new ArrayList<>();
                                        for (int i = 0; i < list.size(); i++) {
                                            if (list.get(i).isChecked()){
                                                deleteList.add(list.get(i));
                                            }
                                        }
                                        list.removeAll(deleteList);
                                        emptyView.setVisibility(list.size()==0?View.VISIBLE:View.GONE);
                                        adapter.notifyDataSetChanged();
                                        break;
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

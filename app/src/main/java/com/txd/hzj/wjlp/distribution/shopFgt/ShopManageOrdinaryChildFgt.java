package com.txd.hzj.wjlp.distribution.shopFgt;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopManageOrdinaryAdapter;
import com.txd.hzj.wjlp.distribution.bean.DistributionGoodsBean;

import java.util.ArrayList;
import java.util.List;

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

    public ShopManageOrdinaryChildFgt(int index) {
        from = index;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_shopmanage_ordinary_child;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        shopManageOrdinaryChild_batchManagement_tv.setOnClickListener(this);

        // TODO 模拟数据，后续请求数据改动的话需要一并修改的文件有：DistributionGoodsBean（商品对象）、ShopManageOrdinaryAdapter（List的Adapter）
        list = new ArrayList<>();
        DistributionGoodsBean distributionGoodsBean;
        for (int i = 0; i < 10; i++) {
            distributionGoodsBean = new DistributionGoodsBean();
            distributionGoodsBean.set_id(i);
            distributionGoodsBean.setImageUrl("https://gd1.alicdn.com/imgextra/i1/646527539/TB2goIfbiMnBKNjSZFCXXX0KFXa_!!646527539.jpg_400x400.jpg");
            distributionGoodsBean.setGoodsName("测试商品" + i);
            distributionGoodsBean.setDaijinquan("最多可用50%代金券");
            distributionGoodsBean.setMeny("1380.00");
            distributionGoodsBean.setJifen("10.00");
            distributionGoodsBean.setChecked(false);
            list.add(distributionGoodsBean);
        }
        adapter = new ShopManageOrdinaryAdapter(getActivity(), list, shopManageOrdinaryChild_selectAll_cbox);
        shopManageOrdinaryChild_data_lv.setAdapter(adapter);

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

    @Override
    protected void immersionInit() {
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


}

package com.txd.hzj.wjlp.minetoAty.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.tools.MoneyUtils;
import com.ants.theantsgo.util.CompressionUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.order.adapter.GridImageAdapter;
import com.txd.hzj.wjlp.minetoAty.order.utils.FullyGridLayoutManager;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：下午 4:07
 * 描述：s申请售后
 * ===============Txunda===============
 */
public class ApplyForAfterSalesAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.apply_updata_pic_rv)
    private RecyclerView apply_updata_pic_rv;
    private List<File> pic = new ArrayList<>();
    private FullyGridLayoutManager manager;
    private GridImageAdapter gridImageAdapter;
    private ImagePicker imagePicker;
    private int selectPicNum = 9;

    /**
     * 退款金额
     */
    @ViewInject(R.id.money_be_back_ev)
    private EditText money_be_back_ev;
    /**
     * 允许输入的最大金额
     */
    private BigDecimal maxPrice = new BigDecimal("108.00");
    /**
     * 用户输入的金额
     */
    private String price;
    private Bundle bundle;

    /**
     * 售后类型
     */
    @ViewInject(R.id.apply_type_tv)
    private TextView apply_type_tv;
    /**
     * 售后类型
     */
    private String type;
    /**
     * 货物状态
     */
    @ViewInject(R.id.goods_status_tv)
    private TextView goods_status_tv;
    /**
     * 货物状态
     */
    private String status;
    /**
     * 退款原因
     */
    @ViewInject(R.id.apply_cause_tv)
    private TextView apply_cause_tv;
    /**
     * 退款原因
     */
    private String cause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("订单详情");

        manager = new FullyGridLayoutManager(ApplyForAfterSalesAty.this, 3, GridLayoutManager.VERTICAL, false);
        apply_updata_pic_rv.setLayoutManager(manager);
        gridImageAdapter = new GridImageAdapter(ApplyForAfterSalesAty.this, onAddPicClickListener);
        gridImageAdapter.setList(pic);
        gridImageAdapter.setSelectMax(selectPicNum);
        apply_updata_pic_rv.setAdapter(gridImageAdapter);
        // 只允许输入两位有效数字
        MoneyUtils.setPricePoint(money_be_back_ev);
    }

    @Override
    @OnClick({R.id.apply_type_tv, R.id.goods_status_tv, R.id.apply_cause_tv, R.id.submit_apply_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.apply_type_tv:
                bundle = new Bundle();
                bundle.putString("title", "售后类型");
                startActivityForResult(TextListAty.class, bundle, 101);
                break;
            case R.id.goods_status_tv:
                bundle = new Bundle();
                bundle.putString("title", "货物状态");
                startActivityForResult(TextListAty.class, bundle, 102);
                break;
            case R.id.apply_cause_tv:
                bundle = new Bundle();
                bundle.putString("title", "原因");
                startActivityForResult(TextListAty.class, bundle, 103);
                break;
            case R.id.submit_apply_tv:
                price = money_be_back_ev.getText().toString();
                BigDecimal temp_price = new BigDecimal(price);
                if (temp_price.compareTo(maxPrice) > 0) {
                    showErrorTip("最多只能退108.00哦");
                    break;
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_apply_for_after_sales;
    }

    @Override
    protected void initialized() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 使用Glide加载
        imagePicker.setMultiMode(true);// 多选
        imagePicker.setCrop(false);// 是否裁剪
        imagePicker.setShowCamera(true);// 是否显示拍照按钮
        imagePicker.setSelectLimit(selectPicNum);
    }

    @Override
    protected void requestData() {

    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener =
            new GridImageAdapter.onAddPicClickListener() {
                @Override
                public void onAddPicClick(int type, int position) {
                    if (0 == type) {
                        startActivityForResult(ImageGridActivity.class, null, 100);
                    } else {
                        pic.remove(position);
                        gridImageAdapter.notifyItemRemoved(position);
                    }
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                for (ImageItem img : images) {
                    String pic_path = CompressionUtil.compressionBitmap(img.path);
                    File file = new File(pic_path);
                    pic.add(file);
                    gridImageAdapter.setList(pic);
                    gridImageAdapter.notifyDataSetChanged();
                }

            } else {
                showErrorTip("哎呀出错了。。");
            }
        } else if (RESULT_OK == resultCode) {
            if (data == null) {
                return;
            }
            switch (requestCode) {
                case 101:// 售后类型
                    type = data.getStringExtra("type");
                    apply_type_tv.setText(type);
                    break;
                case 102:// 货物状态
                    status = data.getStringExtra("status");
                    goods_status_tv.setText(status);
                    break;
                case 103:// 原因
                    cause = data.getStringExtra("cause");
                    apply_cause_tv.setText(cause);
                    break;
            }
        }
    }

}

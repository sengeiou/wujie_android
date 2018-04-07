package com.txd.hzj.wjlp.minetoAty.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.tools.MoneyUtils;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.order.adapter.GridImageAdapter;
import com.txd.hzj.wjlp.minetoAty.order.utils.FullyGridLayoutManager;
import com.txd.hzj.wjlp.new_wjyp.http.AfterSale;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * 允许退款的最大金额
     */
    private double maxPrice;

    /**
     * 用户输入的金额
     */
    private String priceStr;
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
     * 退款金额
     */
    @ViewInject(R.id.layout)
    private LinearLayout ll;
    /**
     * 退款原因
     */
    private String cause;

    /**
     * 最多可退款
     */
    @ViewInject(R.id.tv_price)
    private TextView tv_price;

    @ViewInject(R.id.edittext)
    private EditText edittext;
    private String order_id;
    private String order_goods_id;
    @ViewInject(R.id.layoutlayout)
    private LinearLayout layouttuikuan;
    private int moneyStatus; // 订单商品状态 0不显示退款金额，1显示退款金额
    private String typeTypeId; // 售后类型id
    private String causeTypeId; // 原因id
    private String statusTypeId; // 物流状态id
    private DecimalFormat df; // 格式化金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("申请售后");

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
                bundle.putString("order_goods_id",getIntent().getStringExtra("order_goods_id"));
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
                if (TextUtils.isEmpty(apply_type_tv.getText().toString())) {
                    showToast("请选择售后类型！");
                    return;
                }
                if (TextUtils.isEmpty(goods_status_tv.getText().toString())) {
                    showToast("请选择售后状态！");
                    return;
                }
                if (TextUtils.isEmpty(apply_cause_tv.getText().toString())) {
                    showToast("请选择售后原因！");
                    return;
                }
                priceStr = money_be_back_ev.getText().toString();
                if (moneyStatus == 1 && !priceStr.equals("")) { // 如果显示退款金额

                    double parseDouble = Double.parseDouble(priceStr.equals("") ? "0.0" : priceStr);
                    if (parseDouble > maxPrice){
                        showErrorTip("当前商品最多只能退" + df.format(maxPrice));
                        return;
                    }

                }

//                L.e("wang", df.format(money_be_back_ev.getText().toString()) + "=====================");

                AfterSale.backApply(typeTypeId, priceStr, edittext.getText().toString(), pic, causeTypeId, statusTypeId, order_id, type, order_goods_id, this);
                showProgressDialog();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_apply_for_after_sales;
    }

    @Override
    protected void initialized() {
        df = new DecimalFormat("#.00");
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 使用Glide加载
        imagePicker.setMultiMode(true);// 多选
        imagePicker.setCrop(false);// 是否裁剪
        imagePicker.setShowCamera(true);// 是否显示拍照按钮
        imagePicker.setSelectLimit(selectPicNum);
        order_goods_id = getIntent().getStringExtra("order_goods_id");
        // 获取到order_goods_id，先请求一下商品状态=============================================
        AfterSale.backApplyType(order_goods_id, this);

        order_id = getIntent().getStringExtra("order_id");
        type = getIntent().getStringExtra("type");
        maxPrice = Double.parseDouble(getIntent().getStringExtra("maxPrice"));
        tv_price.setText("最多可退：" + df.format(maxPrice) + "(若涉及运费、税费退还问题，请与商家协商解决。)");
        switch (type) {
            case "0":
                type = "1";
                break;
            case "3":
                type = "3";
                break;
        }
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);

        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);

        L.e("wang", "=======ApplyForAfterSalesAty>>>>>>>jsonStr:" + jsonStr + "\trequestUrl:" + requestUrl);
        String[] split = requestUrl.split("/");

        if (split[split.length - 1].equals("backApplyType")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            moneyStatus = Integer.parseInt(data.get("money_status"));
            if (moneyStatus == 0){
                layouttuikuan.setVisibility(View.GONE);
            }
        }
        if (split[split.length - 1].equals("backApply")) {
            showToast(map.get("message"));
            finish();
        }
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
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
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
                    apply_type_tv.setText(data.getStringExtra("type"));
                    typeTypeId = data.getStringExtra("typeTypeId");
                    break;
                case 102:// 货物状态
                    status = data.getStringExtra("status");
                    statusTypeId = data.getStringExtra("statusTypeId");
                    goods_status_tv.setText(status);
                    break;
                case 103:// 原因
                    cause = data.getStringExtra("cause");
                    causeTypeId = data.getStringExtra("causeTypeId");
                    apply_cause_tv.setText(cause);
                    break;
            }
        }
    }

}

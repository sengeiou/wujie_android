package com.txd.hzj.wjlp.melloffLine;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.util.CompressionUtil;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.minetoaty.order.adapter.GridImageAdapter;
import com.txd.hzj.wjlp.minetoaty.order.utils.FullyGridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/2 10:30
 * 功能描述：线下店铺评价
 */
public class OffLineEvaluationShopAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.imageview)
    private ImageView imageview;
    //店铺环境打分
    @ViewInject(R.id.env_rb)
    RatingBar env_rb;
    //店铺服务打分
    @ViewInject(R.id.service_rb)
    RatingBar service_rb;

    /**
     * 评价内容
     */
    @ViewInject(R.id.evalusete_context_tv)
    private EditText evalusete_context_tv;
    @ViewInject(R.id.ed_text_length)
    private TextView ed_text_length;
    /**
     * 图片列表
     */
    @ViewInject(R.id.updata_pic_rv)
    private RecyclerView updata_pic_rv;

    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;
    //订单ID
    private String mOrder_id;
    private List<File> picList;
    private GridImageAdapter gridImageAdapter;
    private FullyGridLayoutManager manager;
    private int selectPicNum = 9;

    private ImagePicker imagePicker;

    @Override
    protected int getLayoutResId() {
        return R.layout.offline_comment;
    }

    @Override
    protected void initialized() {
        mOrder_id = getIntent().getStringExtra("order_id");
        if (TextUtils.isEmpty(mOrder_id)) {
            return;
        }

        picList = new ArrayList<>();
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 使用Glide加载
        imagePicker.setMultiMode(true);// 多选
        imagePicker.setCrop(false);// 是否裁剪
        imagePicker.setShowCamera(true);// 是否显示拍照按钮
        imagePicker.setSelectLimit(selectPicNum);

        manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        updata_pic_rv.setLayoutManager(manager);
        gridImageAdapter = new GridImageAdapter(this,
                new GridImageAdapter.onAddPicClickListener() {
                    @Override
                    public void onAddPicClick(int type, int position) {
                        if (0 == type) {
                            startActivityForResult(ImageGridActivity.class, null, 100);
                        } else {
                            picList.remove(position);
                            gridImageAdapter.notifyDataSetChanged();
                        }
                    }
                });

        gridImageAdapter.setList(picList);
        gridImageAdapter.setSelectMax(selectPicNum);
        updata_pic_rv.setAdapter(gridImageAdapter);

        evalusete_context_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ed_text_length.setText(s.length() + "/500");
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evalusete_context_tv.getText().length() < 5) {
                    showToast("评价字数不能少于5字");
                    return;
                }
                OfflineStore.commentSubmit(mOrder_id, evalusete_context_tv.getText().toString(), picList, String.valueOf(env_rb.getRating()), String.valueOf(service_rb.getRating()), OffLineEvaluationShopAty.this);
                showProgressDialog();
            }
        });
    }

    @Override
    protected void requestData() {
        OfflineStore.commentPageParameters(mOrder_id, this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if ("1".equals(jsonObject.getString("code"))) {
            if (requestUrl.contains("common")) {
                /*{
                    "code": "1",
                        "message": "请求成功",
                        "data": {
                    "order_id": "1",//订单ID
                            "merchant_id": "38",//商家ID
                            "logo": "http://img.wujiemall.com/Uploads/Merchant/2018-07-24/5b56ca2eb4470.jpg"//店铺logo
                },
                    "nums": "0"
                }*/

                JSONObject data = JSON.parseObject(jsonObject.getString("data"));
                if (data.containsKey("logo")) {
                    String logoUrl = data.getString("logo");
                    Glide.with(OffLineEvaluationShopAty.this).load(logoUrl).into(imageview);
                }
                if (data.containsKey("merchant_name")) {
                    titlt_conter_tv.setText(data.getString("merchant_name"));
                }
            }

            if (requestUrl.contains("comment")) {
                /*{
                    "code": "1",
                        "message": "评论成功",
                        "data": [],
                    "nums": "0"
                }*/
                showToast(jsonObject.getString("message"));
                finish();
            }
        }
    }

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
                    picList.add(file);
                    gridImageAdapter.notifyDataSetChanged();
                }

            } else {
                showErrorTip("哎呀出错了。。");
            }
        }
    }
}

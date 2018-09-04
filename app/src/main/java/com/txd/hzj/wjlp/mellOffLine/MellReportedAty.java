package com.txd.hzj.wjlp.mellOffLine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.util.CompressionUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.merchant.MerchantPst;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.minetoAty.order.adapter.GridImageAdapter;
import com.txd.hzj.wjlp.minetoAty.order.utils.FullyGridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/8/12 0012
 * 时间：上午 9:41
 * 描述：举报商家得红包
 */
public class MellReportedAty extends BaseAty {

    /**
     * 标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 举报类型
     */
    @ViewInject(R.id.report_type_tv)
    private TextView report_type_tv;

    private String merchant_id = "";

    private MerchantPst merchantPst;
    private String report_type_id = "";

    @ViewInject(R.id.report_content_tv)
    private EditText report_content_tv;

    @ViewInject(R.id.ed_text_length)
    private TextView textLength;


    private GridImageAdapter gridImageAdapter;
    private FullyGridLayoutManager manager;
    List<File> list = new ArrayList<>();

    @ViewInject(R.id.updata_pic_rv)
    private RecyclerView updata_pic_rv;

    private ImagePicker imagePicker;
    private int selectPicNum = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("举报商家");
    }

    @Override
    @OnClick({R.id.report_type_layout, R.id.report_mell_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.report_type_layout:
                Bundle bundle = new Bundle();
                bundle.putString("title", "举报类型");
                startActivityForResult(TextListAty.class, bundle, 100);
                break;
            case R.id.report_mell_tv:
                if (!Config.isLogin()) {
                    toLogin();
                }
                String report_content = report_content_tv.getText().toString().trim();
                if(TextUtils.isEmpty(String.valueOf(report_type_tv.getText()))){
                    showErrorTip("请选择举报类型");
                    return;
                }
                if(TextUtils.isEmpty(String.valueOf(report_content))){
                    showErrorTip("请描述举报理由");
                    return;
                }
                if(list.size()==0){
                    showErrorTip("请选择上传凭证");
                    return;
                }

                merchantPst.reportMerchant(report_type_id, report_content, merchant_id, list);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_reported_hzj;
    }

    @Override
    protected void initialized() {
        merchant_id = getIntent().getStringExtra("merchant_id");
        merchantPst = new MerchantPst(this);
        report_content_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textLength.setText(s.length() + "/200");
            }
        });


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
                            list.remove(position);
                            gridImageAdapter.notifyDataSetChanged();
                        }
                    }
                });


        gridImageAdapter.setList(list);
        gridImageAdapter.setSelectMax(selectPicNum);
        updata_pic_rv.setAdapter(gridImageAdapter);

    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("report")) {
            showRightTip("举报成功");
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (RESULT_OK == resultCode) {
            if (100 == requestCode) {
                String type = data.getStringExtra("type");
                report_type_tv.setText(type);
                report_type_id = data.getStringExtra("report_type_id");
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                for (ImageItem img : images) {
                    String pic_path = CompressionUtil.compressionBitmap(img.path);
                    File file = new File(pic_path);
                    list.add(file);
                    gridImageAdapter.notifyDataSetChanged();
                }

            } else {
                showErrorTip("哎呀出错了。。");
            }
        }
    }
}

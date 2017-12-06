package com.txd.hzj.wjlp.minetoAty.mellInto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/16 0016
 * 时间：下午 3:16
 * 描述：推荐详情
 * ===============Txunda===============
 */
public class MellIntoInfoAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.pic_for_mell_into_gv)
    private GridViewForScrollView pic_for_mell_into_gv;
    @ViewInject(R.id.pic_for_mell_into_gv2)
    private GridViewForScrollView pic_for_mell_into_gv2;
    @ViewInject(R.id.pic_for_mell_into_gv3)
    private GridViewForScrollView pic_for_mell_into_gv3;

    private PicAdapter padapter;
    private PicAdapter padapter2;
    private PicAdapter padapter3;

    private UserPst userPst;
    private String refer_id = "";
    private List<Map<String, String>> pic;
    private List<Map<String, String>> pic2;
    private List<Map<String, String>> pic3;

    private int picSize = 0;
    private int size = 0;
    /**
     * 店铺名称
     */
    @ViewInject(R.id.mell_logo_iv)
    private ImageView mell_logo_iv;
    /**
     * 名称·
     */
    @ViewInject(R.id.mell_name_tv)
    private TextView mell_name_tv;
    /**
     * 推荐状态
     */
    @ViewInject(R.id.mell_status_tv)
    private TextView mell_status_tv;
    /**
     * 店铺介绍
     */
    @ViewInject(R.id.mell_desc_tv)
    private TextView mell_desc_tv;
    /**
     * 店铺评分
     */
    @ViewInject(R.id.mell_score_tv)
    private TextView mell_score_tv;
    /**
     * 店铺经营范围
     */
    @ViewInject(R.id.mell_range_tv)
    private TextView mell_range_tv;
    /**
     * 职位
     */
    @ViewInject(R.id.job_for_mell_tv)
    private TextView job_for_mell_tv;
    /**
     * 联系电话
     */
    @ViewInject(R.id.mell_phone_tv)
    private TextView mell_phone_tv;
    /**
     * 天猫
     */
    @ViewInject(R.id.tmail_url_tv)
    private TextView tmail_url_tv;
    /**
     * 京东
     */
    @ViewInject(R.id.jd_url_tv)
    private TextView jd_url_tv;
    /**
     * 其他网店
     */
    @ViewInject(R.id.other_url_tv)
    private TextView other_url_tv;
    /**
     * 产品介绍
     */
    @ViewInject(R.id.product_desc_tv)
    private TextView product_desc_tv;
    /**
     * 推荐时间
     */
    @ViewInject(R.id.create_time_tv)
    private TextView create_time_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("推荐详情");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_into_info_hzj;
    }

    @Override
    protected void initialized() {
        userPst = new UserPst(this);
        refer_id = getIntent().getStringExtra("refer_id");

        pic = new ArrayList<>();
        pic2 = new ArrayList<>();
        pic3 = new ArrayList<>();
        picSize = ToolKit.dip2px(this, 108);
        size = ToolKit.dip2px(this, 80);
    }

    @Override
    protected void requestData() {
        userPst.referInfo(refer_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("referInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));

            Glide.with(MellIntoInfoAty.this).load(data.get("logo"))
                    .centerCrop()
                    .override(size, size)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mell_logo_iv);
            mell_name_tv.setText(data.get("name"));
            switch (data.get("status")) {
                case "0":// 未认证
                    mell_status_tv.setText("未认证");
                    break;
                case "1":// 待审核
                    mell_status_tv.setText("待审核");
                    break;
                case "2":// 已通过
                    mell_status_tv.setText("推荐成功");
                    break;
            }

            mell_desc_tv.setText(data.get("desc"));

            mell_score_tv.setText("评分" + data.get("score") + "分");
            mell_range_tv.setText(data.get("range_id"));
            job_for_mell_tv.setText(data.get("job"));
            mell_phone_tv.setText(data.get("link_phone"));
            tmail_url_tv.setText(data.get("tmail_url"));
            jd_url_tv.setText(data.get("jd_url"));
            other_url_tv.setText(data.get("other_url"));
            product_desc_tv.setText(data.get("product_desc"));
            create_time_tv.setText(data.get("create_time"));


            if (ToolKit.isList(data, "product_pic")) {// 商品图片
                pic = JSONUtils.parseKeyAndValueToMapList(data.get("product_pic"));
            }
            if (ToolKit.isList(data, "business_license")) {// 营业执照
                Map<String, String> tempPic = new HashMap<>();
                tempPic.put("path", data.get("business_license"));
                pic2.add(tempPic);
            }
            if (ToolKit.isList(data, "other_license")) {// 商品图片
                pic3 = JSONUtils.parseKeyAndValueToMapList(data.get("other_license"));
            }
            if (!ListUtils.isEmpty(pic)) {
                padapter = new PicAdapter(pic);
                padapter2 = new PicAdapter(pic2);
                padapter3 = new PicAdapter(pic3);
                pic_for_mell_into_gv.setAdapter(padapter);
                pic_for_mell_into_gv2.setAdapter(padapter2);
                pic_for_mell_into_gv3.setAdapter(padapter3);
            }
        }
    }

    private class PicAdapter extends BaseAdapter {

        List<Map<String, String>> pic;

        public PicAdapter(List<Map<String, String>> pic) {
            this.pic = pic;
        }

        private PicVh pvh;

        @Override
        public int getCount() {
            return pic.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return pic.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> picTemp = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(MellIntoInfoAty.this).inflate(R.layout.item_pic_for_mell_into_gc, null);
                pvh = new PicVh();
                ViewUtils.inject(pvh, view);
                view.setTag(pvh);
            } else
                pvh = (PicVh) view.getTag();

            Glide.with(MellIntoInfoAty.this).load(picTemp.get("path"))
                    .centerCrop()
                    .override(picSize, picSize)
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(pvh.refer_pic_iv);

            return view;
        }

        private class PicVh {

            @ViewInject(R.id.refer_pic_iv)
            private ImageView refer_pic_iv;

        }
    }

}

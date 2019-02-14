package com.txd.hzj.wjlp.new_wjyp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.CarOrder;
import com.txd.hzj.wjlp.http.HouseOrder;
import com.txd.hzj.wjlp.minetoaty.order.adapter.GridImageAdapter;
import com.txd.hzj.wjlp.minetoaty.order.utils.FullyGridLayoutManager;
import com.txd.hzj.wjlp.view.RatingBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提交评价
 */

public class Comment_aty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.titlt_right_tv)
    private TextView titlt_right_tv;
    @ViewInject(R.id.recyclerview)
    private RecyclerView recyclerview;
    @ViewInject(R.id.updata_pic_rv)
    private RecyclerView updata_pic_rv;
    @ViewInject(R.id.et_context)
    private EditText et_context;
    private ImagePicker imagePicker;
    private int selectPicNum = 5;
    private GridImageAdapter gridImageAdapter;
    private FullyGridLayoutManager manager;
    List<File> file_list;
    private String order_id;
    private String type;
    @ViewInject(R.id.imageview)
    private ImageView imageview;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;
    @ViewInject(R.id.tv1)
    private TextView tv1;
    @ViewInject(R.id.tv2)
    private TextView tv2;
    @ViewInject(R.id.layout_rb)
    private LinearLayout layout_rb;
    @ViewInject(R.id.exterior)
    private TextView exterior;
    @ViewInject(R.id.space)
    private TextView space;
    @ViewInject(R.id.controllability)
    private TextView controllability;
    @ViewInject(R.id.consumption)
    private TextView consumption;
    List<Bean> label_list;
    @ViewInject(R.id.rb1)
    RatingBar rb1;
    @ViewInject(R.id.rb2)
    RatingBar rb2;
    @ViewInject(R.id.rb3)
    RatingBar rb3;
    @ViewInject(R.id.rb4)
    RatingBar rb4;
    @ViewInject(R.id.rb5)
    RatingBar rb5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_comment;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("评价");
        titlt_right_tv.setText("提交");
        titlt_right_tv.setTextColor(Color.parseColor("#FF2727"));
        titlt_right_tv.setVisibility(View.VISIBLE);
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 使用Glide加载
        imagePicker.setMultiMode(true);// 多选
        imagePicker.setCrop(false);// 是否裁剪
        imagePicker.setShowCamera(true);// 是否显示拍照按钮
        imagePicker.setSelectLimit(selectPicNum);
        file_list = new ArrayList<File>();
        manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        updata_pic_rv.setLayoutManager(manager);
        gridImageAdapter = new GridImageAdapter(this,
                new GridImageAdapter.onAddPicClickListener() {
                    @Override
                    public void onAddPicClick(int type, int position) {
                        if (0 == type) {
                            startActivityForResult(ImageGridActivity.class, null, 100);
                        } else {
                            file_list.remove(position);
                            gridImageAdapter.notifyDataSetChanged();
                        }
                    }
                });
        gridImageAdapter.setList(file_list);
        gridImageAdapter.setSelectMax(selectPicNum);
        updata_pic_rv.setAdapter(gridImageAdapter);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        titlt_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(",");
                for (int i = 0; i < label_list.size(); i++) {
                    if (label_list.get(i).isCheck()) {
                        stringBuffer.append(label_list.get(i).getLabel_id());
                        stringBuffer.append(",");
                    }
                }
                String str = stringBuffer.toString();
                if (str.equals(",")) {
                    str = "";
                }
                if (type.equals("1")) {

                    CarOrder.addComment(order_id, String.valueOf(rb1.getStarStep())
                            , String.valueOf(rb2.getStarStep())
                            , String.valueOf(rb3.getStarStep())
                            , String.valueOf(rb4.getStarStep()), str, file_list, et_context.getText().toString(), Comment_aty.this
                    );
                    showProgressDialog();
                } else {


                    HouseOrder.addComment(order_id, String.valueOf(rb1.getStarStep())
                            , String.valueOf(rb2.getStarStep())
                            , String.valueOf(rb3.getStarStep())
                            , String.valueOf(rb4.getStarStep()), String.valueOf(rb5.getStarStep()), str, file_list, et_context.getText().toString(), Comment_aty.this
                    );
                    showProgressDialog();
                }
            }
        });
    }

    @Override
    protected void requestData() {
        order_id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            CarOrder.commentPage(order_id, this);
        } else {
            HouseOrder.commentPage(order_id, this);
        }
        showProgressDialog();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("addComment")) {
            showToast("评价成功！");
            finish();
            return;
        }
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        label_list = GsonUtil.getObjectList(map.get("label_list"), Bean.class);
        recyclerview.setAdapter(new Myadp());
        tv_title.setText(type.equals("1") ? map.get("car_name") : map.get("style_name"));
        Glide.with(this).load(type.equals("1") ? map.get("car_img") : map.get("house_style_img")).into(imageview);
        tv_price.setText(map.get("pre_money"));
        if (requestUrl.contains("commentPage") && type.equals("1")) {
            tv1.setText("车全款：¥" + map.get
                    ("all_price") + "\n可    抵：¥" + map.get("true_pre_money") + "车款");
        }
        if (requestUrl.contains("commentPage") && type.equals("2")) {
            layout_rb.setVisibility(View.VISIBLE);
            tv1.setText("房全款：¥" + map.get
                    ("all_price") + "\n可    抵：¥" + map.get("true_pre_money") + "房款");
          //  tv2.setText(map.get("house_name") + "\t\t" + map.get("one_price") + "元/平");
            exterior.setText("价格");
            space.setText("地段");
            controllability.setText("配套");
            consumption.setText("交通");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (requestCode == 100 && data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (ImageItem img : images) {
                    String pic_path = CompressionUtil.compressionBitmap(img.path);
                    File file = new File(pic_path);
                    file_list.add(file);
                    gridImageAdapter.notifyDataSetChanged();
                }
            } else {
                showErrorTip("出错了。。");
            }
        }
    }

    class Myadp extends RecyclerView.Adapter<Myadp.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_comment_checkbox, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.checkbox.setText(type.equals("1") ? getItem(position).getCar_label() : getItem(position).getHouse_label());
            holder.checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getItem(position).isCheck()) {
                        getItem(position).setCheck(false);
                        holder.checkbox.setBackgroundResource(R.drawable.shape_gray_btn_lh);
                        holder.checkbox.setTextColor(Color.parseColor("#999999"));
                    } else {
                        getItem(position).setCheck(true);
                        holder.checkbox.setBackgroundResource(R.drawable.shape_red_btn_01_lh);
                        holder.checkbox.setTextColor(Color.parseColor("#DF3031"));
                    }
                    notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {
            return label_list.size();
        }

        private Bean getItem(int p) {
            return label_list.get(p);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView checkbox;

            public ViewHolder(View itemView) {
                super(itemView);
                checkbox = (TextView) itemView.findViewById(R.id.checkbox);

            }
        }
    }

    class Bean {
        private String label_id;
        private String car_label;
        private String house_label;
        private boolean check = false;

        public String getLabel_id() {
            return label_id;
        }

        public void setLabel_id(String label_id) {
            this.label_id = label_id;
        }

        public String getHouse_label() {
            return house_label;
        }

        public void setHouse_label(String house_label) {
            this.house_label = house_label;
        }

        public String getCar_label() {
            return car_label;
        }

        public void setCar_label(String car_label) {
            this.car_label = car_label;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }
    }

}

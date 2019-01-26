package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/2 9:45
 * 功能描述：线下店铺的商品管理
 */
public class CommodityManagementAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.leftRecyclerView)
    private RecyclerView leftRecyclerView;

    @ViewInject(R.id.rightRecyclerView)
    private RecyclerView rightRecyclerView;


    @ViewInject(R.id.nameTv)
    private TextView nameTv;

    @ViewInject(R.id.selectTv)
    private TextView selectTv;

    @ViewInject(R.id.empty_layout)
    private LinearLayout empty_layout;

    @ViewInject(R.id.dataLayout)
    private LinearLayout dataLayout;

    @ViewInject(R.id.fenleiTv)
    private TextView fenleiTv;

    @ViewInject(R.id.lucaiLayout)
    private LinearLayout lucaiLayout;

    @ViewInject(R.id.guanliTv)
    private TextView guanliTv;

    @ViewInject(R.id.frameLayout)
    private FrameLayout mFrameLayout;

    @ViewInject(R.id.deleteTv)
    private TextView deleteTv;

    @ViewInject(R.id.moveTv)
    private TextView moveTv;

    @ViewInject(R.id.submitTv)
    private TextView submitTv;

    @ViewInject(R.id.saleLayout)
    private LinearLayout saleLayout;


    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;

    private String mType = "1";
    private String merchantId;

    private String mSelectName;

    private int selectP = 0;

    private ArrayList<LeftBean> mBeanArrayList = new ArrayList<>();


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_commodity_management_melloffline;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("商品管理");
        LinearLayoutManager leftLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager rightLayoutManager = new LinearLayoutManager(this);
        leftRecyclerView.setLayoutManager(leftLayoutManager);
        leftRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rightRecyclerView.setLayoutManager(rightLayoutManager);
        merchantId = getIntent().getStringExtra("sta_mid");
    }

    @Override
    protected void requestData() {
        app_goods_cate(mType, merchantId, this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("app_goods_cate")) {
            final ArrayList<LeftBean> data = JSONUtils.parseKeyAndValueToMapList(LeftBean.class, map.get("data"));
            mBeanArrayList.clear();
            mBeanArrayList.addAll(data);
            if (data != null && data.size() > 0) {
                mLeftAdapter = new LeftAdapter(data);
                requestRightData(data, selectP);
                mLeftAdapter.setSelectPosition(selectP);
                mSelectName = (data.get(selectP)).getName();
                mLeftAdapter.setOnItemClickListener(new LeftAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        mLeftAdapter.setSelectPosition(position);
                        requestRightData(data, position);
                        selectP = position;
                        mSelectName = (data.get(position)).getName();
                        selectTv.setVisibility(View.GONE);
                        mFrameLayout.setVisibility(View.VISIBLE);
                        deleteTv.setVisibility(View.GONE);
                        moveTv.setVisibility(View.GONE);
                        submitTv.setVisibility(View.GONE);
                        saleLayout.setVisibility(View.GONE);
                    }
                });
                leftRecyclerView.setAdapter(mLeftAdapter);
            }
            return;
        }
        if (requestUrl.endsWith("app_cate_goods")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            Map<String, String> cateInfo = JSONUtils.parseKeyAndValueToMap(data.get("cate_info"));
            final ArrayList cate_goods_list = JSONUtils.parseKeyAndValueToMapList(CateGoodsListBean.class, data.get("cate_goods_list"));
            if (cate_goods_list != null) {
                if (cate_goods_list.size() > 0) {
                    dataLayout.setVisibility(View.VISIBLE);
                    empty_layout.setVisibility(View.GONE);
                    nameTv.setText(cateInfo.get("name"));
                } else {
                    dataLayout.setVisibility(View.GONE);
                    empty_layout.setVisibility(View.VISIBLE);
                }

                mRightAdapter = new RightAdapter(cate_goods_list, mSelectName);
                rightRecyclerView.setAdapter(mRightAdapter);
                mRightAdapter.setOnItemClickListener(new LeftAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        CateGoodsListBean bean = (CateGoodsListBean) cate_goods_list.get(position);
                        if (!mSelectName.equals("待审核")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("goods_id", bean.getGoods_id());
                            bundle.putString("sta_mid", merchantId);
                            if (mSelectName.equals("待审核")) {
                                bundle.putBoolean("isGone", true);
                            }
                            startActivity(InputAty.class, bundle);
                        }
                    }
                });
            } else {
                dataLayout.setVisibility(View.GONE);
                empty_layout.setVisibility(View.VISIBLE);
            }
        }

        if (requestUrl.endsWith("app_volume_delete") || requestUrl.endsWith("app_dijiao") || requestUrl.endsWith("app_mass_shut_updown")) {
            showToast(map.get("message"));
            mFrameLayout.setVisibility(View.VISIBLE);
            deleteTv.setVisibility(View.GONE);
            moveTv.setVisibility(View.GONE);
            submitTv.setVisibility(View.GONE);
            saleLayout.setVisibility(View.GONE);
            if ("1".equals(map.get("code"))) {
                app_goods_cate(mType, merchantId, this);
                if (selectTv.getVisibility() == View.VISIBLE) {
                    selectTv.setVisibility(View.GONE);
                }
            }
            return;
        }

        if (requestUrl.endsWith("app_after_api")){
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (data.get("status").equals("0")){
                showToast("请先完善店铺信息，设置营业时间");
            }else if (data.get("status").equals("1")){
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid", merchantId);
                startActivity(InputAty.class, bundle);
            }
            return;
        }
    }

    private void requestRightData(ArrayList data, int position) {
        LeftBean bean = (LeftBean) data.get(position);
        app_cate_goods(bean.getId(), merchantId, this);
    }

    void app_goods_cate(String type, String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_goods_cate", params, baseView);
    }

    void app_cate_goods(String cate_id, String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("cate_id", cate_id);
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_cate_goods", params, baseView);
    }

    void app_volume_delete(String goods_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_volume_delete", params, baseView);
    }

    void app_dijiao(String goods_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_dijiao", params, baseView);
    }

    /**
     * @param goods_id
     * @param is_sale  0下架 1上架
     */
    void app_mass_shut_updown(String goods_id, String is_sale, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("is_sale", is_sale);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_mass_shut_updown", params, baseView);
    }


    void app_after_api(String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_after_api", params, baseView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        String label = messageEvent.getLabel();
        String message = messageEvent.getMessage();
        if (label.equals("InputAty") && message.equals("save")) {
            if (mBeanArrayList.size() > 3) {
                selectP = mBeanArrayList.size() - 3;
            }
            app_goods_cate(mType, merchantId, this);
        }

        if (label.equals("ClassifyManageAty") && (message.equals("move") || message.equals("back"))) {
            app_goods_cate(mType, merchantId, this);
        }
    }

    @Override
    @OnClick({R.id.fenleiTv, R.id.lucaiLayout, R.id.guanliTv, R.id.selectTv, R.id.deleteTv, R.id.moveTv, R.id.stopSaleTv, R.id.startSaleTv, R.id.submitTv})
    public void onClick(View v) {
        super.onClick(v);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.fenleiTv:
                bundle.putString("sta_mid", merchantId);
                bundle.putBoolean("isShowDelete", true);
                startActivity(ClassifyManageAty.class, bundle);
                break;
            case R.id.lucaiLayout:
                app_after_api(merchantId,CommodityManagementAty.this);
                break;
            case R.id.guanliTv:
                if (mSelectName.equals("待审核")) {
                    showToast("该分组不支持批量操作");
                } else {
                    if (mSelectName.equals("待递交")) {
                        createPop(v, "递交", false);
                    } else if (mSelectName.equals("审核失败")) {
                        createPop(v, "重新递交", false);
                    } else {
                        createPop(v, "分类", true);
                    }
                }
                break;
            case R.id.selectTv:
                mRightAdapter.selectAll();
                break;
            case R.id.deleteTv:
                try {
                    JSONArray jsonArray = new JSONArray(getGoodsIds());
                    if (jsonArray.length() <= 0) {
                        showToast("请选择商品");
                        return;
                    }
                    app_volume_delete(getGoodsIds(), CommodityManagementAty.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.moveTv:
                try {
                    JSONArray jsonArray = new JSONArray(getGoodsIds());
                    if (jsonArray.length() <= 0) {
                        showToast("请选择商品");
                        return;
                    }
                    bundle.putString("sta_mid", merchantId);
                    bundle.putString("goods_id", getGoodsIds());
                    bundle.putBoolean("isShowDelete", false);
                    startActivity(ClassifyManageAty.class, bundle);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.stopSaleTv:
                try {
                    JSONArray jsonArray = new JSONArray(getGoodsIds());
                    if (jsonArray.length() <= 0) {
                        showToast("请选择商品");
                        return;
                    }
                    app_mass_shut_updown(getGoodsIds(), "0", CommodityManagementAty.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.startSaleTv:
                try {
                    JSONArray jsonArray = new JSONArray(getGoodsIds());
                    if (jsonArray.length() <= 0) {
                        showToast("请选择商品");
                        return;
                    }
                    app_mass_shut_updown(getGoodsIds(), "1", CommodityManagementAty.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.submitTv:
                try {
                    JSONArray jsonArray = new JSONArray(getGoodsIds());
                    if (jsonArray.length() <= 0) {
                        showToast("请选择商品");
                        return;
                    }
                    app_dijiao(getGoodsIds(), CommodityManagementAty.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    private void createPop(View v, final String text, boolean isVisible) {
        final PopupWindow mPopupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = LayoutInflater.from(this).inflate(R.layout.pop_melloffline_manage, null);
        TextView oneTv = view.findViewById(R.id.oneTv);
        oneTv.setText(text);
        TextView twoTv = view.findViewById(R.id.twoTv);
        twoTv.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        TextView threeTv = view.findViewById(R.id.threeTv);

        oneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv.setVisibility(View.VISIBLE);
                mRightAdapter.showCheckBox();
                if (text.equals("分类")) {
                    mFrameLayout.setVisibility(View.GONE);
                    moveTv.setVisibility(View.VISIBLE);
                } else {
                    mFrameLayout.setVisibility(View.GONE);
                    submitTv.setVisibility(View.VISIBLE);
                }
                mPopupWindow.dismiss();
            }
        });

        twoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv.setVisibility(View.VISIBLE);
                mRightAdapter.showCheckBox();
                mFrameLayout.setVisibility(View.GONE);
                saleLayout.setVisibility(View.VISIBLE);
                mPopupWindow.dismiss();
            }
        });

        threeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTv.setVisibility(View.VISIBLE);
                mRightAdapter.showCheckBox();
                mFrameLayout.setVisibility(View.GONE);
                deleteTv.setVisibility(View.VISIBLE);
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow.setContentView(view);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(true);
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, location[1] - v.getHeight() * 2);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1.0f;
                window.setAttributes(attributes);
            }
        });
    }


    private String getGoodsIds() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        ArrayList<CateGoodsListBean> list = mRightAdapter.getList();
        for (CateGoodsListBean bean : list) {
            if (bean.isSelect()) {
                JSONObject object = new JSONObject();
                object.put("id", bean.getGoods_id());
                jsonArray.put(object);
            }
        }
        return jsonArray.toString();
    }

    public static class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {

        private int selectPosition = 0;
        private OnItemClickListener mOnItemClickListener;
        private ArrayList<LeftBean> mList;

        public LeftAdapter(ArrayList<LeftBean> list) {
            mList = list;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_text, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.foodsNameTv.setText(mList.get(position).getName());
            if (position == selectPosition) {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffffff"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffeeeeee"));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }


        public void setSelectPosition(int position) {
            this.selectPosition = position;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.foodsNameTv)
            private TextView foodsNameTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }


        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }

    public static class RightAdapter extends RecyclerView.Adapter<RightAdapter.ViewHolder> {
        private ArrayList<CateGoodsListBean> mList;
        private Context mContext;
        private LeftAdapter.OnItemClickListener mOnItemClickListener;
        private boolean isShowCheckBox;
        private String selectName;

        public RightAdapter(ArrayList<CateGoodsListBean> list, String selectName) {
            mList = list;
            this.selectName = selectName;
        }

        public void setOnItemClickListener(LeftAdapter.OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_right_layout, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            CateGoodsListBean cateGoodsListBean = mList.get(position);
            Glide.with(mContext).load(cateGoodsListBean.getGoods_pic()).into(holder.picIv);
            holder.nameTv.setText(cateGoodsListBean.getName());
            if (selectName.equals("待递交") || selectName.equals("待审核") || selectName.equals("审核失败")) {
                holder.typeTv.setVisibility(View.VISIBLE);
                holder.typeTv.setText("(" + cateGoodsListBean.getC_name() + ")");
                holder.statusTv.setVisibility(View.GONE);
                holder.specTv.setVisibility(View.GONE);
            } else {
                holder.typeTv.setVisibility(View.GONE);
                holder.statusTv.setVisibility(View.VISIBLE);
                holder.specTv.setVisibility(View.VISIBLE);
                if (cateGoodsListBean.getAttr_count() == 0) {
                    holder.specTv.setVisibility(View.GONE);
                } else if (cateGoodsListBean.getAttr_count() > 0) {
                    holder.specTv.setVisibility(View.VISIBLE);
                    holder.specTv.setText("已设置规格");
                }
                if (cateGoodsListBean.getIs_sale().equals("0")) {
                    holder.statusTv.setText("已停售");
                    holder.statusTv.setBackgroundResource(R.drawable.shape_grey_radius30);
                } else {
                    holder.statusTv.setText("已启售");
                    holder.statusTv.setBackgroundResource(R.drawable.shape_orange_radius30);
                }
            }
            if (cateGoodsListBean.getSup_type().equals("1")) {
                holder.priceTv1.setText(setSpannable("¥" + cateGoodsListBean.getShop_price() + "/份"));
                holder.priceTv1.setVisibility(View.VISIBLE);
                holder.priceTv2.setVisibility(View.GONE);
            } else if (cateGoodsListBean.getSup_type().equals("2")) {
                holder.priceTv2.setText(setSpannable("¥" + cateGoodsListBean.getChurch_shop_price() + "/份"));
                holder.priceTv1.setVisibility(View.GONE);
                holder.priceTv2.setVisibility(View.VISIBLE);
            } else if (cateGoodsListBean.getSup_type().equals("3")) {
                holder.priceTv1.setText(setSpannable("¥" + cateGoodsListBean.getShop_price() + "/份"));
                holder.priceTv2.setText(setSpannable("¥" + cateGoodsListBean.getChurch_shop_price() + "/份"));
                holder.priceTv1.setVisibility(View.VISIBLE);
                holder.priceTv2.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });

            if (isShowCheckBox) {
                holder.rightArrowImg.setVisibility(View.GONE);
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.checkBox.setChecked(mList.get(position).isSelect());
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mList.get(position).setSelect(isChecked);
                    }
                });
            } else {
                holder.rightArrowImg.setVisibility(View.VISIBLE);
                holder.checkBox.setVisibility(View.GONE);
            }

        }

        private SpannableString setSpannable(String source) {
            SpannableString spannableString = new SpannableString(source);
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#ffe12b2a"));
            spannableString.setSpan(span, 0, source.length() - 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        public void showCheckBox() {
            this.isShowCheckBox = true;
            notifyDataSetChanged();
        }

        public void selectAll() {
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setSelect(true);
            }
            notifyDataSetChanged();
        }


        public ArrayList<CateGoodsListBean> getList() {
            return mList;
        }


        @Override
        public int getItemCount() {
            return mList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.picIv)
            private ImageView picIv;

            @ViewInject(R.id.nameTv)
            private TextView nameTv;

            @ViewInject(R.id.typeTv)
            private TextView typeTv;

            @ViewInject(R.id.specTv)
            private TextView specTv;

            @ViewInject(R.id.statusTv)
            private TextView statusTv;

            @ViewInject(R.id.priceTv1)
            private TextView priceTv1;

            @ViewInject(R.id.priceTv2)
            private TextView priceTv2;

            @ViewInject(R.id.rightArrowImg)
            private ImageView rightArrowImg;

            @ViewInject(R.id.checkBox)
            private CheckBox checkBox;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }

    public static class LeftBean {

        /**
         * id : 28
         * m_id : 38
         * name : 香糯甜粥
         * desc : 夏日冰饮、清凉一夏
         * sort : 123
         */

        private String id;
        private String m_id;
        private String name;
        private String desc;
        private String sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getM_id() {
            return m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }


    public static class CateGoodsListBean {
        /**
         * id : 98
         * goods_id : 98
         * cate_id : 50
         * name : RWE
         * shop_price : 11.00
         * church_shop_price : 0.00
         * orig_price : 0.00
         * pic : 27985
         * c_name : 精品小菜
         * sup_type : 1
         * desc : asdsfd
         * label : 0
         * bossrec : 0
         * week_price :
         * time_price : {"start_time":"1545868800","end_time":"1545955200","price":"13"}
         * church_week_price :
         * church_time_price :
         * create_time : 2018-12-27 16:18:58
         * is_sale : 1
         * sale_num : 2
         * goods_pic : http://test2.wujiemall.com/Uploads/StageMerchant/2018-12-27/5c248af248d33.jpg
         * attr : [{"attr_id":"137","name":"\u4e8c\u7ef4","price":"0.00","jiesuan_price":"0.00"},{"attr_id":"138","name":"\u4e09\u7ef4","price":"1.00","jiesuan_price":"0.00"},{"attr_id":"139","name":"\u4e0a\u5348","price":"2.00","jiesuan_price":"0.00"}]
         * specs : [{"p_id":"125","prop_title":"\u738b\u4f01\u9e45","taste":[{"id":"87","title":"\u5927"}],"taste_name":"\u5927"}]
         * attr_count : 3
         * specs_count : 1
         */

        private String id;
        private String goods_id;
        private String cate_id;
        private String name;
        private String shop_price;
        private String church_shop_price;
        private String orig_price;
        private String pic;
        private String c_name;
        private String sup_type;
        private String desc;
        private String label;
        private String bossrec;
        //        private String week_price;
        //        private String time_price;
        //        private String church_week_price;
        //        private String church_time_price;
        private String create_time;
        private String is_sale;
        private String sale_num;
        private String goods_pic;
        //        private String attr;
        //        private String specs;
        private int attr_count;
        private int specs_count;

        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getChurch_shop_price() {
            return church_shop_price;
        }

        public void setChurch_shop_price(String church_shop_price) {
            this.church_shop_price = church_shop_price;
        }

        public String getOrig_price() {
            return orig_price;
        }

        public void setOrig_price(String orig_price) {
            this.orig_price = orig_price;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getSup_type() {
            return sup_type;
        }

        public void setSup_type(String sup_type) {
            this.sup_type = sup_type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getBossrec() {
            return bossrec;
        }

        public void setBossrec(String bossrec) {
            this.bossrec = bossrec;
        }

        //        public String getWeek_price() {
        //            return week_price;
        //        }
        //
        //        public void setWeek_price(String week_price) {
        //            this.week_price = week_price;
        //        }
        //
        //        public String getTime_price() {
        //            return time_price;
        //        }
        //
        //        public void setTime_price(String time_price) {
        //            this.time_price = time_price;
        //        }
        //
        //        public String getChurch_week_price() {
        //            return church_week_price;
        //        }
        //
        //        public void setChurch_week_price(String church_week_price) {
        //            this.church_week_price = church_week_price;
        //        }
        //
        //        public String getChurch_time_price() {
        //            return church_time_price;
        //        }
        //
        //        public void setChurch_time_price(String church_time_price) {
        //            this.church_time_price = church_time_price;
        //        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getIs_sale() {
            return is_sale;
        }

        public void setIs_sale(String is_sale) {
            this.is_sale = is_sale;
        }

        public String getSale_num() {
            return sale_num;
        }

        public void setSale_num(String sale_num) {
            this.sale_num = sale_num;
        }

        public String getGoods_pic() {
            return goods_pic;
        }

        public void setGoods_pic(String goods_pic) {
            this.goods_pic = goods_pic;
        }

        //        public String getAttr() {
        //            return attr;
        //        }
        //
        //        public void setAttr(String attr) {
        //            this.attr = attr;
        //        }
        //
        //        public String getSpecs() {
        //            return specs;
        //        }
        //
        //        public void setSpecs(String specs) {
        //            this.specs = specs;
        //        }

        public int getAttr_count() {
            return attr_count;
        }

        public void setAttr_count(int attr_count) {
            this.attr_count = attr_count;
        }

        public int getSpecs_count() {
            return specs_count;
        }

        public void setSpecs_count(int specs_count) {
            this.specs_count = specs_count;
        }
    }
}

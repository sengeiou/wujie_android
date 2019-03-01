package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.SoftKeyboardUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.MessageEvent;
import com.txd.hzj.wjlp.view.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.txd.hzj.wjlp.minetoaty.storemanagement.InputAty.judgePrice;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/8 17:01
 * 功能描述：多规格
 */
public class MultipleSpecificationsAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.rootLayout)
    private RelativeLayout rootLayout;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.addTv)
    private TextView addTv;

    @ViewInject(R.id.showRecyclerView)
    private RecyclerView showRecyclerView;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    @ViewInject(R.id.saveTv)
    private TextView saveTv;

    private MyAdapter mAdapter;
    private String mGoods_id;
    private MyShowAdapter mMyShowAdapter;
    private String mSta_mid;

    private String mGoods_attr;
    private boolean mIsGone;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_multiple_specifications;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("多规格");
        mGoods_id = getIntent().getStringExtra("goods_id");
        mGoods_attr = getIntent().getStringExtra("goods_attr");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        mIsGone = getIntent().getBooleanExtra("isGone", false);
        LinearLayoutManager showLayoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        showRecyclerView.setLayoutManager(showLayoutManager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(new MyAdapter.OnViewClickLisener() {
            @Override
            public void onClick(final int pos) {
                CustomDialog customDialog = new CustomDialog.Builder(mContext)
                        .setIsShowTitle(false)
                        .setMessage("确定要删除此规格？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mAdapter.remove(pos);
                                if (mAdapter.getItemCount() == 0) {
                                    addTv.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                customDialog.show();
            }
        });
        recyclerView.setAdapter(mAdapter);
        if (!TextUtils.isEmpty(mGoods_attr)){
            mAdapter.setData(mGoods_attr);
            addTv.setVisibility(View.GONE);
        }

        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                rootLayout.getWindowVisibleDisplayFrame(rect);
                int height = rootLayout.getRootView().getHeight();
                int mainInvisibleHeight  = height - rect.bottom;
                if (mainInvisibleHeight> height/4){
                    int[] location = new int[2];
                    saveTv.getLocationInWindow(location);
//                    int h = recyclerView.getHeight()>height?mainInvisibleHeight:recyclerView.getHeight();
//                    Log.e("TAG", "onGlobalLayout: "+location[1]+"=="+recyclerView.getHeight()+"==="+rect.bottom+"==="+height+"==="+h);
                    if (recyclerView.getHeight()> rect.bottom){
                        int scrollHeight = location[1] - rect.bottom;
                        Log.e("TAG", "onGlobalLayout: "+recyclerView.getHeight()+"=="+scrollHeight+"==="+location[1]+"==="+rect.bottom);
                        rootLayout.scrollBy(0,scrollHeight);
                    }else {
                        rootLayout.scrollTo(0,0);
                    }
//                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,height-rect.bottom/2);
//                    rootLayout.setLayoutParams(layoutParams);

                }else {
                    rootLayout.scrollTo(0,0);
//                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
//                    rootLayout.setLayoutParams(layoutParams);
                }
            }
        });
    }

    @Override
    protected void requestData() {
        if (mGoods_id != null) {
            app_stage_goods_attr_list(mGoods_id, this);
        }
    }


    void app_stage_goods_attr_list(String goods_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("sta_mid", mSta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_stage_goods_attr_list", params, baseView);
    }

    void appDeleteAttr(String sta_mid, String goods_id, String attr_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("attr_id", attr_id);
        apiTool2.postApi(Config.BASE_URL + "OsManager/appDeleteAttr", params, baseView);
    }

    void appUpdateStageGoodsAttr(String sta_mid, String goods_id, String attr, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("attr",  attr);
        apiTool2.postApi(Config.BASE_URL + "OsManager/appUpdateStageGoodsAttr", params, baseView);
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("app_stage_goods_attr_list")) {
            final ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            if (data != null && data.size() > 0) {
                showRecyclerView.setVisibility(View.VISIBLE);
                mMyShowAdapter = new MyShowAdapter(data);
                showRecyclerView.setAdapter(mMyShowAdapter);
                mMyShowAdapter.setOnViewClickLisener(new MyShowAdapter.OnViewClickLisener() {
                    @Override
                    public void onClick(final int pos) {
                        CustomDialog customDialog = new CustomDialog.Builder(mContext)
                                .setIsShowTitle(false)
                                .setMessage("确定要删除此规格？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        if (!mIsGone) {
                                            appDeleteAttr(mSta_mid, mGoods_id, data.get(pos).get("attr_id"), MultipleSpecificationsAty.this);
                                            mMyShowAdapter.remove(pos);
                                        }
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                        customDialog.show();
                    }
                });
            }
            return;
        }

        if (requestUrl.endsWith("appDeleteAttr") ) {
            showToast(map.get("message"));
            return;
        }

        if (requestUrl.endsWith("appUpdateStageGoodsAttr")){
            showToast(map.get("message"));
            if (map.get("code").equals("1")) {
                EventBus.getDefault().post(new MessageEvent("", "MultipleSpecificationsAty"));
                finish();
            }
            return;
        }
    }

    @Override
    @OnClick({R.id.addTv, R.id.saveTv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addTv:
                if (mIsGone){
                    showToast("暂不支持修改");
                    return;
                }
                if (mMyShowAdapter != null) {
                    mAdapter.setCount(mMyShowAdapter.getItemCount());
                }
                mAdapter.addData();
                addTv.setVisibility(View.GONE);
                break;
            case R.id.saveTv:
                if (mAdapter.getItemCount()>0) {
                    if (mGoods_id != null) {
                        appUpdateStageGoodsAttr(mSta_mid, mGoods_id, mAdapter.getAttr(), this);
                    }else {
                        ArrayList<Map<String, String>> maps = JSONUtils.parseKeyAndValueToMapList(mAdapter.getMultiple());
                        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
                        for (int i = 0; i < maps.size(); i++) {
                            String name = maps.get(i).get("name");
                            String price = maps.get(i).get("price");
                            String jiesuan_price = maps.get(i).get("jiesuan_price");
                            if (TextUtils.isEmpty(name) ||TextUtils.isEmpty(price) ||TextUtils.isEmpty(jiesuan_price)){
                                arrayList.add(maps.get(i));
                            }
                            if (judgePrice(mContext, price, jiesuan_price)) {
                                showToast("规格"+(i+1)+"结算价过高");
                                return;
                            }
                        }
                        if (arrayList.size()>0){
                            showToast("请将信息补充完整");
                            return;
                        }
                        EventBus.getDefault().post(new MessageEvent(mAdapter.getMultiple(), "MultipleSpecificationsAty"));
                        finish();
                    }

                }else {
                    EventBus.getDefault().post(new MessageEvent("", "MultipleSpecificationsAty"));
                    finish();
                }
                break;
        }
    }

    public static class MyShowAdapter extends RecyclerView.Adapter<MyShowAdapter.ViewHolder> {
        private OnViewClickLisener mOnViewClickLisener;
        private ArrayList<Map<String, String>> mList;

        public MyShowAdapter(ArrayList<Map<String, String>> data) {
            mList = data;
        }

        public void setOnViewClickLisener(OnViewClickLisener onViewClickLisener) {
            this.mOnViewClickLisener = onViewClickLisener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specifications, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            Map<String, String> map = mList.get(position);
            holder.numTv.setText("规格" + (position + 1));
            holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnViewClickLisener != null) {
                        mOnViewClickLisener.onClick(position);
                    }
                }
            });

            if (holder.nameEdit.getTag() instanceof TextWatcher) {
                holder.nameEdit.removeTextChangedListener((TextWatcher) holder.nameEdit.getTag());
            }
            if (holder.moneyEdit.getTag() instanceof TextWatcher) {
                holder.moneyEdit.removeTextChangedListener((TextWatcher) holder.moneyEdit.getTag());
            }

            holder.nameEdit.setText(map.get("name"));
            holder.moneyEdit.setText(map.get("price"));
            holder.moneyEdit2.setText(map.get("jiesuan_price"));
            holder.nameEdit.setEnabled(false);
            holder.moneyEdit.setEnabled(false);
            holder.moneyEdit2.setEnabled(false);

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public void remove(int pos) {
            mList.remove(pos);
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.numTv)
            private TextView numTv;
            @ViewInject(R.id.deleteImg)
            private ImageView deleteImg;
            @ViewInject(R.id.nameEdit)
            private EditText nameEdit;
            @ViewInject(R.id.moneyEdit)
            private EditText moneyEdit;
            @ViewInject(R.id.moneyEdit2)
            private EditText moneyEdit2;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }


        public interface OnViewClickLisener {
            void onClick(int pos);
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private OnViewClickLisener mOnViewClickLisener;
        private List<String> nameData;
        private List<String> priceData;
        private List<String> priceData2;
        private Context mContext;

        private int count = 0;

        public MyAdapter(OnViewClickLisener onViewClickLisener) {
            mOnViewClickLisener = onViewClickLisener;
            nameData = new ArrayList<>();
            priceData = new ArrayList<>();
            priceData2 = new ArrayList<>();
        }

        public void setCount(int count) {
            this.count = count;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specifications, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.numTv.setText("规格" + (count + position + 1));
            holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnViewClickLisener != null) {
                        mOnViewClickLisener.onClick(position);
                    }
                }
            });

            holder.nameEdit.setFilters(new InputFilter[]{SoftKeyboardUtil.getInputFilter(mContext)});
            if (holder.nameEdit.getTag() instanceof TextWatcher) {
                holder.nameEdit.removeTextChangedListener((TextWatcher) holder.nameEdit.getTag());
            }
            if (holder.moneyEdit.getTag() instanceof TextWatcher) {
                holder.moneyEdit.removeTextChangedListener((TextWatcher) holder.moneyEdit.getTag());
            }

            holder.nameEdit.setText(nameData.get(position));
            holder.moneyEdit.setText(priceData.get(position));
            holder.moneyEdit2.setText(priceData2.get(position));
            if (position == nameData.size() - 1) {
                holder.addTv.setVisibility(View.VISIBLE);
            } else {
                holder.addTv.setVisibility(View.GONE);
            }

            holder.addTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addData();
                }
            });
            TextWatcher nameWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (nameData.get(position) != null) {
                        nameData.remove(position);
                    }
                    nameData.add(position, s.toString());
                }
            };
            holder.nameEdit.addTextChangedListener(nameWatcher);
            holder.nameEdit.setTag(nameWatcher);

            TextWatcher moneyWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (priceData.get(position) != null) {
                        priceData.remove(position);
                    }
                    priceData.add(position, s.toString());
                }
            };
            holder.moneyEdit.addTextChangedListener(moneyWatcher);
            holder.moneyEdit.setTag(moneyWatcher);

            TextWatcher moneyWatcher2 = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (priceData2.get(position) != null) {
                        priceData2.remove(position);
                    }
                    priceData2.add(position, s.toString());
                }
            };
            holder.moneyEdit2.addTextChangedListener(moneyWatcher2);
            holder.moneyEdit2.setTag(moneyWatcher2);

        }

        @Override
        public int getItemCount() {
            return nameData.size();
        }

        public void remove(int pos) {
            nameData.remove(pos);
            priceData.remove(pos);
            priceData2.remove(pos);
            notifyDataSetChanged();
        }

        public void clearData(){
            nameData.clear();
            priceData.clear();
            priceData2.clear();
            notifyDataSetChanged();
        }

        public void addData() {
            nameData.add("");
            priceData.add("");
            priceData2.add("");
            notifyDataSetChanged();
        }

        public String getAttr() {
            JSONArray array = new JSONArray();
            for (int i = 0; i < nameData.size(); i++) {
                JSONObject object = new JSONObject();
                try {
                    object.put("attr_id", "");
                    object.put("name", nameData.get(i));
                    object.put("price", priceData.get(i));
                    object.put("jiesuan_price", priceData2.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(object);
            }

            return array.toString();
        }

        public String getMultiple() {
            JSONArray array = new JSONArray();
            for (int i = 0; i < nameData.size(); i++) {
                JSONObject object = new JSONObject();
                try {
                    object.put("name", nameData.get(i));
                    object.put("price", priceData.get(i));
                    object.put("jiesuan_price", priceData2.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(object);
            }

            return array.toString();
        }

        public void setData(String goods_attr) {
            ArrayList<Map<String, String>> maps = JSONUtils.parseKeyAndValueToMapList(goods_attr);
            for (int i = 0; i < maps.size(); i++) {
                nameData.add(maps.get(i).get("name"));
                priceData.add(maps.get(i).get("price"));
                priceData2.add(maps.get(i).get("jiesuan_price"));
            }
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.numTv)
            private TextView numTv;
            @ViewInject(R.id.deleteImg)
            private ImageView deleteImg;
            @ViewInject(R.id.nameEdit)
            private EditText nameEdit;
            @ViewInject(R.id.moneyEdit)
            private EditText moneyEdit;
            @ViewInject(R.id.moneyEdit2)
            private EditText moneyEdit2;
            @ViewInject(R.id.addTv)
            private TextView addTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }


        public interface OnViewClickLisener {
            void onClick(int pos);
        }
    }

}

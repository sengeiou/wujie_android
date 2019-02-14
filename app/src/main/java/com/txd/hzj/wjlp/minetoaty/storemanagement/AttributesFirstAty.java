package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/12 8:58
 * 功能描述：
 */
public class AttributesFirstAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    @ViewInject(R.id.addTv)
    private TextView addTv;
    private String mGoods_id;
    private String mP_id;
    private MyAdapter mAdapter;
    private String mGoods_property;
    private boolean mIsGone;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_attributes_first;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("属性");
        mGoods_id = getIntent().getStringExtra("goods_id");
        mGoods_property = getIntent().getStringExtra("goods_property");
        mIsGone = getIntent().getBooleanExtra("isGone", false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnViewClickLisener(new MyAdapter.OnViewClickLisener() {
            @Override
            public void onClick(View v, int pos) {
                if (v.getId() == R.id.deleteImg) {
                    if (mGoods_id != null) {
                        AttributesDataBean dataBean = mAdapter.getData().get(pos);
                        app_delete_property(dataBean.getP_id(), AttributesFirstAty.this);
                    } else {
                        mAdapter.remove(pos);
                    }
                }
                if (v.getId() == R.id.editImg) {
                    AttributesDataBean dataBean = mAdapter.getData().get(pos);
                    Bundle bundle = new Bundle();
                    bundle.putString("goods_id", mGoods_id);
                    bundle.putBoolean("isGone",mIsGone);
                    bundle.putSerializable("AttributesDataBean", dataBean);
                    startActivity(AttributesSecondAty.class, bundle);
                }

            }
        });
        if (!TextUtils.isEmpty(mGoods_property)){
            ArrayList<AttributesDataBean> arrayList = new ArrayList<>();
            ArrayList<Map<String, String>> mapList = JSONUtils.parseKeyAndValueToMapList(mGoods_property);
            for (int i = 0; i < mapList.size(); i++) {
                AttributesDataBean dataBean = new AttributesDataBean();
                Map<String, String> map = mapList.get(i);
                dataBean.setP_id(map.get("p_id"));
                dataBean.setProp_title(map.get("title"));
                ArrayList<Map<String, String>> list = JSONUtils.parseKeyAndValueToMapList(map.get("break_down"));
                List<AttributesDataBean.TasteBean> tasteBeans = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                for (int i1 = 0; i1 < list.size(); i1++) {
                    AttributesDataBean.TasteBean tasteBean = new AttributesDataBean.TasteBean();
                    tasteBean.setId(list.get(i1).get("b_id"));
                    tasteBean.setTitle(list.get(i1).get("name"));
                    builder.append(list.get(i1).get("name"));
                    tasteBeans.add(tasteBean);
                    if (i1 != list.size()-1){
                       builder.append("/");
                    }
                }
                dataBean.setTaste_name(builder.toString());
                dataBean.setTaste(tasteBeans);
                arrayList.add(dataBean);

            }
            mAdapter.addAllData(arrayList);
        }
    }

    @Override
    protected void requestData() {

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
    public void event(MessageEvent messageEvent) {
        String label = messageEvent.getLabel();
        String message = messageEvent.getMessage();
        if (label.equals("AttributesSecondAty")) {
            try {
                JSONObject object = new JSONObject(message);
                AttributesDataBean bean = new AttributesDataBean();
                bean.setP_id(object.optString("p_id"));
                bean.setProp_title(object.optString("title"));
                String break_down = object.optString("break_down");
                ArrayList<Map<String, String>> arrayList = JSONUtils.parseKeyAndValueToMapList(break_down);
                if (arrayList != null && arrayList.size() > 0) {
                    StringBuilder builder = new StringBuilder();
                    List<AttributesDataBean.TasteBean> list = new ArrayList<>();
                    for (int i = 0; i < arrayList.size(); i++) {
                        AttributesDataBean.TasteBean tasteBean = new AttributesDataBean.TasteBean();
                        tasteBean.setId(arrayList.get(i).get("b_id"));
                        tasteBean.setTitle(arrayList.get(i).get("name"));
                        builder.append(arrayList.get(i).get("name"));
                        list.add(tasteBean);
                        if (i != arrayList.size() - 1) {
                            builder.append("/");
                        }
                    }
                    bean.setTaste(list);
                    bean.setTaste_name(builder.toString());
                }
                    mAdapter.addData(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoods_id != null) {
            app_stage_goods_property_list(mGoods_id, mP_id, this);
        }
    }

    void app_stage_goods_property_list(String goods_id, String p_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("p_id", p_id);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_stage_goods_property_list", params, baseView);
    }

    void app_delete_property(String p_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p_id", p_id);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_delete_property", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("app_stage_goods_property_list")) {
            final ArrayList data = JSONUtils.parseKeyAndValueToMapList(AttributesDataBean.class, map.get("data"));
            if (data != null) {
                mAdapter.addAllData(data);
            }
            return;
        }

        if (requestUrl.endsWith("app_delete_property")) {
            showToast(map.get("message"));
            if ("1".equals(map.get("code"))) {
                app_stage_goods_property_list(mGoods_id, mP_id, this);
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
                Bundle bundle = new Bundle();
                bundle.putString("goods_id", mGoods_id);
                startActivity(AttributesSecondAty.class, bundle);
                break;
            case R.id.saveTv:
                if (mGoods_id == null) {
                    try {
                    JSONArray jsonArray = new JSONArray();
                        List<AttributesDataBean> adapterData = mAdapter.getData();
                        for (int j = 0; j < adapterData.size(); j++) {
                        JSONObject object = new JSONObject();
                            object.put("p_id", adapterData.get(j).getP_id());
                            object.put("title", adapterData.get(j).getProp_title());
                            JSONArray array = new JSONArray();
                            List<AttributesDataBean.TasteBean> taste = adapterData.get(j).getTaste();
                            for (int i = 0; i < taste.size(); i++) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("b_id", taste.get(i).getId());
                                jsonObject.put("name", taste.get(i).getTitle());
                                array.put(jsonObject);
                            }
                            object.put("break_down", array.toString());
                            jsonArray.put(object);
                    }
                    EventBus.getDefault().post(new MessageEvent(jsonArray.toString(), "AttributesFirstAty"));
                    finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    EventBus.getDefault().post(new MessageEvent("", "AttributesFirstAty"));
                    finish();
                }

                break;
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private MyAdapter.OnViewClickLisener mOnViewClickLisener;

        private List<AttributesDataBean> mList;


        public MyAdapter() {
            mList = new ArrayList<>();
        }

        public void setOnViewClickLisener(OnViewClickLisener onViewClickLisener) {
            mOnViewClickLisener = onViewClickLisener;
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attribute_first, parent, false);
            MyAdapter.ViewHolder holder = new MyAdapter.ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
            AttributesDataBean dataBean = mList.get(position);
            holder.titleTv.setText(dataBean.getProp_title());

            holder.attrTv.setText(dataBean.getTaste_name());

            holder.editImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnViewClickLisener != null) {
                        mOnViewClickLisener.onClick(v, position);
                    }
                }
            });
            holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnViewClickLisener != null) {
                        mOnViewClickLisener.onClick(v, position);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public void remove(int pos) {
            mList.remove(pos);
            notifyDataSetChanged();
        }

        public void addAllData(ArrayList<AttributesDataBean> data) {
            mList.clear();
            mList.addAll(data);
            notifyDataSetChanged();
        }

        public void addData(AttributesDataBean bean) {
            mList.add(bean);
            notifyDataSetChanged();
        }

        public void replaceData(int pos, AttributesDataBean bean) {
            mList.add(pos, bean);
            notifyDataSetChanged();
        }

        public List<AttributesDataBean> getData() {
            return mList;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.deleteImg)
            private ImageView deleteImg;
            @ViewInject(R.id.editImg)
            private ImageView editImg;
            @ViewInject(R.id.titleTv)
            private TextView titleTv;

            @ViewInject(R.id.attrTv)
            private TextView attrTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }


        public interface OnViewClickLisener {
            void onClick(View v, int pos);
        }
    }


    public static class AttributesDataBean implements Serializable {

        /**
         * p_id : 125
         * prop_title : 王企鹅
         * taste : [{"id":"87","title":"大"}]
         * taste_name : 大
         */

        private String p_id;
        private String prop_title;
        private String taste_name;
        private List<TasteBean> taste;

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getProp_title() {
            return prop_title;
        }

        public void setProp_title(String prop_title) {
            this.prop_title = prop_title;
        }

        public String getTaste_name() {
            return taste_name;
        }

        public void setTaste_name(String taste_name) {
            this.taste_name = taste_name;
        }

        public List<TasteBean> getTaste() {
            return taste;
        }

        public void setTaste(List<TasteBean> taste) {
            this.taste = taste;
        }

        public static class TasteBean implements Serializable {
            /**
             * id : 87
             * title : 大
             */

            private String id;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}

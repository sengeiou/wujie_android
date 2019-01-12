package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.txd.hzj.wjlp.view.CustomDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/10 10:01
 * 功能描述：属性
 */
public class AttributesSecondAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.nameEdit)
    private EditText nameEdit;

    @ViewInject(R.id.showRecyclerView)
    private RecyclerView showRecyclerView;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    private MyAdapter mAdapter;

    @ViewInject(R.id.addTv)
    private TextView addTv;
    private String mGoods_id;
    private String mP_id="";
    private AttributesFirstAty.AttributesDataBean mDataBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_attributes_second;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("属性");
        mGoods_id = getIntent().getStringExtra("goods_id");
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
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(new MyAdapter.OnViewClickLisener() {
            @Override
            public void onClick(final int pos) {
                CustomDialog customDialog = new CustomDialog.Builder(mContext)
                        .setIsShowTitle(false)
                        .setMessage("确定要删除此分类？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (mGoods_id != null) {
                                    List<String> b_id = mAdapter.getB_id();
                                    app_delete_break_down(b_id.get(pos), AttributesSecondAty.this);
                                }
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
        mDataBean = (AttributesFirstAty.AttributesDataBean) getIntent().getSerializableExtra("AttributesDataBean");
        if (mDataBean != null) {
            nameEdit.setText(mDataBean.getProp_title());
            mP_id = mDataBean.getP_id();
            List<AttributesFirstAty.AttributesDataBean.TasteBean> taste = mDataBean.getTaste();
            List<String> stringList = new ArrayList<>();
            List<String> idList = new ArrayList<>();
            if (taste != null && taste.size() > 0) {
                addTv.setVisibility(View.GONE);
                for (int i = 0; i < taste.size(); i++) {
                    stringList.add(taste.get(i).getTitle());
                    idList.add(taste.get(i).getId());
                }
                mAdapter.setData(stringList, idList);
            }

        }
    }

    @Override
    protected void requestData() {
    }

    void appAddStageGoodsProperty(String goods_id, String goods_property, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("goods_property", goods_property);
        apiTool2.postApi(Config.BASE_URL + "OsManager/appAddStageGoodsProperty", params, baseView);
    }

    void app_delete_break_down(String b_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("b_id", b_id);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_delete_break_down", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("appAddStageGoodsProperty")) {
            showToast(map.get("message"));
            if (map.get("code").equals("1")) {
                finish();
            }
        }
    }

    @Override
    @OnClick({R.id.addTv, R.id.saveTv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addTv:
                mAdapter.addData();
                addTv.setVisibility(View.GONE);
                break;
            case R.id.saveTv:
                JSONObject object = new JSONObject();
                try {
                    object.put("p_id", mP_id);
                    object.put("title", nameEdit.getText().toString());
                    JSONArray array = new JSONArray();
                    List<String> stringList = mAdapter.getData();
                    List<String> bIdList = mAdapter.getB_id();
                    for (int i = 0; i < stringList.size(); i++) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("b_id", bIdList.get(i));
                        jsonObject.put("name", stringList.get(i));
                        array.put(jsonObject);
                    }
                    object.put("break_down", array.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mGoods_id != null) {
                    appAddStageGoodsProperty(mGoods_id, object.toString(), this);

                } else {
                    EventBus.getDefault().post(new MessageEvent(object.toString(), "AttributesSecondAty"));
                    finish();
                }
                break;
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private OnViewClickLisener mOnViewClickLisener;

        private List<String> data;
        private List<String> b_id;


        public MyAdapter(OnViewClickLisener onViewClickLisener) {
            data = new ArrayList<>();
            b_id = new ArrayList<>();
            mOnViewClickLisener = onViewClickLisener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attribute_second, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnViewClickLisener != null) {
                        mOnViewClickLisener.onClick(position);
                    }
                }
            });

            if (position == data.size() - 1) {
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

            if (holder.nameEdit.getTag() instanceof TextWatcher) {
                holder.nameEdit.removeTextChangedListener((TextWatcher) holder.nameEdit.getTag());
            }
            holder.nameEdit.setText(data.get(position));
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (data.get(position) != null) {
                        data.remove(position);
                    }
                    data.add(position, s.toString());
                }
            };
            holder.nameEdit.addTextChangedListener(textWatcher);
            holder.nameEdit.setTag(textWatcher);
        }

        public List<String> getData() {
            return data;
        }

        public List<String> getB_id() {
            return b_id;
        }

        public void addData() {
            data.add("");
            b_id.add("");
            notifyDataSetChanged();
        }

        public void setData(List<String> data, List<String> b_id) {
            this.data.addAll(data);
            this.b_id.addAll(b_id);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void remove(int pos) {
            data.remove(pos);
            b_id.remove(pos);
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.deleteImg)
            private ImageView deleteImg;
            @ViewInject(R.id.nameEdit)
            private EditText nameEdit;
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

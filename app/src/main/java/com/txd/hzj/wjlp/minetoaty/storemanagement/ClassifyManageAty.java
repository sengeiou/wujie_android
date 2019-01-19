package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/7 16:13
 * 功能描述：
 */
public class ClassifyManageAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.time_select_img)
    private ImageView time_select_img;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    @ViewInject(R.id.editLayout)
    private LinearLayout editLayout;

    @ViewInject(R.id.titleEdit)
    private EditText titleEdit;

    @ViewInject(R.id.numEdit)
    private EditText numEdit;

    @ViewInject(R.id.briefEdit)
    private EditText briefEdit;

    @ViewInject(R.id.addClassifyTv)
    private TextView addClassifyTv;

    @ViewInject(R.id.saveTv)
    private TextView saveTv;

    @ViewInject(R.id.moveTv)
    private TextView moveTv;

    private ClassifyAdpater mClassifyAdpater;

    private Context mContext;
    private String mSta_mid;

    private String mId="";
    private String mIs_del="";
    private String mSort="";
    private boolean mIsShowDelete;
    private String mGoods_id;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_classify_manage;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("分类管理");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        mIsShowDelete = getIntent().getBooleanExtra("isShowDelete", false);
        mGoods_id = getIntent().getStringExtra("goods_id");
        if (mIsShowDelete) {
            time_select_img.setVisibility(View.VISIBLE);
            time_select_img.setImageResource(R.drawable.icon_trash);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mClassifyAdpater = new ClassifyAdpater();
        if (mGoods_id != null) {
            addClassifyTv.setVisibility(View.GONE);
            saveTv.setVisibility(View.GONE);
            moveTv.setVisibility(View.VISIBLE);
            mClassifyAdpater.isShowEdit(false);
        } else {
            addClassifyTv.setVisibility(View.VISIBLE);
            saveTv.setVisibility(View.GONE);
            moveTv.setVisibility(View.GONE);
            mClassifyAdpater.isShowEdit(true);
        }
        mClassifyAdpater.setOnItemClickListener(new ClassifyAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<ClassifyDataBean> data = mClassifyAdpater.getData();
                ClassifyDataBean classifyDataBean = data.get(position);
                if (mIsShowDelete) {
                    mId = classifyDataBean.getId();
                    mSort = classifyDataBean.getSort();
                    mClassifyAdpater.setSelectPosition(position);
                } else {
                    if (mGoods_id == null) {
                        EventBus.getDefault().post(new MessageEvent(classifyDataBean.getName() + "-" + classifyDataBean.getId(), "ClassifyManageAty"));
                        finish();
                    } else {
                        mId = classifyDataBean.getId();
                        mClassifyAdpater.setSelectPosition(position);
                    }
                }
            }
        });

        mClassifyAdpater.setOnItemViewClickListener(new ClassifyAdpater.OnItemViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                time_select_img.setVisibility(View.GONE);
                addClassifyTv.setVisibility(View.GONE);
                saveTv.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                editLayout.setVisibility(View.VISIBLE);
                ArrayList<ClassifyDataBean> data = mClassifyAdpater.getData();
                ClassifyDataBean classifyDataBean = data.get(position);
                titleEdit.setText(classifyDataBean.getName());
                numEdit.setText(classifyDataBean.getSort());
                briefEdit.setText(classifyDataBean.getDesc());
                mId = classifyDataBean.getId();
                mSort = classifyDataBean.getSort();
            }
        });
        recyclerView.setAdapter(mClassifyAdpater);
    }

    @Override
    protected void requestData() {
        app_goods_cate("2", mSta_mid, this);
    }

    void app_goods_cate(String type, String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_goods_cate", params, baseView);
    }

    void app_edit_cate(String id, String is_del, String name, String desc, String sort,String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        if (!TextUtils.isEmpty(id)){
            params.addBodyParameter("id", id);
        }
        if (!TextUtils.isEmpty(is_del)){
            params.addBodyParameter("is_del", is_del);
        }
        params.addBodyParameter("name", name);
        params.addBodyParameter("desc", desc);
        if (!TextUtils.isEmpty(sort)){
            params.addBodyParameter("sort", sort);
        }
        params.addBodyParameter("sta_mid",sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_edit_cate", params, baseView);
    }

    void appMoveGoodsToCate(String goods_id, String cate_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("cate_id", cate_id);
        apiTool2.postApi(Config.BASE_URL + "OsManager/appMoveGoodsToCate", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("app_goods_cate")) {
            ArrayList<ClassifyDataBean> arrayList = JSONUtils.parseKeyAndValueToMapList(ClassifyDataBean.class, map.get("data"));
            mClassifyAdpater.setData(arrayList);
            return;
        }

        if (requestUrl.endsWith("app_edit_cate")) {
            showToast(map.get("message"));
            if (map.get("code").equals("1")) {
                requestData();
                if (TextUtils.isEmpty(mIs_del)) {
                    time_select_img.setVisibility(View.VISIBLE);
                    addClassifyTv.setVisibility(View.VISIBLE);
                    saveTv.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    editLayout.setVisibility(View.GONE);
                }
            }
            return;
        }

        if (requestUrl.endsWith("appMoveGoodsToCate")) {
            showToast(map.get("message"));
            if ("1".equals(map.get("code"))) {
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid", mSta_mid);
                startActivity(CommodityManagementAty.class, bundle);
                finish();
            }
            return;
        }

    }

    @Override
    @OnClick({R.id.time_select_img, R.id.addClassifyTv, R.id.saveTv, R.id.moveTv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.time_select_img:
                if (TextUtils.isEmpty(mId)) {
                    showToast("请选择要删除的分类");
                    return;
                }
                mIs_del = "1";
                CustomDialog customDialog = new CustomDialog.Builder(mContext)
                        .setIsShowTitle(false)
                        .setMessage("确定要删除此分类？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                app_edit_cate(mId, mIs_del, titleEdit.getText().toString(), briefEdit.getText().toString(), mSort,mSta_mid, ClassifyManageAty.this);
                                dialog.dismiss();
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
                break;
            case R.id.addClassifyTv:
                time_select_img.setVisibility(View.GONE);
                addClassifyTv.setVisibility(View.GONE);
                saveTv.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                editLayout.setVisibility(View.VISIBLE);
                titleEdit.getText().clear();
                numEdit.getText().clear();
                briefEdit.getText().clear();
                break;
            case R.id.saveTv:
                mIs_del = "";
                String name = titleEdit.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    showToast("分类名称不能为空");
                    return;
                }
                app_edit_cate(mId, mIs_del, name, briefEdit.getText().toString(), mSort, mSta_mid,this);
                break;
            case R.id.moveTv:
                CustomDialog customDialog2 = new CustomDialog.Builder(mContext)
                        .setIsShowTitle(false)
                        .setMessage("确定移动？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                appMoveGoodsToCate(mGoods_id, mId, ClassifyManageAty.this);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                customDialog2.show();
                break;
        }
    }



    public static class ClassifyAdpater extends RecyclerView.Adapter<ClassifyAdpater.ViewHolder> {

        private int selectPosition = -1;

        private OnItemViewClickListener mOnItemViewClickListener;
        private OnItemClickListener mOnItemClickListener;

        private ArrayList<ClassifyDataBean> mList;

        private boolean isShowEdit;

        public ClassifyAdpater() {
            mList = new ArrayList<>();
        }

        public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
            mOnItemViewClickListener = onItemViewClickListener;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classify_manage, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            ViewUtils.inject(viewHolder, view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            if (selectPosition == position) {
                holder.itemView.setBackgroundColor(Color.parseColor("#EEEEEE"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            ClassifyDataBean classifyDataBean = mList.get(position);
            holder.titleTv.setText(classifyDataBean.getName());
            if (isShowEdit) {
                holder.editTv.setVisibility(View.VISIBLE);
                holder.editTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemViewClickListener != null) {
                            mOnItemViewClickListener.onClick(v, position);
                        }
                    }
                });
            } else {
                holder.editTv.setVisibility(View.GONE);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position);
                    }
                }
            });

        }

        public void setSelectPosition(int position) {
            selectPosition = position;
            notifyDataSetChanged();
        }

        public void isShowEdit(boolean isShow) {
            this.isShowEdit = isShow;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public void setData(ArrayList<ClassifyDataBean> arrayList) {
            mList.clear();
            mList.addAll(arrayList);
            notifyDataSetChanged();
        }

        public ArrayList<ClassifyDataBean> getData() {
            return mList;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.titleTv)
            TextView titleTv;
            @ViewInject(R.id.editTv)
            TextView editTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemViewClickListener {
            void onClick(View view, int position);
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }


    public static class ClassifyDataBean {

        /**
         * id : 50
         * m_id : 1
         * name : 精品小菜
         * desc : 啊飒飒的
         * sort : 7
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


}

package com.txd.hzj.wjlp.clearinventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.publicinterface.OnItemViewClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/28 9:49
 * 功能描述：
 */
public class RefundedAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    private Context mContext;
    private RefundedAdapter mRefundedAdapter;
    private String mClean_id;
    private String mGoods_num;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_refunded;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("退款");
        mClean_id = getIntent().getStringExtra("clean_id");
        mGoods_num = getIntent().getStringExtra("goods_num");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void requestData() {
        cause_list();
    }

    public void cause_list() {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        apiTool2.postApi(Config.SHARE_URL + "index.php/Api/Clean/cause_list", params, this);
    }

    public void clean_refuse(String clean_id,String goods_num,String cause_id,String reason_desc) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("clean_id",clean_id);
        params.addBodyParameter("goods_num",goods_num);
        params.addBodyParameter("type","1");
        if (!cause_id.equals("-1")){
            params.addBodyParameter("cause_id",cause_id);
        }
        params.addBodyParameter("reason_desc",reason_desc);
        apiTool2.postApi(Config.SHARE_URL + "index.php/Api/Clean/clean_refuse", params, this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        final Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("cause_list")) {
            final ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            if (mapArrayList != null) {
                mRefundedAdapter = new RefundedAdapter(mapArrayList);
                recyclerView.setAdapter(mRefundedAdapter);
            }
            return;
        }
        if (requestUrl.endsWith("clean_refuse")){
            showToast(map.get("message"));
            finish();
        }
    }

    @OnClick({R.id.commit})
    public void OnClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.commit:
                if (TextUtils.isEmpty(mRefundedAdapter.getSelect())){
                    showToast("请选择退款原因");
                    return;
                }
                clean_refuse(mClean_id,mGoods_num,mRefundedAdapter.getSelect(),mRefundedAdapter.getEdit());
                break;
            default:
                break;
        }
    }

    private static class RefundedAdapter extends RecyclerView.Adapter {
        private Context mContext;

        private OnItemViewClickListener mOnItemViewClickListener;

        private ArrayList<Map<String, String>> mList;

        private final int Normal = 0;

        private final int Bottom = 1;

        private List<Boolean> checkList;

        private String editContent;


        public RefundedAdapter(ArrayList<Map<String, String>> list) {
            mList = list;
            checkList = new ArrayList<>();
            clearChecked();
        }

        public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
            mOnItemViewClickListener = onItemViewClickListener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            if (viewType == Normal) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_refunded, parent, false);
                NormalViewHolder holder = new NormalViewHolder(view);
                ViewUtils.inject(holder, view);
                return holder;
            } else if (viewType == Bottom) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_refunded_bottom, parent, false);
                BottomViewHolder holder = new BottomViewHolder(view);
                ViewUtils.inject(holder, view);
                return holder;
            }

            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            if (position != mList.size()) {
                NormalViewHolder normalViewHolder = (NormalViewHolder) holder;
                Map<String, String> map = mList.get(position);
                normalViewHolder.reasonTv.setText(map.get("title"));
                if (checkList.get(position)) {
                    normalViewHolder.selectImg.setImageResource(R.drawable.icon_cart_goods_selected);
                } else {
                    normalViewHolder.selectImg.setImageResource(R.drawable.icon_cart_goods_unselect);
                }
                normalViewHolder.selectImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearChecked();
                        checkList.add(position, true);
                        notifyDataSetChanged();
                    }
                });
            } else {
                final BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;

                if (bottomViewHolder.reasonEt.getTag() != null && bottomViewHolder.reasonEt.getTag() instanceof TextWatcher) {
                    bottomViewHolder.reasonEt.removeTextChangedListener((TextWatcher) bottomViewHolder.reasonEt.getTag());
                }
                TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int length = s.length();
                        editContent = s.toString();
                        bottomViewHolder.numTv.setText(length + "/100");
                    }
                };
                bottomViewHolder.reasonEt.addTextChangedListener(watcher);
                bottomViewHolder.reasonEt.setTag(watcher);
            }
        }

        public String getEdit() {
            return editContent == null ? "" : editContent;
        }

        public String getSelect() {
            String cause_id ="";
            for (int i = 0; i < checkList.size(); i++) {
                if (checkList.get(i)) {
                    cause_id = mList.get(i).get("cause_id");
                    break;
                }
            }
            return cause_id;
        }

        private void clearChecked() {
            for (int i = 0; i < mList.size(); i++) {
                checkList.add(i,false);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == mList.size()) {
                return Bottom;
            }
            return Normal;
        }

        @Override
        public int getItemCount() {
            return mList.size() + 1;
        }

        public static class NormalViewHolder extends RecyclerView.ViewHolder {

            @ViewInject(R.id.selectImg)
            private ImageView selectImg;

            @ViewInject(R.id.reasonTv)
            private TextView reasonTv;

            public NormalViewHolder(View itemView) {
                super(itemView);
            }
        }

        public static class BottomViewHolder extends RecyclerView.ViewHolder {

            @ViewInject(R.id.reasonEt)
            private EditText reasonEt;

            @ViewInject(R.id.numTv)
            private TextView numTv;

            public BottomViewHolder(View itemView) {
                super(itemView);
            }
        }

    }
}

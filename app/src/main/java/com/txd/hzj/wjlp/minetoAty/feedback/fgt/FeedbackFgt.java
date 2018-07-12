package com.txd.hzj.wjlp.minetoAty.feedback.fgt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.http.article.ArticlePst;
import com.txd.hzj.wjlp.http.register.RegisterPst;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/8/10 0010
 * 时间：下午 3:48
 * 描述：意见反馈
 *
 */
public class FeedbackFgt extends BaseFgt {
    /**
     * 反馈类型
     */
    private String type;
    /**
     * 用户id
     */
    private String user_id;
    /**
     * 真实姓名
     */
    private String real_name;

    /**
     * 用户名
     */
    @ViewInject(R.id.real_name_tv)
    private TextView real_name_tv;
    /**
     * 用户id
     */
    @ViewInject(R.id.user_id_tv)
    private TextView user_id_tv;

    /**
     * 内容
     */
    @ViewInject(R.id.feedback_content_ev)
    private EditText feedback_content_ev;

    /**
     * 长度
     */
    @ViewInject(R.id.leng_tips_tv)
    private TextView leng_tips_tv;
    private int len = 0;

    private ArticlePst articlePst;

    public static FeedbackFgt newInstance(String type, String user_id, String real_name) {
        FeedbackFgt fragment = new FeedbackFgt();
        fragment.type = type;
        fragment.user_id = user_id;
        fragment.real_name = real_name;
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        real_name_tv.setText(real_name);
        user_id_tv.setText(user_id);

        feedback_content_ev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                len = charSequence.length();
                leng_tips_tv.setText(len + "/300");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    @OnClick({R.id.submit_feed_back_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.submit_feed_back_tv:// 提交
                String feedback_content = feedback_content_ev.getText().toString();
                articlePst.feedback(type, feedback_content);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_feedback_hzj;
    }

    @Override
    protected void initialized() {
        articlePst = new ArticlePst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        showRightTip("反馈成功");
        getActivity().finish();
    }
}

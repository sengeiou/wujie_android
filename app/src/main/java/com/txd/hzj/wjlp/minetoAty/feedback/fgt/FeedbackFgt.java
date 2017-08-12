package com.txd.hzj.wjlp.minetoAty.feedback.fgt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/10 0010
 * 时间：下午 3:48
 * 描述：意见反馈
 * ===============Txunda===============
 */
public class FeedbackFgt extends BaseFgt {
    private String type;

    public static FeedbackFgt newInstance(String type) {
        FeedbackFgt fragment = new FeedbackFgt();
        fragment.type = type;
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_feedback_hzj;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void immersionInit() {

    }
}

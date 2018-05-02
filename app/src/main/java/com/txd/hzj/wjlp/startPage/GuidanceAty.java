package com.txd.hzj.wjlp.startPage;

import android.os.Bundle;
import android.view.KeyEvent;

import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.txd.hzj.wjlp.MainAty;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 创建人： Txd_Cjh
 * 创建时间： 2017/9/29 18:28
 * 功能描述：启动页
 */
public class GuidanceAty extends BaseAty {
    /**
     * 首次进入App
     */
    private boolean is_first_time = true;

    private Timer timer;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_guidance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.guidance_title_layout);
    }

    @Override
    protected void initialized() {
        timer = new Timer();
        is_first_time = PreferencesUtils.getBoolean(this, "is_first_time", true);
        initEvent();
    }

    private void initEvent() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toDoNext();
            }
        }, 0); // 原始取值1200
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            timer.cancel();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void requestData() {
    }

    /**
     * 进入引导页或者主页
     */
    private void toDoNext() {
        if (is_first_time) {
            startActivity(GuidePageAty.class, null);
            PreferencesUtils.putBoolean(GuidanceAty.this, "is_first_time", false);
        } else {
            startActivity(MainAty.class, null);
        }
        finish();
    }
}

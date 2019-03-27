package com.txd.hzj.wjlp.clearinventory;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoaty.help.HelpFgt;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/26 17:33
 * 功能描述：互清库存寄存攻略和常见问题
 */
public class StrategyAndProblemAty extends BaseAty {

    /**
     * 常见问题  寄存攻略
     */
    private String mName;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_strategy_problem;
    }

    @Override
    protected void initialized() {
        mName = getIntent().getStringExtra("name");
        HelpFgt helpFgt = HelpFgt.getFgt(mName);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,helpFgt).commit();
    }

    @Override
    protected void requestData() {

    }
}

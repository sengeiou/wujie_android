package com.txd.hzj.wjlp.jpush;

import android.content.Context;

import com.ants.theantsgo.config.Config;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.txd.hzj.wjlp.jpush.TagAliasOperatorHelper.sequence;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/28 0028
 * 时间：11:38
 * 描述：极光推送设置别名和Tag
 * ===============Txunda===============
 */

public class JpushSetTagAndAlias {

    int action = -1;

    private static JpushSetTagAndAlias instance;
    private TagAliasOperatorHelper.TagAliasBean tagAliasBean;

    private JpushSetTagAndAlias() {
    }

    public static synchronized JpushSetTagAndAlias getInstance() {
        if (instance == null) {
            instance = new JpushSetTagAndAlias();
        }
        return instance;
    }

    /**
     * 设置标签和别名
     *
     * @param context 上下文
     */
    public void setTag(Context context) {
        action = TagAliasOperatorHelper.ACTION_SET;
        setOrDelTagAndAlias(context,false);
    }

    /**
     * 删除tag
     *
     * @param context 上下文
     */
    public void delTag(Context context) {
        action = TagAliasOperatorHelper.ACTION_DELETE;
        setOrDelTagAndAlias(context,false);
    }

    /**
     * 设置别名
     *
     * @param context 上下文
     */
    public void setAlias(Context context) {
        action = TagAliasOperatorHelper.ACTION_SET;
        setOrDelTagAndAlias(context,true);
    }

    /**
     * 删除Alias
     * @param context   上下文
     */
    public void delAlias(Context context){
        action = TagAliasOperatorHelper.ACTION_DELETE;
        setOrDelTagAndAlias(context,true);
    }

    /**
     * * 设置或删除Tag
     *
     * @param context       上线文
     * @param isAliasAction 设置的是Tag还是alias,true是alias false是tag
     */
    private void setOrDelTagAndAlias(Context context, boolean isAliasAction) {
        tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        if (isAliasAction) {
            tagAliasBean.action = action;
            tagAliasBean.alias = Config.getToken();
        } else {
            Set<String> tagSet = new LinkedHashSet<>();
            tagSet.add(Config.getToken());
            tagAliasBean.tags = tagSet;
        }
        tagAliasBean.action = action;
        sequence++;
        tagAliasBean.isAliasAction = isAliasAction;
        TagAliasOperatorHelper.getInstance().handleAction(context, sequence, tagAliasBean);
    }

}

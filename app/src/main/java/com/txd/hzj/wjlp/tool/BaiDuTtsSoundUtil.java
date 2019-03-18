package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.text.TextUtils;

import com.ants.theantsgo.util.L;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：百度语音合成工具类
 */
public class BaiDuTtsSoundUtil implements SpeechSynthesizerListener {

    private static final String APP_ID = "14469834";
    private static final String APP_KEY = "OoxiAtx474sQQkuo4I0THiDf";
    private static final String APP_SECRET = "70fDXcxAiF2iskY5495YCBntngS0tgTW";

    private static BaiDuTtsSoundUtil baiDuTtsSoundUtil;

    private SpeechSynthesizer mSpeechSynthesizer;

    Context context;

    public static BaiDuTtsSoundUtil getInstance(Context context) {
        if (baiDuTtsSoundUtil == null) {
            synchronized (BaiDuTtsSoundUtil.class) {
                if (baiDuTtsSoundUtil == null) {
                    baiDuTtsSoundUtil = new BaiDuTtsSoundUtil(context);
                }
            }
        }
        return baiDuTtsSoundUtil;
    }

    private BaiDuTtsSoundUtil(Context context) {
        this.context = context;
    }

    public void speak(String content) {
        initBauDuTTS(content, "0");
    }

    public void speak(String content, String speaker) {
        initBauDuTTS(content, speaker);
    }

    /**
     * 初始化百度语音合成
     *
     * @param content 上下文
     * @param speaker 语音角色类型 0普通女声(默认) 1普通男声 2特别男声 3情感男声<度逍遥> 4情感儿童声<度丫丫>
     */
    private void initBauDuTTS(String content, String speaker) {
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context); // 设置当前Context 是Context的之类，如Activity
        mSpeechSynthesizer.setAppId(APP_ID); // 设置 App Id和 App Key 及 App Secret
        mSpeechSynthesizer.setApiKey(APP_KEY, APP_SECRET); // 设置 App Key 及 App Secret
        mSpeechSynthesizer.auth(TtsMode.MIX);
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        mSpeechSynthesizer.setSpeechSynthesizerListener(this); //listener是SpeechSynthesizerListener 的实现类，需要实现您自己的业务逻辑。SDK合成后会对这个类的方法进行回调。
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, speaker); // 设置发声的人声音，在线
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE,SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
        if (!TextUtils.isEmpty(content)) {
            mSpeechSynthesizer.speak(content);
        }
    }

    @Override
    public void onSynthesizeStart(String s) {
        // 开始合成
    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        // 合成过程中的数据回调接口
    }

    @Override
    public void onSynthesizeFinish(String s) {
        // 合成结束
    }

    @Override
    public void onSpeechStart(String s) {
        // 开始播放声音
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        // 播放中....
    }

    @Override
    public void onSpeechFinish(String s) {
        // 播放结束
        mSpeechSynthesizer.release(); // 释放资源
        mSpeechSynthesizer = null;
    }

    @Override
    public void onError(String s, SpeechError speechError) {
        // 合成或播放过程中出错
        mSpeechSynthesizer.release(); // 释放资源
        mSpeechSynthesizer = null;
        L.i("speechError.code:" + getErrorStr(speechError.code));
    }

    /**
     * 返回错误码对照表
     *
     * @param state
     * @return
     */
    private String getErrorStr(int state) {
        String string = "";
        switch (state) {
            case -1:
                string = "在线引擎授权失败";
                break;
            case -2:
                string = "在线合成请求失败";
                break;
            case -3:
                string = "在线合成停止失败";
                break;
            case -4:
                string = "在线授权中断异常";
                break;
            case -5:
                string = "在线授权执行时异常";
                break;
            case -6:
                string = "在线授权时间超时";
                break;
            case -7:
                string = "在线合成返回错误信息";
                break;
            case -8:
                string = "在线授权token为空";
                break;
            case -9:
                string = "在线引擎没有初始化";
                break;
            case -10:
                string = "在线引擎合成时异常";
                break;
            case -100:
                string = "离线引擎授权失败";
                break;
            case -101:
                string = "离线合成停止失败";
                break;
            case -102:
                string = "离线授权下载License失败";
                break;
            case -103:
                string = "离线授权信息为空";
                break;
            case -104:
                string = "离线授权类型未知";
                break;
            case -105:
                string = "离线授权中断异常";
                break;
            case -106:
                string = "离线授权执行时异常";
                break;
            case -107:
                string = "离线授权执行时间超时";
                break;
            case -108:
                string = "离线合成引擎初始化失败";
                break;
            case -109:
                string = "离线引擎未初始化";
                break;
            case -110:
                string = "离线合成时异常";
                break;
            case -111:
                string = "离线合成返回值非0";
                break;
            case -112:
                string = "离线授权已过期";
                break;
            case -113:
                string = "离线授权包名不匹配";
                break;
            case -114:
                string = "离线授权签名不匹配";
                break;
            case -115:
                string = "离线授权设备信息不匹配";
                break;
            case -116:
                string = "离线授权平台不匹配";
                break;
            case -117:
                string = "离线授权的license文件不存在";
                break;
            case -200:
                string = "混合引擎离线在线都授权失败";
                break;
            case -201:
                string = "混合引擎授权中断异常";
                break;
            case -202:
                string = "混合引擎授权执行时异常";
                break;
            case -203:
                string = "混合引擎授权执行时间超时";
                break;
            case -204:
                string = "在线合成初始化成功，离线合成初始化失败。 可能是离线资源dat文件未加载或包名错误";
                break;
            case -300:
                string = "合成文本为空";
                break;
            case -301:
                string = "合成文本长度过长（不要超过GBK1024个字节）";
                break;
            case -302:
                string = "合成文本无法获取GBK字节";
                break;
            case -400:
                string = "TTS未初始化";
                break;
            case -401:
                string = "TTS模式无效";
                break;
            case -402:
                string = "TTS合成队列已满（最大限度为1000）";
                break;
            case -403:
                string = "TTS批量合成文本过多（最多为100）";
                break;
            case -404:
                string = "TTS停止失败";
                break;
            case -405:
                string = "TTS APP ID无效";
                break;
            case -406:
                string = "TTS被调用方法参数无效";
                break;
            case -500:
                string = "Context被释放或为空";
                break;
            case -600:
                string = "播放器为空";
                break;
            case -9999:
                string = "未知错误";
                break;
        }
        return string;
    }
}

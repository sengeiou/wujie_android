package com.txd.hzj.wjlp.tool;

import android.content.Context;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/10/18 018上午 11:07
 * <br>功能描述：百度文字转语音工能
 */
public class BaiDuTtsSoundUtil {

    private static final String APP_ID = "14469834";
    private static final String API_KEY = "OoxiAtx474sQQkuo4I0THiDf";
    private static final String APP_SECRET = "70fDXcxAiF2iskY5495YCBntngS0tgTW";

    private static boolean isStop = true;

    private static SpeechSynthesizer mSpeechSynthesizer;

    /**
     * 转换并播放语音
     *
     * @param context 上下文
     * @param content 转换的内容
     */
    public static void playSound(Context context, String content) {
        initialTts(context, content, "0");
    }

    /**
     * 转换并播放语音
     *
     * @param context 上下文
     * @param content 转换的内容
     * @param param   设置角色 0:普通女声 1:普通男声 2:特别男声 3:情感男声<度逍遥> 4:情感儿童声<度丫丫>
     */
    public static void playSound(Context context, String content, String param) {
        initialTts(context, content, param);
    }

    private static void initialTts(Context context, String content, String param) {
        // 建议每次只使用一个实例。release方法调用后，可以使用第二个。
        if (isStop) {
            mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        }
        mSpeechSynthesizer.setContext(context); // this 是Context的之类，如Activity
        mSpeechSynthesizer.setSpeechSynthesizerListener(new MyListener());
        mSpeechSynthesizer.setAppId(APP_ID); // 这里只是为了让Demo运行使用的APPID,请替换成自己的id
        mSpeechSynthesizer.setApiKey(API_KEY, APP_SECRET); // 这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey
        mSpeechSynthesizer.auth(TtsMode.ONLINE);  // 纯在线  或 TtsMode.MIX 离在线混合
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, param); // 设置发声的人声音，在线生效
        mSpeechSynthesizer.initTts(TtsMode.MIX); // 初始化离在线混合模式，如果只需要在线合成功能，使用 TtsMode.ONLINE

        if (!content.isEmpty()) { // 如果内容不为空，转换完成后直接播放
            mSpeechSynthesizer.speak(content);
        }

    }

    static class MyListener implements SpeechSynthesizerListener {

        @Override
        public void onSynthesizeStart(String s) {
//            合成开始
            isStop = false;
        }

        @Override
        public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
//            合成过程中的数据回调接口
        }

        @Override
        public void onSynthesizeFinish(String s) {
//            合成结束
        }

        @Override
        public void onSpeechStart(String s) {
//            播放开始
        }

        @Override
        public void onSpeechProgressChanged(String s, int i) {
//            播放过程中的回调
        }

        @Override
        public void onSpeechFinish(String s) {
//            播放结束
            mSpeechSynthesizer.release(); // 释放资源
            isStop = true;
        }

        @Override
        public void onError(String s, SpeechError speechError) {
//            合成和播放过程中出错时的回调
        }
    }

}

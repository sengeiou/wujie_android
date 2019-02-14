package com.txd.hzj.wjlp.webviewH5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.payByThirdParty.AliPay;
import com.ants.theantsgo.payByThirdParty.aliPay.AliPayCallBack;
import com.ants.theantsgo.rsa.Base64Utils;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.PreferencesUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bluetoothPrint.SearchBluetoothAty;
import com.txd.hzj.wjlp.http.Pay;
import com.txd.hzj.wjlp.http.index.IndexPst;
import com.txd.hzj.wjlp.login.LoginAty;
import com.txd.hzj.wjlp.tool.BitmapUtils;
import com.txd.hzj.wjlp.tool.MapIntentUtil;
import com.txd.hzj.wjlp.wxapi.GetPrepayIdTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：浏览器界面(展示商品什么的一些东西）
 */
public class WebViewAty extends BaseAty {

    //    private String url = Config.OFFICIAL_WEB;
    private String url = "";

    @ViewInject(R.id.webView_show_webv)
    private WebView webView_show_webv;

    // 标题栏
    @ViewInject(R.id.webView_title_layout)
    public LinearLayout webView_title_layout;
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    private String payType; // 支付类型
    private String type; // 返回Type
    private String order_id; // 订单id
    private String discount_type; // 折扣类型

    String divination_id;
    String divination_type;
    String reurl;
    String faileurl;

    IndexPst indexPst;

    // 图片裁切的宽高
    private static int width = 0;
    private static int high = 0;
    private boolean mIsShowTitle;
    private String mTitle;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_webview;
    }

    @Override
    protected void initialized() {

        AndroidBug5497Workaround.assistActivity(this);

        // 获取传入的Url
        Intent intent = getIntent();
        if (intent.hasExtra("url")) {
            String urlTemp = intent.getStringExtra("url");
            if (!urlTemp.isEmpty()) {
                url = urlTemp; // 如果存在url字段，并且字段不为空，则赋值给需要加载的url
            }
        }

        mIsShowTitle = intent.getBooleanExtra("isShowTitle", false);
        if (mIsShowTitle) {
            showStatusBar(R.id.webView_title_layout);
            mTitle = intent.getStringExtra("title");
            titlt_conter_tv.setText(mTitle);
        }
        webView_title_layout.setVisibility(mIsShowTitle ? View.VISIBLE : View.GONE);

        wxPayReceiver = new WxPayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("wjyp.wxPay");
        registerReceiver(wxPayReceiver, intentFilter);

        initWebView();

        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                // 获取当前界面可视部分
                WebViewAty.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                // 获取屏幕的高度
                int screenHeight = WebViewAty.this.getWindow().getDecorView().getRootView().getHeight();
                // 此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;

                boolean mKeyboardUp = isKeyboardShown(rootView);
                if (mKeyboardUp) {
                    // 参数说明：第一个为是否弹出键盘，第二个为屏幕高度，第三个为软键盘高度
                    webView_show_webv.loadUrl("JavaScript:focuser('1','" + screenHeight + "','" + r.bottom + "')");
                    L.e("JavaScript:focuser('1','" + screenHeight + "','" + r.bottom + "')");
                } else {
                    webView_show_webv.loadUrl("JavaScript:focuser('0','" + screenHeight + "','0')");
                    L.e("JavaScript:focuser('0','" + screenHeight + "','0')");
                }
            }
        });


        if (mIsShowTitle && mTitle != null && mTitle.equals("紫薇斗数")){
            webView_show_webv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final WebView.HitTestResult hitTestResult = webView_show_webv.getHitTestResult();
                    // 如果是图片类型或者是带有图片链接的类型
                    if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                            hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                        String resultExtra = hitTestResult.getExtra();
                        BitmapUtils.gainInstance().savePic(WebViewAty.this, resultExtra, "ziweidoushu", new BitmapUtils.Listener() {
                            @Override
                            public void saveSuccess() {
                                WebViewAty.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(WebViewAty.this, "已成功保存到相册！", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    private void initWebView() {
        WebSettings webSettings = webView_show_webv.getSettings();
        webSettings.setJavaScriptEnabled(true); // JS支持
        webSettings.setAllowContentAccess(true); // 允许访问内容
        webSettings.setAppCacheEnabled(false); // 允许缓存
        webSettings.setBuiltInZoomControls(false); // 支持缩放
        webSettings.setUseWideViewPort(true); // 使用宽视图窗口
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webView_show_webv.addJavascriptInterface(new H5WebViewJsInterface(), Constant.H5NAME); // 原生的WebView主要是进行产品展示
        webView_show_webv.getSettings().setDomStorageEnabled(true); // 开启DOM缓存
        webView_show_webv.getSettings().setDatabaseEnabled(true); // 开启（LocalStorage）数据存储
        webView_show_webv.getSettings().setDatabasePath(this.getCacheDir().getAbsolutePath()); // 设置数据缓存路径
        webView_show_webv.setWebChromeClient(new WebChromeClient());


        // WebView加载web资源头Head
        Map<String, String> map = new HashMap<>();
        map.put("token", Config.getToken());
        map.put("device", "Android");
        webView_show_webv.loadUrl(url, map);

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView_show_webv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }

    @Override
    protected void requestData() {
    }


    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 接口请求返回 STA ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject data = jsonObject.getJSONObject("data");
            // ===================== 微信支付 ==================
            if (requestUrl.contains("getJsTine")) {
                GetPrepayIdTask wxPay = new GetPrepayIdTask(WebViewAty.this, data.getString("sign"), data.getString("appid"),
                        data.getString("nonce_str"), data.getString("package"), data.getString("time_stamp"), data.getString("prepay_id"),
                        data.getString("mch_id"), "");
                wxPay.execute();
            }
            // ===================== 支付宝支付 =================
            if (requestUrl.contains("getAlipayParam")) {
                AliPay aliPay = new AliPay(data.getString("pay_string"), new AliPayCallBack() {
                    @Override
                    public void onComplete() {
                        showToast("支付成功！");
                        if (type.equals("17")) {
                            url = Config.SHARE_URL + reurl;
                            initWebView();
                        } else {
                            Pay.findPayResult(order_id, type, WebViewAty.this);
                        }
                    }

                    @Override
                    public void onFailure() {
                        showToast("支付失败！");
                        if (type.equals("17")) {
                            url = Config.SHARE_URL + faileurl;
                            initWebView();
                        } else {
                            Pay.findPayResult(order_id, type, WebViewAty.this);
                        }
                    }

                    @Override
                    public void onProcessing() {
                    }
                });
                aliPay.pay();
            }
            // ===================== 返回支付结果 =================
            if (requestUrl.contains("findPayResult")) {
                paySuccess(jsonObject);
            }
            // ===================== 图片上传结果 =================
            if (requestUrl.contains("/Index/upload")) {
                if ("1".equals(jsonObject.getString("code"))) {
                    webView_show_webv.loadUrl("JavaScript:TakePhoto('" + jsonStr + "')");
                }
            }
        } catch (JSONException e) {
        }
    }
    // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 接口请求返回 END ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 微信支付以及回调
     */
    private WxPayReceiver wxPayReceiver;

    private class WxPayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 5);
            if (errCode == 0) {
                showToast("支付成功");
            } else {
                showToast("支付失败");
            }
            if (type.equals("17") && errCode == 1) {
                url = Config.SHARE_URL + reurl;
                initWebView();
            }else if (type.equals("17") && errCode == 0) {
                url = Config.SHARE_URL + faileurl;
                initWebView();
            } else {
                Pay.findPayResult(order_id, type, WebViewAty.this);
            }
        }
    }

    /**
     * 支付成功后执行方法
     */
    private void paySuccess(JSONObject jsonObject) {
        L.e("findPayResult:" + jsonObject.toString());
        try {
            JSONObject data = jsonObject.has("data") ? jsonObject.getJSONObject("data") : null;
            String order_sn = data.has("order_sn") ? data.getString("order_sn") : "";
            String jump_url = data.has("jump_url") ? data.getString("jump_url") : "";
            // 20 线下店铺保证金充值
            if (!TextUtils.isEmpty(jump_url) && "20".equals(type)) {
                url = jump_url;
            } else {
                //          http://www.wujiemall.com/Wap/Pay/pay_back/order/153232656966415.html
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(Config.SHARE_URL);
                stringBuffer.append("Wap/Pay/pay_back/order/");
                stringBuffer.append(order_sn);
                stringBuffer.append(".html");
                url = stringBuffer.toString();
            }

            initWebView();
        } catch (JSONException e) {
        }

    }

    /**
     * 初始化单选、拍照ImagePicker
     */
    private void initImageOnePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        if (width <= 0 || high <= 0) {
            imagePicker.setCrop(false);// 不裁剪
        } else {
            imagePicker.setCrop(true);// 裁剪
            imagePicker.setSaveRectangle(true);// 矩形保存
            imagePicker.setFocusWidth(Settings.displayWidth);//裁剪框宽度
            imagePicker.setFocusHeight(Settings.displayWidth * high / width);// 裁剪框高度
            imagePicker.setOutPutX(Settings.displayWidth);// 保存图片高度
            imagePicker.setOutPutY(Settings.displayWidth * high / width);// 保存图片宽度
        }
        imagePicker.setMultiMode(false);// 多选模式
        imagePicker.setShowCamera(false);// 不显示拍照按钮
    }

    /**
     * 初始化多选ImagePicker
     */
    private void initImageMorePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        imagePicker.setCrop(false);// 不裁剪
        imagePicker.setMultiMode(true);// 多选模式
        imagePicker.setShowCamera(false);// 不显示拍照按钮
    }

    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ H5交互接口 STA ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    class H5WebViewJsInterface {

        /**
         * H5调用付款（微信支付和支付宝支付）
         *
         * @param resultJson
         */
        @JavascriptInterface
        public void payForApplication(String resultJson) {
            try {
                JSONObject jsonObject = new JSONObject(resultJson);
                payType = jsonObject.has("pay_type") ? jsonObject.getString("pay_type") : "";
                type = jsonObject.has("type") ? jsonObject.getString("type") : "";
                if (type.equals("17")) {
                    divination_id = jsonObject.has("divination_id") ? jsonObject.getString("divination_id") : "";
                    divination_type = jsonObject.has("divination_type") ? jsonObject.getString("divination_type") : "";
                    reurl = jsonObject.has("reurl") ? jsonObject.getString("reurl") : "";
                    faileurl = jsonObject.has("faileurl")?jsonObject.getString("faileurl"):"";
                    if ("WeChat".equals(payType)) { // 微信支付
                        Pay.getWeChat(divination_id, divination_type, type, WebViewAty.this);
                    } else if ("Alipay".equals(payType)) { // 支付宝支付
                        Pay.getAlipay(divination_id, divination_type, type, WebViewAty.this);
                    }
                } else {
                    order_id = jsonObject.has("order_id") ? jsonObject.getString("order_id") : "";
                    discount_type = jsonObject.has("discount_type") ? jsonObject.getString("discount_type") : "";

                    if ("WeChat".equals(payType)) { // 微信支付
                        Pay.getJsTine(order_id, discount_type, type, WebViewAty.this);
                    } else if ("Alipay".equals(payType)) { // 支付宝支付
                        Pay.getAlipayParam(order_id, discount_type, type, WebViewAty.this);
                    } else {
                        L.e("payForApplication show: other pay type");
                    }
                }
            } catch (JSONException e) {
                L.e("payForApplication Exception:" + e.toString());
            }
        }

        /**
         * H5调用登录
         *
         * @param url
         */
        @JavascriptInterface
        public void toLogin(String url) { // window.phone.toLogin(String url)
            if (!url.isEmpty()) { // 如果Url不为空，那么直接解码并添加到本地共享存储
                byte[] decode = Base64Utils.decode(url);
                PreferencesUtils.putString(WebViewAty.this, "NoticeDetailsUrl", new String(decode));
            }
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            startActivity(LoginAty.class, bundle);
            WebViewAty.this.finish();
        }

        /**
         * H5支付结果返回首页
         */
        @JavascriptInterface
        public void toOfflinePage() {
            WebViewAty.this.finish();
        }

        /**
         * H5调用分享
         *
         * @param content
         */
        @JavascriptInterface
        public void toShare(String content) {
            L.e("NoticeDetailsJsInterface.toShare" + content);
            try {
                JSONObject jsonObject = new JSONObject(content);
                String goodsName = jsonObject.has("goodsName") ? jsonObject.getString("goodsName") : "";
                String share_img = jsonObject.has("share_img") ? jsonObject.getString("share_img") : "";
                String share_url = jsonObject.has("share_url") ? jsonObject.getString("share_url") : "";
                String share_content = jsonObject.has("share_content") ? jsonObject.getString("share_content") : "";
                String id = jsonObject.has("id") ? jsonObject.getString("id") : "0";
                WebViewAty.this.toShare(goodsName, share_img, share_url, share_content, id, "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * 跳转至个人中心
         */
        @JavascriptInterface
        public void toPersonalCenter() {
            backMain(3);
            WebViewAty.this.finish();
        }

        /**
         * 打开相机
         * <p>
         * * @param cut 裁剪
         */
        @JavascriptInterface
        public void openCamera(int width, int high) {
            //            WebViewAty.width = width;
            //            WebViewAty.high = high;
            L.e("qwertyuiopzxcvbnm", "width:" + width + " high:" + high);
            initImageOnePicker();
            Intent intent = new Intent(WebViewAty.this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 直接调取相机
            startActivityForResult(intent, 111);
        }

        /**
         * 打开相册选择图片
         *
         * @param selectMode 选择类型 0单选 1多选
         */
        @JavascriptInterface
        public void openPhotoFolder(int selectMode, int width, int high) {
            WebViewAty.width = width;
            WebViewAty.high = high;
            L.e("qwertyuiopzxcvbnm", "width:" + width + " high:" + high);
            switch (selectMode) {
                case 0: // 单选
                    initImageOnePicker();
                    Intent i = new Intent(WebViewAty.this, ImageGridActivity.class);
                    startActivityForResult(i, 111);
                    break;
                case 1: // 多选
                    initImageMorePicker();
                    Intent intent = new Intent(WebViewAty.this, ImageGridActivity.class);
                    startActivityForResult(intent, 222);
                    break;
            }
        }

        /**
         * 去到聊天界面
         *
         * @param parameter
         */
        @JavascriptInterface
        public void toChat(String parameter) {
            try {
                JSONObject jsonObject = new JSONObject(parameter);
                String easemob_account = jsonObject.has("userId") ? jsonObject.getString("userId") : "";
                String head_pic = jsonObject.has("userHead") ? jsonObject.getString("userHead") : "";
                String nickname = jsonObject.has("userName") ? jsonObject.getString("userName") : "";
                //                String myName = jsonObject.has("myName") ? jsonObject.getString("myName") : "";
                //                String myHead = jsonObject.has("myHead") ? jsonObject.getString("myHead") : "";
                toChatFriend(easemob_account, head_pic, nickname);
            } catch (JSONException e) {
                L.e("回传Json字符串格式异常");
                showToast("回传数据异常");
            }
        }

        /**
         * 下载保存图片
         *
         * @param imagePath 图片路径
         */
        @JavascriptInterface
        public void downImageToPhone(final String imagePath) {
            Bitmap recordBitmap = null;
            try {
                recordBitmap = Glide.with(WebViewAty.this)
                        .load(imagePath)
                        .asBitmap().into(500, 500).get();
            } catch (InterruptedException e) {
                L.e("WebView.H5WebViewJsInterface.downImageToPhone is error:" + e.toString());
            } catch (ExecutionException e) {
                L.e("WebView.H5WebViewJsInterface.downImageToPhone is error:" + e.toString());
            } finally {
                if (null != recordBitmap) {
                        BitmapUtils.gainInstance().saveBmp2Gallery(WebViewAty.this, recordBitmap, "zhucema", new BitmapUtils.Listener() {
                            @Override
                            public void saveSuccess() {
                                showToast("已成功保存到相册！");
                            }
                        });

                } else {
                    showToast("图片保存异常，请重试");
                }
            }

        }

        /**
         * 调起地图导航并规划路线
         *
         * @param navigationCoordinates
         */
        @JavascriptInterface
        public void getNavigationLine(String navigationCoordinates) {
            try {
                JSONObject jsonObject = new JSONObject(navigationCoordinates);
                //                JSONObject data = jsonObject.getJSONObject("data");
                double baiDuLat = Double.parseDouble(jsonObject.has("baiDuLat") ? jsonObject.getString("baiDuLat") : "0");
                double baiDuLng = Double.parseDouble(jsonObject.has("baiDuLng") ? jsonObject.getString("baiDuLng") : "0");
                double gaoDeLat = Double.parseDouble(jsonObject.has("gaoDeLat") ? jsonObject.getString("gaoDeLat") : "0");
                double gaoDeLng = Double.parseDouble(jsonObject.has("gaoDeLng") ? jsonObject.getString("gaoDeLng") : "0");
                MapIntentUtil mapIntentUtil = new MapIntentUtil();
                mapIntentUtil.openMap(WebViewAty.this, baiDuLat, baiDuLng, gaoDeLat, gaoDeLng);
            } catch (JSONException e) {
                showToast("此处数据回传格式异常");
            }
        }

        /**
         * 连接蓝牙
         */
        @JavascriptInterface
        public void connectBluetooth() {
            // http://doc.wotianhui.com/web/#/10?page_id=271
            startActivity(SearchBluetoothAty.class, null);
        }

        /**
         * 拨打电话
         */
        @JavascriptInterface
        public void callPhone(String phone) {
            call(phone);
        }

    }
    // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ H5交互接口 END ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 跳转到聊天界面
     *
     * @param easemob_account 对方环信账号
     * @param head_pic        对方头像
     * @param nickname        对方昵称
     */
    public void toChatFriend(String easemob_account, String head_pic, String nickname) {
        toChat(easemob_account, head_pic, nickname);
    }


    /**
     * 判断软键盘是否弹出
     *
     * @param rootView
     * @return
     */
    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != wxPayReceiver) {
            unregisterReceiver(wxPayReceiver);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    switch (requestCode) {
                        case 111: // 图片单选
                            String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                            try {
                                File file = new File(pic_path);
                                indexPst = (indexPst == null) ? new IndexPst(WebViewAty.this) : indexPst;
                                indexPst.uploadImg("", file);
                            } catch (Exception e) {
                                L.e("File Exception:" + e.toString());
                                showToast("未找到图片文件，请重新选择。");
                            }
                            break;
                        case 222: // 图片多选
                            File[] imageFiles = new File[images.size()];
                            try {
                                for (int i = 0; i < images.size(); i++) {
                                    imageFiles[i] = new File(images.get(i).path);
                                }
                                indexPst = (indexPst == null) ? new IndexPst(WebViewAty.this) : indexPst;
                                indexPst.uploadImg("", imageFiles);
                            } catch (Exception e) {
                                L.e("File Exception:" + e.toString());
                                showToast("未找到图片文件，多选无效。");
                            }
                            break;
                    }
                }
            } else {
                showErrorTip("没有返回任何数据。。");
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView_show_webv.canGoBack()) {
            webView_show_webv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

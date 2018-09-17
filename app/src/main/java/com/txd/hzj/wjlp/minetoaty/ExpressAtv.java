package com.txd.hzj.wjlp.minetoAty;

import android.Manifest;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * 开发者： WangJJ
 * 创建时间： 2018/3/28 028 0:48:56.
 * 功能描述： 物流查询
 * 联系方式： jingjie.office@qq.com
 */
public class ExpressAtv extends BaseAty {

    /**
     * 网页WebView控件
     */
    @ViewInject(R.id.express_webView)
    private WebView express_webView;
    /**
     * 加载进度条，环形方式
     */
    @ViewInject(R.id.express_loading_pb)
    private ProgressBar express_loading_pb;
    /**
     * 页面标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.atv_express;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("物流详情");
        // 订单商品的id
        String order_goods_id = getIntent().getStringExtra("order_goods_id");
        String type = getIntent().getStringExtra("type");//0 普通商品 1拼单购
        inquireExpress(order_goods_id, type);
    }
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    // ====================android M+动态授权====================
    private void requestSomePermission() {
        /**
         * 暂时不需要的权限，维护用户体验
         * !AndPermission.hasPermission(MainAty.this, Manifest.permission.CALL_PHONE) ||电话
         * !AndPermission.hasPermission(MainAty.this, Manifest.permission.RECORD_AUDIO)录音
         *
         * Manifest.permission.RECORD_AUDIO, // 启用录音权限
         * Manifest.permission.CALL_PHONE,
         * */
        // 先判断是否有权限。
        if (!AndPermission.hasPermission(ExpressAtv.this, Manifest.permission.CALL_PHONE)) {
            // 申请权限。
            AndPermission.with(ExpressAtv.this)
                    .requestCode(100)
                    .permission(Manifest.permission.CALL_PHONE)
                    .send();
        }else{
            call(mobile);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 只需要调用这一句，其它的交给AndPermission吧，最后一个参数是PermissionListener。
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, listener);
    }
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            call(mobile);
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(ExpressAtv.this, deniedPermissions)) {
                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(ExpressAtv.this, 300)
                        .setTitle("权限申请失败")
                        .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }
    };

    private String mobile;
    /**
     * 加载WebView查询订单物流
     *
     * @param order_goods_id 订单中商品的id
     */
    private void inquireExpress(String order_goods_id,String type) {

        // 字符串拼接
        StringBuffer expressUrl =new StringBuffer();
        expressUrl.append(Config.BASE_URL + "Order/logistics/order_goods_id/" + order_goods_id);
        if(!TextUtils.isEmpty(type)){
            expressUrl.append("/type/"+type);
        }
        LogUtils.i("在 ExpressAtv 中，物流的请求地址是 :"+String.valueOf(expressUrl));
        express_webView.getSettings().setJavaScriptEnabled(true); // 允许使用JS
        express_webView.setWebChromeClient(new WebChromeClient());
        express_webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading
                    (WebView view, String url) {
                Log.i("用户单击超连接", url);
                //判断用户单击的是那个超连接
                String tag="tel:";
                if (url.contains(tag))
                {

                    mobile = url.substring(tag.length(),url.length());
                    requestSomePermission();
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 网页开始加载
                express_loading_pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 网页加载结束
                express_loading_pb.setVisibility(View.GONE);
            }
        });
        express_webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 不使用缓存，只从网络获取数据
        express_webView.getSettings().setSupportZoom(true); // 支持屏幕缩放
        express_webView.getSettings().setSupportMultipleWindows(true);
        express_webView.loadUrl(String.valueOf(expressUrl));
    }

    @Override
    protected void requestData() {
    }

}

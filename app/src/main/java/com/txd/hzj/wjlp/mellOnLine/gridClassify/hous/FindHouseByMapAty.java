package com.txd.hzj.wjlp.mellOnLine.gridClassify.hous;

import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 *
 * 作者：DUKE_HwangZj
 * 日期：2017/7/13 0013
 * 时间：下午 4:02
 * 描述：位置(10-6位置)
 *
 */
public class FindHouseByMapAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.baidu_map_for_house)
    private MapView baidu_map_for_house;

    private BaiduMap baiduMap;

    private String lat = "";
    private String lng = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
//        titlt_conter_tv.setText("");
        // 获取地图对象
        baiduMap = baidu_map_for_house.getMap();
        // 普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        LatLng point = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        CoordinateConverter converter = new CoordinateConverter();
        converter.coord(point);
        LatLng convertLatLng = converter.convert();
        OverlayOptions options = new MarkerOptions().position(convertLatLng).icon(bitmapDescriptor).zIndex(4).draggable(true);
        baiduMap.addOverlay(options);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(convertLatLng, 14.6f);
        baiduMap.animateMapStatus(u);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_find_house_by_map;
    }

    @Override
    protected void initialized() {
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        String title = getIntent().getStringExtra("title");
        titlt_conter_tv.setText(TextUtils.isEmpty(title) ? "位置" : title);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        baidu_map_for_house.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        baidu_map_for_house.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        baidu_map_for_house.onDestroy();
    }
}

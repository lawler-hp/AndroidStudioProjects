package com.example.lbstest;

import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;

public class MyLocationListener extends BDAbstractLocationListener {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private TextView mPositionText = null;

    public MyLocationListener(MapView mMapView, BaiduMap mBaiduMap, TextView mPositionText) {
        this.mMapView = mMapView;
        this.mBaiduMap = mBaiduMap;
        this.mPositionText = mPositionText;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        StringBuffer currentPositon = new StringBuffer();
        //mapView 销毁后不在处理新接收的位置
        /*if (location == null || mMapView == null){
            return;
        }*/
        currentPositon.append("纬度：").append(location.getLatitude()).append("\n");
        currentPositon.append("纬度：").append(location.getLongitude()).append("\n");
        currentPositon.append("定位方式");
        if (location.getLocType() == BDLocation.TypeGpsLocation){
            currentPositon.append("GPS");
        }else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
            currentPositon.append("网络");
        }
        mPositionText.setText(currentPositon);

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
    }
}
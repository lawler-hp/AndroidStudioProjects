package com.example.lbstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;

import java.util.List;

/**
 * 百度地图SDK使用
 *
 * 创建
 * 获取地图控件引用
 * mapView = (MapView) findViewById(R.id.bmapView);
 *
 * 直接在Java代码中添加MapView的方式来展示地图
 *  MapView mapView = new MapView(this);
 *  setContentView(mapView);
 *
 * BaiduMapOptions类支持设置的状态如下：
 * 状态	                含义
 * mapStatus	        地图状态
 * compassEnable	    是否开启指南针，默认开启
 * mapType	            地图模式，默认为普通地图
 * rotateGesturesEnabled	是否允许地图旋转手势，默认允许
 * scrollGesturesEnabled	是否允许拖拽手势，默认允许
 * overlookingGesturesEnabled	是否允许俯视图手势，默认允许
 * zoomControlsEnabled	是否显示缩放按钮控件，默认允许
 * zoomControlsPosition	设置缩放控件位置
 * zoomGesturesEnabled	是否允许缩放手势，默认允许
 * scaleControlEnabled	是否显示比例尺控件，默认显示
 * scaleControlPosition	设置比例尺控件位置
 * logoPosition	        设置Logo位置
 *
 *
 */

public class MainActivity extends Activity{

    private MapView mapView = null;
    private LocationClient locationClient = null;
    private BaiduMap mBaiduMap = null;
    private TextView positionText = null;

    private  static String[]PERMISSIONS_STORAGE={  //需要的權限數組
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.INTERNET,
            Manifest.permission.INSTALL_PACKAGES,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_SETTINGS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //权限申请
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,1);

        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.bmapView);
        positionText = (TextView) findViewById(R.id.position_text_view);

        //直接在Java代码中添加MapView的方式来展示地图
        /*mapView = new MapView(this);
        setContentView(mapView);*/

        //在Java代码中添加MapView的方式支持通过BaiduMapOptions对象根据需求构造包含特定地图状态类型和控件显示状态的MapView对象
        BaiduMapOptions options = new BaiduMapOptions();
        //设置地图模式为卫星地图
        /*MAP_TYPE_NORMAL	普通地图（包含3D地图）
        MAP_TYPE_SATELLITE	卫星图
        MAP_TYPE_NONE	空白地图*/
        options.mapType(BaiduMap.MAP_TYPE_NORMAL);
        mapView = new MapView(this, options);
        setContentView(mapView);

        //设置地图缩放比例
        mBaiduMap = mapView.getMap();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        //开启交通图
        mBaiduMap.setTrafficEnabled(true);
        //路况颜色设置
        mBaiduMap.setCustomTrafficColor("#ffba0101", "#fff33131", "#ffff9e19", "#00000000");
        //  对地图状态做更新，否则可能不会触发渲染，造成样式定义无法立即生效。
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(13);
        mBaiduMap.animateMapStatus(u);

        //开启热力图（只有在地图层级介于11-20级时，可显示城市热力图。）
        //mBaiduMap.setBaiduHeatMapEnabled(true);
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //通过LocationClient发起定位
        //定位初始化
        locationClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

        //设置locationClientOption
        locationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener(mapView, mBaiduMap,positionText);
        locationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        locationClient.start();

        MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;//定位跟随态
        //自定义精度圈填充颜色
        int accuracyCircleFillColor = 0xFF03DAC5;
        //自定义精度圈边框颜色
        int accuracyCircleStrokeColor = 0xAA00FF00;
        MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(mCurrentMode,true,null,accuracyCircleFillColor,accuracyCircleStrokeColor);
        mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);
        mBaiduMap.setIndoorEnable(true);//打开室内图，默认为关闭状态

    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }
}

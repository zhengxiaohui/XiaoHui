package com.zdemo.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.oranllc.baidumap.BaseBaiduLocation;
import com.zbase.util.PopUtil;
import com.zdemo.R;

import java.util.List;


/**
 * 创建人：郑晓辉
 * 创建日期：2016/5/18
 * 描述：
 */
public class BaiduMapActivity extends BaseActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private TextView tv_location;
    private boolean isFirstLoc = true; // 是否首次定位

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_baidu_map;
    }

    @Override
    protected void initView(View view) {
        mMapView = (MapView) findViewById(R.id.bmapView);
        tv_location = (TextView) findViewById(R.id.tv_location);
    }

    @Override
    protected void setListener() {
        tv_location.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);  //普通地图
//        mBaiduMap.setTrafficEnabled(true);//开启交通图
//        mBaiduMap.setBaiduHeatMapEnabled(true);//开启热力图
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
        location.init(getMyApplication());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_location:
                location.startLocation();
                break;
        }
    }

    /**
     * time : 2016-05-19 18:04:47
     * error code : 161
     * latitude : 26.059322
     * lontitude : 119.343587
     * radius : 100.0
     * addr : 中国福建省福州市台江区曙光支路
     * operationers : 0
     * describe : 网络定位成功
     * locationdescribe : 在世纪百联附近
     * poilist size = : 5
     * poi= : 7798028016905280537 世纪百联 0.99
     * poi= : 8006595558302021209 福建交通集团物流信息大厦 0.99
     * poi= : 18013703059787481087 宏洋大厦 0.99
     * poi= : 2016195776067963611 升龙汇金中心 0.99
     * poi= : 15734167142037524777 恒丰大厦 0.99
     */

    private BaseBaiduLocation location = new BaseBaiduLocation() {
        @Override
        protected void onLocationReceive(BDLocation location) {

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getTriangRadius())// 设置精确半径,有显示一个蓝色的圆圈
//                    .direction(100)// 设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                        .fromResource(R.mipmap.location_point);
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);//mCurrentMarker可以传null就是默认图标
                mBaiduMap.setMyLocationConfigeration(config);
                LatLng latLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(18.0f);//缩放等级18
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            PopUtil.toast(context, location.getAddrStr());
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        location.stopLocation(); // 退出时销毁定位
        mBaiduMap.setMyLocationEnabled(false); // 关闭定位图层
        mMapView.onDestroy();//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView = null;
    }
}


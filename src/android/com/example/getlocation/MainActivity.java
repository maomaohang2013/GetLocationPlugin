package com.example.getlocation;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements AMapLocationListener {
	private LocationManagerProxy mLocationManagerProxy;
	public static final String action = "com.broadcast.action"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	/**
	 * 初始化定位
	 */
	private void init() {

		mLocationManagerProxy = LocationManagerProxy.getInstance(this);

		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);

		mLocationManagerProxy.setGpsEnable(false);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null
				&& amapLocation.getAMapException().getErrorCode() == 0) {
			// 获取位置信息
			Double geoLat = amapLocation.getLatitude();
			Double geoLng = amapLocation.getLongitude();
			String desc = "";
			Bundle locBundle = amapLocation.getExtras();
			if (locBundle != null) {
			    desc = locBundle.getString("desc");
			}
			Log.e("provider", amapLocation.getProvider());
			Log.e("lat", Double.toString(geoLat));
			Log.e("lng", Double.toString(geoLng));
			Log.e("address", desc);
			
			Intent intent = new Intent(action);  
            intent.putExtra("longitude", Double.toString(geoLng));  
            intent.putExtra("latitude", Double.toString(geoLat)); 
            intent.putExtra("address", desc);

            sendBroadcast(intent);  
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destory();
			onDestroy();
			MainActivity.this.finish();
		}

	}
	
	
	

}

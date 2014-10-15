
package com.amap.map3d.demo.geocoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.map3d.demo.util.ToastUtil;
import com.example.getlocation.R;


public class GeocoderActivity extends Activity implements
		OnGeocodeSearchListener, OnClickListener {

	private ProgressDialog progDialog = null;
	private GeocodeSearch geocoderSearch;
	private String addressName;
	
	public static final String action = "com.broadcast.action"; 
	private LatLonPoint latLonPoint;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();
		
		Intent intent = getIntent();
		String longitude = intent.getStringExtra("longitude");
		String latitude = intent.getStringExtra("latitude");
		
		Log.e("long", longitude);
		Log.e("lat", latitude);
		getAddress(latLonPoint = new LatLonPoint(Double.parseDouble(latitude),Double.parseDouble(longitude)));
	}

	private void init() {


		geocoderSearch = new GeocodeSearch(this);
		geocoderSearch.setOnGeocodeSearchListener(this);
		progDialog = new ProgressDialog(this);
	}

	public void showDialog() {
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(true);
		progDialog.setMessage("正在获取地址");
		progDialog.show();
	}

	public void dismissDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	
	/**
	 * 响应逆地理编码
	 */
	public void getAddress(final LatLonPoint latLonPoint) {
		showDialog();
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
				GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
		geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
	}

	/**
	 * 地理编码查询回调
	 */
	@Override
	public void onGeocodeSearched(GeocodeResult result, int rCode) {
		
	}

	/**
	 * 逆地理编码回调
	 */
	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		dismissDialog();
		if (rCode == 0) {
			if (result != null && result.getRegeocodeAddress() != null
					&& result.getRegeocodeAddress().getFormatAddress() != null) {
				addressName = result.getRegeocodeAddress().getFormatAddress()
						+ "附近";
				
				Intent intent = new Intent(action);  
	            intent.putExtra("address", addressName);  

	            sendBroadcast(intent); 
				
	            finish();
				//ToastUtil.show(GeocoderActivity.this, addressName);
			} else {
				ToastUtil.show(GeocoderActivity.this, R.string.no_result);
			}
		} else if (rCode == 27) {
			ToastUtil.show(GeocoderActivity.this, R.string.error_network);
		} else if (rCode == 32) {
			ToastUtil.show(GeocoderActivity.this, R.string.error_key);
		} else {
			ToastUtil.show(GeocoderActivity.this,
					getString(R.string.error_other) + rCode);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.regeoButton:
			getAddress(latLonPoint);
			break;
		default:
			break;
		}
	}
}
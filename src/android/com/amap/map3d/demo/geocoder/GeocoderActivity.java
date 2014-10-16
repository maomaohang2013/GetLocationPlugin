
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
import com.example.testhello.R;


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
		progDialog.setMessage("正在獲取地址...");
		progDialog.show();
	}

	public void dismissDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	public void getAddress(final LatLonPoint latLonPoint) {
		showDialog();
		RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
				GeocodeSearch.AMAP);
		geocoderSearch.getFromLocationAsyn(query);
	}

	@Override
	public void onGeocodeSearched(GeocodeResult result, int rCode) {
		
	}

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
				ToastUtil.show(GeocoderActivity.this, "对不起，没有搜索到相关数据！");
			}
		} else if (rCode == 27) {
			ToastUtil.show(GeocoderActivity.this, "搜索失败,请检查网络连接！");
		} else if (rCode == 32) {
			ToastUtil.show(GeocoderActivity.this, "key验证无效！");
		} else {
			ToastUtil.show(GeocoderActivity.this,
					"未知错误，请稍后重试!错误码为" + rCode);
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
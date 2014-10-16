package com.example.getlocation;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.amap.map3d.demo.geocoder.GeocoderActivity;
import com.amap.map3d.demo.location.LocationSourceActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;



public class GetLocationPlugin extends CordovaPlugin {
	
	private ProgressDialog mProgressDialog; 
	

	public GetLocationPlugin() {
	}

	CallbackContext callbackContext;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		this.callbackContext = callbackContext;

		if (action.equals("intent")) {
			String message = args.getString(0);
			this.function();
			return true;
		}
		return false;

	}

	private void function() {		
		//MainActivity activity = new MainActivity(cordova.getActivity(),mHandle);
		createProgressDialog();
		Intent intent = new Intent();
		intent.setClass(cordova.getActivity(), LocationSourceActivity.class);
		cordova.getActivity().startActivity(intent);
		
		IntentFilter filter = new IntentFilter(LocationSourceActivity.action);  
		cordova.getActivity().registerReceiver(broadcastReceiver, filter); 
		
	}
	
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
		  
        @Override  
        public void onReceive(Context context, Intent intent) {  

            String longitude = intent.getExtras().getString("longitude");
    		String latitude = intent.getExtras().getString("latitude");

			mProgressDialog.dismiss();
            cordova.getActivity().unregisterReceiver(broadcastReceiver);  
            
            Intent intent1 = new Intent();
    		intent1.setClass(cordova.getActivity(), GeocoderActivity.class);
    		intent1.putExtra("longitude", longitude);  
            intent1.putExtra("latitude", latitude); 
    		cordova.getActivity().startActivity(intent1);
    		
    		IntentFilter filter = new IntentFilter(GeocoderActivity.action);  
    		cordova.getActivity().registerReceiver(broadcastReceiver1, filter); 
        }  
    };  
    
    BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {  
		  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            // TODO Auto-generated method stub  

            
            String address = intent.getExtras().getString("address");
    		
            
            callbackContext.success("¦a§}¡G  "+address);			
            cordova.getActivity().unregisterReceiver(broadcastReceiver1);  

        }  
    };

    
    private void createProgressDialog(){
        mProgressDialog=new ProgressDialog(cordova.getActivity());
        mProgressDialog.setMessage("½Ðµyµ¥...");
        mProgressDialog.show();
    }
    

}



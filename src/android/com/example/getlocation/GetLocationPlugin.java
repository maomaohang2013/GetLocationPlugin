package com.example.getlocation;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.example.getlocation.MainActivity;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class GetLocationPlugin extends CordovaPlugin {
	
	private ProgressDialog mProgressDialog; 
	public String message;
	

	public GetLocationPlugin() {
	}

	CallbackContext callbackContext;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		this.callbackContext = callbackContext;

		if (action.equals("getlocation")) {
			message = args.getString(0);
			this.function();
			return true;
		}
		return false;

	}

	private void function() {		
		//MainActivity activity = new MainActivity(cordova.getActivity(),mHandle);
		createProgressDialog();
		Intent intent = new Intent();
		intent.setClass(cordova.getActivity(), MainActivity.class);
		cordova.getActivity().startActivity(intent);
		
		IntentFilter filter = new IntentFilter(MainActivity.action);  
		cordova.getActivity().registerReceiver(broadcastReceiver, filter); 
		
	}
	
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
		  
        @Override  
        public void onReceive(Context context, Intent intent) {  

            
            String longitude = intent.getExtras().getString("longitude");
    		String latitude = intent.getExtras().getString("latitude");
    		String address = intent.getExtras().getString("address");
    		if (message.equals("longitude")) {
    			callbackContext.success(longitude);
			}else if (message.equals("latitude")) {
				callbackContext.success(latitude);
			}else{
				callbackContext.success(address);			
			}
            
			mProgressDialog.dismiss();
            cordova.getActivity().unregisterReceiver(broadcastReceiver);  
            
            
        }  
    };  
    
    
    private void createProgressDialog(){
        mProgressDialog=new ProgressDialog(cordova.getActivity());
        mProgressDialog.setMessage("請稍等..");
        mProgressDialog.show();
    }
    

}



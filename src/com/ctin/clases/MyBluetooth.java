package com.ctin.clases;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

@TargetApi(10)
public class MyBluetooth {

	private BluetoothAdapter ba;
	private BroadcastReceiver mReceiver;
	private ArrayList<BluetoothDevice> devices;
	private final UUID SERIAL_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private BluetoothDevice bd;
	private BluetoothSocket bs;

	public BluetoothAdapter getBa() {
		return ba;
	}
	
	public void setBa(BluetoothAdapter ba) {
		this.ba = ba;
	}
	
	public BroadcastReceiver getmReceiver() {
		return mReceiver;
	}

	public void setmReceiver(BroadcastReceiver mReceiver) {
		this.mReceiver = mReceiver;
	}
	
	public MyBluetooth() {
		ba = BluetoothAdapter.getDefaultAdapter();
		if (ba == null) {
		}
		devices = new ArrayList<BluetoothDevice>();
		mReceiver = new BroadcastReceiver() {
			@Override
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
		            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		            devices.add(device);
		        }
		    }
		};
	}
	
	public Boolean isActivated(){
		return ba.isEnabled();
	}
	
	public void startDiscovery(){
		ba.startDiscovery();
	}
	
	public OutputStream getOutputStreamFromBluetooth(String mac){
		bd = ba.getRemoteDevice(mac);
		Log.d("remoteD", bd.toString());
		OutputStream os = null;
		try{
//			bs = bd.createInsecureRfcommSocketToServiceRecord(SERIAL_UUID);
			Method m = bd.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
	         bs = (BluetoothSocket) m.invoke(bd, 1);
			bs.connect();
			os = bs.getOutputStream();
		}catch(Exception ex){
			Log.d("bluetooth", ex.getMessage());
		}
		return os;
	}
	
	public InputStream getInputStreamFromBluetooth(){
		InputStream is = null;
		try {
			is = bs.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}
}

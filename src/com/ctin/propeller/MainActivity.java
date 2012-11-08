package com.ctin.propeller;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ctin.clases.*;

public class MainActivity extends Activity {

	private static final int REQUEST_ENABLE_BT = 1;
	public static MyBluetooth mb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mb = new MyBluetooth();
		if (!mb.isActivated()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		mb.startDiscovery();
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		final List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
		final ArrayAdapterBluetooth aab = new ArrayAdapterBluetooth(
				MainActivity.this, R.layout.row, devices);
		BroadcastReceiver mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					devices.add(device);
					aab.notifyDataSetChanged();
				}
			}
		};
		registerReceiver(mReceiver, filter);
		final ListView lista = (ListView) findViewById(R.id.list);
		lista.setAdapter(aab);
		lista.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String mac = ((TextView)view.findViewById(R.id.row_address_device)).getText().toString();
				String name = ((TextView)view.findViewById(R.id.row_name_device)).getText().toString();
				Intent enviarDatosIntent = new Intent(MainActivity.this,
						EnviarDatos.class);
				enviarDatosIntent.putExtra("mac", mac);
				enviarDatosIntent.putExtra("name", name);
				startActivity(enviarDatosIntent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		mb.getBa().cancelDiscovery();
		unregisterReceiver(mb.getmReceiver());
		super.onDestroy();
	}

}

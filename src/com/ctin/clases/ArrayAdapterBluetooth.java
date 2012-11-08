package com.ctin.clases;

import java.util.List;

import com.ctin.propeller.R;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterBluetooth extends ArrayAdapter<BluetoothDevice> {

	Context context;
	int layoutResourceId;
	List<BluetoothDevice> devices;

	public ArrayAdapterBluetooth(Context context, int textViewResourceId,
			List<BluetoothDevice> devices) {
		super(context, textViewResourceId, devices);
		this.context = context;
		this.layoutResourceId = textViewResourceId;
		this.devices = devices;

	}

	static class BluetoothHolder {
		public TextView name;
		public TextView address;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			rowView = inflater.inflate(R.layout.row , null);
			BluetoothHolder bluetoothHolder = new BluetoothHolder();
			bluetoothHolder.name = (TextView) rowView.findViewById(R.id.row_name_device);
			bluetoothHolder.address = (TextView) rowView.findViewById(R.id.row_address_device);
			rowView.setTag(bluetoothHolder);
		}

		BluetoothHolder holder = (BluetoothHolder) rowView.getTag();
		BluetoothDevice bd = devices.get(position);
		String name = "";
		String address = "";
		if(bd.getName()!=null)
			name=bd.getName();
		if(bd.getAddress()!=null)
			address=bd.getAddress();
		holder.name.setText(name);
		holder.address.setText(address);
		return rowView;
	}

}

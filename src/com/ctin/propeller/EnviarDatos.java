package com.ctin.propeller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class EnviarDatos extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enviar_datos);
		Intent intent = getIntent();
		String mac = intent.getStringExtra("mac");
//		String name = intent.getStringExtra("name");
		((TextView)findViewById(R.id.mac)).setText(mac);
		OutputStream os = MainActivity.mb.getOutputStreamFromBluetooth(mac);
		InputStream is = MainActivity.mb.getInputStreamFromBluetooth();
		try {
			for (int i = 0; i < 100; i++) {
				os.write(65);
				Thread.sleep(500);
				os.write(66);
				Thread.sleep(500);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException ie){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_enviar_datos, menu);
        return true;
    }
}

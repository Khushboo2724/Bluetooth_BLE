package com.example.myapplicationnew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class BLE_devices extends AppCompatActivity {
    Button Scan1;
    BluetoothAdapter bluetoothAdapter;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    //BluetoothLeScanner bluetoothLeScanner;
    //BluetoothDevice device;
    ListView listview2;
    ArrayList<String> stringArrayList = new ArrayList<String>();




    ArrayAdapter<String> arrayAdapter;






    boolean scanning;
    Handler handler = new Handler();

    private ScanCallback leScanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    stringArrayList.add((result.getDevice()).toString());

                        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,stringArrayList);
                        listview2.setAdapter(arrayAdapter);

                   // arrayAdapter.notifyDataSetChanged();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_devices);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        ActivityCompat.requestPermissions(this,
                new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        //Manifest.permission.ACCESS_BACKGROUND_LOCATION


                },0);


        Scan1 = (Button) findViewById(R.id.btnScanBLE);
        //Pair1=(Button) findViewById(R.id.btnPairBLE);
        listview2=(ListView) findViewById(R.id.LV2);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        Scan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanLeDevice();
            }

            private void scanLeDevice() {
                if (!scanning) {
            // Stops scanning after a predefined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanning = false;
                    if (ActivityCompat.checkSelfPermission(BLE_devices.this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {

                    }
                    bluetoothLeScanner.stopScan(leScanCallback);
                }
            }, SCAN_PERIOD);

            scanning = true;
            if (ActivityCompat.checkSelfPermission(BLE_devices.this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            }
            bluetoothLeScanner.startScan(leScanCallback);

        } else {
            scanning = false;
            bluetoothLeScanner.stopScan(leScanCallback);
        }

            }
        });

//        Pair1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

//    private void scanLeDevice() {
//        if (!scanning) {
//            // Stops scanning after a predefined scan period.
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    scanning = false;
//                    if (ActivityCompat.checkSelfPermission(BLE_devices.this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//
//                    }
//                    bluetoothLeScanner.stopScan(leScanCallback);
//                }
//            }, SCAN_PERIOD);
//
//            scanning = true;
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//            }
//            bluetoothLeScanner.startScan(leScanCallback);
//        } else {
//            scanning = false;
//            bluetoothLeScanner.stopScan(leScanCallback);
//        }
//    }

}

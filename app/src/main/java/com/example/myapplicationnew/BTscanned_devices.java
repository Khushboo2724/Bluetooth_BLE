package com.example.myapplicationnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BTscanned_devices extends AppCompatActivity {
    private static final String TAG = "BT Scanned devices";
    ListView listView1;
    BluetoothAdapter btadapter1;
    BroadcastReceiver broadcasterScaled;
    ArrayAdapter<String> arrayAdapter;
    LocationManager locationManager;
    String provider;


//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == 100) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == 200) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcasterScaled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btscanned_devices);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        listView1 = (ListView) findViewById(R.id.LV1);
        btadapter1 = BluetoothAdapter.getDefaultAdapter();

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        provider = locationManager.getBestProvider(new Criteria(), false);

//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,
//                    new String[]{
////                            Manifest.permission.ACCESS_FINE_LOCATION,
////                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
//
//
//                    }, 0);
//
//        }
//
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,
//                    new String[]{
////                            Manifest.permission.ACCESS_FINE_LOCATION,
//                          Manifest.permission.ACCESS_COARSE_LOCATION,
//                            //Manifest.permission.ACCESS_BACKGROUND_LOCATION
//
//
//                    }, 100);
//
//        }
//
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,
//                    new String[]{
//                          Manifest.permission.ACCESS_FINE_LOCATION,
////                            Manifest.permission.ACCESS_COARSE_LOCATION,
//          //                  Manifest.permission.ACCESS_BACKGROUND_LOCATION
//
//
//                    }, 200);
//
//        }
       // Location location = locationManager.getLastKnownLocation(provider);








    }

    @Override
    protected void onResume() {
        super.onResume();

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{
////                            Manifest.permission.ACCESS_FINE_LOCATION,
////                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
//
//
//                    }, 0);
//
//        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
//                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            //Manifest.permission.ACCESS_BACKGROUND_LOCATION


                    }, 100);

        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            //                  Manifest.permission.ACCESS_BACKGROUND_LOCATION


                    }, 200);

        }
        btadapter1.startDiscovery();
        ArrayList<String> stringArrayList = new ArrayList<>();

        broadcasterScaled = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    Log.d(TAG, "Inside IF CONDITION TO SHOW THE BLUETOOTH DEVICES");
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Log.i(TAG, "BT devices", device.getName());
                    if (ActivityCompat.checkSelfPermission(BTscanned_devices.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    }
                    stringArrayList.add(device.getName());
                   // arrayAdapter.notifyDataSetChanged();



                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,stringArrayList);
                    listView1.setAdapter(arrayAdapter);
                }
            }
        };

        IntentFilter intentFilter1=new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcasterScaled,intentFilter1);


    }
}
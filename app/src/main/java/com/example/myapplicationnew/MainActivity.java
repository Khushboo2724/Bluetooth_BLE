package com.example.myapplicationnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity";
    private int BT_resc_code = 100;
    private int ADV_resc_code = 200;
    private int sacn_resc_code = 300;

    ListView listView;
    //public ArrayList<BluetoothDevice> mDeviceList = new ArrayList<>();
    BluetoothAdapter btadapter;
    Button btnEnableDisableDiscoverable;
    Button BT;
    Button BLE;
    BluetoothDevice btDevice;
    //Button btnFindUnpairedDevices;
    ListView listViewScan;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


    private final BroadcastReceiver mbroadcastrecv = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //When discovery finds a device
            if (action.equals(btadapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, btadapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mbroadcastrecv: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mbroadcastrecv: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mbroadcastrecv: STATE TURNING ON");
                        break;

                }
            }
        }

    };

    private final BroadcastReceiver mbroadcastrecvEnable2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //When discovery finds a device
            if (action.equals(btadapter.ACTION_SCAN_MODE_CHANGED)) {
                final int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, btadapter.ERROR);

                switch (mode) {
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mbroadcastrecv2: Discoverability Enabled");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mbroadcastrecv2:Discoverability disabled:able to receive connection");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mbroadcastrecv2: Discoverability disabled. Not able to recv connection");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mbroadcastrecv2: connecting");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mbroadcastrecv2: connected");
                        break;

                }
            }
        }

    };

//    BroadcastReceiver mbroadcastrecvScaned = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
//                Log.d(TAG, "Inside IF CONDITION TO SHOW THE BLUETOOTH DEVICES");
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                }
//                stringArrayList.add(device.getName());
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//        }
//    };


//    @Override
//    protected void onDestroy() {
//        Log.d(TAG, "ON DESTROY called");
//        super.onDestroy();
//        unregisterReceiver(mbroadcastrecv);
//        unregisterReceiver(mbroadcastrecvEnable2);
//    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        finding the bt button on the screen using id
        Button OnOff = (Button) findViewById(R.id.btnOnOff);
        Button EnableDisable = (Button) findViewById(R.id.btnEnableDisableDiscoverable);
//        Button Discover = (Button) findViewById(R.id.btnFindUnpairedDevices);
//        listView = (ListView) findViewById(R.id.lvNewDevices);
        BT = (Button) findViewById(R.id.btnBT);
        BLE=(Button) findViewById(R.id.btnBLE);
        //listViewScan = (ListView) findViewById(R.id.lvScan);
        //mDeviceList= new ArrayList<>();

//      setting the recv as default
        btadapter = BluetoothAdapter.getDefaultAdapter();

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION


                },0);


//        inst after clicking the button
        OnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:enabling/disabling bt");

                enableDisable();
            }
        });
        EnableDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:discoverability");

                setBtnEnableDisableDiscoverable();

            }
        });

//        Discover.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick:to show paired devices");
//                getPaired();
//            }
//        });

        BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//                }
//                btadapter.startDiscovery();
                Intent intent1=new Intent(MainActivity.this,BT_Devices.class);
                startActivity(intent1);
            }
        });
        //SCbt.onCreateView();
//        IntentFilter ScanIntent =new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        registerReceiver(mbroadcastrecvScaned,ScanIntent);
//
//        arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,stringArrayList);
//        listViewScan.setAdapter(arrayAdapter);
//        listViewScan.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //listViewScan.removeViewAt(i);
//
//            }
//        });

        BLE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(MainActivity.this,BLE_devices.class);
                startActivity(intent2);
            }
        });

    }


//    private void getPaired() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//        }
//        Set<BluetoothDevice> btPair = btadapter.getBondedDevices();
//        String[] strings = new String[btPair.size()];
//        int index = 0;
//
//        if (btPair.size() > 0) {
//            for (BluetoothDevice device : btPair) {
//                strings[index] = device.getName();
//                index++;
//            }
//            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
//            listView.setAdapter(arrayAdapter);
//        }

    //}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BT_resc_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ADV_resc_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == sacn_resc_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void enableDisable() {
//        btadapter returns null or boolean value
        if (btadapter == null) {
            Log.d(TAG, "doesn't have matching config");
        }
        if (!btadapter.isEnabled()) {

            Log.d(TAG, "enabling bt");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
//
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                        BT_resc_code);

            }
            Intent BTenableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(BTenableIntent);

            IntentFilter BTintent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mbroadcastrecv, BTintent);
        }
        if (btadapter.isEnabled()) {
            Log.d(TAG, "disabling bt");

            btadapter.disable();

            IntentFilter BTintent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mbroadcastrecv, BTintent);
        }

    }


    public void setBtnEnableDisableDiscoverable() {
        Log.d(TAG, "btnEnableDisable_Discoverable: Making device discoverable for 300 sec");
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_ADVERTISE},
                    ADV_resc_code);
        }
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mbroadcastrecvEnable2, intentFilter);


    }
}


package com.example.myapplicationnew;

import static com.example.myapplicationnew.R.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class BT_Devices extends AppCompatActivity {
    private static final String TAG = "BT_Devices";
    Button Scan;
    Button Pair;
    BluetoothAdapter btadapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_devices);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button Scan=(Button) findViewById(R.id.btnScanBT);
        Button Pair=(Button) findViewById(id.btnPairBT);
        listView=(ListView) findViewById(id.lvNewDevices);

        btadapter = BluetoothAdapter.getDefaultAdapter();

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(BT_Devices.this,BTscanned_devices.class);
                startActivity(intent3);

            }
        });

        Pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:to show paired devices");
                getPaired();
            }
        });



    }

    private void getPaired() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
        }
        Set<BluetoothDevice> btPair = btadapter.getBondedDevices();
        ArrayList<String> strings = new ArrayList<String>();
        int index = 0;

        if (btPair.size() > 0) {
            for (BluetoothDevice device : btPair) {
                strings.add(index, device.getName());
                index++;
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    new AlertDialog.Builder(BT_Devices.this)
                            .setTitle("Do you want to remove "+strings.get(i)+" from list?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int ind) {
                                    strings.remove(i);
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();

                                }
                            }).create().show();



                    return false;
                }
            });



            //THIS IS TO DELETE THE PAIRED DEVICES
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
//                listView.removeViewAt(i);
//                arrayAdapter.notifyDataSetChanged();
//
//            }
//        });
        }
    }
}
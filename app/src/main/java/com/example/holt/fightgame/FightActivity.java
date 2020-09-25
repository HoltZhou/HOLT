package com.example.holt.fightgame;


import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothServerSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.holt.fightgame.adapter.BlueToothDeviceAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;



public class FightActivity extends AppCompatActivity implements View.OnClickListener {

    private BluetoothAdapter bTAdatper;
    private ListView listView;
    private BlueToothDeviceAdapter adapter;

    private TextView text_state;
    private TextView text_msg;

    private final int BUFFER_SIZE = 1024;
    private static final String NAME = "BT_DEMO";
    private static final UUID BT_UUID = UUID.fromString("02001101-0001-1000-8080-00805F9BA9BA");

    private ConnectThread connectThread;
    private ListenerThread listenerThread;

    private int hp1=60,hp2=60,defend=0,distance=1000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fight);
        initView();
        bTAdatper = BluetoothAdapter.getDefaultAdapter();
        initReceiver();
       listenerThread = new ListenerThread();
       listenerThread.start();
        ImageButton btn8 =(ImageButton)findViewById(R.id.imageButton12);
        btn8.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Intent intent6 = new Intent(FightActivity.this, MainActivity.class);
                startActivity(intent6);
            }
        });
        Button btn9 =(Button)findViewById(R.id.Button2);
        btn9.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                Intent intent6 = new Intent(FightActivity.this,Second.class);
                startActivity(intent6);
            }
        });
    }

    private void initView() {
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button11).setOnClickListener(this);
        findViewById(R.id.button12).setOnClickListener(this);
        findViewById(R.id.button13).setOnClickListener(this);

        text_state = (TextView) findViewById(R.id.text_state);

        text_msg = (TextView) findViewById(R.id.text_msg);

        listView = (ListView) findViewById(R.id.listView1);
        adapter = new BlueToothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (bTAdatper.isDiscovering()) {
                    bTAdatper.cancelDiscovery();
                }
                BluetoothDevice device = (BluetoothDevice) adapter.getItem(position);
                connectDevice(device);
            }
        });
    }

    private void initReceiver() {
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button8:
                openBlueTooth();
                break;
            case R.id.button9:
                searchDevices();
                break;
            case R.id.button:
                if (connectThread != null) {
                    connectThread.sendMsg("这是一个攻击的消息");
                }
                break;
            case R.id.button11:
                if (connectThread != null) {
                    connectThread.sendMsg("这是一个防御的消息");
                }
                break;
            case R.id.button12:
                if (connectThread != null) {
                connectThread.sendMsg("这是一个前进的消息");
            }
                break;
            case R.id.button13:
                if (connectThread != null) {
                    connectThread.sendMsg("这是一个后退的消息");
             }
                break;

        }
    }



    private void openBlueTooth() {
        if (bTAdatper == null) {
            Toast.makeText(this, "当前设备不支持蓝牙功能", Toast.LENGTH_SHORT).show();
        }
        if (!bTAdatper.isEnabled()) {
            bTAdatper.enable();
        }
        if (bTAdatper.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            startActivity(i);
        }
    }


    private void searchDevices() {
        if (bTAdatper.isDiscovering()) {
            bTAdatper.cancelDiscovery();
        }
        getBoundedDevices();
        bTAdatper.startDiscovery();
    }


    private void getBoundedDevices() {
        Set<BluetoothDevice> pairedDevices = bTAdatper.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                adapter.add(device);
            }
        }
    }

    private void connectDevice(BluetoothDevice device) {

        text_state.setText(getResources().getString(R.string.connecting));

        try {
            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(BT_UUID);
            connectThread = new ConnectThread(socket, true);
            connectThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bTAdatper != null && bTAdatper.isDiscovering()) {
            bTAdatper.cancelDiscovery();
        }
        unregisterReceiver(mReceiver);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    adapter.add(device);
                    adapter.notifyDataSetChanged();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Toast.makeText(FightActivity.this, "开始搜索", Toast.LENGTH_SHORT).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(FightActivity.this, "搜索完毕", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private class ConnectThread extends Thread {

        private BluetoothSocket socket;
        private boolean activeConnect;
        InputStream inputStream;
        OutputStream outputStream;

        private ConnectThread(BluetoothSocket socket, boolean connect) {
            this.socket = socket;
            this.activeConnect = connect;
        }

        @Override
        public void run() {
            try {
                if (activeConnect) {
                    socket.connect();
                }
                text_state.post(new Runnable() {
                    @Override
                    public void run() {
                        text_state.setText(getResources().getString(R.string.connect_success));
                    }
                });
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytes;
                while (true) {
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {

                        final byte[] data = new byte[bytes];

                        System.arraycopy(buffer, 0, data, 0, bytes);
                        text_msg.post(new Runnable() {
                            @Override
                            public void run() {
                                text_msg.setText(getResources().getString(R.string.get_msg)+new String(data));
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                text_state.post(new Runnable() {
                    @Override
                    public void run() {
                        text_state.setText(getResources().getString(R.string.connect_error));
                    }
                });
            }
        }

        public void sendMsg(final String msg) {

            byte[] bytes = msg.getBytes();
            if (outputStream != null) {
                try {
                    //发送数据
                    outputStream.write(bytes);
                    text_msg.post(new Runnable() {
                        @Override
                        public void run() {
                            text_msg.setText(getResources().getString(R.string.send_msgs)+msg);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    text_msg.post(new Runnable() {
                        @Override
                        public void run() {
                            text_msg.setText(getResources().getString(R.string.send_msg_error)+msg);
                        }
                    });
                }
            }
        }
    }
    private class ListenerThread extends Thread {

        private BluetoothServerSocket serverSocket;
        private BluetoothSocket socket;

        @Override
        public void run() {
            try {
                serverSocket = bTAdatper.listenUsingRfcommWithServiceRecord(
                        NAME, BT_UUID);
                while (true) {
                    //线程阻塞，等待别的设备连接
                    socket = serverSocket.accept();
                    text_state.post(new Runnable() {
                        @Override
                        public void run() {
                            text_state.setText(getResources().getString(R.string.connecting));
                        }
                    });
                    connectThread = new ConnectThread(socket, false);
                    connectThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


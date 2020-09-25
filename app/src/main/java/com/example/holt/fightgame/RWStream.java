package com.example.holt.fightgame;

import android.bluetooth.BluetoothSocket;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

public class RWStream extends Thread {

    InputStream is;
    OutputStream os;

    private final BluetoothSocket socket;

    public RWStream(BluetoothSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();

            byte[] buf = new byte[1024];
            int len = 0;
            while (socket.isConnected()) {
                while ((len = is.read(buf)) != -1) {
                    String message = new String(buf, 0, len);
                    Log.e("TAG", "---" + message);
                    if (dateShow != null) {
                        dateShow.getMessager(message);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("TAG", "线程异常");
        }
    }

    public void write(String msg) {
        Log.e("TAG", "os!=null   " + (os != null));
        if (os != null) {
            try {

                os.write(msg.getBytes());

                os.flush();
            } catch (Exception e) {
                Log.e("TAG", "异常" + e.getMessage());
            }
        }
    }


    interface DataShow {
        void getMessager(String message);

    }

    DataShow dateShow;

    public void setDataShow(DataShow dateShow) {
        this.dateShow = dateShow;
    }
}
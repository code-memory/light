package com.example.dell.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class BluetoothChatService {
    private static final String TAG = "BluetoothChatService";
    private static final boolean D = true;

    private static final UUID MY_UUID_SECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private final BluetoothAdapter mAdapter;
    private Handler mHandler;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;
    private Context mContext;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    public static final int STATE_NONE = 0;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;


    public BluetoothChatService(Context context, Handler handler) {
        mContext = context;
        mAdapter = BluetoothAdapter.getDefaultAdapter();//获取蓝牙适配器
        mState = STATE_NONE;
        mHandler = handler;
    }



    private synchronized void setState(int state) {
        if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;


        mHandler.obtainMessage(MESSAGE_STATE_CHANGE, state, -1).sendToTarget();//通知主视图服务器的当前状态
    }


    public synchronized int getState() {
        return mState;
    }


    public synchronized void start() {//synchronized修饰类方法，表示在当前对象中，在同一时间只能有一个线程执行这个方法
        if (D) Log.d(TAG, "start");

        // 取消所有企图连接的线程
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // 取消所有正在运行的连接线程
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
    }


    public synchronized void connect(BluetoothDevice device, boolean secure) {
        if (D) Log.d(TAG, "connect to: " + device);

        // 取消所有企图连接的线程
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}//取消正在连接的线程
        }

        // 取消所有正在运行的连接线程
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}//取消已经连接的线程

        mConnectThread = new ConnectThread(device);//新建一个蓝牙连接线程
        mConnectThread.start();//启动蓝牙连接线程
        setState(STATE_CONNECTING);
    }


    public synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device) {//已连接处理函数
        if (D) Log.d(TAG, "connected");

        // 取消已完成的连接线程
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // 取消正在运行的连接线程
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        mHandler.obtainMessage(MainActivity.MESSAGE_DEVICE,device).sendToTarget();
        mConnectedThread = new ConnectedThread(socket);//新建一个已连接线程
        mConnectedThread.start();//启动已连接线程

    }


    public synchronized void stop() {//停止服务器
        if (D) Log.d(TAG, "stop");

        if (mConnectThread != null) {
            mConnectThread.cancel();//取消连接线程
            mConnectThread = null;//删除连接线程
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();//取消已连接线程
            mConnectedThread = null;//删除已连接线程
        }
        setState(STATE_NONE);//改变服务器状态
    }

    public void write(byte[] out) {
        ConnectedThread r;
        synchronized (this) {//synchronized修饰代码区块，表示在当前对象中，在同一时间只能有一个线程执行大括号中的代码
            if (mState != STATE_CONNECTED) return;//如果设备没有连接则返回
            r = mConnectedThread;//获取蓝牙通信的连接线程
        }
        r.write(out);//通过蓝牙通信连接线程的写数据方法把数据发送出去
    }


    private void connectionFailed() {//蓝牙连接失败处理函数
        // 通知主视图蓝牙连接失败
        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);//通知主视图蓝牙连接失败
        Bundle bundle = new Bundle();//Bundle意为绑定，可以实现两个Activity之间的通信
        bundle.putString(TOAST, "unable_to_connect");//设置信息内容
        msg.setData(bundle);//设置信息内容
        mHandler.sendMessage(msg);//向主视图发送信息
        setState(STATE_NONE);//改变服务器状态

        BluetoothChatService.this.start();//重新启动服务器
    }


    private void connectionLost() {//连接断开
        // 向主视图发送失败消息
        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);//通知主视图蓝牙连接断开
        Bundle bundle = new Bundle();//Bundle意为绑定，可以实现两个Activity之间的通信
        bundle.putString(TOAST, "lost_connect");//设置信息内容
        msg.setData(bundle);//设置信息内容
        mHandler.sendMessage(msg);//向主视图发送信息

        BluetoothChatService.this.start();//重新启动服务器
    }


    private class ConnectThread extends Thread {//连接蓝牙设备的线程
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {//连接蓝牙设备的线程类的构造函数
            mmDevice = device;
            BluetoothSocket tmp = null;

            try {
                tmp = device.createRfcommSocketToServiceRecord(
                        MY_UUID_SECURE);//生成一个加密的BluetoothSocket
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + "create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");//设置线程的名称

            mAdapter.cancelDiscovery();//取消搜索设备，蓝牙连接前必须取消搜索

            try {

                mmSocket.connect();//BluetoothSocket连接
                MainActivity.usedDeviceAddress = mmDevice.getAddress();
            } catch (IOException e) {

                try {
                    mmSocket.close();//如果发生异常(连接失败)则将BluetoothSocket关闭
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() " +
                            " socket during connection failure", e2);
                }
                connectionFailed();//蓝牙连接失败处理
                return;
            }

            synchronized (BluetoothChatService.this) {
                mConnectThread = null;
            }

            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();//关闭socket
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " + " socket failed", e);
            }
        }
    }


    private class ConnectedThread extends Thread {//连接以后获取输入输出流
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();//获取输入流
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {//获取输出流
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            setState(STATE_CONNECTED);//改变服务器状态
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[512];
            byte[] tempbuffer = new byte[512];
            int bytes;

            // Keep listening to the InputStream while connected
            while (mState == STATE_CONNECTED) {
                try {
                    bytes = mmInStream.read(tempbuffer);//读取接收到的信息
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();//将接收到的信息发送到主视图
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();//连接断开
                    break;
                }
            }
        }


        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);//写数据
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();//关闭socket
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }
}

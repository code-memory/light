package com.example.dell.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends Activity {
    private static Context mContext;
    String[] setStr;
    public static String usedDeviceAddress = "";
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_START_ABOUT = 2;
    private static final int REQUEST_USEINFO_SET = 4;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE = 4;
    public static final int MESSAGE_TOAST = 5;

    private Button img_search = null;
    public static int screenWidth = 0;

    private ActionSheet cActionSheet;
    private ActionSheet mActionSheet = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothChatService mChatService = null;
    private String mConnectedDeviceName = null;
    private String mConnectedDeviceAddress = null;
    private Button button_o1_1, button_o2_1, button_o3_1, button_o4_1, button_o5_1, button_o6_1, button_o7_1, button_o8_1, button_oa_1;
    private Button button_o1_2, button_o2_2, button_o3_2, button_o4_2, button_o5_2, button_o6_2, button_o7_2, button_o8_2, button_oa_2;
    private Button button_o1_3, button_o2_3, button_o3_3, button_o4_3, button_o5_3, button_o6_3, button_o7_3, button_o8_3, button_oa_3,
            button_c1, button_c2, button_c3, button_c4, button_c5, button_c6, button_c7, button_c8, button_ca;

    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.set_main);
        Log.e(TAG, "main 1");
        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();//????????????????????????????????????
        if (mBluetoothAdapter == null) {//???????????????????????????????????????????????????????????????
            Toast.makeText(this, R.string.no_bt, Toast.LENGTH_LONG).show();//???????????????????????????
            finish();//????????????Activity????????????????????????.
            return;
        }


        img_search = (Button) findViewById(R.id.btn_bluebooth);
        img_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(mContext, DeviceListActivity.class);//Intent???????????????????????????Android???????????????????????????????????????
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
            }

        });
        button_o1_1 = (Button) findViewById(R.id.button_o1_1);
        button_o2_1 = (Button) findViewById(R.id.button_o2_1);
        button_o3_1 = (Button) findViewById(R.id.button_o3_1);
        button_o4_1 = (Button) findViewById(R.id.button_o4_1);
        button_o5_1 = (Button) findViewById(R.id.button_o5_1);
        button_o6_1 = (Button) findViewById(R.id.button_o6_1);
        button_o7_1 = (Button) findViewById(R.id.button_o7_1);
        button_o8_1 = (Button) findViewById(R.id.button_o8_1);
        button_oa_1 = (Button) findViewById(R.id.button_oa_1);

        button_o1_2 = (Button) findViewById(R.id.button_o1_2);
        button_o2_2 = (Button) findViewById(R.id.button_o2_2);
        button_o3_2 = (Button) findViewById(R.id.button_o3_2);
        button_o4_2 = (Button) findViewById(R.id.button_o4_2);
        button_o5_2 = (Button) findViewById(R.id.button_o5_2);
        button_o6_2 = (Button) findViewById(R.id.button_o6_2);
        button_o7_2 = (Button) findViewById(R.id.button_o7_2);
        button_o8_2 = (Button) findViewById(R.id.button_o8_2);
        button_oa_2 = (Button) findViewById(R.id.button_oa_2);

        button_o1_3 = (Button) findViewById(R.id.button_o1_3);
        button_o2_3 = (Button) findViewById(R.id.button_o2_3);
        button_o3_3 = (Button) findViewById(R.id.button_o3_3);
        button_o4_3 = (Button) findViewById(R.id.button_o4_3);
        button_o5_3 = (Button) findViewById(R.id.button_o5_3);
        button_o6_3 = (Button) findViewById(R.id.button_o6_3);
        button_o7_3 = (Button) findViewById(R.id.button_o7_3);
        button_o8_3 = (Button) findViewById(R.id.button_o8_3);
        button_oa_3 = (Button) findViewById(R.id.button_oa_3);

        button_c1 = (Button) findViewById(R.id.button_c1);
        button_c2 = (Button) findViewById(R.id.button_c2);
        button_c3 = (Button) findViewById(R.id.button_c3);
        button_c4 = (Button) findViewById(R.id.button_c4);
        button_c5 = (Button) findViewById(R.id.button_c5);
        button_c6 = (Button) findViewById(R.id.button_c6);
        button_c7 = (Button) findViewById(R.id.button_c7);
        button_c8 = (Button) findViewById(R.id.button_c8);
        button_ca = (Button) findViewById(R.id.button_ca);

        button_o1_1.setOnClickListener(myButtonListener);
        button_o2_1.setOnClickListener(myButtonListener);
        button_o3_1.setOnClickListener(myButtonListener);
        button_o4_1.setOnClickListener(myButtonListener);
        button_o5_1.setOnClickListener(myButtonListener);
        button_o6_1.setOnClickListener(myButtonListener);
        button_o7_1.setOnClickListener(myButtonListener);
        button_o8_1.setOnClickListener(myButtonListener);
        button_oa_1.setOnClickListener(myButtonListener);

        button_o1_2.setOnClickListener(myButtonListener);
        button_o2_2.setOnClickListener(myButtonListener);
        button_o3_2.setOnClickListener(myButtonListener);
        button_o4_2.setOnClickListener(myButtonListener);
        button_o5_2.setOnClickListener(myButtonListener);
        button_o6_2.setOnClickListener(myButtonListener);
        button_o7_2.setOnClickListener(myButtonListener);
        button_o8_2.setOnClickListener(myButtonListener);
        button_oa_2.setOnClickListener(myButtonListener);

        button_o1_3.setOnClickListener(myButtonListener);
        button_o2_3.setOnClickListener(myButtonListener);
        button_o3_3.setOnClickListener(myButtonListener);
        button_o4_3.setOnClickListener(myButtonListener);
        button_o5_3.setOnClickListener(myButtonListener);
        button_o6_3.setOnClickListener(myButtonListener);
        button_o7_3.setOnClickListener(myButtonListener);
        button_o8_3.setOnClickListener(myButtonListener);
        button_oa_3.setOnClickListener(myButtonListener);

        button_c1.setOnClickListener(myButtonListener);
        button_c2.setOnClickListener(myButtonListener);
        button_c3.setOnClickListener(myButtonListener);
        button_c4.setOnClickListener(myButtonListener);
        button_c5.setOnClickListener(myButtonListener);
        button_c6.setOnClickListener(myButtonListener);
        button_c7.setOnClickListener(myButtonListener);
        button_c8.setOnClickListener(myButtonListener);
        button_ca.setOnClickListener(myButtonListener);


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /*?????????????????????????????????*/
    private OnClickListener myButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
                Intent serverIntent = new Intent(mContext, DeviceListActivity.class);//Intent???????????????????????????Android???????????????????????????????????????
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return;
            }
            switch (v.getId()) {//???????????????????????????????????????????????????
                case R.id.button_o1_1:
                    mChatService.write("A1".getBytes());
                    break;
                case R.id.button_o2_1:
                    mChatService.write("A2".getBytes());
                    break;
                case R.id.button_o3_1:
                    mChatService.write("A3".getBytes());
                    break;
                case R.id.button_o4_1:
                    mChatService.write("A4".getBytes());
                    break;
                case R.id.button_o5_1:
                    mChatService.write("A5".getBytes());
                    break;
                case R.id.button_o6_1:
                    mChatService.write("A6".getBytes());
                    break;
                case R.id.button_o7_1:
                    mChatService.write("A7".getBytes());
                    break;
                case R.id.button_o8_1:
                    mChatService.write("A8".getBytes());
                    break;
                case R.id.button_oa_1:
                    mChatService.write("A1A2A3A4A5A6A7A8".getBytes());
                    break;
                case R.id.button_o1_2:
                    mChatService.write("B1".getBytes());
                    break;
                case R.id.button_o2_2:
                    mChatService.write("B2".getBytes());
                    break;
                case R.id.button_o3_2:
                    mChatService.write("B3".getBytes());
                    break;
                case R.id.button_o4_2:
                    mChatService.write("B4".getBytes());
                    break;
                case R.id.button_o5_2:
                    mChatService.write("B5".getBytes());
                    break;
                case R.id.button_o6_2:
                    mChatService.write("B6".getBytes());
                    break;
                case R.id.button_o7_2:
                    mChatService.write("B7".getBytes());
                    break;
                case R.id.button_o8_2:
                    mChatService.write("B8".getBytes());
                    break;
                case R.id.button_oa_2:
                    mChatService.write("B1B2B3B4B5B6B7B8".getBytes());
                    break;
                case R.id.button_o1_3:
                    mChatService.write("C1".getBytes());
                    break;
                case R.id.button_o2_3:
                    mChatService.write("C2".getBytes());
                    break;
                case R.id.button_o3_3:
                    mChatService.write("C3".getBytes());
                    break;
                case R.id.button_o4_3:
                    mChatService.write("C4".getBytes());
                    break;
                case R.id.button_o5_3:
                    mChatService.write("C5".getBytes());
                    break;
                case R.id.button_o6_3:
                    mChatService.write("C6".getBytes());
                    break;
                case R.id.button_o7_3:
                    mChatService.write("C7".getBytes());
                    break;
                case R.id.button_o8_3:
                    mChatService.write("C8".getBytes());
                    break;
                case R.id.button_oa_3:
                    mChatService.write("C1C2C3C4C5C6C7C8".getBytes());
                    break;

                case R.id.button_c1:
                    mChatService.write("S1".getBytes());
                    break;
                case R.id.button_c2:
                    mChatService.write("S2".getBytes());
                    break;
                case R.id.button_c3:
                    mChatService.write("S3".getBytes());
                    break;
                case R.id.button_c4:
                    mChatService.write("S4".getBytes());
                    break;
                case R.id.button_c5:
                    mChatService.write("S5".getBytes());
                    break;
                case R.id.button_c6:
                    mChatService.write("S6".getBytes());
                    break;
                case R.id.button_c7:
                    mChatService.write("S7".getBytes());
                    break;
                case R.id.button_c8:
                    mChatService.write("S8".getBytes());
                    break;
                case R.id.button_ca:
                    mChatService.write("S1S2S3S4S5S6S7S8".getBytes());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        client.connect();
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(mReceiver, filter);//?????????????????????


        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    private void startService() {
        if (mChatService == null) {
            mChatService = new BluetoothChatService(this, mHandler);//?????????????????????????????????

            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {//???????????????????????????????????????????????????????????????????????????
                mChatService.start();//?????????????????????
            }
        }
        if (mChatService != null) { //?????????????????????????????????????????????????????????
            mChatService.setHandler(mHandler);
        }

    }

    @Override
    public synchronized void onResume() {//synchronized???????????????????????????????????????????????????????????????????????????????????????????????????
        super.onResume();                //????????????ACTION_REQUEST_ENABLE activity???????????????
        Log.e(TAG, "+ ON RESUME +");
        if (!mBluetoothAdapter.isEnabled()) {//?????????????????????????????????
            mBluetoothAdapter.enable();//???????????????????????????????????????????????????
        } else {
            startService();
        }
    }

    @Override
    public void onDestroy() {//?????????????????????????????????????????????finish()????????????????????????
        super.onDestroy();
        if (mChatService != null) mChatService.stop();//???????????????
        Log.e(TAG, "--- ON DESTROY ---");
        this.unregisterReceiver(mReceiver);//?????????????????????
        System.exit(0);//?????????????????????????????????????????????????????????????????????????????????
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE://??????????????????????????????
                if (resultCode == Activity.RESULT_OK) {//???????????????
                    connectDevice(data, true);//????????????????????????
                }
                break;
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();//??????????????????
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {//
                if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
                    Toast.makeText(getApplicationContext(), "????????????????????????????????????5????????????", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 5000); //??????5?????????
                }
                if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                    startService();
                }
            }
        }//???????????????

    };

    private final Handler mHandler = new Handler() {
        @Override                                //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????UI
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE://??????????????????
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED://?????????????????????
                            img_search.setBackgroundResource(R.drawable.btn_search__con);
                            break;
                        case BluetoothChatService.STATE_CONNECTING://????????????????????????
                            break;
                        case BluetoothChatService.STATE_NONE://?????????????????????????????????
                            img_search.setBackgroundResource(R.drawable.btn_search_normal);
                            break;
                    }
                    break;
                case MESSAGE_READ://????????????
                    byte[] readBuf = (byte[]) msg.obj;//??????????????????????????????
                    String str = BaseOperations.byteToHex(readBuf, msg.arg1);
                    break;
                case MESSAGE_DEVICE://??????????????????
                    // save the connected device's name
                    mConnectedDeviceName = ((BluetoothDevice) msg.obj).getName();//???????????????????????????????????????
                    mConnectedDeviceAddress = ((BluetoothDevice) msg.obj).getAddress();// ????????????????????????????????????????????????
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();//??????????????????????????????
                    break;
                case MESSAGE_TOAST://??????
                    if (msg.getData().getString("toast").equals("unable_to_connect"))
                        Toast.makeText(getApplicationContext(), R.string.unable_to_connect,
                                Toast.LENGTH_SHORT).show();
                    if (msg.getData().getString("toast").equals("lost_connect")) {
                        Toast.makeText(getApplicationContext(), R.string.lost_connect,
                                Toast.LENGTH_SHORT).show();
                        img_search.setBackgroundResource(R.drawable.btn_search_normal);
                    }
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//??????????????????????????????????????????
        MenuInflater inflater = getMenuInflater();//MenuInflater?????????????????????menu??????????????????????????????(inflater???????????????????????????????????????????????????)
        inflater.inflate(R.menu.option_menu, menu);//?????????????????????????????????????????????(????????????XML??????)????????????????????????(????????????)
        return true;
    }

    public void connectDevice(Intent data, boolean secure) {//??????????????????
        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);//???????????????????????????
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);//????????????????????????
        if (mChatService != null) {
            mChatService.connect(device, secure);//??????????????????
        } else {
            Log.e("???", "mChatService=null");
        }
    }

    private void myMenu() {
        // ????????????????????????????????????????????????
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.my_menu, null);
        contentView.setMinimumWidth(getWallpaperDesiredMinimumWidth());
        if (mActionSheet == null) {
            mActionSheet = new ActionSheet(this, Gravity.BOTTOM, R.style.ActionSheetDialog);
            mActionSheet.setContentView(contentView);
            mActionSheet.setAnimation(R.style.ActionSheetDialogAnimation);
            mActionSheet.setCancelable(true);
            mActionSheet.setCanceledOnTouchOutside(true);
            mActionSheet.getWindow().setDimAmount(0.3f);
        }
        mActionSheet.show();
        OnClickListener popMenuOnClickListener = new OnClickListener() {
            Intent serverIntent = null;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.layout_pop_useinfo:
                        serverIntent = new Intent(getApplicationContext(), UseInfoActivity.class);
                        startActivityForResult(serverIntent, REQUEST_USEINFO_SET);
                        mActionSheet.dismiss();
                        break;

                    case R.id.layout_pop_about:
                        serverIntent = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivityForResult(serverIntent, REQUEST_START_ABOUT);
                        break;

                    case R.id.layout_pop_exit:
                        exitDialog();
                        break;

                    case R.id.layout_pop_cancle:
                        break;
                }
                mActionSheet.dismiss();
            }

        };
        contentView.findViewById(R.id.layout_pop_useinfo).setOnClickListener(popMenuOnClickListener);
        contentView.findViewById(R.id.layout_pop_about).setOnClickListener(popMenuOnClickListener);
        contentView.findViewById(R.id.layout_pop_exit).setOnClickListener(popMenuOnClickListener);
        contentView.findViewById(R.id.layout_pop_cancle).setOnClickListener(popMenuOnClickListener);
    }

    private void exitDialog() {
        // ????????????????????????????????????????????????
        final View contentView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.exitdialog, null);
        contentView.setMinimumWidth(getWindowManager().getDefaultDisplay().getWidth() * 5 / 6);
        if (cActionSheet == null) {
            cActionSheet = new ActionSheet(this, Gravity.CENTER, R.style.ActionSheetDialog);
            cActionSheet.setGravity(Gravity.CENTER);
            cActionSheet.setContentView(contentView);
            cActionSheet.setCancelable(true);
            cActionSheet.setCanceledOnTouchOutside(true);
            cActionSheet.getWindow().setDimAmount(0.3f);
            contentView.findViewById(R.id.layout_exit_cancle).setOnClickListener(
                    new OnClickListener() {
                        public void onClick(View v) {
                            cActionSheet.dismiss();
                        }
                    });
            contentView.findViewById(R.id.layout_exit_ok).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) contentView.findViewById(R.id.cb_exit_closebt)).isChecked()) {
                        mBluetoothAdapter.disable();
                    }
                    cActionSheet.dismiss();
                    finish();
                }
            });
        }
        cActionSheet.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("mengdd", "onKey");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);//??????????????????
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            myMenu();
            return true;
        }

        return false;

    }


    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page")

                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

package com.watersystem.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bluetoothprinter.BlueToothService;
import com.watersystem.app.Api.Get_Rate;
import com.watersystem.app.Api.Get_Reading;
import com.watersystem.app.Includes.Api;
import com.watersystem.app.Includes.Config;
import com.watersystem.app.Includes.DataHandler;
import com.watersystem.app.Includes.Rate;
import com.watersystem.app.Includes.Reading;
import com.watersystem.app.Includes.Report;
import com.watersystem.app.Includes.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,OnItemSelectedListener,SurfaceHolder.Callback {

    private BlueToothService mBTService = null;
    private static final int REQUEST_EX = 1;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    private Button checkButton;
    private Button controlButton;
    private Button bt_matches;// 配对蓝牙 Pairing Bluetooth
    private ListView deviceList;// 设备列表 Device List
    private ArrayAdapter<String> mPairedDevicesArrayAdapter = null;// 已配对 Has been paired
    private ArrayAdapter<String> mNewDevicesArrayAdapter = null;// 新搜索列表 New search list
    private BluetoothAdapter mBluetoothAdapter = null;
    private Set<BluetoothDevice> devices;
    private Button bt_scan;// 扫描设备 Scanning equipment
    public Handler handler = null;
    public Handler mhandler;
    private ProgressDialog progressDialog = null;
    private EditText edit;
    private ViewGroup vg;
    private LinearLayout layout;
    private LinearLayout layoutscan;
    private Button bt_print;// 文字打印 Text printing
    private Button bt_image;// 图片打印 Image printing
    private Button bt_order;// 指令打印 Instruction print
    private Button bt_openpic;// 打开图片目录 Open the picture directory
    private ImageView iv;// 显示的图片 Show the picture
    private Button bt_2d;// 生成二维码 Generate two-dimensional code
    private Button bt_bar;// 生成条形码 Generate bar code
    private String picPath = "";// 打开图片保存的路径 Open the image to save the path
    private Bitmap btMap = null;// 缓存图片 Cache the picture
    private TextView tv_status;
    private Button bt_disconnect;
    private Thread tv_update;
    private boolean tvFlag = true;
    private Thread bt_update = null;
    private boolean updateflag = true;
    private Button nbt_img;
    private int verson = 48;// 76和80打印机，58打印机 48； 76 and 80 printers, 58 printer 48;
    private Spinner sp1;
    private ArrayAdapter<String> adapter;
    private static String[] arr = { "58", "76" };
    private static String[] value = { "384", "576" };

    final Context context = this;
    private EditText search_Box;
    private Spinner filter_Type;
    private ListView listView;

    private Button button_Setting;
    private Button synch;

    private static DataHandler db;

    TextView testView;

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;

    PictureCallback rawCallback;
    ShutterCallback shutterCallback;
    PictureCallback jpegCallback;

    PictureCallback padsAddedPicCallback;

    private ProgressDialog progress;
    private String reading_id;

    TableLayout country_table;

    TableRow row;
    TextView t1, t2,t3, t4;

    ProgressDialog globaldialog;


    private String
        var_id,
        var_billing_date,
        var_current_reading,
        var_current_balance,
        var_bill_to,
        var_cashtendered,
        var_customer_id,
        var_receipt_no;

    private Bitmap btmp;

    private String encoded_string;

    private String KEY_IMAGE = "image";
    private String KEY_FILENAME = "name";
    private String KEY_ID = "id";
    private String KEY_CUSTOMER = "customerid";
    private String KEY_BILLING_DATE = "billingdate";
    private String KEY_CURRENT_READING = "currentreading";
    private String KEY_CURRENT_BALANCE = "currentbalance";
    private String KEY_BILL_TO = "billto";
    private String KEY_CASHTENDERED = "cashtendered";
    private String KEY_RECEIPT = "receipt_number";

    public MainActivity(){
        //dialog = new ProgressDialog(activity);can not resolve symbol R
    }

    public String getReading_id() {
        return reading_id;
    }

    public void setReading_id(String reading_id) {
        this.reading_id = reading_id;
    }

    //-------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //----------------------------

        layout = (LinearLayout) View.inflate(MainActivity.this, R.layout.edittext, null);
        iv = (ImageView) findViewById(R.id.iv_test);
        deviceList = (ListView) findViewById(R.id.lv_device);
        vg = (ViewGroup) deviceList.getParent();
        edit =  layout.findViewById(R.id.et_input);
        edit.setFocusable(false);
        layout.removeAllViews();
        vg.addView(edit, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        edit.setEnabled(false);
        edit.setVisibility(View.GONE);
        sp1 = (Spinner) findViewById(R.id.spinner1);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arr);

        //Will be optional with content ArrayAdapter connection
        // 将可选内容与ArrayAdapter连接
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set the style of the drop-down list
        // 设置下拉列表的风格
        sp1.setAdapter(adapter);
        // 将adapter添加到Spinner中
        sp1.setSelection(0);
        sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 == 0) {
                    verson = 48;
                } else if (arg2 == 1) {
                    verson = 72;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

                verson = 48;

            }
        });

        bt_print = (Button) findViewById(R.id.bt_print);
        layoutscan = (LinearLayout) findViewById(R.id.layoutscan);
        layoutscan.setVisibility(View.GONE);

        bt_order = (Button) findViewById(R.id.bt_order);
        bt_order.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mBTService.getState() != mBTService.STATE_CONNECTED) {
                    Toast.makeText(
                            MainActivity.this,
                            MainActivity.this.getResources().getString(
                                    R.string.str_unconnected),  Toast.LENGTH_LONG).show();
                    return;
                }
                byte[] send = new byte[10];
                mBTService.SendOrder(send);
            }
        });
        bt_openpic = (Button) findViewById(R.id.bt_openpci);
        bt_openpic.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_EX);
            }
        });

        mhandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MESSAGE_STATE_CHANGE:// 蓝牙连接状态 Bluetooth connection status
                        switch (msg.arg1) {
                            case BlueToothService.STATE_CONNECTED:// 已经连接 Has been connected
                                break;
                            case BlueToothService.STATE_CONNECTING:// 正在连接 connecting
                                break;
                            case BlueToothService.STATE_LISTEN:
                            case BlueToothService.STATE_NONE:
                                break;
                            case BlueToothService.SUCCESS_CONNECT:
                                Toast.makeText(
                                        MainActivity.this,
                                        MainActivity.this.getResources().getString(
                                                R.string.str_succonnect),  Toast.LENGTH_LONG).show();
                                vg.getChildAt(0).setVisibility(View.GONE);
                                vg.getChildAt(1).setVisibility(View.GONE);
                                vg.getChildAt(2).setVisibility(View.VISIBLE);
                                vg.getChildAt(2).setFocusable(true);
                                vg.getChildAt(2).setFocusableInTouchMode(true);

                                break;
                            case BlueToothService.FAILED_CONNECT:
                                Toast.makeText(
                                        MainActivity.this,
                                        MainActivity.this.getResources().getString(
                                                R.string.str_faileconnect),  Toast.LENGTH_LONG)
                                        .show();
                                vg.getChildAt(0).setVisibility(View.VISIBLE);
                                vg.getChildAt(1).setVisibility(View.VISIBLE);
                                vg.getChildAt(2).setVisibility(View.GONE);
                                vg.getChildAt(2).setFocusable(false);
                                break;
                            case BlueToothService.LOSE_CONNECT:
                                Toast.makeText(
                                        MainActivity.this,
                                        MainActivity.this.getResources().getString(
                                                R.string.str_lose),
                                        Toast.LENGTH_LONG).show();
                                vg.getChildAt(0).setVisibility(View.VISIBLE);
                                vg.getChildAt(1).setVisibility(View.VISIBLE);
                                vg.getChildAt(2).setVisibility(View.GONE);
                                vg.getChildAt(2).setFocusable(false);
                                break;
                        }
                        break;
                    case MESSAGE_READ:
                        // sendFlag = false;//缓冲区已满 The buffer is full
                        break;
                    case MESSAGE_WRITE:// 缓冲区未满 Buffer is not full
                        // sendFlag = true;
                        break;

                }
            }
        };

        bt_disconnect = (Button) findViewById(R.id.bt_disconnect);
        bt_disconnect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mBTService.getState() == mBTService.STATE_CONNECTED) {
                    mBTService.DisConnected();
                }
            }
        });

        bt_2d = (Button) findViewById(R.id.bt_2d);
//       bt_2d.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                if (mBTService.getState() != mBTService.STATE_CONNECTED) {
//                    Toast.makeText(
//                            MainActivity.this,
//                            MainActivity.this.getResources().getString(
//                                    R.string.str_unconnected),  Toast.LENGTH_LONG).show();
//                    return;
//                }
//                String message = edit.getText().toString();
//                if (message.length() > 0) {
//
//                    try {
//                        message = new String(message.getBytes("utf8"));
//                    } catch (UnsupportedEncodingException e1) {
//                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
//                    }
//
//                    btMap = BarcodeCreater.encode2dAsBitmap(message, 384, 384,
//                            2);
//                    BarcodeCreater.saveBitmap2file(btMap, "mypic1.JPEG");
//                    iv.setImageBitmap(btMap);
//
//                }
//            }
//        });

        bt_bar = (Button) findViewById(R.id.bt_bar);
//        bt_bar.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                if (mBTService.getState() != mBTService.STATE_CONNECTED) {
//                    Toast.makeText(
//                            MainActivity.this,
//                            MainActivity.this.getResources().getString(
//                                    R.string.str_unconnected),  Toast.LENGTH_LONG).show();
//                    return;
//                }
//                String message = edit.getText().toString();
//
//                if (message.getBytes().length > message.length()) {
//                    Toast.makeText(
//                            MainActivity.this,
//                            MainActivity.this.getResources().getString(
//                                    R.string.str_cannotcreatebar),  Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (message.length() > 0) {
//
//                    btMap = BarcodeCreater.creatBarcode(MainActivity.this,
//                            message, 384, 120, true, 1);// 最后一位参数是条码格式 The last parameter is the barcode format
//                    iv.setImageBitmap(btMap);
//
//                }
//
//            }
//        });


        mBTService = new BlueToothService(this, mhandler);// 创建对象的时候必须有一个是Handler类型 When creating an object, there must be Handler Types of
        // 点击检查是否有蓝牙设备 Click to check if there is a Bluetooth device
        checkButton = (Button) findViewById(R.id.bt_check);
        checkButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mBTService.HasDevice()) {
                    Toast.makeText(
                            MainActivity.this,
                            MainActivity.this.getResources().getString(
                                    R.string.str_devecehasblue),  Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(
                            MainActivity.this,
                            MainActivity.this.getResources().getString(
                                    R.string.str_hasnodevice),  Toast.LENGTH_LONG).show();
                }
            }
        });

        // 点击打开或者关闭蓝牙设备 Click to turn the Bluetooth device on or off

        controlButton = (Button) findViewById(R.id.bt_openclose);
        if (mBTService.IsOpen()) {// 判断蓝牙是否打开 To determine whether Bluetooth is open
            controlButton.setText(MainActivity.this.getResources().getString(
                    R.string.str_open));
        }
        controlButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mBTService.IsOpen()) {// 判断蓝牙是否打开 To determine whether Bluetooth is open
                    if (mBTService.getState() == mBTService.STATE_CONNECTED) {
                        mBTService.DisConnected();
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mBTService.CloseDevice();
                } else {
                    mBTService.OpenDevice();
                }

            }
        });

        // 更新按钮状态 Update button status
        bt_update = new Thread() {
            public void run() {
                while (updateflag) {
                    if (mBTService.IsOpen()) {// 判断蓝牙是否打开 To determine whether Bluetooth is open
                        controlButton.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                controlButton.setText(MainActivity.this
                                        .getResources().getString(
                                                R.string.str_close));
                            }
                        });
                    } else {
                        controlButton.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                controlButton.setText(MainActivity.this
                                        .getResources().getString(
                                                R.string.str_open));
                            }
                        });

                    }

                }
            }
        };

        bt_update.start();
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.device_name);

        // 查看已配对蓝牙 View Paired Bluetooth
        bt_matches = (Button) findViewById(R.id.bt_matches);
        bt_matches.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!mBTService.IsOpen()) {
                    mBTService.OpenDevice();
                    return;
                }
                deviceList.setAdapter(mPairedDevicesArrayAdapter);
                mPairedDevicesArrayAdapter.clear();
                devices = mBTService.GetBondedDevice();
                if (devices.size() > 0) {
                    for (BluetoothDevice device : devices) {
                        mPairedDevicesArrayAdapter.add(device.getName() + "\n"
                                + device.getAddress());
                    }
                } else {
                    String noDevices = MainActivity.this.getResources()
                            .getString(R.string.str_nomatched);
                    mPairedDevicesArrayAdapter.add(noDevices);
                }
            }
        });

        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,
                R.layout.device_name);
        // 扫描所有区设备 Scan all zone devices

        bt_scan = (Button) findViewById(R.id.bt_scan);
        bt_scan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 先判断是否正在扫描 First determine whether it is scanning

                if (!mBTService.IsOpen()) {// 判断蓝牙是否打开 To determine whether Bluetooth is open
                    mBTService.OpenDevice();
                    return;
                }
                if (mBTService.GetScanState() == mBTService.STATE_SCANING)
                    return;
                vg.getChildAt(0).setVisibility(View.VISIBLE);
                vg.getChildAt(1).setVisibility(View.VISIBLE);
                layoutscan.setVisibility(View.VISIBLE);
                mNewDevicesArrayAdapter.clear();
                devices = mBTService.GetBondedDevice();

                if (devices.size() > 0) {

                    for (BluetoothDevice device : devices) {
                        mNewDevicesArrayAdapter.add(device.getName() + "\n"
                                + device.getAddress());
                    }
                }
                deviceList.setAdapter(mNewDevicesArrayAdapter);
                new Thread() {
                    public void run() {
                        mBTService.ScanDevice();
                    }
                }.start();

            }

        });

        mBTService.setOnReceive(new BlueToothService.OnReceiveDataHandleEvent() {

            @Override
            public void OnReceive(BluetoothDevice device) {
                // TODO Auto-generated method stub
                if (device != null) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n"
                            + device.getAddress());
                } else {
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                }
            }
        });

        deviceList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 获取蓝牙物理地址 Get the Bluetooth physical address
                if (!mBTService.IsOpen()) {// 判断蓝牙是否打开 To determine whether Bluetooth is open
                    mBTService.OpenDevice();
                    return;
                }
                if (mBTService.GetScanState() == mBTService.STATE_SCANING) {
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
                if (mBTService.getState() == mBTService.STATE_CONNECTING) {
                    return;
                }

                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);
                mBTService.DisConnected();
                mBTService.ConnectToDevice(address);// 连接蓝牙 Connect to Bluetooth
            }
        });

        bt_print.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this,"Print !",  Toast.LENGTH_LONG).show();

                if (mBTService.getState() != mBTService.STATE_CONNECTED) {
                    Toast.makeText(
                            MainActivity.this,
                            MainActivity.this.getResources().getString(
                                    R.string.str_unconnected),  Toast.LENGTH_LONG).show();
                    return;
                }

                byte[] bt = new byte[3];
                bt[0] = 27;
                bt[1] = 56;
                bt[2] = (1/2);// 1,2//设置字体大小 Set the font size
                mBTService.write(bt);
                mBTService.PrintCharacters("123456789012345678901234567890");
            }
        });

        bt_image = (Button) findViewById(R.id.bt_image);
        bt_image.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (mBTService.getState() != mBTService.STATE_CONNECTED) {
                    Toast.makeText(
                            MainActivity.this,
                            MainActivity.this.getResources().getString(
                                    R.string.str_unconnected),  Toast.LENGTH_LONG).show();
                    return;
                }
                if (btMap != null) {
                    Bitmap bitmapOrg = btMap;// BitmapFactory.decodeFile(picPath);
                    int w = bitmapOrg.getWidth();
                    int h = bitmapOrg.getHeight();
                    mBTService.PrintImage(
                            resizeImage(bitmapOrg, verson * 8, h), 500);// 第二个参数代表延时操作，如果时间太短，会导致打印机堵塞。76打印机需要延时4000到5000
                    // The second parameter represents the delay operation, if the time is too short, will cause the printer to block.
                    //4000 to 5000
                    return;
                }
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:

                        break;
                    case 1:// 扫描完毕 The scan is finished
                        // progressDialog.cancel();
                        mBTService.StopScan();
                        layoutscan.setVisibility(View.GONE);
                        Toast.makeText(
                                MainActivity.this,
                                MainActivity.this.getResources().getString(
                                        R.string.str_scanover),  Toast.LENGTH_LONG).show();
                        break;
                    case 2:// 停止扫描 Stop scanning

                        layoutscan.setVisibility(View.GONE);
                        break;
                }
            }
        };

        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_update = new Thread() {
            public void run() {
                while (tvFlag) {
                    try {
                        Thread.sleep( Toast.LENGTH_LONG);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    tv_status.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            if (mBTService != null) {
                                if (mBTService.getState() == mBTService.STATE_CONNECTED) {
                                    tv_status.setText(MainActivity.this
                                            .getResources().getString(
                                                    R.string.str_connected));
                                    edit.setVisibility(View.GONE);
                                } else if (mBTService.getState() == mBTService.STATE_CONNECTING) {
                                    tv_status.setText(MainActivity.this
                                            .getResources().getString(
                                                    R.string.str_connecting));
                                } else {
                                    tv_status.setText(MainActivity.this
                                            .getResources().getString(
                                                    R.string.str_disconnected));
                                }
                            }
                        }
                    });
                }
            }
        };
        tv_update.start();

        db = new DataHandler(this);

        search_Box = (EditText) findViewById(R.id.search_keywords);
        filter_Type = (Spinner) findViewById(R.id.filter_Type);
        listView = (ListView) findViewById(R.id.mobile_list);
        synch = (Button) findViewById(R.id.seach_button);

        filter_Type.setOnItemSelectedListener(this);

        optionCategories();
        list_Reading_data("","New");

        //connect_ReST_Api();

        synch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //connect_Api();
            }

        });
        search_Box.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                try {

                }catch(RuntimeException ex){

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                try {

                }catch(RuntimeException ex){

                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String synch_status = filter_Type.getSelectedItem().toString();
                    list_Reading_data(s.toString(), synch_status);
                }catch(RuntimeException ex){

                }
            }
        });



        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //////////////////////
                /////DATA PROMPT//////
                //////////////////////
                String[] parts = String.valueOf(((TextView) view).getText()).split(" ")  ;

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_data,  null, false);

                final EditText reading_id = promptsView.findViewById(R.id.reading_id);
                final EditText reading_customer_id = promptsView.findViewById(R.id.reading_customer_id);
                final EditText reading_fullname  = promptsView.findViewById(R.id.reading_fullname);
                final EditText reading_address = promptsView.findViewById(R.id.reading_address);
                final EditText reading_billing_period_id = promptsView.findViewById(R.id.reading_billing_period_id);
                final EditText reading_billing_date = promptsView.findViewById(R.id.reading_billing_date);
                final EditText reading_previous_meter = promptsView.findViewById(R.id.reading_previous_meter);
                final EditText reading_previous_balance = promptsView.findViewById(R.id.reading_previous_balance);
                final EditText reading_current_meter = promptsView.findViewById(R.id.reading_current_meter);
                final EditText reading_current_balance  = promptsView.findViewById(R.id.reading_current_balance);
                final EditText reading_synch_status =  promptsView.findViewById(R.id.reading_synch_status);
                final EditText reading_consumer_type =  promptsView.findViewById(R.id.reading_consumer_type);
                final EditText reading_meter = promptsView.findViewById(R.id.reading_meter_id);
                //update
                final EditText reading_bill_to = promptsView.findViewById(R.id.reading_bill_to);
                final EditText reading_bill_from  = promptsView.findViewById(R.id.reading_bill_from);
                final EditText reading_cashtendered  = promptsView.findViewById(R.id.reading_cashtendered);
                final EditText reading_reciept_no  = promptsView.findViewById(R.id.reading_reciept_no);

                final Button button_Print =  promptsView.findViewById(R.id.btn_Print);
                final Button button_Back =  promptsView.findViewById(R.id.btn_Back);
                final Button button_Capture = promptsView.findViewById(R.id.btn_Capture);
                final Button button_Save = promptsView.findViewById(R.id.btn_Save);
                final Button button_date_from = promptsView.findViewById(R.id.btn_date_from);
                final Button button_date_to = promptsView.findViewById(R.id.btn_date_to);
                final Button button_billing_date = promptsView.findViewById(R.id.btn_billing_to);

                final LinearLayout layout_view_edit = promptsView.findViewById(R.id.layout_base_edit);

                final ImageView dataImg = promptsView.findViewById(R.id.dataView);

                reading_id.setEnabled(false);
                reading_customer_id.setEnabled(false);
                reading_fullname.setEnabled(false);
                reading_address.setEnabled(false);
                reading_billing_period_id.setEnabled(false);
                reading_billing_date.setEnabled(false);
                reading_previous_meter.setEnabled(false);
                reading_previous_balance.setEnabled(false);
                reading_current_balance.setEnabled(false);
                reading_synch_status.setEnabled(false);
                reading_consumer_type.setEnabled(false);
                reading_meter.setEnabled(false);
                reading_bill_from.setEnabled(false);
                reading_bill_to.setEnabled(false);

                reading_id.setTextColor(Color.BLACK);
                reading_customer_id.setTextColor(Color.BLACK);
                reading_fullname.setTextColor(Color.BLACK);
                reading_address.setTextColor(Color.BLACK);
                reading_billing_period_id.setTextColor(Color.BLACK);
                reading_billing_date.setTextColor(Color.BLACK);
                reading_previous_meter.setTextColor(Color.BLACK);
                reading_previous_balance.setTextColor(Color.BLACK);
                reading_current_balance.setTextColor(Color.BLACK);
                reading_synch_status.setTextColor(Color.BLACK);
                reading_consumer_type.setTextColor(Color.BLACK);
                reading_current_meter.setTextColor(Color.BLACK);
                reading_meter.setTextColor(Color.BLACK);
                reading_bill_to.setTextColor(Color.BLACK);
                reading_bill_from.setTextColor(Color.BLACK);
                reading_cashtendered.setTextColor(Color.BLACK);
                reading_reciept_no.setTextColor(Color.BLACK);

                final Reading reading = new Reading();

                reading_id.setText(reading.get_Data_Reading(parts[0]).getId());
                reading_customer_id.setText(reading.get_Data_Reading(parts[0]).getCustomer_id());
                reading_fullname.setText(reading.get_Data_Reading(parts[0]).getFullname());
                reading_address.setText(reading.get_Data_Reading(parts[0]).getLocation());
                reading_billing_period_id.setText(reading.get_Data_Reading(parts[0]).getBilling_period_id());
                reading_billing_date.setText(reading.get_Data_Reading(parts[0]).getBilling_date());
                reading_previous_meter.setText(reading.get_Data_Reading(parts[0]).getPrevious_reading());
                reading_previous_balance.setText(reading.get_Data_Reading(parts[0]).getPrevious_balance());
                reading_current_meter.setText(reading.get_Data_Reading(parts[0]).getCurrent_reading());
                reading_current_balance.setText(reading.get_Data_Reading(parts[0]).getCurrent_balance());
                reading_synch_status.setText(reading.get_Data_Reading(parts[0]).getSynch_status());
                reading_consumer_type.setText(reading.get_Data_Reading(parts[0]).getType());
                reading_meter.setText(reading.get_Data_Reading(parts[0]).getMeter_number());
                reading_bill_to.setText(reading.get_Data_Reading(parts[0]).getBillto());
                reading_bill_from.setText(reading.get_Data_Reading(parts[0]).getBillfrom());
                reading_cashtendered.setText(reading.get_Data_Reading(parts[0]).getCashtendered());
                reading_reciept_no.setText(reading.get_Data_Reading(parts[0]).getRecietno());


                if(reading.get_Data_Reading(parts[0]).getSynch_status().equals("Posted")){
                    layout_view_edit.setVisibility(View.GONE);
                    reading_current_meter.setEnabled(false);
                }

                setReading_id(reading.get_Data_Reading(parts[0]).getId());

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                //////////////////////
                ////Picture File /////
                //////////////////////
                try {

                    File f = new File("/sdcard/water_system/"+ getReading_id() +".png");

                    if(f.exists()) {

                        dataImg.setImageBitmap(decodeSampledBitmapFromFile( f, 100, 100));

                  }else{
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_picture);
                        dataImg.setImageBitmap(bitmap);
                    }
                }
                catch(Exception exception)
                {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_picture);
                    dataImg.setImageBitmap(bitmap);
                }


                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();


                reading_current_meter.addTextChangedListener(new TextWatcher(){
                    public void afterTextChanged(Editable s) {}

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        //Logic Here
                        double cubicMeter;

                        try {

                        cubicMeter = Double.parseDouble(s.toString()) - Double.parseDouble(reading_previous_meter.getText().toString());

                        if (cubicMeter >= 0){

                            Rate rate = new Rate();

                            if (cubicMeter >= 0) {


                                if (cubicMeter <= Double.parseDouble(rate.get_Data_Rate(reading_consumer_type.getText().toString()).getMincubic())){

                                    double current = Double.parseDouble(rate.get_Data_Rate(reading_consumer_type.getText().toString()).getMinrate());
                                    double previous = Double.parseDouble(reading_previous_balance.getText().toString());

                                    reading_current_balance.setText(String.valueOf(current + previous));

                                }else{
                                    double current = cubicMeter * Double.parseDouble(rate.get_Data_Rate(reading_consumer_type.getText().toString()).getPercubic());
                                    double previous = Double.parseDouble(reading_previous_balance.getText().toString());

                                    reading_current_balance.setText(String.valueOf(current + previous));

                                }


                            }else{
                                reading_current_balance.setText("0");
                            }

                        }else{

                        }

                        }catch (Exception ex){
                            //reading_current_balance.setText("0");
                            //reading_current_meter.setText(reading_previous_meter.getText().toString());
                        }
                    }
                });


                button_Capture.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        ////////////////////////
                        /////CAMERA Prompt//////
                        ////////////////////////
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.prompt_capture,null, false);

                        final Button button_new_capture = promptsView.findViewById(R.id.button_new_capture);
                        final Button button_save_capture = promptsView.findViewById(R.id.button_save_capture);


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        // create alert dialog
                        final AlertDialog alertDialog = alertDialogBuilder.create();

                        surfaceView = promptsView.findViewById(R.id.surfaceView);

                        surfaceHolder = surfaceView.getHolder();

                        globaldialog = new ProgressDialog(context);

                        button_save_capture.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //Toast.makeText(getApplicationContext(), "TEST CAPTURE" , Toast.LENGTH_SHORT).show();
                                globaldialog.setMessage("Capturing ...");
                                globaldialog.show();

                                padsAddedPicCallback = getPictureCallback();
                                camera.takePicture(null, null, padsAddedPicCallback);
                            }
                        });

                        button_new_capture.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Camera Starts..." , Toast.LENGTH_SHORT).show();

                                refreshCamera();

                            }
                        });





                        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

                            @Override
                            public void surfaceCreated(SurfaceHolder holder) {
                                try {
                                    // open the camera
                                    camera = Camera.open();
                                } catch (RuntimeException e) {
                                    // check for exceptions
                                    System.err.println(e);
                                    return;
                                }
                                Camera.Parameters param;
                                param = camera.getParameters();

                                // modify parameter
                                param.setPreviewSize(352, 288);
                                camera.setDisplayOrientation(90);
                                camera.setParameters(param);

                                try {
                                    // The Surface has been created, now tell the camera where to draw
                                    // the preview.
                                    camera.setPreviewDisplay(surfaceHolder);
                                    camera.startPreview();
                                } catch (Exception e) {
                                    // check for exceptions
                                    System.err.println(e);
                                    return;
                                }
                            }

                            @Override
                            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                                // Now that the size is known, set up the camera parameters and begin
                                // the preview.
                            }

                            @Override
                            public void surfaceDestroyed(SurfaceHolder holder) {
                                // stop preview and release camera
                                camera.stopPreview();
                                camera.release();
                                camera = null;


                            }
                        });

                        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                        // show it
                        alertDialog.show();

                        alertDialog.setOnKeyListener(new Dialog.OnKeyListener() {

                            @Override
                            public boolean onKey(DialogInterface arg0, int keyCode,
                                                 KeyEvent event) {
                                // TODO Auto-generated method stub
                                if (keyCode == KeyEvent.KEYCODE_BACK) {
                                    //finish();
                                    alertDialog.dismiss();

                                    //Toast.makeText(getApplicationContext(), " TEST " , Toast.LENGTH_LONG).show();

                                    try {

                                        File f = new File("/sdcard/water_system/"+ getReading_id() +".png");

                                        if(f.exists()) {

                                            dataImg.setImageBitmap(decodeSampledBitmapFromFile( f, 100, 100));

                                        }else{
                                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_picture);
                                            dataImg.setImageBitmap(bitmap);
                                        }
                                    }
                                    catch(Exception exception)
                                    {
                                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.default_picture);
                                        dataImg.setImageBitmap(bitmap);
                                    }
                                }
                                return true;
                            }
                        });
                    }
                });

                button_Save.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        double previous = Double.parseDouble(reading_previous_meter.getText().toString());
                        double current = Double.parseDouble(reading_current_meter.getText().toString());

                        if(previous <= current) {

                            Reading reading = new Reading(
                                    reading_id.getText().toString(),
                                    reading_current_meter.getText().toString(),
                                    reading_current_balance.getText().toString(),
                                    "Pending",
                                    reading_cashtendered.getText().toString(),
                                    reading_reciept_no.getText().toString(),
                                    reading_billing_date.getText().toString(),
                                    reading_bill_to.getText().toString()
                            );

                            reading.update_Reading_Current_Balance();

                            list_Reading_data(
                                    search_Box.getText().toString(),
                                    filter_Type.getSelectedItem().toString());

                            Toast.makeText(getApplicationContext(),"Updated ", Toast.LENGTH_LONG  ).show();

                            alertDialog.cancel();
                        }else{
                            Toast.makeText(getApplicationContext(), "Previous Meter : "+
                                    reading_previous_meter.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


                ////////////////////////
                ///////CALENDAR/////////
                ////////////////////////
                button_date_from.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.prompt_date,null, false);
                        final Button button_calendar_save = promptsView.findViewById(R.id.btn_cal_save);
                        final Button button_calendar_cancel = promptsView.findViewById(R.id.btn_cal_cancel);
                        final DatePicker calendar = promptsView.findViewById(R.id.datePicker);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        // create alert dialog
                        final AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                        button_calendar_save.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                reading_bill_from.setText(calendar.getYear() +"-"+(calendar.getMonth()+1)+"-"+calendar.getDayOfMonth() );
                                alertDialog.cancel();
                            }
                        });


                        button_calendar_cancel.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.cancel();
                            }
                        });

                    }
                });


                button_date_to.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.prompt_date,null, false);
                        final Button button_calendar_save = promptsView.findViewById(R.id.btn_cal_save);
                        final Button button_calendar_cancel = promptsView.findViewById(R.id.btn_cal_cancel);
                        final DatePicker calendar = promptsView.findViewById(R.id.datePicker);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        // create alert dialog
                        final AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                        button_calendar_save.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String day =  (((calendar.getDayOfMonth()) / 10) >= 1? String.valueOf(calendar.getDayOfMonth()) :"0"+String.valueOf(calendar.getDayOfMonth()));
                                String month = (((calendar.getMonth()+1) / 10) >= 1? String.valueOf(calendar.getMonth()+1) :"0"+String.valueOf(calendar.getMonth()+1));
                                reading_bill_to.setText(calendar.getYear() +"-"+month+"-"+day);

                                alertDialog.cancel();
                            }
                        });


                        button_calendar_cancel.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.cancel();
                            }
                        });


                    }
                });

                button_billing_date.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.prompt_date,null, false);
                        final Button button_calendar_save = promptsView.findViewById(R.id.btn_cal_save);
                        final Button button_calendar_cancel = promptsView.findViewById(R.id.btn_cal_cancel);
                        final DatePicker calendar = promptsView.findViewById(R.id.datePicker);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        // create alert dialog
                        final AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                        button_calendar_save.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String day =  (((calendar.getDayOfMonth()) / 10) >= 1? String.valueOf(calendar.getDayOfMonth()) :"0"+String.valueOf(calendar.getDayOfMonth()));
                                String month = (((calendar.getMonth()+1) / 10) >= 1? String.valueOf(calendar.getMonth()+1) :"0"+String.valueOf(calendar.getMonth()+1));
                                reading_billing_date.setText(calendar.getYear() +"-"+month+"-"+day);

                                alertDialog.cancel();
                            }
                        });


                        button_calendar_cancel.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.cancel();
                            }
                        });
                    }
                });

                button_Back.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                button_Print.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if( mBTService.getState() == mBTService.STATE_CONNECTED) {


                            double previous = Double.parseDouble(reading_previous_meter.getText().toString());
                            double current = Double.parseDouble(reading_current_meter.getText().toString());

                            if(previous <= current) {
                                Toast.makeText(getApplicationContext(), "Printing...", Toast.LENGTH_LONG).show();
                                Rate rate = new Rate();

                                byte[] bt = new byte[3];
                                bt[0] = 27;
                                bt[1] = 56;
                                bt[2] = 1 / 2;// 1,2//设置字体大小 Set the font size
                                mBTService.write(bt);

                                String cubic_meter_used = String.valueOf(
                                        Double.parseDouble(reading_current_meter.getText().toString()) -
                                        Double.parseDouble(reading_previous_meter.getText().toString())
                                );

                                String total = String.valueOf(
                                        Double.parseDouble(reading_current_balance.getText().toString())-
                                        Double.parseDouble(reading_previous_balance.getText().toString())
                                );

                                String new_bal = String.valueOf(
                                        Double.parseDouble(total) -
                                        Double.parseDouble(reading_cashtendered.getText().toString())
                                );

                                User user = User.getUser_Status("1");

                                String due_days =  rate.get_Data_Rate(reading_consumer_type.getText().toString()).getDuedate();
                                //date
                                String dt = reading_bill_to.getText().toString();  // Start date
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar c = Calendar.getInstance();

                                try {
                                    c.setTime(sdf.parse(dt));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                c.add(Calendar.DATE, Integer.parseInt(due_days));  // number of days to add
                                dt = sdf.format(c.getTime());  // dt is now the new date

                                try {
                                        Report r = new Report(
                                            reading_billing_date.getText().toString(),            //0 billing_date
                                            dt,                                                   //1 due_date
                                            reading_address.getText().toString(),                 //2 location
                                            reading_fullname.getText().toString(),                //3 fullname
                                            reading_meter.getText().toString(),                   //5 meter
                                            reading_bill_from.getText().toString(),               //6 previous_reading_date
                                            reading_previous_meter.getText().toString(),          //7 previous_meter
                                            reading_previous_balance.getText().toString(),        //8 previous_balance
                                            reading_bill_to.getText().toString(),                 //9 current_reading
                                            reading_current_meter.getText().toString(),           //10 current_meter
                                            cubic_meter_used,                                     //11 cubicmeter_used
                                            reading_current_balance.getText().toString(),         //12 current_balance
                                            total,                                                //13 current_total
                                            reading_cashtendered.getText().toString(),            //14 cash_tendered
                                            new_bal,                                              //15 new_balance
                                            user.getFirstname() + " " +user.getLastname(),        //16 reader
                                            reading_reciept_no.getText().toString()               //17 OR
                                    );

                                    //Toast.makeText(getApplicationContext(), "Mess "+
                                    //        reading_cashtendered.getText().toString(), Toast.LENGTH_LONG).show();

                                    /////////////////////////////////////////
                                   mBTService.PrintCharacters(r.message());

                                } catch (Exception ex){
                                    mBTService.PrintCharacters(ex.toString());
                                }


                            }else{
                                Toast.makeText(getApplicationContext(), "Previous Meter : "+
                                        reading_previous_meter.getText().toString(), Toast.LENGTH_LONG).show();
                            }

                        }else if(mBTService.getState() == mBTService.STATE_CONNECTING){

                        }else{
                            Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });


        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "water_system");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            // Do something on success
        } else {
            // Do something else on failure
        }


        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enable :)
                mBluetoothAdapter.enable();

                Toast.makeText(
                        MainActivity.this, "Bluetooth Enable",
                        Toast.LENGTH_LONG).show();
            }
        }

        //----------------------------

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

      //  country_table=(TableLayout)findViewById(R.id.country_table);
      //  fillCountryTable();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_download) {
            api_Download();
        } else if (id == R.id.nav_upload) {

            ArrayList<Reading> list_readings = Reading.get_List_Pending("Pending");
            Iterator iterate = list_readings.iterator();

            if(iterate.hasNext()) {
                Reading iterate_reading = (Reading) iterate.next();

                uploadImage(
                    iterate_reading.getId(),
                    iterate_reading.getBilling_date(),
                    iterate_reading.getCurrent_reading(),
                    iterate_reading.getCurrent_balance(),
                    iterate_reading.getBillto(),
                    iterate_reading.getCashtendered(),
                    iterate_reading.getCustomer_id(),
                    iterate_reading.getRecietno()
               );

                Toast.makeText(MainActivity.this,
                        "\n Uploaded \n" +
                        "\n ID: " + iterate_reading.getId() +
                        "\n Billing Date: " + iterate_reading.getBilling_date() +
                        "\n Current Reading: " + iterate_reading.getCurrent_reading()  +
                        "\n Current Balance: " +iterate_reading.getCurrent_balance() +
                        "\n Bill To: " +iterate_reading.getBillto() +
                        "\n CashTendered: " +iterate_reading.getCashtendered() +
                        "\n Customer ID: " +iterate_reading.getCustomer_id() +
                        "\n Receipt No: " +iterate_reading.getRecietno()
                        , Toast.LENGTH_LONG).show();
            }



            //testupload();
        } else if (id == R.id.nav_info) {
                /////////////////////////
                /////RATE PROMPT/////////
                /////////////////////////

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_rate,   null, false);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);

                country_table= promptsView.findViewById(R.id.country_table);
                fillCountryTable();

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

               // Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_database) {

            /////////////////////////
            /////RESET PROMPT////////
            /////////////////////////
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.prompt_clear,   null, false);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptsView);

            final Button btn_deleteDatabase = promptsView.findViewById(R.id.btn_clear);

            final CheckBox check_New = promptsView.findViewById(R.id.checkBox_New);
            final CheckBox check_Pending = promptsView.findViewById(R.id.checkBox_Pending);
            final CheckBox check_Posted = promptsView.findViewById(R.id.checkBox_Posted);
            final EditText input_keyWord = promptsView.findViewById(R.id.input_keyWord);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


            btn_deleteDatabase.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(input_keyWord.getText().toString().equalsIgnoreCase( "DELETE")) {
                        if (check_New.isChecked()) {
                            Reading r = new Reading("New");
                            r.delete_Reading();
                        }

                        if (check_Pending.isChecked()) {
                            Reading r1 = new Reading("Pending");
                            r1.delete_Reading();
                        }

                        if (check_Posted.isChecked()) {
                            Reading r2 = new Reading("Posted");
                            r2.delete_Reading();
                        }

                        Toast.makeText(getApplicationContext(), "VALID "+ input_keyWord.getText().toString(), Toast.LENGTH_LONG).show();

                        alertDialog.cancel();

                        list_Reading_data(
                                search_Box.getText().toString(),
                                filter_Type.getSelectedItem().toString());

                    }else{
                        Toast.makeText(getApplicationContext(), "INVALID " + input_keyWord.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else if (id == R.id.nav_pair){
            if (!mBTService.IsOpen()) {
                mBTService.OpenDevice();
                //return;
            }
            deviceList.setAdapter(mPairedDevicesArrayAdapter);
            mPairedDevicesArrayAdapter.clear();
            devices = mBTService.GetBondedDevice();
            if (devices.size() > 0) {
                for (BluetoothDevice device : devices) {
                    mPairedDevicesArrayAdapter.add(device.getName() + "\n"
                            + device.getAddress());
                }
            } else {
                String noDevices = MainActivity.this.getResources()
                        .getString(R.string.str_nomatched);
                mPairedDevicesArrayAdapter.add(noDevices);
            }
        } else if (id == R.id.nav_logout) {
            off_flag();

            User user = new User();
            user.update_User_Status();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EX && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            // String[] filePathColumn = { MediaStore.Images.Media.DATA };
            // Cursor cursor = getContentResolver().query(selectedImage,
            // filePathColumn, null, null, null);
            // cursor.moveToFirst();
            // int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            // String picturePath = cursor.getString(columnIndex);
            // picPath = picturePath;
            // // iv.setImageURI(selectedImage);
            // btMap = BitmapFactory.decodeFile(picPath);
            ContentResolver cr = this.getContentResolver();

            try {
                btMap = BitmapFactory.decodeStream(cr
                        .openInputStream(selectedImage));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (btMap.getHeight() > 384) {
                // btMap = BitmapFactory.decodeFile(picPath);
                btMap = resizeImage(btMap, 384, 384);
                iv.setImageBitmap(btMap);

            } else {
                iv.setImageBitmap(btMap);
            }
            // cursor.close();
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            off_flag();
            //System.exit(0);

            super.onBackPressed();
        }

    }


    public void optionCategories(){
        List<String> categories = new ArrayList<String>();
        categories.add("New");
        categories.add("Pending");
        categories.add("Posted");

        ArrayAdapter<String> dataAdapter = new  ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        filter_Type.setAdapter(dataAdapter);
    }

    public void list_Reading_data(String filter, String status_synch){

        Reading reading = new Reading();
        List<String> var = reading.get_List_Data_Reading_Filter(filter, status_synch);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, var);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        listView.setAdapter(dataAdapter);
    }

    //////////////////////////
    ////////Auto Gen//////////
    //////////////////////////
    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String filtered = search_Box.getText().toString();

        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        list_Reading_data(filtered, item);
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item + " : " + content  , Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // open the camera
            camera = Camera.open();


        } catch (RuntimeException e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();

        // modify parameter
        param.setPreviewSize(352, 288);
        camera.setDisplayOrientation(90);
        camera.setParameters(param);
        try {
            // The Surface has been created, now tell the camera where to draw
            // the preview.
            camera.setPreviewDisplay(surfaceHolder);

            camera.startPreview();
        } catch (Exception e) {
            // check for exceptions
            System.err.println(e);
            return;
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        //refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // stop preview and release camera
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    //////////////////////////
    ////////Auto END//////////
    //////////////////////////
    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }

    //public void captureImage(View v) throws IOException {
        //take the picture
    //    padsAddedPicCallback = getPictureCallback();
    //    camera.takePicture(null, null, padsAddedPicCallback);
    //}

    public void api_Download() {

        Api api = Api.getApi("1");

        Config config = new Config();

        config.setUrl_Rate_data(api.getIp());
        config.setUrl_Reading_data(api.getIp());

        User user_active = User.getUser_Status("1");

        Get_Reading get_Reading = new Get_Reading(MainActivity.this);
        get_Reading.execute(config.getUrl_Reading_data()+"?empid="+ user_active.getUser_id());

        Get_Rate get_rate = new Get_Rate(MainActivity.this);
        get_rate.execute(config.getUrl_Rate_data());
    }

    public void off_flag(){
        if (bt_update != null) {
            updateflag = false;
            bt_update = null;
        }
        if (tv_update != null) {
            tvFlag = false;
            tv_update = null;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mBTService != null) {
            mBTService.DisConnected();
            mBTService = null;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void fillCountryTable() {
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        addTableRows(
                "MIN__ RATE",
                "CUBIC MIN",
                "PER__ CUBIC",
                "TYPE",
                dip
        );

        Rate r = new Rate();


            ArrayList<ArrayList<Object>> data = r.get_Rate_Rows();

            for (int current = 0; current < r.get_Rate_Rows().size(); current++) {

                ArrayList<Object> row = data.get(current);

                addTableRows(
                        row.get(2).toString(),
                        row.get(3).toString(),
                        row.get(4).toString(),
                        row.get(0).toString()+" "+row.get(1).toString(),
                        dip
                );
            }
    }

    void addTableRows (String s1, String s2, String s3, String s4,int dip){
        row = new TableRow(this);

        t1 = new TextView(this);
        t2 = new TextView(this);
        t3 = new TextView(this);
        t4 = new TextView(this);

        t1.setText(s1);
        t2.setText(s2);
        t3.setText(s3);
        t4.setText(s4);

        t1.setTypeface(null, 1);
        t2.setTypeface(null, 1);
        t3.setTypeface(null, 1);
        t4.setTypeface(null, 1);

        t1.setWidth(30 * dip);
        t2.setWidth(30 * dip);
        t3.setWidth(70 * dip);
        t4.setWidth(200 * dip);

        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.addView(t4);

        country_table.addView(row, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    //  bitmap image pocesses
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile( File resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resId.getAbsolutePath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(resId.getAbsolutePath(), options);
    }


    private PictureCallback getPictureCallback() {

        //original jpegCallback
        PictureCallback jpegCallback = new PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //FileOutputStream outStream = null;
                try {

                    FileOutputStream outStream = new FileOutputStream(String.format("/sdcard/water_system/" + getReading_id() + ".png"));
                    outStream.write(data);
                    outStream.close();
                    /* end of mode */

                    //Bitmap bitmap = BitmapFactory.decodeFile(outStream.get);

                    Log.d("Log", "  onPictureTaken - wrote bytes: " + data.length);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }

                globaldialog.cancel();

                Toast.makeText(getApplicationContext(), "Reading ID : " + getReading_id() + " Saved", Toast.LENGTH_LONG).show();
                //refreshCamera();
            }
        };
        //end of original jpegcallback
        return jpegCallback;
    }

    public void uploadImage(
            String id,
            String billing_date,
            String current_reading,
            String current_balance,
            String bill_to,
            String cashtendered,
            String customer_id,
            String receipt_no
    ){
        var_id = id;
        var_billing_date = billing_date;
        var_current_reading = current_reading;
        var_current_balance  = current_balance;
        var_bill_to = bill_to;
        var_cashtendered = cashtendered;
        var_customer_id = customer_id;
        var_receipt_no = receipt_no;

        File imgFile = new  File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/water_system/"+id+".png");
        if(imgFile.exists()){
            btmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Toast.makeText(MainActivity.this, id+".png Uploading ...", Toast.LENGTH_LONG).show();
            new Encode_image().execute();
        }else{
            Toast.makeText(MainActivity.this, id+".png Not Uploading ...", Toast.LENGTH_LONG).show();
        }
    }

    private class Encode_image extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            btmp.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            btmp.recycle();

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            makeRequest();
        }
    }

    private void makeRequest() {

        Api api = Api.getApi("1");
        Config config = new Config();
        config.setUrl_Update_Reading(api.getIp());

        com.android.volley.RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, config.getUrl_Update_Reading(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //try {

                            //JSONObject obj = new JSONObject(response);
                            //String status = (String) obj.get("status");

                            //if(status == "success") {

                                Reading reading = new Reading(var_id, "Posted");
                                reading.update_Reading_Status();

                                ArrayList<Reading> list_readings = Reading.get_List_Pending("Pending");
                                Iterator iterate = list_readings.iterator();

                                if (iterate.hasNext()) {

                                    Reading iterate_reading = (Reading) iterate.next();

                                    uploadImage(
                                            iterate_reading.getId(),
                                            iterate_reading.getBilling_date(),
                                            iterate_reading.getCurrent_reading(),
                                            iterate_reading.getCurrent_balance(),
                                            iterate_reading.getBillto(),
                                            iterate_reading.getCashtendered(),
                                            iterate_reading.getCustomer_id(),
                                            iterate_reading.getRecietno()
                                    );

                                    Toast.makeText(MainActivity.this,
                                                    "\n Uploaded \n" +
                                                    "\n ID: " + iterate_reading.getId() +
                                                    "\n Billing Date: " + iterate_reading.getBilling_date() +
                                                    "\n Current Reading: " + iterate_reading.getCurrent_reading() +
                                                    "\n Current Balance: " + iterate_reading.getCurrent_balance() +
                                                    "\n Bill To: " + iterate_reading.getBillto() +
                                                    "\n CashTendered: " + iterate_reading.getCashtendered() +
                                                    "\n Customer ID: " + iterate_reading.getCustomer_id() +
                                                    "\n Receipt No: " + iterate_reading.getRecietno()
                                            ,Toast.LENGTH_LONG).show();
                                }
                            //}else {
                            //    Toast.makeText(MainActivity.this, "\n Upload fail! ", Toast.LENGTH_LONG).show();
                            //}

                        //} catch (JSONException e) {
                        //    Toast.makeText(MainActivity.this, "\n Api parsing fail! ", Toast.LENGTH_LONG).show();
                        //}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Library Error Uploading", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();

                map.put(KEY_IMAGE, encoded_string);
                map.put(KEY_FILENAME, var_id);
                map.put(KEY_ID,var_id);
                map.put(KEY_BILLING_DATE,var_billing_date);
                map.put(KEY_CURRENT_READING,var_current_reading);
                map.put(KEY_CURRENT_BALANCE,var_current_balance);
                map.put(KEY_BILL_TO,var_bill_to);
                map.put(KEY_CASHTENDERED,var_cashtendered);
                map.put(KEY_CUSTOMER,var_customer_id);
                map.put(KEY_RECEIPT,var_receipt_no);

                return map;
            }
        };
        requestQueue.add(request);
    }


}

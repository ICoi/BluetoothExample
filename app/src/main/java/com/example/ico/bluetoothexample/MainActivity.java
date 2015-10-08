package com.example.ico.bluetoothexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final String TAG = "BluetoothService";

    private BluetoothService btService = null;

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create BluetoothService Class
        if (btService == null) {
            btService = new BluetoothService(this, mHandler);
        }

        setOnClickListener();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if(resultCode == Activity.RESULT_OK){
                    // 선택한 기기의 정보를 받아 getDeviceInfo라는 메소드로 전달
                    // getDeviceInfo 메소드는 그 정보를 이용하여 블루투스 연결을 시도
                    btService.getDeviceInfo(data);
                }
                break;
            case REQUEST_ENABLE_BT:
                // when the request to enable Bluetooth returns
                if(resultCode == Activity.RESULT_OK){
                    // Ȯ�� ������ ��
                    // Next Step
                    // Bluetooth 가 켜져있는 경우 - Bluetoothh device를 검색하는 Activity 실행
                    // When bluetooth is tunred on then make device search bluetooth device
                    btService.scanDevice();
                } else{
                    // ��� ������ ��
                    Log.d(TAG, "Bluetooth is not enabled");
                }
                break;
        }
    }

    public void setOnClickListener(){
        Button btn = (Button)findViewById(R.id.btn_connect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btService.getDeviceState()){
                    // ��������� ���� ������ ����� ��
                    btService.enableBluetooth();
                }else{
                    finish();;
                }

            }
        });
    }
}

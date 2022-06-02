package com.pu.a0327interface;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.zxing.integration.android.*;  //以下為手動匯入之函釋庫
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.google.zxing.integration.android.IntentIntegrator;

public class MainActivity extends AppCompatActivity {
    private Button B1,btn_A,btn_b;   //B1為掃碼 btn A為跳頁
    private TextView TV;      //TV為顯示結果的
    private Activity context = this;   //此處是為了掃碼所定義一個參數


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_A = findViewById(R.id.btn_A);
        btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginPage.class);
                startActivity(intent);

            }
        });
        btn_b = findViewById(R.id.btn_B);
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, User_login.class);
                startActivity(intent);

            }
        });
        TV = (TextView) findViewById(R.id.TV);
        B1 = (Button) findViewById(R.id.button);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator Integrator = new IntentIntegrator(context);  //此處為掃描語法
                Integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);//此處為qrcode
                Integrator.setPrompt("掃描二維碼");
                Integrator.setCameraId(0);   //設定使用鏡頭 1為前  0為後
                Integrator.setBeepEnabled(false);  //是否開啟鈴聲
                Integrator.setBarcodeImageEnabled(false);//是否開啟條碼圖像
                Integrator.setOrientationLocked(false);//是否開啟方向鎖定
                Integrator.initiateScan();  //開啟自動掃描
            }
        });
    }

        public void onActivityResult ( int requestCode, int resultCode, Intent intent){
            IntentResult SR = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

            if (SR != null) {
                if (SR.getContents() != null) {
                    String SC = SR.getContents();
                    if (!SC.equals("")) {
                        TV.setText(SC.toString());
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, intent);
                TV.setText("發生錯誤");
            }


        }

}
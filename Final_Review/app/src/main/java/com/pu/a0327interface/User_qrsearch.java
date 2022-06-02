package com.pu.a0327interface;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Toast;

import com.google.zxing.integration.android.*;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class User_qrsearch extends AppCompatActivity {
    private ImageButton B1; //B1為掃碼
    private TextView TV;      //TV為顯示結果的
    private Activity context = this;   //此處是為了掃碼所定義一個參數
    String produce_id,batch;
    Button btn_todetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_1);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        TV = (TextView) findViewById(R.id.qrcode_text1);
        B1 = (ImageButton) findViewById(R.id.btn_qrcode);

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
        btn_todetail=findViewById(R.id.btn_todetail);
        btn_todetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(User_qrsearch.this,User_detail.class);
                startActivity(intent2);

            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult SR = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        String SC = SR.getContents();
        try {
            //qrcode查詢到資料
            TV.setText(SC.toString());

            String[] datas = SC.split("\\&");
            produce_id=datas[0];
            batch=datas[1];
            Log.d("json-produce", produce_id);
            Log.d("json-batch",batch);

            SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);
            pref.edit()
                    .putString("produce",produce_id)
                    .commit();

            pref.edit()
                    .putString("batch", batch)
                    .commit();


            Toast.makeText(getApplicationContext(), "點擊下方查看資訊", Toast.LENGTH_LONG).show();
            //Log.d("json", SC.toString());
        }catch (Exception e){
            TV.setText("發生錯誤");
            Toast.makeText(getApplicationContext(),"發生錯誤",Toast.LENGTH_LONG).show();
            Log.d("json",""+e);
        }
        super.onActivityResult(requestCode, resultCode, intent);

    }

}



package com.pu.a0327interface;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.zxing.integration.android.*;  //以下為手動匯入之函釋庫
import com.google.zxing.integration.android.IntentIntegrator;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton B1, btn_b,Btn_detail,btn_track,btn_home,btn_history,btn_userinfo,btn_search,btn_qrcode; //B1為掃碼 btn_b為跳到登入
    private TextView TV;      //TV為顯示結果的
    private Activity context = this;   //此處是為了掃碼所定義一個參數
    String user_id,company_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        List<String> images = new ArrayList<>();
        images.add("https://i.ytimg.com/vi/8j20ELhpXYk/maxresdefault.jpg");
        images.add("http://epaper.cts.com.tw/epaper_img/2016-10-21/resize_1_2309.jpg");
        images.add("https://www.taiwanhot.net/wp-content/uploads/2019/11/5dd239cbda8a7.jpg?mode=all");
        images.add("https://cdn1.cybassets.com/media/W1siZiIsIjE2OTMzL2F0dGFjaGVkX3Bob3Rvcy8xNjI1MjE2MTMzX2Jsb2NrX2h0bWxfNDk3MDUuanBlZyJdLFsicCIsInRodW1iIiwiMjA0OHgyMDQ4Il1d.jpeg?sha=0ee70391cc149837");
        Banner banner = findViewById(R.id.banner);
//設定你的Banner想要什麼樣式的，我選的樣式是圖片下面有圓點點，如果要其他的就到他的Git自己去看囉
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//把想要呈現的圖片設定到banner
        banner.setImages(images)
                .setImageLoader(new GlideImageLoader())

                //設定每張圖片要呈現的時間
                .setDelayTime(3000)
                .start();
    

        //設定一般用戶，廠商id=0;
        SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);
        pref.edit()
                .putString("user", "0")
                .putString("company", "0")
                .commit();

        user_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("user", "");
        company_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("company", "");
       /* btn_A = findViewById(R.id.btn_A);
        btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginPage.class);
                startActivity(intent);

            }
        });  */
        Btn_detail = findViewById(R.id.btn_detial);
        Btn_detail .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, User_detail.class);
                startActivity(intent);

            }
        });

        btn_search = findViewById(R.id.btn_search);
        btn_search .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, User_search.class);
                startActivity(intent);

            }
        });
        btn_b = findViewById(R.id.btn_b);
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, User_login.class);
                startActivity(intent);

            }
        });
        btn_qrcode  =findViewById(R.id.btn_qrcode);
        btn_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, User_qrsearch.class);
                startActivity(intent);

            }
        });

        btn_history=(ImageButton)findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.result_background)
                        .setTitle("註冊會員以獲取更多功能")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("cancel",null).create()
                        .show();
            }
        });
       /* B1 = (ImageButton) findViewById(R.id.btn_list2);
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
        });*/
        btn_track=(ImageButton)findViewById(R.id.btn_track);
        btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.result_background)
                        .setTitle("註冊會員以獲取更多功能")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("cancel",null).create()
                        .show();
            }
        });

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        btn_history=(ImageButton)findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.result_background)
                        .setTitle("註冊會員以獲取更多功能")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("cancel",null).create()
                        .show();
            }
        });
        btn_userinfo=(ImageButton)findViewById(R.id.btn_userinfo);
        btn_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.result_background)
                        .setTitle("註冊會員以獲取更多功能")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("cancel",null).create()
                        .show();
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //
        SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);
        pref.edit()
                .putString("user", "0")
                .putString("company", "0")
                .commit();
        user_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("user", "");
        company_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("company", "");

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult SR = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (SR != null) {
            if (SR.getContents() != null) {
                String SC = SR.getContents();
                if (!SC.equals("")) {
                    TV.setText(SC.toString());
                    //
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
            TV.setText("發生錯誤");
        }
    }


}








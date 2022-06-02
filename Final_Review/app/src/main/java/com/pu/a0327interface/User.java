package com.pu.a0327interface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

public class User extends AppCompatActivity {
   // LinearLayout LL_search;
    private ImageButton btn_b,Btn_detail,btn_track,btn_history,btn_userinfo,btn_bbb,btn_usersearch,btn_qrcode; //B1為掃碼 btn_b為跳到登入
    private Activity context = this;   //此處是為了掃碼所定義一個參數
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
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

        //取得使用者id
        String user_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("user", "");
        Log.d("jsonget",user_id);



        btn_qrcode  =findViewById(R.id.btn_qrcode);
        btn_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, User_qrsearch.class);
                startActivity(intent);

            }
        });
        Btn_detail = findViewById(R.id.btn_detial);
        Btn_detail .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, User_detail.class);
                startActivity(intent);
            }
        });
        btn_usersearch = findViewById(R.id.btn_search);
        btn_usersearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(User.this,User_search.class);
                startActivity(intent);
            }
        });
        btn_bbb = findViewById(R.id.btn_bbb);
        btn_bbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(User.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以關掉所要到的介面中間的activity
                startActivity(intent);
            }
        });

        btn_track=findViewById(R.id.btn_track);
        btn_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, User_track.class);
                startActivity(intent);
            }
        });


        btn_history = findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, History_page.class);
                startActivity(intent);
            }
        });
        btn_userinfo = findViewById(R.id.btn_userinfo);
        btn_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, Userinfo_page.class);
                startActivity(intent);
            }
        });
    }
}


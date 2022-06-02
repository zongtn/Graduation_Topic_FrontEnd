package com.pu.a0327interface;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Company_qrcode_create  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_qrcode_create);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態
        EditText Ed_qrcode;
        Button button2;
        button2 = (Button)findViewById(R.id.btn_todetail);
        Ed_qrcode = (EditText)findViewById(R.id.qrcode_text1);

        String name,batch,produce_id;
        //取得商品id
        name= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("p_name", "");
        batch= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("batch", "");
        produce_id=getSharedPreferences("prefdata",MODE_PRIVATE)
                .getString("c_produce","");
        Ed_qrcode.setText(produce_id+"&"+batch);
        Log.d("商品id: ",name);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    BarcodeEncoder BE = new BarcodeEncoder();
                    //Bitmap bitmap = BE.encodeBitmap(Ed_qrcode.getText().toString(), BarcodeFormat.QR_CODE,250,250);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    String content=Ed_qrcode.getText().toString();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(
                            new String(content.getBytes("UTF-8"),"ISO-8859-1")
                            , BarcodeFormat.QR_CODE
                            , 250
                            , 250);
                    ImageView IV = (ImageView)findViewById(R.id.B1);
                    IV.setImageBitmap(bitmap);
                }catch(Exception e){}
            }
        });
    }

}

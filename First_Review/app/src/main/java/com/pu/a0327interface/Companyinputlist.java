package com.pu.a0327interface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Companyinputlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companyinputlist);
        TextView ed_produce_name,ed_produce_origin,ed_produce_ingredient,ed_produce_additive;
        ed_produce_name=findViewById(R.id.ed_produce_name);
        ed_produce_origin=findViewById(R.id.ed_produce_origin);
        ed_produce_ingredient=findViewById(R.id.ed_produce_ingredient);
        ed_produce_additive=findViewById(R.id.ed_produce_additive);
        Button btn_output = findViewById(R.id.btn_output);
        Intent intent = this.getIntent();
        String get_company = intent.getStringExtra("company_name");
        //Toast.makeText(getApplicationContext(),company,Toast.LENGTH_LONG).show();
        btn_output.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name=ed_produce_name.getText().toString();
                String origin=ed_produce_origin.getText().toString();;
                String ingredient=ed_produce_ingredient.getText().toString();
                String additive=ed_produce_additive.getText().toString();;
                if(name.matches("") || origin.matches("")|| ingredient.matches("")|| additive.matches("")){
                    Toast.makeText(getApplicationContext(),"不能有空",Toast.LENGTH_LONG).show();
                }
                else {
                    HttpThread myThread = new HttpThread();
                    //設定變數值
                    myThread.company = get_company;
                    myThread.name = name;
                    myThread.origin = origin;
                    myThread.ingredient = ingredient;
                    myThread.additive = additive;
                    //開始執行緒
                    myThread.start();
                    //Log.d("produce_name",""+ed_produce_name.getText().toString());
                    Intent intent = new Intent(Companyinputlist.this, company_product_list.class);
                    finish();
                }
            }
        });

    }

    class HttpThread extends Thread {
        //宣告變數並指定預設值
        public String company="";
        public String name = "" ;
        public String origin = "";
        public String ingredient = "";
        public String additive = "";
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            //宣告一個新的Bundle物件，Bundle可以在多個執行緒之間傳遞訊息
            Bundle myBundle = new Bundle();
            try {
                Log.d("produce_name",""+name+" "+origin+" "+ingredient+" "+additive+"");
                HttpClient client = new DefaultHttpClient();
                URI website = new URI("http://10.0.2.2/projects/eatsafe.php");
                //指定POST模式
                HttpPost request = new HttpPost();
                //POST傳值必須將key、值加入List<NameValuePair>
                List<NameValuePair> parmas = new ArrayList<NameValuePair>();
                //逐一增加POST所需的Key、值
                parmas.add(new BasicNameValuePair("produce_company",company));
                parmas.add(new BasicNameValuePair("produce_name",name));
                parmas.add(new BasicNameValuePair("produce_origin",origin));
                parmas.add(new BasicNameValuePair("produce_ingredient",ingredient));
                parmas.add(new BasicNameValuePair("produce_additive",additive));
                //宣告UrlEncodedFormEntity來編碼POST，指定使用UTF-8
                UrlEncodedFormEntity env = new UrlEncodedFormEntity(parmas, HTTP.UTF_8);
                request.setURI(website);
                //設定POST的List
                request.setEntity(env);
                HttpResponse response = client.execute(request);
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    myBundle.putString("response", EntityUtils.toString(resEntity));
                }else{
                    myBundle.putString("response","Nothing");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //msg.getData會傳回Bundle，Bundle類別可以由getString(<KEY_NAME>)取出指定KEY_NAME的值
            //ed.setText(msg.getData().getString("response"));
        }
    };
}


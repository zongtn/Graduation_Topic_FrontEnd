package com.pu.a0327interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Companyinputlist3 extends AppCompatActivity {

    String urlpath=server.urlpath;
    EditText ed_add1,ed_add2,ed_add3,ed_add4;
    String add1,add2,add3,add4;
    ArrayList<String> add_list=new ArrayList<>();
    String inputlist3=",";//要記錄下的商品資訊

    String company_id;//公司id
    String name,origin,totalweight;//商品資訊
    String inputlist1;//成分
    String weight,quantity,calories,protein,fat,saturatedfat,transfat,sugar,na;//營養標示
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companyinputlist3);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

//公司id
        company_id = getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("company", "");
        Log.d("input company",company_id);
//商品資訊第0頁
        name = getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("name", "");
        totalweight = getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("totalweight", "");
        origin = getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("origin", "");
        Log.d("inputlist0", name+","+totalweight+","+origin);
//商品成分材料第1頁
        inputlist1 = getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("inputlist1", "");
        Log.d("inputlist1成分原材料",inputlist1);

//商品營養標示 第二頁
        Intent intent = this.getIntent();
        //Log.d("jsondata",name+" "+origin);
        weight=intent.getStringExtra("weight");
        quantity=intent.getStringExtra("quantity");
        calories=intent.getStringExtra("calories");
        protein=intent.getStringExtra("protein");
        fat=intent.getStringExtra("fat");
        saturatedfat=intent.getStringExtra("saturatedfat");
        transfat=intent.getStringExtra("transfat");
        sugar=intent.getStringExtra("sugar");
        na=intent.getStringExtra("na");
        Log.d("inputlist2營養成分:",weight+quantity+calories+protein+fat+saturatedfat+transfat+sugar+na);
//此頁的添加劑

        ed_add1=findViewById(R.id.ed_add1);
        ed_add2=findViewById(R.id.ed_add2);
        ed_add3=findViewById(R.id.ed_add3);
        ed_add4=findViewById(R.id.ed_add4);




        Button btn_toinput2=findViewById(R.id.btn_toinput2);
        btn_toinput2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Companyinputlist3.this,Companyinputlist2.class);
                startActivity(intent);
            }
        });

        Button btn_ok=findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputlist3=",";//要記錄下的商品資訊
                add_list.clear();
                add1 = ed_add1.getText().toString();
                add2 = ed_add2.getText().toString();
                add3 = ed_add3.getText().toString();
                add4 = ed_add4.getText().toString();
                add_list.add(add1);
                add_list.add(add2);
                add_list.add(add3);
                add_list.add(add4);

                int count=0;
                for(int i=0;i< add_list.size();i++){
                    String item=add_list.get(i).toString();
                    if(!item.matches("")){
                        count++;
                        if(count==1){
                            inputlist3 = inputlist3.substring(0, inputlist3.length()-1);
                        }
                        inputlist3=inputlist3+item+",";
                        Log.d("input"+i, "0");
                    }
                }
                inputlist3 = inputlist3.substring(0, inputlist3.length()-1);
                Log.d("input inputlist3", inputlist3);

                DownloadAsyncTask dTask = new DownloadAsyncTask();
                dTask.execute(urlpath+"company_input_produce.php", "...", "...");
            }
        });
    }
    class DownloadAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            //String url = urls[0];
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoInput(true);
                connection.setUseCaches(false);// 忽略快取

                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("company_id=").append(URLEncoder.encode(company_id, "UTF-8")).append("&");
//商品資訊
                stringBuilder.append("produce_name=").append(URLEncoder.encode(name, "UTF-8")).append("&");
                stringBuilder.append("produce_origin=").append(URLEncoder.encode(origin, "UTF-8")).append("&");
                stringBuilder.append("produce_weight=").append(URLEncoder.encode(totalweight, "UTF-8")).append("&");
//成分
                stringBuilder.append("produce_ingreds=").append(URLEncoder.encode(inputlist1, "UTF-8")).append("&");
//營養標示
                stringBuilder.append("nut_weight=").append(URLEncoder.encode(weight, "UTF-8")).append("&");
                stringBuilder.append("nut_quantity=").append(URLEncoder.encode(quantity, "UTF-8")).append("&");
                stringBuilder.append("nut_calories=").append(URLEncoder.encode(calories, "UTF-8")).append("&");
                stringBuilder.append("nut_protein=").append(URLEncoder.encode(protein, "UTF-8")).append("&");
                stringBuilder.append("nut_fat=").append(URLEncoder.encode(fat, "UTF-8")).append("&");
                stringBuilder.append("nut_saturatedfat=").append(URLEncoder.encode(saturatedfat, "UTF-8")).append("&");
                stringBuilder.append("nut_transfat=").append(URLEncoder.encode(transfat, "UTF-8")).append("&");
                stringBuilder.append("nut_sugar=").append(URLEncoder.encode(sugar, "UTF-8")).append("&");
                stringBuilder.append("nut_na=").append(URLEncoder.encode(na, "UTF-8")).append("&");
//添加劑
                stringBuilder.append("produce_adds=").append(URLEncoder.encode(inputlist3, "UTF-8")).append("&");


                dataOutputStream.writeBytes(stringBuilder.toString());
                dataOutputStream.close();

                String result="";
                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    // 當正確響應時處理資料
                    Log.d("TAG002",""+connection.getResponseCode());
                    StringBuffer response = new StringBuffer();
                    String line;
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(
                            connection.getInputStream(),
                            "utf-8"));
                    // 處理響應流，必須與伺服器響應流輸出的編碼一致
                    while (null != (line = responseReader.readLine())) {
                        response.append(line);
                        result += (line + "\n");
                    }
                    responseReader.close();
                }return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("json input",s);
            try {
                JSONObject alldata = new JSONObject(s);
                String input= alldata.getString("input");
                if(input.matches("ok")){
                    Intent intent = new Intent(Companyinputlist3.this, company_product_list.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Log.d("jsonlkdj","ok");
                    Toast.makeText(getApplicationContext(),"商品新增成功",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
package com.pu.a0327interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.net.URL;
import java.net.URLEncoder;

public class Companyinputlist extends AppCompatActivity {

    String urlpath=server.urlpath;
    String name,origin,totalweight,inputlist,company_id;
    TextView ed_name,ed_origin,ed_totalweight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companyinputlist);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        company_id = getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("company", "");

        ed_name = findViewById(R.id.ed_name);
        ed_origin = findViewById(R.id.ed_origin);
        ed_totalweight=findViewById(R.id.ed_totalweight);


        Button btn_tolist = findViewById(R.id.btn_tolist);
        btn_tolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Companyinputlist.this, company_product_list.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以關掉所要到的介面中間的activity
                startActivity(intent);
            }
        });

        Button btn_toinput1 = findViewById(R.id.btn_toinput1);
        btn_toinput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取得欄位資料:商品名稱、總重。產地
                name=ed_name.getText().toString();
                totalweight=ed_totalweight.getText().toString();
                origin=ed_origin.getText().toString();

                if(name.matches("") || origin.matches("")||origin.matches("")){
                    Toast.makeText(getApplicationContext(),"以上欄位均為必填",Toast.LENGTH_LONG).show();
                }else {
                    inputlist = "produce_name=" + name + "&produce_origin=" + origin + "&produce_weight=" + totalweight;
                    Log.d("json", inputlist);
                    DownloadAsyncTask dTask = new DownloadAsyncTask();
                    dTask.execute(urlpath+"company_input_checkproduce.php", "...", "...");
                }

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
                String parms="company_id="+company_id+"&produce_name="+ URLEncoder.encode(name, "UTF-8");

                dataOutputStream.writeBytes(parms.toString());
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
            //Log.d("json input",s);
            try {
                JSONObject alldata = new JSONObject(s);
                String produce= alldata.getString("produce");
                if(produce.matches("no")){
                    Log.d("json inputno,3",s);

                    Intent intent = new Intent(Companyinputlist.this, Companyinputlist1.class);
                    //用sharepreferences 將商品資訊記錄下來
                    SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);

                    pref.edit()
                            .putString("inputlist", inputlist)
                            .putString("name", name)
                            .putString("totalweight", totalweight)
                            .putString("origin", origin)
                            .commit();
                    startActivity(intent);

                }else if(produce.matches("already")){
                    Log.d("json input",s);//在資料庫裡同公司有同名商品
                    Toast.makeText(getApplicationContext(),"此商品已填寫",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}

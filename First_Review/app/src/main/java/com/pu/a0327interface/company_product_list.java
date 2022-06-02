package com.pu.a0327interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;

public class company_product_list extends AppCompatActivity {
    String account;
    String company;
    String company_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_product_list);
        Button bt1 = findViewById(R.id.bt1);

        Intent intent = this.getIntent();
        account = intent.getStringExtra("account");
        company_name=intent.getStringExtra("company_name");
        DownloadAsyncTask dTask = new DownloadAsyncTask();
        dTask.execute("http://10.0.2.2/projects/produce_list.php", "...", "...");
       // Toast.makeText(getApplicationContext(),company_name+"",Toast.LENGTH_LONG).show();
        bt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(company_product_list.this,Companyinputlist.class);
                intent.putExtra("company_name",company_name);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),company_name,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DownloadAsyncTask dTask = new DownloadAsyncTask();
        dTask.execute("http://10.0.2.2/projects/produce_list.php", "...", "...");
    }

    class DownloadAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            //String url = urls[0];
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                //connection.setRequestProperty("authentication", MainActivity.Authentication);
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoInput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);// 忽略快取

                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                String parms="company_account="+account;
                dataOutputStream.writeBytes(parms);
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
            ArrayList<String> producelist=new ArrayList<>();
           try {
                JSONArray allproducelist = new JSONArray(s);
                for(int i=0; i<allproducelist.length();i++){
                    JSONObject produce = allproducelist.getJSONObject(i);
                    String produce_name= produce.getString("produce_name");
                    String produce_origin= produce.getString("produce_origin");
                    company=produce.getString("produce_company");
                    producelist.add("商品名稱:"+produce_name);
                }
                ListView lv = findViewById(R.id.listview);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        company_product_list.this,
                        android.R.layout.simple_list_item_1,
                        producelist
                );
                lv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
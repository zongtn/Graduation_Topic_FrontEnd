package com.pu.a0327interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    String urlpath=server.urlpath;
    String company_id;
    ArrayList<String> produce_id_list=new ArrayList<>();
    ArrayList<String> batch_list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_product_list);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        Button btn_toinput = findViewById(R.id.btn_toinput);

        company_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("company", "");


        DownloadAsyncTask dTask = new DownloadAsyncTask();
        dTask.execute(urlpath+"produce_list.php?company_id="+company_id, "...", "...");
        //Toast.makeText(getApplicationContext(),company_id+"",Toast.LENGTH_LONG).show();

        btn_toinput.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(company_product_list.this,Companyinputlist.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        DownloadAsyncTask dTask = new DownloadAsyncTask();
        dTask.execute(urlpath+"produce_list.php?company_id="+company_id, "...", "...");
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
                connection.setUseCaches(false);// 忽略快取

                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                String parms="company_id="+company_id;
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
            Log.d("json",""+s);
            ArrayList<String> produce_list=new ArrayList<>();
            try {
                JSONArray allproducelist = new JSONArray(s);
                for(int i=0; i<allproducelist.length();i++){
                    JSONObject produce = allproducelist.getJSONObject(i);
                    String produce_name= produce.getString("produce_name");
                    String produce_id= produce.getString("produce_id");
                    String batch_id= produce.getString("batch_id");


                    produce_list.add("商品名稱:"+produce_name);
                    produce_id_list.add(produce_id);
                    batch_list.add(batch_id);
                }
                ListView lv = findViewById(R.id.listview);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        company_product_list.this,
                        android.R.layout.simple_list_item_1,
                        produce_list
                );
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(onClickListView);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(company_product_list.this, Company_detail.class);
            //用sharepreferences 將用戶的登陸後的id記錄下來
            SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);
            pref.edit()
                    .putString("c_produce", produce_id_list.get(position).toString())
                    .commit();
            pref.edit()
                    .putString("batch", batch_list.get(position).toString())
                    .commit();
            Log.d("json",produce_id_list.get(position).toString()+" "+batch_list.get(position).toString());
            startActivity(intent);
        }
    };

}
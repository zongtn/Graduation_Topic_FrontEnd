package com.pu.a0327interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Userinfo_page extends AppCompatActivity {

    private ImageButton btn_home, btn_history, btn_userinfo;
    String user_id;
    EditText ed_phone,ed_name,ed_email,ed_account,ed_password;
    Button btn_fix,btn_alter;
    String account,password,email,phone,name;
    String urlpath=server.urlpath;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態


        ed_account=findViewById(R.id.ed_account);
        ed_email=findViewById(R.id.ed_email);
        ed_name=findViewById(R.id.ed_name);
        ed_password=findViewById(R.id.ed_password);
        ed_phone=findViewById(R.id.ed_phone);

        ed_account.setInputType(InputType.TYPE_NULL);
        ed_email.setInputType(InputType.TYPE_NULL);
        ed_name.setInputType(InputType.TYPE_NULL);
        ed_password.setInputType(InputType.TYPE_NULL);
        ed_phone.setInputType(InputType.TYPE_NULL);



        //取得使用者id
        user_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("user", "");
        Log.d("jsonget",user_id);
        //取得用戶資料
        DownloadAsyncTask dTask = new DownloadAsyncTask();
        dTask.execute(urlpath+"user_info.php", "...", "...");

        btn_alter=findViewById(R.id.btn_alter);
        btn_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_account.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_email.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_name.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_password.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_phone.setInputType(InputType.TYPE_CLASS_TEXT);
                Toast.makeText(getApplicationContext(),"已可修改用戶資訊!",Toast.LENGTH_LONG).show();
            }
        });

        //btn_fix確認修改
        btn_fix=findViewById(R.id.btn_fix);
        btn_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account=ed_account.getText().toString();
                password=ed_password.getText().toString();
                email=ed_email.getText().toString();
                phone=ed_phone.getText().toString();
                name=ed_name.getText().toString();
                Log.d("jsondata818",account +" "+password+" "+phone+" "+email+" "+name);


                DownloadAsyncTask2 dTask = new DownloadAsyncTask2();
                dTask.execute(urlpath+"user_info_update.php", "...", "...");


            }
        });

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Userinfo_page.this, User.class);
                startActivity(intent);

            }
        });
        btn_history = findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Userinfo_page.this, History_page.class);
                startActivity(intent);

            }
        });
        btn_userinfo = findViewById(R.id.btn_userinfo);
        btn_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Userinfo_page.this, Userinfo_page.class);
                startActivity(intent);

            }
        });
    }
    //送出修改
    class DownloadAsyncTask2 extends AsyncTask<String, Void, String> {
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
                stringBuilder.append("user_id=").append(URLEncoder.encode(user_id, "UTF-8")).append("&");
                //商品資訊
                stringBuilder.append("user_account=").append(URLEncoder.encode(account, "UTF-8")).append("&");
                stringBuilder.append("user_password=").append(URLEncoder.encode(password, "UTF-8")).append("&");
                stringBuilder.append("user_email=").append(URLEncoder.encode(email, "UTF-8")).append("&");
                //成分
                stringBuilder.append("user_phone=").append(URLEncoder.encode(phone, "UTF-8")).append("&");
                //營養標示
                stringBuilder.append("user_name=").append(URLEncoder.encode(name, "UTF-8")).append("&");


                dataOutputStream.writeBytes(stringBuilder.toString());
                dataOutputStream.close();

                String result = "";
                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    // 當正確響應時處理資料
                    Log.d("TAG002", "" + connection.getResponseCode());
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
                }
                return result;
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
            Log.d("json input", s);
            try {
                JSONObject alldata = new JSONObject(s);
                String update = alldata.getString("update");
                if (update=="true") {
                    Log.d("json input", "修改完成");
                    Intent intent = new Intent();
                    intent.setClass(Userinfo_page.this, User.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以關掉所要到的介面中間的activity
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
                String parms="user_id="+user_id;
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
            Log.d("jsoninfo",s);
            try {
                JSONArray data = new JSONArray(s);
                for(int i=0; i<data.length();i++) {
                    JSONObject item = data.getJSONObject(i);
                    //商品名稱、商品來源、公司名稱
                    String user_account = item.getString("user_account");
                    String user_password = item.getString("user_password");
                    String user_email = item.getString("user_email");
                    String user_phone = item.getString("user_phone");
                    String user_name = item.getString("user_name");

                    ed_account.setText(user_account);
                    ed_password.setText(user_password);
                    ed_email.setText(user_email);
                    ed_phone.setText(user_phone);
                    ed_name.setText(user_name);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.pu.a0327interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginPage extends AppCompatActivity {
    TextView ed_text;
    String input_account,input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        EditText ed_password,ed_account;
        ed_account=findViewById(R.id.ed_account);
        ed_password=findViewById(R.id.ed_password);
        Button btn_login = findViewById(R.id.btn_login);
        ed_text=findViewById(R.id.ed_text);

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                input_account=ed_account.getText().toString();
                input_password=ed_password.getText().toString();
                if(input_account.matches("") || input_password.matches("")){
                    ed_text.setText("帳號密碼不能為空");
                }
                else{
                    ed_text.setText("");
                    DownloadAsyncTask dTask = new DownloadAsyncTask();
                    dTask.execute("http://10.0.2.2/projects/logintest.php", "...", "...");
                }
            }
        });

        Button btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this,company_register.class);
                startActivity(intent);
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
                //connection.setRequestProperty("authentication", MainActivity.Authentication);
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoInput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);// 忽略快取

                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                String parms="company_account="+input_account+"&company_password="+input_password;
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
/*
            try {
                JSONArray alldata=new JSONArray(s);
                for(int i=0; i<alldata.length();i++){
                    JSONObject data = alldata.getJSONObject(i);
                    String jsoncompany= data.getString("company_name");
                    String jsonacc=data.getString("company_account");
                    String jsonpass=data.getString("company_password");
                    Log.d("tag",""+550);
                    if(jsonacc.matches(input_account)&&jsonpass.matches(input_password)) {
                        Intent intent = new Intent(LoginPage.this, company_product_list.class);
                        intent.putExtra("account", jsonacc);
                        intent.putExtra("company_name", jsoncompany);
                        startActivity(intent);
                        //Log.d("tag", "00" + jsoncompany + " " + jsonacc + " " + jsonpass);
                        Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "登入失敗", Toast.LENGTH_LONG).show();
                        Log.d("tag",""+55);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            try {
                JSONObject alldata = new JSONObject(s);
                String login= alldata.getString("login");
                JSONObject companyname = alldata.getJSONObject("name");
                String getname= companyname.getString("company_name");
                if(login=="true"){
                   Intent intent = new Intent(LoginPage.this, company_product_list.class);
                    intent.putExtra("account", input_account);
                    intent.putExtra("company_name", getname);
                    startActivity(intent);
                    Log.d("tag",""+login+" "+companyname);
                    Toast.makeText(getApplicationContext(),"登入成功"+getname,Toast.LENGTH_LONG).show();
                }else if(login=="false"){
                    Toast.makeText(getApplicationContext(),"帳號或密碼錯誤",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
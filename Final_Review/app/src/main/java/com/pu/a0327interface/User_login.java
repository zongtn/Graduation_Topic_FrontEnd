package com.pu.a0327interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class User_login extends AppCompatActivity {

    String urlpath=server.urlpath;
    Button btn_login,btn_signup,btn_A;
    String input_account,input_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        EditText ed_password,ed_account;
        ed_account=findViewById(R.id.ed_account);
        ed_password=findViewById(R.id.ed_password);
        btn_login=findViewById(R.id.btn_search);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(User_login.this,User.class);
               // startActivity(intent);
                input_account=ed_account.getText().toString();
                input_password=ed_password.getText().toString();
                if(input_account.matches("") || input_password.matches("")){
                    Toast.makeText(getApplicationContext(),"帳號與密碼不能為空",Toast.LENGTH_LONG).show();
                }
                else {
                    DownloadAsyncTask dTask = new DownloadAsyncTask();
                    dTask.execute(urlpath+"user_login.php", "...", "...");
                }
            }
        });
        btn_signup=findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_login.this,User_signup.class);
                startActivity(intent);
            }
        });
        btn_A = findViewById(R.id.btn_company_A);
        btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_login.this, LoginPage.class);
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
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoInput(true);
                connection.setUseCaches(false);// 忽略快取

                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                String parms="user_account="+input_account+"&user_password="+input_password;
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

            try {
                JSONObject alldata = new JSONObject(s);
                String login= alldata.getString("login");
                if(login=="true"){
                    JSONObject companyname = alldata.getJSONObject("name");
                    String getname= companyname.getString("user_name");
                    String getid= companyname.getString("user_id");
                    Intent intent = new Intent(User_login.this, User.class);

                    //用sharepreferences 將用戶的登陸後的id記錄下來
                    SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);
                    pref.edit()
                            .putString("user", getid)
                            .commit();
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(),"登入成功",Toast.LENGTH_LONG).show();
                }else if(login=="false"){
                    Toast.makeText(getApplicationContext(),"帳號或密碼錯誤",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
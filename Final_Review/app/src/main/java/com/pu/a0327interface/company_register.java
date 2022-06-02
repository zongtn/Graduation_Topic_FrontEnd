package com.pu.a0327interface;

import androidx.appcompat.app.AppCompatActivity;

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

public class company_register extends AppCompatActivity {

    String account,password,email,name,phone;
    String urlpath=server.urlpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        EditText ed_account=findViewById(R.id.ed_account);
        EditText ed_password=findViewById(R.id.ed_password);
        EditText ed_email=findViewById(R.id.ed_email);
        EditText ed_name=findViewById(R.id.ed_name);
        EditText ed_phone=findViewById(R.id.ed_phone);

        Button btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                account=ed_account.getText().toString();
                password=ed_password.getText().toString();
                name=ed_name.getText().toString();
                email=ed_email.getText().toString();
                phone=ed_phone.getText().toString();

                if(account.matches("") || password.matches("") || name.matches("")  || email.matches("") || phone.matches("")){
                    Toast.makeText(getApplicationContext(),"欄位不能為空",Toast.LENGTH_LONG).show();
                }else {
                    DownloadAsyncTask dTask = new DownloadAsyncTask();
                    dTask.execute(urlpath+"company_signup.php", "...", "...");
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
                connection.setRequestProperty("charset","utf-8");

                OutputStream outputStream = connection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                String parms="company_name="+name+"&company_account="+account+"&company_password="+password+"&company_email="+email+"&company_phone="+phone;
                //dataOutputStream.writeBytes(parms);
                dataOutputStream.write(parms.toString().getBytes());
                dataOutputStream.close();
                String result="";
                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    // 當正確響應時處理資料
                    Log.d("TAG002",""+connection.getResponseCode());

                    StringBuffer response = new StringBuffer();
                    String line;
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(
                            connection.getInputStream(),
                            "UTF-8"));
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
            Log.d("json",s);
            try {
                JSONObject alldata = new JSONObject(s);
                String signup=alldata.getString("signup");
                // Log.d("json",signup);
                if(signup.equals("true")){
                    ///Log.d("tag",""+login+" "+companyname);
                    Toast.makeText(getApplicationContext(),"註冊成功",Toast.LENGTH_LONG).show();
                    Log.d("json","true");
                    finish();
                }else if(signup.equals("already")){
                    Toast.makeText(getApplicationContext(),"帳號或公司名稱已被使用",Toast.LENGTH_LONG).show();
                    Log.d("json","already");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
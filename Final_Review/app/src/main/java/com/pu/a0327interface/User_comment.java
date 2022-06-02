package com.pu.a0327interface;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class User_comment extends AppCompatActivity {

    String urlpath=server.urlpath;

    String comment;
    String user_id,produce_id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comment);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        EditText ed_comment;
        ImageButton btn_sent;
        //取得商品id
        produce_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("produce", "");
        user_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("user", "");

        Log.d("user_comment:",produce_id+" userid "+user_id);
        //顯示detail訊息
        DownloadAsyncTask dTask = new DownloadAsyncTask();
        dTask.execute(urlpath+"user_comment.php?produce_id="+produce_id);

        ed_comment = findViewById(R.id.ed_comment);

        btn_sent = findViewById(R.id.btn_sent);
        btn_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment=ed_comment.getText().toString();
                //Log.d("json",comment);
                //Toast.makeText(getApplicationContext(),produce_id+" userid "+user_id+" "+comment,Toast.LENGTH_LONG).show();
                //新增訊息
                DownloadAsyncTasks dTask = new DownloadAsyncTasks();
                dTask.execute(urlpath+"user_add_comment.php", "...", "...");
                DownloadAsyncTask dTasks = new DownloadAsyncTask();
                dTasks.execute(urlpath+"user_comment.php?produce_id="+produce_id);
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
                connection.setRequestMethod("GET");
                //connection.setRequestProperty("authentication", MainActivity.Authentication);
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoInput(true);
                InputStream inputStream = connection.getInputStream();
                int status = connection.getResponseCode();
                String result = "";
                if (inputStream != null) {
                    InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader in = new BufferedReader(reader);
                    String line = "";
                    while ((line = in.readLine()) != null) {
                        result += (line + "\n");
                    }
                } else {
                    result = "Did not work!";
                }
                connection.disconnect();
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

            Log.d("json",""+s);
            ArrayList<String> message_list=new ArrayList<>();
            try {
                JSONArray allproducelist = new JSONArray(s);
                for(int i=0; i<allproducelist.length();i++){
                    JSONObject produce = allproducelist.getJSONObject(i);
                    String message_con= produce.getString("message_con");
                    message_list.add(message_con);
                }
                ListView lv = findViewById(R.id.comment_board);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        User_comment.this,
                        android.R.layout.simple_list_item_1,
                        message_list
                );
                lv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    class DownloadAsyncTasks extends AsyncTask<String, Void, String> {

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
                stringBuilder.append("produce_id=").append(URLEncoder.encode(produce_id, "UTF-8")).append("&");
                stringBuilder.append("message_con=").append(URLEncoder.encode(comment, "UTF-8")).append("&");


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

                    Log.d("jsonlkdj","ok");
                    Toast.makeText(getApplicationContext(),"商品新增成功",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

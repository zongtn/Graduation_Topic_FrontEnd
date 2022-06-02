package com.pu.a0327interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class History_page  extends AppCompatActivity {


    String urlpath=server.urlpath;
    //顯示的資料列
    ArrayList<String> list=new ArrayList<>();
    //商品名稱、商品來源，商品公司
    ArrayList<String> produce_id_list=new ArrayList<>();
    ArrayList<String> produce_name_list=new ArrayList<>();
    ArrayList<String> produce_origin_list=new ArrayList<>();
    ArrayList<String> company_name_list=new ArrayList<>();
    ArrayList<String> batch_id_list=new ArrayList<>();
    private ImageButton btn_home, btn_history, btn_userinfo;
    String user_id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        user_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("user", "");

        DownloadAsyncTasks dTask = new DownloadAsyncTasks();
        dTask.execute(urlpath+"user_history_list.php?user_id="+user_id);

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History_page.this, User.class);
                startActivity(intent);

            }
        });
        btn_history = findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History_page.this, History_page.class);
                startActivity(intent);

            }
        });
        btn_userinfo = findViewById(R.id.btn_userinfo);
        btn_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History_page.this, Userinfo_page.class);
                startActivity(intent);

            }
        });
    }
    //畫面回到track重新顯示追蹤資料
    @Override
    protected void onRestart() {
        super.onRestart();
        DownloadAsyncTasks dTask = new DownloadAsyncTasks();
        dTask.execute(urlpath+"user_history_list.php?user_id=" + user_id);
    }

        class DownloadAsyncTasks extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            //String url = urls[0];
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                //connection.setRequestProperty("authentication", MainActivity.Authentication);
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
            list.clear();
            produce_id_list.clear();
            produce_name_list.clear();
            produce_origin_list.clear();
            company_name_list.clear();
            batch_id_list.clear();
            Log.d("json",""+s);
            try {
                JSONArray data=new JSONArray(s);
                for(int i=0; i<data.length();i++){
                    JSONObject item = data.getJSONObject(i);
                    //商品名稱、商品來源、公司名稱
                    String produce_name= item.getString("produce_name");
                    String produce_origin= item.getString("produce_origin");
                    String company_name= item.getString("company_name");
                    String produce_id= item.getString("produce_id");
                    String history_time= item.getString("history_time");
                    String batch_id=item.getString("batch_id");
                    // Log.d("json",company_name+""+produce_name);
                    Log.d("json1",""+produce_name+produce_origin+company_name+produce_id);
                    list.add(produce_name +company_name+" , "+history_time);
                    produce_id_list.add(produce_id);
                    produce_name_list.add(produce_name);
                    produce_origin_list.add(produce_origin);
                    company_name_list.add(company_name);
                    batch_id_list.add(batch_id);
                }

                ListView lv = findViewById(R.id.listview);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        History_page.this,
                        android.R.layout.simple_list_item_1,
                        list
                );
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(onClickListView);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    //listview 監聽器事件
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(History_page.this, User_detail.class);

            //用sharepreferences 將用戶的登陸後的id記錄下來
            SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);
            pref.edit()
                    .putString("produce", produce_id_list.get(position).toString())
                    .commit();
            pref.edit()
                    .putString("batch", batch_id_list.get(position).toString())
                    .commit();
            startActivity(intent);
        }
    };
}

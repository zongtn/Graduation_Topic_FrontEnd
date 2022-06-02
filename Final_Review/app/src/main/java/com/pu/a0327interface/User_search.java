package com.pu.a0327interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class User_search extends AppCompatActivity {
    private ImageButton B1;

    String urlpath=server.urlpath;
    //搜尋後顯示的資料列
    ArrayList<String> list=new ArrayList<>();
    //商品id，商品名稱，商品來源，商品公司
    ArrayList<String> produce_id_list=new ArrayList<>();
    ArrayList<String> produce_name_list=new ArrayList<>();
    ArrayList<String> produce_origin_list=new ArrayList<>();
    ArrayList<String> company_name_list=new ArrayList<>();
    ArrayList<String> batch_list=new ArrayList<>();
    //用戶有無追蹤
    //ArrayList<String> user_follow_list=new ArrayList<>();
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        user_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("user", "");


        EditText ed_search=findViewById(R.id.ed_search);
        Button btn_all=findViewById(R.id.btn_all);
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search_text=ed_search.getText().toString();
                DownloadAsyncTasks dTask = new DownloadAsyncTasks();
                dTask.execute(urlpath+"user_search.php?search_by=all&search_key="+search_text+"&user_id="+user_id);
            }
        });
        Button btn_company=findViewById(R.id.btn_company);
        btn_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_text=ed_search.getText().toString();
                DownloadAsyncTasks dTask = new DownloadAsyncTasks();
                dTask.execute(urlpath+"user_search.php?search_by=produce_company&search_key="+search_text+"&user_id="+user_id);
            }
        });
        Button btn_origin=findViewById(R.id.btn_origin);
        btn_origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_text=ed_search.getText().toString();
                DownloadAsyncTasks dTask = new DownloadAsyncTasks();
                dTask.execute(urlpath+"user_search.php?search_by=produce_name&search_key="+search_text+"&user_id="+user_id);
            }
        });

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
        //

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("json",""+s);
            list.clear();
            produce_id_list.clear();
            produce_name_list.clear();
            produce_origin_list.clear();
            company_name_list.clear();
            batch_list.clear();
            //user_follow_list.clear();
            try {
                JSONObject alldata = new JSONObject(s);
                String search= alldata.getString("search");
                if(search=="true"){
                    String datas = alldata.getString("data");
                    JSONArray data=new JSONArray(datas);
                    for(int i=0; i<data.length();i++){
                        JSONObject item = data.getJSONObject(i);
                        //商品名稱、商品來源、公司名稱
                        String produce_name= item.getString("produce_name");
                        String produce_origin= item.getString("produce_origin");
                        String company_name= item.getString("company_name");
                        String produce_id= item.getString("produce_id");
                        String batch_id= item.getString("batch_id");
                        //String follow_time= item.getString("follow_time");


                        //判段並顯示最新的batch 資料
                        if(produce_id_list.contains(produce_id)) {
                            Log.d("json", "有" + produce_id+":"+batch_id);

                        }else{
                            list.add(produce_name + company_name);
                            produce_id_list.add(produce_id);
                            produce_name_list.add(produce_name);
                            produce_origin_list.add(produce_origin);
                            company_name_list.add(company_name);
                            batch_list.add(batch_id);
                        }
                    }
                    ListView lv = findViewById(R.id.listview);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            User_search.this,
                            android.R.layout.simple_list_item_1,
                            list
                    );
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(onClickListView);
                }else if(search=="false"){
                    Toast.makeText(getApplicationContext(),"查無此資料",Toast.LENGTH_LONG).show();
                    list.clear();
                    ListView lv = findViewById(R.id.listview);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            User_search.this,
                            android.R.layout.simple_list_item_1,
                            list
                    );
                    lv.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    //listview 監聽器事件
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.d("jsonname",""+produce_name_list.get(position).toString());
            Log.d("jsonorigin",""+produce_origin_list.get(position).toString());
            Log.d("jsoncompany",""+company_name_list.get(position).toString());
            Log.d("jsonbatch",""+batch_list.get(position).toString());
            Intent intent = new Intent(User_search.this, User_detail.class);

            //用sharepreferences 將用戶的登陸後的id記錄下來
            SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);
            pref.edit()
                    .putString("produce", produce_id_list.get(position).toString())
                    .commit();

            pref.edit()
                    .putString("batch", batch_list.get(position).toString())
                    .commit();

            startActivity(intent);

        }
    };
}
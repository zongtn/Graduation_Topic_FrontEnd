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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.like.LikeButton;
import com.like.OnLikeListener;

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

public class User_detail extends AppCompatActivity {

    String urlpath=server.urlpath;
    String user_id,produce_id;
    String company_name,produce_name,produce_origin,follow_time;
    String nutrition_weight,nutrition_quantity,nutrition_calories,nutrition_protein,nutrition_fat,nutrition_saturated,nutrition_trans,nutrition_sugar,nutrition_na;
    String ingredient,additive;
    TextView tv_produce,tv_company,tv_origin;
    TextView tv_weight,tv_quantity,tv_calories,tv_protein,tv_fat,tv_saturatedfat,tv_transfat,tv_sugar,tv_na;
    TextView tv_ingreds,tv_adds;
    LikeButton btn_like;
    ImageButton btn_comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        tv_produce=findViewById(R.id.tv_produce);
        tv_company=findViewById(R.id.tv_company);
        tv_origin=findViewById(R.id.tv_origin);

        tv_weight=findViewById(R.id.tv_weight);
        tv_quantity=findViewById(R.id.tv_quantity);
        tv_calories=findViewById(R.id.tv_calories);
        tv_protein=findViewById(R.id.tv_protein);
        tv_fat=findViewById(R.id.tv_fat);
        tv_saturatedfat=findViewById(R.id.tv_saturatedfat);
        tv_transfat=findViewById(R.id.tv_transfat);
        tv_sugar=findViewById(R.id.tv_sugar);
        tv_na=findViewById(R.id.tv_na);

        tv_ingreds=findViewById(R.id.tv_ingreds);
        tv_adds=findViewById(R.id.tv_adds);

        btn_like=findViewById(R.id.btn_like);

        //取得使用者id
        String user_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("user", "");

        //隱藏愛心
        if(user_id.matches("0")) {
            Log.d("detail0", user_id);
            btn_like.setVisibility(View.INVISIBLE);

        }
        //取得商品id,批次id
        String produce_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("produce", "");
        String batch_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("batch", "");

        Log.d("detail","user_id="+user_id+"&produce_id="+produce_id+"&batch_id="+batch_id);
        //顯示detail訊息
        DownloadAsyncTask3 dTask = new DownloadAsyncTask3();
        dTask.execute(urlpath+"user_detail.php?user_id="+user_id+"&produce_id="+produce_id+"&batch_id="+batch_id);


        btn_like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                //典籍追蹤
               // Toast.makeText(getApplicationContext(),user_id+"like"+produce_id,Toast.LENGTH_LONG).show();
                DownloadAsyncTask dTask = new DownloadAsyncTask();
                dTask.execute(urlpath+"user_follow.php?user_id="+user_id+"&produce_id="+produce_id, "...", "...");
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                //典籍取消追蹤
                //Toast.makeText(getApplicationContext(),"un",Toast.LENGTH_LONG).show();
                DownloadAsyncTasks dTask = new DownloadAsyncTasks();
                dTask.execute(urlpath+"user_unfollow.php?user_id="+user_id+"&produce_id="+produce_id, "...", "...");
            }
        });
        btn_comment = findViewById(R.id.btn_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_detail.this, User_comment.class);
                startActivity(intent);

            }
        });

    }


    //like
    class DownloadAsyncTask extends AsyncTask<String, Void, String> {

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
            Log.d("tag", "" + s);
        }
    }
    //unlike
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
            Log.d("tag", "" + s);
        }
    }
    //顯示detail資訊
    class DownloadAsyncTask3 extends AsyncTask<String, Void, String> {

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

            Log.d("json1",""+s);

            try {
                JSONArray data=new JSONArray(s);
                for(int i=0; i<data.length();i++){
                    JSONObject item = data.getJSONObject(i);
                    //商品名稱、商品來源、公司名稱
                    follow_time= item.getString("follow_time");
                    company_name= item.getString("company_name");
                    produce_name= item.getString("produce_name");
                    produce_origin= item.getString("produce_origin");
                    tv_produce.setText(produce_name);
                    tv_company.setText(company_name);
                    tv_origin.setText(produce_origin);

                    nutrition_weight=item.getString("nutrition_weight");
                    nutrition_quantity=item.getString("nutrition_quantity");
                    nutrition_calories=item.getString("nutrition_calories");
                    nutrition_protein=item.getString("nutrition_protein");
                    nutrition_fat=item.getString("nutrition_fat-interview");
                    nutrition_saturated=item.getString("nutrition_saturated-fat-interview");
                    nutrition_trans=item.getString("nutrition_trans-lipid-interview");
                    nutrition_sugar=item.getString("nutrition_sugar");
                    nutrition_na=item.getString("nutrition_na");
                    tv_weight.setText(nutrition_weight);
                    tv_quantity.setText(nutrition_quantity);
                    tv_calories.setText(nutrition_calories);
                    tv_protein.setText(nutrition_protein);
                    tv_fat.setText(nutrition_fat);
                    tv_saturatedfat.setText(nutrition_saturated);
                    tv_transfat.setText(nutrition_trans);
                    tv_sugar.setText(nutrition_sugar);
                    tv_na.setText(nutrition_na);

                    additive=item.getString("additive");
                    ingredient=item.getString("ingredient");
                    tv_ingreds.setText(additive);
                    tv_adds.setText(ingredient);

                    //愛心設定
                    if(follow_time.matches("null") ){
                        Log.d("json","null");
                        btn_like.setLiked(false);
                    }else {
                        btn_like.setLiked(true);
                    }
                    Log.d("json",company_name+" "+produce_name+" "+follow_time+" "+produce_origin);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}


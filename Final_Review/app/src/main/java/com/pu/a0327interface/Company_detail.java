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

public class Company_detail extends AppCompatActivity {
    private ImageButton btn_qrcode_create,btn_datafix;  //btn_datafix尚未撰寫 此button 用以更改資料
    private Button  btn_fixdata_access;

    String urlpath=server.urlpath;
    String produce_id;

    EditText ed_name,ed_totalweight,ed_origin;//商品資訊
    String name,totalweight,origin;

    EditText ed_weight,ed_quantity,ed_calories,ed_protein,ed_fat,ed_saturatedfat,ed_transfat,ed_sugar,ed_na;//營養標示
    String weight,quantity,calories,protein,fat,saturatedfat,transfat,sugar,na;

    EditText ed_ingred1,ed_ingred2,ed_ingred3,ed_ingred4;//成分
    String ingreds;
    String ingred1,ingred2,ingred3,ingred4;
    ArrayList<String> ingred_list=new ArrayList<>();
    String inputlist1="";//要記錄下的商品資訊



    EditText ed_add1,ed_add2,ed_add3,ed_add4;//添加劑
    String adds;
    String add1,add2,add3,add4;
    ArrayList<String> add_list=new ArrayList<>();
    String inputlist3="";//要記錄下的商品資訊

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        //取得商品id
        produce_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("c_produce", "");
        String batch_id= getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("batch", "");

        Log.d("商品id: ",produce_id+batch_id);

        //顯示detail訊息
        DownloadAsyncTask dTask = new DownloadAsyncTask();
        dTask.execute(urlpath+"company_detail.php?produce_id="+produce_id+"&batch_id="+batch_id);

        ed_name=findViewById(R.id.ed_name);
        ed_totalweight=findViewById(R.id.ed_totalweight);
        ed_origin=findViewById(R.id.ed_origin);

        ed_ingred1=findViewById(R.id.ed_ingred1);
        ed_ingred2=findViewById(R.id.ed_ingred2);
        ed_ingred3=findViewById(R.id.ed_ingred3);
        ed_ingred4=findViewById(R.id.ed_ingred4);

        ed_weight=findViewById(R.id.ed_weight);
        ed_quantity=findViewById(R.id.ed_quantity);
        ed_calories=findViewById(R.id.ed_calories);
        ed_protein=findViewById(R.id.ed_protein);
        ed_fat=findViewById(R.id.ed_fat);
        ed_saturatedfat=findViewById(R.id.ed_saturatedfat);
        ed_transfat=findViewById(R.id.ed_transfat);
        ed_sugar=findViewById(R.id.ed_sugar);
        ed_na=findViewById(R.id.ed_na);


        ed_add1=findViewById(R.id.ed_add1);
        ed_add2=findViewById(R.id.ed_add2);
        ed_add3=findViewById(R.id.ed_add3);
        ed_add4=findViewById(R.id.ed_add4);

        ed_name.setInputType(InputType.TYPE_NULL);
        ed_totalweight.setInputType(InputType.TYPE_NULL);
        ed_origin.setInputType(InputType.TYPE_NULL);

        ed_weight.setInputType(InputType.TYPE_NULL);
        ed_quantity.setInputType(InputType.TYPE_NULL);
        ed_calories.setInputType(InputType.TYPE_NULL);
        ed_protein.setInputType(InputType.TYPE_NULL);
        ed_fat.setInputType(InputType.TYPE_NULL);
        ed_saturatedfat.setInputType(InputType.TYPE_NULL);
        ed_transfat.setInputType(InputType.TYPE_NULL);
        ed_sugar.setInputType(InputType.TYPE_NULL);
        ed_na.setInputType(InputType.TYPE_NULL);

        ed_ingred1.setInputType(InputType.TYPE_NULL);
        ed_ingred2.setInputType(InputType.TYPE_NULL);
        ed_ingred3.setInputType(InputType.TYPE_NULL);
        ed_ingred4.setInputType(InputType.TYPE_NULL);

        ed_add1.setInputType(InputType.TYPE_NULL);
        ed_add2.setInputType(InputType.TYPE_NULL);
        ed_add3.setInputType(InputType.TYPE_NULL);
        ed_add4.setInputType(InputType.TYPE_NULL);

        btn_datafix = findViewById(R.id.btn_datafix);
        btn_datafix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"已可修改產品資訊!",Toast.LENGTH_LONG).show();
                ed_name.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_totalweight.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_origin.setInputType(InputType.TYPE_CLASS_TEXT);

                ed_weight.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_quantity.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_calories.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_protein.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_fat.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_saturatedfat.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_transfat.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_sugar.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_na.setInputType(InputType.TYPE_CLASS_TEXT);

                ed_ingred1.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_ingred2.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_ingred3.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_ingred4.setInputType(InputType.TYPE_CLASS_TEXT);

                ed_add1.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_add2.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_add3.setInputType(InputType.TYPE_CLASS_TEXT);
                ed_add4.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });



        btn_qrcode_create = findViewById(R.id.btn_qrcode_create);
        btn_qrcode_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Company_detail.this, Company_qrcode_create.class);
                String p_name=ed_name.getText().toString();
                SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);
                pref.edit()
                        .putString("p_name", p_name)
                        .commit();

                startActivity(intent);

            }
        });
//送出資料
        btn_fixdata_access = findViewById(R.id.btn_fixdata_access);
        btn_fixdata_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//商品資訊
                name = ed_name.getText().toString();
                origin=ed_origin.getText().toString();
                totalweight=ed_totalweight.getText().toString();
//營養標示
                weight=ed_weight.getText().toString();
                quantity=ed_quantity.getText().toString();
                calories=ed_calories.getText().toString();
                protein=ed_protein.getText().toString();
                fat=ed_fat.getText().toString();
                saturatedfat=ed_saturatedfat.getText().toString();
                transfat=ed_transfat.getText().toString();
                sugar=ed_sugar.getText().toString();
                na=ed_na.getText().toString();

//成分
                inputlist1=",";
                ingred_list.clear();
                ingred1 = ed_ingred1.getText().toString();
                ingred2 = ed_ingred2.getText().toString();
                ingred3 = ed_ingred3.getText().toString();
                ingred4 = ed_ingred4.getText().toString();
                ingred_list.add(ingred1);
                ingred_list.add(ingred2);
                ingred_list.add(ingred3);
                ingred_list.add(ingred4);
                int count=0;
                for(int i=0;i< ingred_list.size();i++){
                    String item=ingred_list.get(i).toString();
                    if(!item.matches("")){
                        count++;
                        if(count==1){
                            inputlist1 = inputlist1.substring(0, inputlist1.length()-1);
                        }
                        inputlist1=inputlist1+item+",";

                    }
                }
                inputlist1 = inputlist1.substring(0, inputlist1.length()-1);
                Log.d("ingredinput", ""+inputlist1);
//添加劑
                inputlist3=",";
                add_list.clear();
                add1 = ed_add1.getText().toString();
                add2 = ed_add2.getText().toString();
                add3 = ed_add3.getText().toString();
                add4 = ed_add4.getText().toString();
                add_list.add(add1);
                add_list.add(add2);
                add_list.add(add3);
                add_list.add(add4);
                count=0;
                for(int i=0;i< add_list.size();i++){
                    String item=add_list.get(i).toString();
                    if(!item.matches("")){
                        count++;
                        if(count==1){
                            inputlist3 = inputlist3.substring(0, inputlist3.length()-1);
                        }
                        inputlist3=inputlist3+item+",";
                        Log.d("addinput"+i, "0");
                    }
                }
                inputlist3 = inputlist3.substring(0, inputlist3.length()-1);
                Log.d("ingredinput", ""+inputlist3);
                DownloadAsyncTask2 dTask = new DownloadAsyncTask2();
                dTask.execute(urlpath+"company_update_produce.php", "...", "...");




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
            try {
                JSONArray data=new JSONArray(s);
                for(int i=0; i<data.length();i++) {
                    JSONObject item = data.getJSONObject(i);
                    name = item.getString("produce_name");
                    totalweight = item.getString("produce_weight");
                    origin = item.getString("produce_origin");
                    //商品資訊
                    ed_name.setText(name);
                    ed_totalweight.setText(totalweight);
                    ed_origin.setText(origin);

                    //商品營養標示
                    weight = item.getString("nutrition_weight");
                    quantity = item.getString("nutrition_quantity");
                    calories = item.getString("nutrition_calories");
                    protein = item.getString("nutrition_protein");
                    fat = item.getString("nutrition_fat-interview");
                    saturatedfat = item.getString("nutrition_saturated-fat-interview");
                    transfat = item.getString("nutrition_trans-lipid-interview");
                    sugar = item.getString("nutrition_sugar");
                    na = item.getString("nutrition_na");
                    if(weight=="null") {
                        ed_weight.setText("");
                        ed_quantity.setText("");
                        ed_calories.setText("");
                        ed_protein.setText("");
                        ed_fat.setText("");
                        ed_saturatedfat.setText("");
                        ed_transfat.setText("");
                        ed_sugar.setText("");
                        ed_na.setText("");
                    }else {
                        ed_weight.setText(weight);
                        ed_quantity.setText(quantity);
                        ed_calories.setText(calories);
                        ed_protein.setText(protein);
                        ed_fat.setText(fat);
                        ed_saturatedfat.setText(saturatedfat);
                        ed_transfat.setText(transfat);
                        ed_sugar.setText(sugar);
                        ed_na.setText(na);
                    }

                    //成分
                    ingreds = item.getString("ingredient");
                    if(ingreds=="null"){
                        ed_ingred1.setText("");
                    }else{
                        String str = ingreds;
                        String[] datas = str.split("\\,");
                        for (int j=0;j<datas.length;j++) {
                            switch(j) {
                                case 0:
                                    ed_ingred1.setText(datas[j]);
                                    continue;
                                case 1:
                                    ed_ingred2.setText(datas[j]);
                                    continue;
                                case 2:
                                    ed_ingred3.setText(datas[j]);
                                    continue;
                                case 3:
                                    ed_ingred4.setText(datas[j]);
                                    continue;

                            }
                        }

                    }

                    //添加劑
                    adds = item.getString("additive");
                    if(adds=="null"){
                        ed_add1.setText("");

                    }else{
                        String str = adds;
                        String[] datas = str.split("\\,");
                        for (int j=0;j<datas.length;j++) {
                            switch(j) {
                                case 0:
                                    ed_add1.setText(datas[j]);
                                    continue;
                                case 1:
                                    ed_add2.setText(datas[j]);
                                    continue;
                                case 2:
                                    ed_add3.setText(datas[j]);
                                    continue;
                                case 3:
                                    ed_add4.setText(datas[j]);
                                    continue;

                            }
                        }
                    }

                }
            }catch (Exception e){

            }
        }
    }

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
                stringBuilder.append("produce_id=").append(URLEncoder.encode(produce_id, "UTF-8")).append("&");
//商品資訊
                stringBuilder.append("produce_name=").append(URLEncoder.encode(name, "UTF-8")).append("&");
                stringBuilder.append("produce_origin=").append(URLEncoder.encode(origin, "UTF-8")).append("&");
                stringBuilder.append("produce_weight=").append(URLEncoder.encode(totalweight, "UTF-8")).append("&");

//成分
                stringBuilder.append("produce_ingreds=").append(URLEncoder.encode(inputlist1, "UTF-8")).append("&");
//營養標示
                stringBuilder.append("nut_weight=").append(URLEncoder.encode(weight, "UTF-8")).append("&");
                stringBuilder.append("nut_quantity=").append(URLEncoder.encode(quantity, "UTF-8")).append("&");
                stringBuilder.append("nut_calories=").append(URLEncoder.encode(calories, "UTF-8")).append("&");
                stringBuilder.append("nut_protein=").append(URLEncoder.encode(protein, "UTF-8")).append("&");
                stringBuilder.append("nut_fat=").append(URLEncoder.encode(fat, "UTF-8")).append("&");
                stringBuilder.append("nut_saturatedfat=").append(URLEncoder.encode(saturatedfat, "UTF-8")).append("&");
                stringBuilder.append("nut_transfat=").append(URLEncoder.encode(transfat, "UTF-8")).append("&");
                stringBuilder.append("nut_sugar=").append(URLEncoder.encode(sugar, "UTF-8")).append("&");
                stringBuilder.append("nut_na=").append(URLEncoder.encode(na, "UTF-8")).append("&");
//添加劑
                stringBuilder.append("produce_adds=").append(URLEncoder.encode(inputlist3, "UTF-8")).append("&");





                dataOutputStream.writeBytes(stringBuilder.toString());
                //dataOutputStream.flush();
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
                String input= alldata.getString("update");
                if(input.matches("true")){
                    Intent intent = new Intent(Company_detail.this, company_product_list.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Log.d("jsonlkdj","ok");
                    Toast.makeText(getApplicationContext(),"商品修改成功",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}


package com.pu.a0327interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Companyinputlist1 extends AppCompatActivity {

    EditText ed_ingred1,ed_ingred2,ed_ingred3,ed_ingred4;
    String ingred1,ingred2,ingred3,ingred4;

    ArrayList<String> ingred_list=new ArrayList<>();
    String inputlist1="";//要記錄下的商品資訊


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companyinputlist1);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態


        ed_ingred1=findViewById(R.id.ed_ingred1);
        ed_ingred2=findViewById(R.id.ed_ingred2);
        ed_ingred3=findViewById(R.id.ed_ingred3);
        ed_ingred4=findViewById(R.id.ed_ingred4);



        Button btn_toinput = findViewById(R.id.btn_toinput);   //ok的id保持不變
        btn_toinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Companyinputlist1.this, Companyinputlist.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以關掉所要到的介面中間的activity
                startActivity(intent);

            }
        });

        Button btn_toinput2 = findViewById(R.id.btn_toinput2);  //本來為2  這裡改為toinput1
        btn_toinput2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputlist1=",";//要記錄下的商品資訊
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
                        Log.d("input"+i, "0");
                    }
                }
                inputlist1 = inputlist1.substring(0, inputlist1.length()-1);
                Log.d("input inputlist1", inputlist1);

                //Log.d("input array", String.valueOf(ingred_list));
                Intent intent = new Intent(Companyinputlist1.this, Companyinputlist2.class);
                SharedPreferences pref = getSharedPreferences("prefdata", MODE_PRIVATE);
                pref.edit()
                        .putString("inputlist1", inputlist1)
                        .commit();
                startActivity(intent);
            }
        });

    }
}
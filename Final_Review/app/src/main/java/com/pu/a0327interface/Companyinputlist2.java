package com.pu.a0327interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Companyinputlist2 extends AppCompatActivity {

    String inputlist,inputlist1;

    String weight,quantity,calories,protein,fat,saturatedfat,transfat,sugar,na;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companyinputlist2);
        //getSupportActionBar().hide(); //隱藏標題
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN); //隱藏狀態

        //取得inputlist,inputlist1資訊
  /*      inputlist = getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("name", "");
        Log.d("input to 1",inputlist);
        inputlist1 = getSharedPreferences("prefdata", MODE_PRIVATE)
                .getString("inputlist1", "");
        Log.d("input 1 to 2",inputlist1);
*/

        TextView ed_weight = findViewById(R.id.ed_weight);
        TextView ed_quantity = findViewById(R.id.ed_quantity);
        TextView ed_calories = findViewById(R.id.ed_calories);
        TextView ed_protein = findViewById(R.id.ed_protein);
        TextView ed_fat = findViewById(R.id.ed_fat);
        TextView ed_saturatedfat = findViewById(R.id.ed_saturatedfat);
        TextView ed_transfat = findViewById(R.id.ed_transfat);
        TextView ed_sugar = findViewById(R.id.ed_sugar);
        TextView ed_na = findViewById(R.id.ed_na);

        Button btn_toinput1=findViewById(R.id.btn_toinput1);
        btn_toinput1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Companyinputlist2.this,Companyinputlist1.class);
                startActivity(intent);
            }
        });

        Button btn_toinput3=findViewById(R.id.btn_toinput3);
        btn_toinput3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight=ed_weight.getText().toString();
                quantity=ed_quantity.getText().toString();
                calories=ed_calories.getText().toString();
                protein=ed_protein.getText().toString();
                fat=ed_fat.getText().toString();
                saturatedfat=ed_saturatedfat.getText().toString();
                transfat=ed_transfat.getText().toString();
                sugar=ed_sugar.getText().toString();
                na=ed_na.getText().toString();

                if(weight.matches("") || quantity.matches("")||calories.matches("")||
                        protein.matches("") || fat.matches("")||saturatedfat.matches("")||
                        transfat.matches("") || sugar.matches("")||na.matches("")){
                    Toast.makeText(getApplicationContext(),"以上欄位均為必填",Toast.LENGTH_LONG).show();
                }else {
                    //Log.d("input 2 to 3", weight + quantity + calories + protein + fat + saturatedfat + transfat + sugar + na);
                    Intent intent = new Intent(Companyinputlist2.this, Companyinputlist3.class);
                    intent.putExtra("weight", weight);
                    intent.putExtra("quantity", quantity);
                    intent.putExtra("calories", calories);
                    intent.putExtra("protein", protein);
                    intent.putExtra("fat", fat);
                    intent.putExtra("saturatedfat", saturatedfat);
                    intent.putExtra("transfat", transfat);
                    intent.putExtra("sugar", sugar);
                    intent.putExtra("na", na);
                    startActivity(intent);
                }
            }
        });
    }
}

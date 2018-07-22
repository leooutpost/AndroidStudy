package com.leo.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DataSaveToXML1 extends AppCompatActivity {

    String name,key;
    EditText nameText,keyText;
    Button dataButton,shoppingButton1,shoppingButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_save_to_xml1);

        nameText=(EditText) findViewById(R.id.dataName);
        keyText=(EditText)findViewById(R.id.dataKey);

        dataButton= (Button) findViewById(R.id.dataButton);
        shoppingButton1=(Button)findViewById(R.id.shoppingButton1);
        shoppingButton2=(Button)findViewById(R.id.shoppingButton2);

        /*按钮点击事件*/
        shoppingButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DataSaveToXML1.this,ShoppingActivity.class);
               startActivity(intent);

            }
        });
        shoppingButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DataSaveToXML1.this,Shopping2.class);
                startActivity(intent);

            }
        });
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DataSaveToXML1.this,DataSaveToXML2.class);
                startActivityForResult(intent,0);

            }
        });//end dataButton onClick()

        SharedPreferences sp=getSharedPreferences("data",0);//获取xml文件（没有则创建一个）
        String name = sp.getString("name1","");
        if(!"nothing".equals(name)){
            nameText.setText(name);
        }

    }//end onCreate()

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String name = nameText.getText().toString().trim();//获取输入的用户名并保存到xml
        if("".equals(name)){
            Toast.makeText(DataSaveToXML1.this, "不需要保护的", Toast.LENGTH_SHORT).show();
        }else{

            SharedPreferences spf = getSharedPreferences("data",0);
            //获取编辑权限
            SharedPreferences.Editor editor = spf.edit();
            editor.putString("name",name);  //< name="name"> name </name>
            boolean bol = editor.commit();
            Toast.makeText(DataSaveToXML1.this, "保护状态="+bol, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == 200){
            String backValue = data.getStringExtra("dataText");
            Toast.makeText(DataSaveToXML1.this,"backValue:"+ backValue, Toast.LENGTH_SHORT).show();
        }
    }

}

package com.leo.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DataSaveToXML2 extends AppCompatActivity {

    private EditText eT;
    private Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_save_to_xml2);

       eT=(EditText)findViewById(R.id.backData);
       b=(Button)findViewById(R.id.backButton);

        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String dataText=eT.getText().toString();
               // Toast.makeText(DataSaveToXML2.this, dataText, Toast.LENGTH_SHORT).show();
                Intent data=new Intent(DataSaveToXML2.this,DataSaveToXML1.class);
                data.putExtra("dataText",dataText);
                setResult(200,data);
                finish();
            }
        });



    }
}

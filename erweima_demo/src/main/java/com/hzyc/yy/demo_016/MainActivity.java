package com.hzyc.yy.demo_016;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xys.libzxing.zxing.activity.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    private Button sao;
    private EditText backValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sao = (Button) findViewById(R.id.sao);
        backValue = (EditText) findViewById(R.id.backValue);

        sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到zxing页面
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,200);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK){
            String value = data.getStringExtra("result");
            backValue.setText(value);
        }
    }
}

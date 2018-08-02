package com.hzyc.yy.demo_016;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class CreateEwActivity extends AppCompatActivity {

    private Button createEr;
    private EditText createValue;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ew);
        createEr = (Button) findViewById(R.id.createEr);
        createValue = (EditText) findViewById(R.id.createValue);
        imageView = (ImageView) findViewById(R.id.imageView);

        createEr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = createValue.getText().toString().trim();
                Bitmap logo = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                Bitmap bitmap = EncodingUtils.createQRCode(value,200,200,logo);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}

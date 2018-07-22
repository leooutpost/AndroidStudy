package com.leo.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.myapplication.bean.PhoneBook;
import com.leo.myapplication.utils.DBUtils;

import java.io.IOException;

public class ConcreteContactsActivity extends AppCompatActivity {

    boolean flashMainActivity=false;

    private int contactsId;
    private String phoneName;
    private Long phoneNumber;
    private String headPicPath;

    private ImageView headPicture;
    private TextView text_phoneName, text_phoneNumber;
    private Button button_call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete_contacts);
        //接收传值
        Intent intent = getIntent();
        this.contactsId = intent.getIntExtra("id", 0);
        this.phoneName = intent.getStringExtra("phoneName");
        this.phoneNumber = intent.getLongExtra("phoneNumber", 0L);
        this.headPicPath = intent.getStringExtra("headPicPath");


        //控件获取、赋值
        text_phoneName = (TextView) findViewById(R.id.contacts_name);
        text_phoneNumber = (TextView) findViewById(R.id.contacts_number);
        text_phoneName.setText(phoneName);
        text_phoneNumber.setText(String.valueOf(phoneNumber));

        headPicture=(ImageView)findViewById(R.id.imageView) ;
        if(headPicPath!=null&&(!"".equals(headPicPath))){
            Uri uri = Uri.parse(headPicPath);
            try {
                Bitmap bitmap = MediaStore.Images.Media.
                        getBitmap(ConcreteContactsActivity.this.getContentResolver(),uri);
                headPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        //Toast.makeText(ConcreteContactsActivity.this, "init id:"+contactsId+phoneName+phoneNumber+headPicPath, Toast.LENGTH_SHORT).show();
        button_call = (Button) findViewById(R.id.button_call);
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断通话权限是否开启，是：开启拨号意图；否：开启权限。
                if (ContextCompat.checkSelfPermission(ConcreteContactsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ConcreteContactsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {//权限已开启时
                    Intent callIntent = new Intent();
                    callIntent.setAction(Intent.ACTION_CALL);//设置活动类型
                    callIntent.setData(Uri.parse("tel:" + phoneNumber));//设置数据
                    startActivity(callIntent);//开启意图(进行拨号)
                }
            }
        });

    }
    //菜单相关
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=new MenuInflater(ConcreteContactsActivity.this);
        mi.inflate(R.menu.menu_concrete_contacts,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent=new Intent();
        flashMainActivity=true;
        setResult(1,resultIntent);
        switch (item.getItemId()) {
            case R.id.item_edit:
                //Toast.makeText(ConcreteContactsActivity.this, "edit", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ConcreteContactsActivity.this,UpdateActivity.class);
                intent.putExtra("id",contactsId);
                intent.putExtra("name",phoneName);
                intent.putExtra("number",phoneNumber);
                intent.putExtra("picture",headPicPath);

                startActivityForResult(intent,102);
                //startActivity(intent);

                break;
            case R.id.item_delete:
               // Toast.makeText(ConcreteContactsActivity.this, "delete", Toast.LENGTH_SHORT).show();

                DBUtils.getDB(ConcreteContactsActivity.this).
                        delete("PhoneBook","id=?",new String[]{String.valueOf(contactsId)});
                this.finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      if(resultCode==1){
          finish();
      }

    }

    @Override
    protected void onDestroy() {
        //Toast.makeText(ConcreteContactsActivity.this, "ConcreteContactsActivity is destroying!", Toast.LENGTH_SHORT).show();
        Log.i("Activity:","destroying");
        this.finish();
        super.onDestroy();
    }
}

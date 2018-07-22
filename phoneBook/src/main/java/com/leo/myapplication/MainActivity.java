package com.leo.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.myapplication.bean.PhoneBook;
import com.leo.myapplication.utils.DBUtils;
import com.leo.myapplication.utils.OpenSqlite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView lv;
    private QueryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        OpenSqlite op = new OpenSqlite(MainActivity.this);

        lv = (ListView) findViewById(R.id.listView);
        adapter = new QueryAdapter(DBUtils.getPhoneBookList(MainActivity.this));
        lv.setAdapter(adapter);
        //联系人长按事件（跳转到具体联系人页面-拨号、删除、更改）
      /*  lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });*/
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PhoneBook pb = adapter.getList().get(i);
                // Toast.makeText(MainActivity.this, pb.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ConcreteContactsActivity.class);
                intent.putExtra("id", pb.getId());
                intent.putExtra("phoneName", pb.getPhoneName());
                intent.putExtra("phoneNumber", pb.getPhoneNumber());
                intent.putExtra("headPicPath", pb.getHeadPicPath());
                if (pb.getHeadPicPath() != null) {
                    Toast.makeText(MainActivity.this, pb.getHeadPicPath(), Toast.LENGTH_LONG).show();
                }

                //startActivity(intent);
                startActivityForResult(intent, 101);//requestCode:101,详细联系人页面（更改、删除、拨号）
            }
        });


    }

    /*菜单相关*/
    //菜单-op1：添加联系人
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_mainactivity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.option1:
                //Toast.makeText(MainActivity.this, "click op1", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AddPhoneNumberActivity.class);
                startActivityForResult(intent, 100);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*活动返回相关*/
    //从添加联系人获取返回信息，判断要不要刷新adpter
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            Log.i("数据是否有更新：", "是，更新当前页adpter数据");
            //更新ListView数据，改变adapter持有的List，并通知该适配器数据更新
            adapter.setList(DBUtils.getPhoneBookList(MainActivity.this));
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "数据已更新", Toast.LENGTH_SHORT).show();
            //Log.i("添加联系人：", data.getStringExtra("addName") + " ; " + data.getStringExtra("addNumber"));

        } else if (resultCode == 0) {
            Log.i("数据是否有更新：", "否");
        } else {
            Log.i("错误的返回值：", "没正确返回1或0");
        }
    }


    /*适配器相关*/
    //ListView的适配器
    public class QueryAdapter extends BaseAdapter {

        private List<PhoneBook> list = null;

        protected QueryAdapter(List<PhoneBook> dataList) {
            this.list = dataList;
        }


        public List<PhoneBook> getList() {
            return this.list;
        }

        protected void setList(List<PhoneBook> phoneBookList) {
            this.list = phoneBookList;
        }

        ;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return list.get(position).getId();
        }//获取的是数据库id

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this, R.layout.item, null);

            if (list.get(position).getHeadPicPath() != null) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {//根据数据库存入的uri（headPicPath）来为ImageView添加引用
                    Uri uri = Uri.parse(list.get(position).getHeadPicPath());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), uri);
                        ((ImageView) view.findViewById(R.id.item_headPicture)).setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }else{
                ((ImageView) view.findViewById(R.id.item_headPicture)).setImageResource(R.mipmap.ic_launcher);
            }

            ((TextView) view.findViewById(R.id.item_name)).setText(list.get(position).getPhoneName());

            return view;
        }
    }


}

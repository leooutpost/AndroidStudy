package com.leo.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class Menu2 extends AppCompatActivity {
    private ListView listView;

    private ArrayAdapter<String> arrayAdapter;
    private ActionMode actionMode;

    //action mode模式(匿名内部类--注册填充)
    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu1, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
//点击事件注册
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case R.id.option1:
                    Toast.makeText(Menu2.this, "点击了op1", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    break;
                case R.id.option2:
                    Toast.makeText(Menu2.this, "点击了op2", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    break;
                case R.id.option3:
                    Toast.makeText(Menu2.this, "点击了op3", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getList());
        listView.setAdapter(arrayAdapter);


        //注册一个 actionMode菜单.长按时才弹出菜单！！！
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionMode != null) {
                    return false;
                }
                actionMode = Menu2.this.startActionMode(callback);
                return true;
            }
        });
    }

    public List<String> getList() {

        List<String> list = Arrays.asList("数据1", "数据2", "数据3", "数据4", "数据5", "数据6");

        return list;
    }
}

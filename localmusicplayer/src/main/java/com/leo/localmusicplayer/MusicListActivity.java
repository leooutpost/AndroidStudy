package com.leo.localmusicplayer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bean.MusicMedia;

public class MusicListActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        listView = (ListView) findViewById(R.id.listView);
        //arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,getList());
        listView.setAdapter(new MyAdapter(getList()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = getList().get(position).getUrl();
                Intent intent = new Intent(MusicListActivity.this, MusicPlayerActivity.class);
                intent.putExtra("url",url);

                startActivity(intent);
            }
        });
    }



    //获取本地音乐库列表
    public List<MusicMedia> getList(){
        List<MusicMedia> list = new ArrayList<MusicMedia>();
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        assert cursor != null;
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                //歌曲编号
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                //歌曲标题
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                //if (size >1024*800){//大于800K
                MusicMedia musicMedia = new MusicMedia();
                musicMedia.setId(id);
                musicMedia.setArtist(artist);
                musicMedia.setSize(size);
                musicMedia.setTitle(title);
                musicMedia.setTime(duration);
                musicMedia.setUrl(url);
                musicMedia.setAlbum(album);
                musicMedia.setAlbumId(albumId);
                list.add(musicMedia);
                //}
                cursor.moveToNext();
            }
        }
        return list;
    }

    //歌曲列表适配器
    class MyAdapter extends BaseAdapter {
        private List<MusicMedia> list;
        public MyAdapter(List<MusicMedia> list){
            this.list = list;
        }
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
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if(convertView == null){
                textView = new TextView(MusicListActivity.this);
                textView.setPadding(8,8,8,8);
                textView.setTextSize(20);
            }else{
                textView = (TextView) convertView;
            }
            textView.setText(list.get(position).getTitle());
            return textView;
        }
    }
}

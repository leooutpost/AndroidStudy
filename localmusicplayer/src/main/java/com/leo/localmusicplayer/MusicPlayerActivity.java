package com.leo.localmusicplayer;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayerActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private Button stop, start_pause, replay;
    
    private MediaPlayer mediaPlayer = null;
    private Timer timer=null;//调度器  指定让他什么开始一个线程什么时候结束  按什么时间重复的执行
    private boolean seekBarTrackTouching = false;

    private int id;
    private String url;
    private int stop_time;
    private int resume_time=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        widgetInit();
        
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarTrackTouching = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress()); //设置播放直接到seekBar指定的位置
                seekBarTrackTouching = false;
            }
        });


    }



    //播放音乐
    public void start() {
        //按路径模式播放
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();

            start_pause.setBackgroundResource(R.drawable.pause);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置seekBar总的进度
        seekBar.setMax(mediaPlayer.getDuration());
        if(resume_time!=0){
            seekBar.setProgress(resume_time);
            mediaPlayer.seekTo(resume_time);
        }
        mediaPlayer.start();
        if(timer!=null){
            timer=null;
        }

        timer = new Timer(); // 任务 timeTask 就是一个调度线程

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //指定任务的作用
                if (seekBarTrackTouching) {
                    return;
                }
                resume_time=mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mediaPlayer.getCurrentPosition()); //播放位置设置到进度条中
            }
        };

        //开始调度
        timer.schedule(timerTask, 0, 500);
    }

    //暂停
    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            start_pause.setBackgroundResource(R.drawable.play);
            return;
        }
    }

    //重播
    public void replay() {
        seekBar.setProgress(1);
        mediaPlayer.seekTo(1);
        start_pause.setBackgroundResource(R.drawable.pause);
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
    }

    //结束播放。结束播放器activity
    public void end() {
        if (mediaPlayer != null) {
                //结束调度
                timer.cancel();
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                seekBar.setProgress(0);
        }
    }


    //页面每次打开都会加载
    @Override
    protected void onResume() {
        super.onResume();
 
        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        AudioManager am=(AudioManager)this.getSystemService(AUDIO_SERVICE);
        if(am.isMusicActive()){
            end();
        }
        start();
        Toast.makeText(MusicPlayerActivity.this, ""+resume_time, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stop_time=mediaPlayer.getDuration();
        //end();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        end();
    }

    /*控件初始化*/
    private void widgetInit() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        stop = (Button) findViewById(R.id.stop);
        start_pause = (Button) findViewById(R.id.start_pause);
        replay = (Button) findViewById(R.id.replay);
    }
    /*按钮点击事件*/
    public void onButtonClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.start_pause:
                if(mediaPlayer==null){
                    start();
                }else if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    pause();
                    start_pause.setBackgroundResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    start_pause.setBackgroundResource(R.drawable.pause);
                }

                Toast.makeText(MusicPlayerActivity.this, "s_p", Toast.LENGTH_SHORT).show();
                ;
                break;

            case R.id.stop:
                Toast.makeText(MusicPlayerActivity.this, "stop", Toast.LENGTH_SHORT).show();
               end();
                finish();
                ;
                break;

            case R.id.replay:
                Toast.makeText(MusicPlayerActivity.this, "replay", Toast.LENGTH_SHORT).show();
                replay();
                break;
            case R.id.back:
                end();
                finish();
                Toast.makeText(MusicPlayerActivity.this, "finish", Toast.LENGTH_SHORT).show();
                ;break;

        }

    }

}

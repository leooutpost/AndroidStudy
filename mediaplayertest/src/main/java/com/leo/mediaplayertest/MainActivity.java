package com.leo.mediaplayertest;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private MediaPlayer mediaPlayer = null;
    private Button start_pause, restart, end;
    private TextView now_time;
    private SeekBar seekBar;

    private static Boolean musicPlaying = false;
    private static Boolean seekBarTrackTouching = false;//判断SeekBar是否正在被拖动
    private static int time = 0;

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        widgetInit();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //time=
                //now_time.setText(showTime(time));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBarTrackTouching=true;
                //Toast.makeText(MainActivity.this, "onStartTrackingTouch", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                time=seekBar.getProgress();
                seekBarTrackTouching=false;

                int progress=seekBar.getProgress();
                now_time.setText(showTime(progress));
                if(mediaPlayer!=null)mediaPlayer.seekTo(progress);

                new Thread(new SeekBarThread()).start();

                Toast.makeText(MainActivity.this, ""+progress, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*button与对应函数*/
    public void check(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.start_pause:
                if (musicPlaying) {
                    pause();
                } else {
                    start();
                }
                break;
            case R.id.restart:
                replay();
                break;
            case R.id.end:
                end();
                break;

        }
    }

    public void start() {
        if(mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.monsterhunterworld);
        }

        //根据音乐时长设置SeekBar长度
        int musicDuration=mediaPlayer.getDuration();
        if(musicDuration!=seekBar.getMax()){
            seekBar.setMax(musicDuration);
        }
        //Toast.makeText(MainActivity.this, ""+musicDuration, Toast.LENGTH_SHORT).show();

       if(thread==null){
           thread=new Thread(new SeekBarThread());
           thread.start();
       }
        //启动音乐播放器
        mediaPlayer.start();
        if(thread.isAlive()){
            mediaPlayer.seekTo(time);
        }else{
            mediaPlayer.seekTo(time);
            thread=new Thread(new SeekBarThread());
            thread.start();
        }


        musicPlaying = mediaPlayer.isPlaying();
        start_pause.setText("暂停");

    }

    //暫停播放
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            time = mediaPlayer.getCurrentPosition();
            musicPlaying = mediaPlayer.isPlaying();
            start_pause.setText("播放");

            //音乐停止后，thread线程run()循环结束.子线程dead
        }
    }

    //播放
    public void replay() {

        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(1000);
            mediaPlayer.start();
            seekBar.setProgress(0);
            now_time.setText("00:01");

            musicPlaying=true;
            start_pause.setText("暂停");

        }

    }

    public void end() {
        if(mediaPlayer!=null){
            musicPlaying=false;
            start_pause.setText("播放");
            time=0;

            if(thread!=null){
                thread=null;
            }

            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        end();
    }

    //时间函数()
    public String showTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

    public void changeNowTime(int musicPosition){
        if(now_time!=null)now_time.setText(showTime(musicPosition));
    }

    //控件初始化
    private void widgetInit(){
        start_pause = (Button) findViewById(R.id.start_pause);
        restart = (Button) findViewById(R.id.restart);
        end = (Button) findViewById(R.id.end);

        now_time=(TextView)findViewById(R.id.now_time);

        seekBar=(SeekBar)findViewById(R.id.seekBar);

    }

    // 自定义的计时线程，用于更新当前播放时间
     class SeekBarThread implements Runnable {

        @Override
        public void run() {

            while(mediaPlayer!=null){
                // 将SeekBar位置设置到当前播放位置
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                //Log.i("seekBar progress:",""+seekBar.getProgress());
                //Log.i("class name:",""+this.getClass().getName());

                /*报异常：只有UI线程能UI线程中的对象
                （但是，SeekBar、ProgressBar、ProgressDialog等控件可以在子线程中控制，
                它们的方法中，若判断当前操作它们的不是UI线程，它们会采取措施回归UI线程然后操作）*/
                // now_time.setText(showTime(mediaPlayer.getCurrentPosition()));
                // MainActivity.this.changeNowTime(mediaPlayer.getCurrentPosition());

                /*下面代码是子线程中让UI线程更新控件的一种方法*/
                runOnUiThread(new Runnable() {//让UI线程执行操作
                    @Override
                    public void run() {
                        now_time.setText(showTime(mediaPlayer.getCurrentPosition()));
                    }
                });

                try {
                    // 每100毫秒更新一次位置
                    Thread.sleep(100);
                    //播放进度
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            }//end run()

    }


}

package com.dragger.wl.videophotolook;

import android.graphics.Color;
import android.net.ParseException;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private IjkMediaPlayer ijkMediaPlayer;
    private FrameLayout rootView;
    private boolean isShowing;
    private SeekBar seekBar;
    private String path;
    private TextView totalTime;
    private long duration;
    private long durationTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStatusBarMethod();

        initViewMethod();
        holder = surfaceView.getHolder();
        surfaceView.setBackgroundColor(Color.TRANSPARENT);
        if (ijkMediaPlayer==null)
        ijkMediaPlayer = new IjkMediaPlayer();

        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
              if (!isShowing){
                  //显示
                  showView();
              }else{
                  //隐藏
                  hideView();
              }
              isShowing=!isShowing;
                return false;
            }
        });
//        ijkMediaPlayer.setDataSource("http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");//"http://ic.snssdk.com/neihan/video/playback/?video_id=090d865fd9e04043b4de2e0550798e56&quality=360p&line=0&is_gif=1");
       /* rootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);*/
        hideView();
        playVideo();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }
    private void playVideo() {
        try {
            path = TextUtils.isEmpty(path)?"http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv":path;
            ijkMediaPlayer.setDataSource(path);//"http://ic.snssdk.com/neihan/video/playback/?video_id=090d865fd9e04043b4de2e0550798e56&quality=360p&line=0&is_gif=1");
              holder=surfaceView.getHolder();
              holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    ijkMediaPlayer.setDisplay(holder);
                }
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                }
            });
            ijkMediaPlayer.prepareAsync();
            ijkMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {


                @Override
                public void onPrepared(IMediaPlayer iMediaPlayer) {
                    ijkMediaPlayer.start();
                    long currentPosition = ijkMediaPlayer.getCurrentPosition();
                    duration = ijkMediaPlayer.getDuration();
                    durationTotal=duration;
                    seekBar.setMax((int) (durationTotal/1000));
                    startProcessRun();
                    mHandler.sendEmptyMessage(START);
                    ijkMediaPlayer.setLooping(true);
                }
            });
            ijkMediaPlayer.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(IMediaPlayer iMediaPlayer) {
                    mHandler.sendEmptyMessage(STOP);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final int START=0;
    private final int PAUSE=1;
    private final int STOP=2;
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case START:

                    mHandler.sendEmptyMessageDelayed(START,1000);
                    startProcessRun();
                    break;
                case PAUSE:
                    mHandler.removeMessages(START);

                case STOP:
                    mHandler.removeMessages(START);
                    resetProcess();
                    break;
            }
            return false;
        }
    });

    private void resetProcess() {
        seekBar.setProgress(START);
        duration=0;
    }

    private void startProcessRun() {
        int progress = (int) (seekBar.getProgress()+1);
        seekBar.setProgress(progress);
        duration-=1000;
        totalTime.setText(longToString(duration, "HH:mm:ss"));
    }

    // 进度条同视频同步
    private void synchronizedProcess() {

    }

    private void hideView() {
        totalTime.setVisibility(View.GONE);
        seekBar.setVisibility(View.GONE);
        hideStatusBar(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void showView() {
        totalTime.setVisibility(View.VISIBLE);
        hideStatusBar(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        seekBar.setVisibility(View.VISIBLE);
    }

    private void hideStatusBar(int visibility) {
        showStatusBar(visibility);
    }

    private void showStatusBar(int visibility) {
        rootView.setSystemUiVisibility(visibility);
    }

    private void initViewMethod() {
        rootView = ((FrameLayout) findViewById(R.id.root_view));
        surfaceView = ((SurfaceView) findViewById(R.id.surfaceView));
        seekBar = ((SeekBar) findViewById(R.id.process_seek));
        totalTime = ((TextView) findViewById(R.id.total_time));
    }

    private void initStatusBarMethod() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.transparent));
                //底部导航栏
                window.setNavigationBarColor(this.getResources().getColor(R.color.transparent));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkMediaPlayer.stop();
        ijkMediaPlayer.release();
    }
}

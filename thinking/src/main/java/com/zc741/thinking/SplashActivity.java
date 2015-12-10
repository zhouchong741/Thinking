package com.zc741.thinking;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.john.waveview.WaveView;

public class SplashActivity extends AppCompatActivity {


    protected WaveView waveView;
    private TextView tv_slide_note;
    protected SeekBar seekBar;
    private String progress_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //要求用户联网
        checkNet();

        initViews();
    }

    //检查网络
    private void checkNet() {
        boolean flag = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null) {
            flag = connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        if (!flag) {
            Toast.makeText(SplashActivity.this, "网络连接出问题啦！可能会影响使用呀", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(SplashActivity.this, "当前网络已连接", Toast.LENGTH_SHORT).show();
        }
    }


    private void initViews() {
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        waveView = (WaveView) findViewById(R.id.wave_view);

        tv_slide_note = (TextView) findViewById(R.id.slide);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setProgress(progress);

                //判断当seekBar==100 显示和隐藏
                if (progress == 100) {
                    //整个主布局显示
                    //ll_content.setVisibility(View.VISIBLE);
                    //设置提示手指滑动的文字隐藏
                    tv_slide_note.setVisibility(View.INVISIBLE);
                    //进度条隐藏
                    seekBar.setVisibility(View.GONE);
                    //跳转到MainActivity
                    turnToMainActivity();
                }
                //获取到当前的progress的值
                progress_value = String.valueOf(progress);
                //System.out.println(progress_value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //将字符串类型转换为int类型
                int i = Integer.parseInt(progress_value);
                if (i > 50 & i != 100) {
                    //提示用户
                    Toast.makeText(SplashActivity.this, "滑动到最右哟！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //跳转到MainActivity
    private void turnToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
        //结束当前页，当进入MainActivity后返回时，会销毁，直接退出app
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(300);
                    System.out.println("sleep 300ms");
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}

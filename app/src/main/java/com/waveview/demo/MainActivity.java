package com.waveview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.john.waveview.WaveView;

/**
 * Created by zc741 on 18/11/15.
 */
public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private WaveView waveView;

    private LinearLayout ll_content;
    private TextView tv_slide_note;
    private String progress_value;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        waveView = (WaveView) findViewById(R.id.wave_view);

        tv_slide_note = (TextView) findViewById(R.id.slide);

        ll_content = (LinearLayout) findViewById(R.id.ll_content);

        final ActionBar actionBar = getSupportActionBar();
        //actionBar隐藏
        actionBar.hide();
        //设置Title
        actionBar.setTitle("克");

        //seekBar设置监听
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveView.setProgress(progress);

                //判断当seekBar==100 显示和隐藏
                if (progress == 100) {
                    //整个主布局显示
                    ll_content.setVisibility(View.VISIBLE);
                    //设置提示手指滑动的文字隐藏
                    tv_slide_note.setVisibility(View.INVISIBLE);
                    //进度条隐藏
                    seekBar.setVisibility(View.INVISIBLE);
                    //actionBar显示
                    actionBar.show();
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
                if (i != 100) {
                    //提示用户
                    Toast.makeText(MainActivity.this, "滑动到尾部哟", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        MenuItem shareItem = menu.findItem(R.id.share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        shareActionProvider.setShareIntent(getDefaultIntent());
        return super.onCreateOptionsMenu(menu);
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        return intent;
    }
}
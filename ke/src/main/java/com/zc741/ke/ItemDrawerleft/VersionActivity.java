package com.zc741.ke.ItemDrawerleft;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zc741.ke.R;
import com.zc741.ke.domain.Utils.DpToPx;

public class VersionActivity extends BaseActivity {
    private String mVersion;
    private LinearLayout mLinearLayout;

    @Override
    public void setContentView(int layoutResID) {

        super.setContentView(R.layout.activity_check_update);

        //显示版本号
        checkUpdate();
    }

    private void checkUpdate() {
        TextView tv_version = (TextView) findViewById(R.id.tv_version);

        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            mVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_version.setText("当前版本:" + mVersion);
    }

    public void checkupdate(View view) {
        System.out.println("检查更新");

        //Toast.makeText(CheckUpdate.this, "当前版本是最新的哟!", Toast.LENGTH_SHORT).show();
    }

    public void initItem() {
        //侧边栏listItem
        final int[] pics = new int[]{
                R.mipmap.ic_chat_black_18dp,
                R.mipmap.ic_chat_black_18dp,
                R.mipmap.ic_markunread_black_18dp


        };
        final String[] items = new String[]{
                "         About",
                "         Advice",
                "         Contribute",

        };

        mListview = (ListView) findViewById(R.id.lv_left);

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return pics.length;
            }

            @Override
            public Object getItem(int position) {
                return pics[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //System.out.println(items[position]);
                //添加两个布局
                mLinearLayout = new LinearLayout(getApplicationContext());
                mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                mLinearLayout.setGravity(Gravity.CENTER_VERTICAL);

                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageResource(pics[position]);
                imageView.setPadding(28, 0, 0, 0);

                TextView textView = new TextView(getApplicationContext());
                textView.setText(items[position]);

                textView.setTextSize(14);
                textView.setHeight(DpToPx.dip2px(getApplicationContext(), 40));
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.GRAY);
                mLinearLayout.addView(imageView);
                mLinearLayout.addView(textView);

                return mLinearLayout;

            }
        };
        mListview.setAdapter(adapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position==" + position + "; items==" + items[position]);
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(intent0);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getApplicationContext(), ContributeActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getApplicationContext(), PublishActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    default:
                        break;
                }
                finish();
            }
        });
    }


}
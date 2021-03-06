package com.zc741.thinking.ItemDrawerleft;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zc741.thinking.R;
import com.zc741.thinking.domain.Utils.DpToPx;

public class PublishActivity extends BaseActivity {
    private LinearLayout mLinearLayout;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_publish);
        initItem();

        //发送邮件
        sendEamil();
    }

    public void initItem() {
        //侧边栏listItem
        final int[] pics = new int[]{
                R.mipmap.ic_chat_black_18dp,
                R.mipmap.ic_chat_black_18dp,
                R.mipmap.ic_chat_black_18dp,
                R.mipmap.ic_markunread_black_18dp
        };
        final String[] items = new String[]{
                "         关于",
                "         建议",
                "         版本",
                "         给您的一封信"
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
                        Intent intent3 = new Intent(getApplicationContext(), VersionActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    case 3:
                        Intent intent4 = new Intent(getApplicationContext(), ThinkerActivity.class);
                        startActivity(intent4);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    default:
                        break;
                }
                finish();
            }
        });
    }

    //发送邮件
    private void sendEamil() {
        final String[] mail = {"zhouchong741@hotmail.com", "zhouchong741@gmail.com", "postmaster@zc741.com"};

        TextView tv_hotmail = (TextView) findViewById(R.id.tv_hotmail);
        TextView tv_gmail = (TextView) findViewById(R.id.tv_gmail);
        TextView tv_postmaster = (TextView) findViewById(R.id.tv_postmaster);
        tv_hotmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("mailto:" + mail[0]);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, "投稿");
                intent.putExtra(Intent.EXTRA_TEXT, "正文内容");
                startActivity(Intent.createChooser(intent, "选择邮件"));
            }
        });
        tv_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("mailto:" + mail[1]);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, "投稿");
                intent.putExtra(Intent.EXTRA_TEXT, "正文内容");
                startActivity(Intent.createChooser(intent, "选择邮件"));
            }
        });
        tv_postmaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Uri uri = Uri.parse("mailto:" + mail[2]);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_SUBJECT, "投稿");
                intent.putExtra(Intent.EXTRA_TEXT, "正文内容");
                startActivity(Intent.createChooser(intent, "选择邮件"));
            }
        });
    }

}
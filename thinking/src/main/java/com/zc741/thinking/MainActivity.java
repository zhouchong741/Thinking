package com.zc741.thinking;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zc741.thinking.ItemDrawerleft.AboutActivity;
import com.zc741.thinking.ItemDrawerleft.ContributeActivity;
import com.zc741.thinking.ItemDrawerleft.PublishActivity;
import com.zc741.thinking.ItemDrawerleft.VersionActivity;
import com.zc741.thinking.domain.Content;
import com.zc741.thinking.domain.Utils.DpToPx;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ListView mListview;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //侧边栏
        initItem();

        initDrawerToggle();
        //获取服务器数据
        getDataFromServer();
    }

    private void initDrawerToggle() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open_string, R.string.close_string);
        mActionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initItem() {
        //侧边栏listItem
        final int[] pics = new int[]{
                R.mipmap.ic_chat_black_18dp,
                R.mipmap.ic_chat_black_18dp,
                R.mipmap.ic_markunread_black_18dp,
                R.mipmap.ic_markunread_black_18dp

        };
        final String[] items = new String[]{
                "         About",
                "         Advice",
                "         Contribute",
                "         Version"
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
                mLinearLayout = new LinearLayout(MainActivity.this);
                mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                mLinearLayout.setGravity(Gravity.CENTER_VERTICAL);

                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(pics[position]);
                imageView.setPadding(28, 0, 0, 0);

                TextView textView = new TextView(MainActivity.this);
                textView.setText(items[position]);

                textView.setTextSize(14);
                textView.setHeight(DpToPx.dip2px(getApplicationContext(), 40));
                textView.setGravity(Gravity.CENTER);
                mLinearLayout.addView(imageView);
                mLinearLayout.addView(textView);

                return mLinearLayout;
            }
        };
        mListview.setAdapter(adapter);

        //侧边栏点击事件
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position==" + position + "; items==" + items[position] + "; pics==" + pics[position]);
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
                    case 3:
                        Intent intent3 = new Intent(getApplicationContext(), VersionActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        break;
                    default:
                        break;
                }
                //从 非MainActivity返回时保持抽屉处于关闭状态
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    //当点击返回键时MainActivity的Drawerlayout是关闭状态
    //设置返回键监听
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //实现Home键效果
            //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
            //super.onBackPressed();
        }
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    //获取服务器数据
    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        String uri = "http://www.zc741.com/thinking/1.json";
        utils.send(HttpMethod.GET, uri, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                //System.out.println("result=" + result);
                parseJson(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    /**
     * 解析json
     *
     * @param result
     */
    private void parseJson(String result) {
        Gson gson = new Gson();

        Content data = gson.fromJson(result, Content.class);
        //System.out.println(data);

        //填充内容
        TextView tv_word = (TextView) findViewById(R.id.tv_word);
        ImageView iv_pic = (ImageView) findViewById(R.id.iv_pic);
        tv_word.setText(data.getWord());

        try {
            URL url = new URL(data.getPic());
            Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
            iv_pic.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(data.getWord());
        System.out.println(data.getPic());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        } else if (id == R.id.menu_share) {
            shareFunction();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    //实现分享功能
    private void shareFunction() {
        System.out.println("点击了分享");
    }

    //创建分享条目
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        MenuItem mi = menu.findItem(R.id.menu_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(mi);
        //shareActionProvider.setShareIntent(getDefaultIntent());
        return super.onCreateOptionsMenu(menu);
    }
}

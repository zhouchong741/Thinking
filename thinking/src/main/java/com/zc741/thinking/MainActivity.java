package com.zc741.thinking;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ListView mListview;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    private LinearLayout mLinearLayout;
    private TextView mTv_word;
    private ImageView mIv_pic;
    private Content mData;
    private URL mUrl;

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

        //ShareSDK
        ShareSDK.initSDK(this);
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

        mData = gson.fromJson(result, Content.class);
        //System.out.println(data);

        //填充内容
        mTv_word = (TextView) findViewById(R.id.tv_word);
        mIv_pic = (ImageView) findViewById(R.id.iv_pic);
        mTv_word.setText(mData.getWord());

        try {
            mUrl = new URL(mData.getPic());
            Bitmap bitmap = BitmapFactory.decodeStream(mUrl.openStream());
            mIv_pic.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(data.getWord());
        System.out.println(mData.getPic());
    }


    //设置缓存



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

        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mData.getWord());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数  //图片要缓存到这里
        oks.setImagePath(Environment.getExternalStorageDirectory().getPath()+"/8.png");//确保SDcard下面存在此张图片 "/sdcard/test.jpg"
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(String.valueOf(mUrl));
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);

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

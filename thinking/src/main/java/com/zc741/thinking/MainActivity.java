package com.zc741.thinking;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import com.zc741.thinking.ItemDrawerleft.ThinkingerActivity;
import com.zc741.thinking.ItemDrawerleft.VersionActivity;
import com.zc741.thinking.domain.Content;
import com.zc741.thinking.domain.Utils.DpToPx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
    private Bitmap mBitmap;
    private URL mURL;

    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //处理消息时，需要知道到底是成功的消息，还是失败的消息
            switch (msg.what) {
                case 1:
                    //把位图对象显示至imageview
                    break;
                case 0:
                    break;
            }
        }
    };

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

    //执行onStart()方法 调用getDataFromServer()一次
    @Override
    protected void onStart() {
        super.onStart();
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
                R.mipmap.ic_markunread_black_18dp,
                R.mipmap.ic_markunread_black_18dp
        };
        final String[] items = new String[]{
                "         About",
                "         Advice",
                "         Contribute",
                "         Version",
                "         Thinkinger"
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
                    case 4:
                        Intent intent4 = new Intent(getApplicationContext(), ThinkingerActivity.class);
                        startActivity(intent4);
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
        //System.out.println(mData);

        //填充内容
        mTv_word = (TextView) findViewById(R.id.tv_word);
        mIv_pic = (ImageView) findViewById(R.id.iv_pic);
        mTv_word.setText(mData.getWord());

        try {
            mURL = new URL(mData.getPic());
            mBitmap = BitmapFactory.decodeStream(mURL.openStream());
            mIv_pic.setImageBitmap(mBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(mData.getWord());//这是第一天的内容，作为测试用的。这是第一天的内容，作为测试用的。这是第一天的内容，作为测试用的。
        //System.out.println(mData.getPic());//http://www.zc741.com/thinking/pictures/bbg.png
        System.out.println(mURL);//http://www.zc741.com/thinking/pictures/bbg.png

        //缓存图片
        cachePNG();
    }

    //缓存图片
    private void cachePNG() {
        //下载图片
        String path = mURL + "";
        final File file = new File(Environment.getExternalStorageDirectory().getPath(), "cache.png");

        Thread t = new Thread() {
            @Override
            public void run() {

                try {
                    HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
                    //4.对连接对象进行初始化
                    //设置请求方法，注意大写
                    conn.setRequestMethod("GET");
                    //设置连接超时
                    conn.setConnectTimeout(5000);
                    //设置读取超时
                    conn.setReadTimeout(5000);
                    //5.发送请求，与服务器建立连接
                    conn.connect();

                    if (conn.getResponseCode() == 200) {
                        //获取服务器响应头中的流，流里的数据就是客户端请求的数据
                        InputStream is = conn.getInputStream();

                        //读取服务器返回的流里的数据，把数据写到本地文件，缓存起来

                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] b = new byte[1024];
                        int len = 0;
                        while ((len = is.read(b)) != -1) {
                            fos.write(b, 0, len);
                        }
                        fos.close();

                        //读取出流里的数据，并构造成位图对象
                        //流里已经没有数据了
//							Bitmap bm = BitmapFactory.decodeStream(is);
                        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                        Message msg = new Message();
                        //消息对象可以携带数据
                        msg.obj = bm;
                        msg.what = 1;
                        //把消息发送至主线程的消息队列
                        handler.sendMessage(msg);
                    } else {
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
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

            //判断是否联网 无效的
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                Toast.makeText(MainActivity.this, "您的手机没有联网，暂时不能使用此功能哟！", Toast.LENGTH_SHORT).show();
            } else {
                shareFunction();
            }
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
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        //oks.setTheme(OnekeyShareTheme.CLASSIC);//设置主题

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mData.getWord());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数  //图片要缓存到这里
        oks.setImagePath(Environment.getExternalStorageDirectory().getPath() + "/cache.png");//确保SDcard下面存在此张图片 "/sdcard/test.jpg"
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
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

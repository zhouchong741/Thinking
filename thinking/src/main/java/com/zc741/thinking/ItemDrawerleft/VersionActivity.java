package com.zc741.thinking.ItemDrawerleft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
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
import com.zc741.thinking.R;
import com.zc741.thinking.domain.Utils.DpToPx;
import com.zc741.thinking.domain.VersionData;

import java.io.File;

public class VersionActivity extends BaseActivity {
    private String mVersion;
    private LinearLayout mLinearLayout;
    private VersionData mVersionData;
    private String mTargetPath;
    private int mVersionCode;

    @Override
    public void setContentView(int layoutResID) {

        super.setContentView(R.layout.activity_check_update);

        //显示版本号
        showVersion();

        //解析更新的json
        getDataFromServer();
    }

    private void showVersion() {
        TextView tv_version = (TextView) findViewById(R.id.tv_version);

        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            mVersionCode = packageInfo.versionCode;
            mVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_version.setText("当前版本:" + mVersion);
    }

    //获取服务器跟新数据
    private void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        String uri = "http://www.zc741.com/thinking/update.json";
        httpUtils.send(HttpMethod.GET, uri, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                //System.out.println(result);

                //解析更新的json
                parseData(result);
            }
            @Override
            public void onFailure(HttpException e, String s) {
                System.out.println("解析失败");
                System.out.println(s);
                e.printStackTrace();
            }
        });
    }

    //解析更新的json
    private void parseData(String result) {
        Gson gson = new Gson();
        mVersionData = gson.fromJson(result, VersionData.class);
        System.out.println("code==" + mVersionData.getVersionCode());

        /*
         versionName=2.0
         versionCode=2
         description=有新版本，建议更新
         downLoadUrl=http://www.zc741.com/thinking/Thinking.apk
         */
    }

    //检查更新
    public void checkupdate(View view) {
        /*
         * mVersionData.getVersionCode(); 服务器版本
         * mVersionCode; 当前版本
         */
        if (mVersionData.getVersionCode() >= mVersionCode) {
            alertDialog();
        } else {
            Toast.makeText(VersionActivity.this, "当前是最新版本哟！", Toast.LENGTH_SHORT).show();
        }
    }

    //弹出对话框下载
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("建议下载并安装，体验新功能");
        builder.setTitle("有新版本");
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //System.out.println("下载了。。。。");
                dialog.dismiss();
                //进行下载程序XUtils
                downloadAPK();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //System.out.println("取消下载了。。。。");
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //进行下载程序XUtils
    private void downloadAPK() {
        HttpUtils utils = new HttpUtils();
        String uri = mVersionData.getDownLoadUrl();
        mTargetPath = Environment.getExternalStorageDirectory().getPath() + "/Thinking.apk";
        utils.download(uri, mTargetPath, true, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                //System.out.println("下载成功");
                //成功后跳转到安装界面
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                startActivity(intent);

                //删除apk文件
                deleteAPK();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
//                System.out.println("下载进度：" + current + "/" + total);
                long percent = current * 100 / total;
                TextView tv_download = (TextView) findViewById(R.id.tv_download);
                tv_download.setText("下载进度:" + percent + "%");
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(VersionActivity.this, s, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    //删除apk
    private void deleteAPK() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String currentVersion = packageInfo.versionName;
            System.out.println("currentVersion" + currentVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("删除apk");
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

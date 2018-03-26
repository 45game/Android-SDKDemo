package com.fy.sdkdemo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.fy.sdk.FYSDK;
import com.fy.sdk.models.biz.output.FYOrder;
import com.fy.sdk.models.biz.output.FYRole;
import com.fy.sdk.models.biz.output.FYUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView imbg;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final Button initButton = (Button) findViewById(R.id.init);
        final Button loginButton = (Button) findViewById(R.id.login);
        final Button payButton = (Button) findViewById(R.id.pay);
        final Button centerButton = (Button) findViewById(R.id.center);
        final Button logoutButton = (Button) findViewById(R.id.logout);

        imbg = (ImageView) findViewById(R.id.imbg);

        /*SDK初始化*/
        init();

        /*注销回调*/
        FYSDK.getInstance().setSdkLogoutCallback(new FYSDK.IFYSDKLogoutCallback() {
            @Override
            public void logout() {
                Log.e("FYSDKDemo", "注销成功");
            }
        });

        /*登陆回调*/
        FYSDK.getInstance().setSdkLoginCallback(new FYSDK.IFYSDKLoginCallback() {
            @Override
            public void loginSuccess(FYUser user) {
                String username = user.getUsername();
                String accessToken = user.getAccessToken();
                String userId = user.getUsername();
                String text = "userId = " + userId + ";username = " + username + ";accessToken = " + accessToken;
                Log.e("FYSDKDemo", "登陆成功" + text);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String timeStr = formatter.format(curDate);

                FYRole role = new FYRole();
                role.setRoleId("9527");
                role.setRoleName("凯特琳");
                role.setServerId("server1");
                role.setServerName("紫陌红尘");
                role.setRoleLevel(1);
                role.setLoginTime(timeStr);
                FYSDK.getInstance().fySaveRole(role);

            }

            @Override
            public void loginFail(String errorString) {
                Log.e("FYSDKDemo", "登陆失败" + errorString);
            }
        });

        initButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("FYSDKDemo","initButton");
                init();
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FYSDK.getInstance().fyLogin();
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FYOrder orderModel = new FYOrder();
                orderModel.setProductId("productId1");
                orderModel.setServerId("serverId1");
                orderModel.setServerName("紫陌红尘");
                orderModel.setTotalFee(200);
                orderModel.setRoleId("9527");
                orderModel.setRoleName("GG20思密达");
                orderModel.setProductId("productId1");
                orderModel.setProductName("拉克丝小姐姐");
                orderModel.setProductDescription("真是一个深思熟虑的选择");
                orderModel.setOrderId(getOrderStringByTime());
                orderModel.setCustomInfo("自定义字段");
                Log.e("FYSDKDemo","" + orderModel.toString());
                FYSDK.getInstance().fyPay(orderModel);
            }
        });
//
        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FYSDK.getInstance().fyCenter();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FYSDK.getInstance().fyLogout();
            }
        });

    }

    public String getOrderStringByTime(){
        Date nowDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeNowString = dateFormat.format(nowDate) + getRandom();
        return timeNowString;
    }

    public String getRandom() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public void init(){
        FYSDK.getInstance().fyInit(MainActivity.this, 1, 1,
                "ddba75a7871543628652fb20996be609", new FYSDK.IFYSDKInitCallback() {
                    @Override
                    public void initSuccess() {
                        Log.e("FYSDKDemo", "初始化SDK成功");
                    }

                    @Override
                    public void initFail(String errorString) {
                        Log.e("FYSDKDemo", "初始化SDK失败-" + errorString);
                    }
                });

        /*注销回调*/
        FYSDK.getInstance().setSdkLogoutCallback(new FYSDK.IFYSDKLogoutCallback() {
            @Override
            public void logout() {
                Log.e("FYSDKDemo", "注销成功");
            }
        });
    }

    @Override
    protected void onResume() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
//            imbg.setImageResource(R.drawable.bgp);
        }
        else
        {
//            imbg.setImageResource(R.drawable.bgl);
        }


        super.onResume();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        FYSDK.getInstance().fyExit(new FYSDK.IFYSDKExitCallback() {
            @Override
            public void exit(boolean isExist) {
                Log.e("FYSDKDemo","" + isExist);
            }
        });
    }
}

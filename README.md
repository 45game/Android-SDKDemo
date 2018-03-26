# 45游戏Android SDK客户端说明文档
[![License MIT](https://img.shields.io/badge/license-MIT-green.svg?style=flat)](https://raw.githubusercontent.com/mikegame/Android-SDKDemo/master/LICENSE)&nbsp;



演示项目
==============
查看并运行 `Android-SDKDemo/FYSDKDemo`


使用
==============



1. 将 libFYSDK-release.aar 添加(拖放)到你的工程目录下lib中。
<img src="https://github.com/45game/Android-SDKDemo/blob/master/Snapshots/Framework.png"><br/>
2. 在对应项目下找到build.gradle添加以下库。<br/>
   
```java
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:24.0.0'
    compile 'com.google.code.gson:gson:2.8.0'
    compile 'com.loopj.android:android-async-http:1.4.9'
    compile 'com.android.support:appcompat-v7:24.0.0'
    compile(name: 'libFYSDK-release', ext:'aar')
}
```


3. 导入 `com.fy.sdk`。
```
import com.fy.sdk.FYSDK;
import com.fy.sdk.models.biz.output.FYOrder;
import com.fy.sdk.models.biz.output.FYRole;
import com.fy.sdk.models.biz.output.FYUser;
```

4. 在AndroidManifest.xml添加以下权限
```
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_LOGS"/>
<uses-permission android:name="android.permission.GET_TASKS" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
```

5. 初始化SDK。并更改对应的参数

#### 初始化SDK
 *  @param gameId    游戏编号
 *  @param subGameId 游戏子包
 *  @param apiKey 游戏密钥
```java
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
```

#### 登陆方法

```java
FYSDK.getInstance().fyLogin();
```

#### 登陆回调方法

```java
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

        //登陆后或者角色信息发生改变时调用
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
```



#### 支付方法

```java
FYOrder orderModel = new FYOrder();
orderModel.setProductId("productId1");
orderModel.setServerId("serverId1");
orderModel.setServerName("紫陌红尘");
orderModel.setTotalFee(1);
orderModel.setRoleId("9527");
orderModel.setRoleName("GG20思密达");
orderModel.setProductId("productId1");
orderModel.setProductName("拉克丝小姐姐");
orderModel.setProductDescription("真是一个深思熟虑的选择");
orderModel.setOrderId(getOrderStringByTime());
orderModel.setCustomInfo("自定义字段");
Log.e("FYSDKDemo","" + orderModel.toString());
FYSDK.getInstance().fyPay(orderModel);
```


#### 用户注销回调方法

```java
FYSDK.getInstance().setSdkLogoutCallback(new FYSDK.IFYSDKLogoutCallback() {
    @Override
    public void logout() {
        Log.e("FYSDKDemo", "注销成功");
    }
});
```





系统要求
==============
该项目最低支持 `minSdkVersion 16`。



许可证
==============
FYSDK 使用 MIT 许可证，详情见 LICENSE 文件。

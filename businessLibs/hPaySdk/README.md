

# Pay

对微信App支付、支付宝App支付、银联App支付的二次封装,对外提供一个相对简单的接口以及支付结果的回调

**使用方法**

### 1、直接引入
后续可以放到maven上面

### 2. Android Manifest配置

##### 2.1权限声明

```
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
<uses-permission android:name="org.simalliance.openmobileapi.SMARTCARD" />
<uses-permission android:name="android.permission.NFC" />
<uses-feature android:name="android.hardware.nfc.hce"/>
```

##### 2.2注册activity

`application`节点添加如下类容
```
        <!-- 微信支付 -->
              <activity
                  android:name="com.hdh.pay.weixin.WXPayEntryActivity"
                  android:configChanges="orientation|keyboardHidden|navigation|screenSize"
                  android:launchMode="singleTop"
                  android:theme="@android:style/Theme.Translucent.NoTitleBar" />
              <activity-alias
                  android:name=".wxapi.WXPayEntryActivity"
                  android:exported="true"
                  android:targetActivity="com.hdh.pay.weixin.WXPayEntryActivity" />
        <!-- 微信支付 end -->


        <!-- 支付宝支付 -->

        <activity
            android:name="com.alipay.sdk.app.H5PayActivity"
            android:configChanges="orientation|keyboardHidden|navigation|screenSize"
            android:exported="false"
            android:screenOrientation="behind"
            android:windowSoftInputMode="adjustResize|stateHidden" >
        </activity>
        <activity
            android:name="com.alipay.sdk.app.H5AuthActivity"
            android:configChanges="orientation|keyboardHidden|navigation"
            android:exported="false"
            android:screenOrientation="behind"
            android:windowSoftInputMode="adjustResize|stateHidden" >
        </activity>

        <!-- 支付宝支付 end -->

         <!-- 银联支付 -->
                <uses-library android:name="org.simalliance.openmobileapi" android:required="false"/>
                <activity
                    android:name="com.unionpay.uppay.PayActivity"
                    android:screenOrientation="portrait"
                    android:configChanges="orientation|keyboardHidden"
                    android:excludeFromRecents="true"
                    android:windowSoftInputMode="adjustResize"/>
                <activity
                    android:name="com.unionpay.UPPayWapActivity"
                    android:configChanges="orientation|keyboardHidden|fontScale"
                    android:screenOrientation="portrait"
                    android:windowSoftInputMode="adjustResize" >
                </activity>
        <!-- 银联支付 end -->
```

### 3. 发起支付

##### 3.1 微信支付


```
HPay.getIntance(mContext).toPay(JPay.PayMode.WXPAY, payParameters, new JPay.JPayListener() {
			@Override
			public void onPaySuccess() {
				Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show()
			}

			@Override
			public void onPayError(int error_code, String message) {
				Toast.makeText(mContext, "支付失败>"+error_code+" "+ message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPayCancel() {
				Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
			}
		});
```
`payParameters` 为JSON字符串格式如下：
```
{
  "appId": "",
  "partnerId": "",
  "prepayId": "",
  "sign": "",
  "nonceStr" : "",
  "timeStamp": ""
}
```

或者

```
HPay.getIntance(mContext).toWxPay(appId, partnerId, prepayId, nonceStr, timeStamp, sign, new JPay.JPayListener() {
			@Override
			public void onPaySuccess() {
				Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPayError(int error_code, String message) {
				Toast.makeText(mContext, "支付失败>"+error_code+" "+ message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPayCancel() {
				Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
			}
		});
```
##### 3.2 支付宝支付

```
HPay.getIntance(mContext).toPay(JPay.PayMode.ALIPAY, orderInfo, new JPay.JPayListener() {
			@Override
			public void onPaySuccess() {
				Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPayError(int error_code, String message) {
				Toast.makeText(mContext, "支付失败>"+error_code+" "+ message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPayCancel() {
				Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
			}
		});
```

或者

```
Alipay.getInstance(mContext).startAliPay(orderInfo, new JPay.JPayListener() {
			@Override
			public void onPaySuccess() {

			}

			@Override
			public void onPayError(int error_code, String message) {

			}

			@Override
			public void onPayCancel() {

			}
		});
```

##### 3.3 银联支付

步骤一 调起银联手机控件支付

```
HPay.getIntance(mContext).toUUPay("01",tn, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String dataOrg, String sign, String mode) {
                Toast.makeText(mContext, "支付成功>需要后台查询订单确认>"+dataOrg+" "+mode, Toast.LENGTH_SHORT).show();
            }
        });
```

>说明
第一个参数`mode` "00" - 启动银联正式环境 "01" - 连接银联测试环境
第二个参数`tn` 后台获取下单交易的流水号
第三方参数是`JPay` 封装的客户端结果的回调,需要在`onUUPay`中自行去后台查询订单的最终支付状态

步骤二 设置回调

```
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            UPPay.getInstance(this).onUUPayResult(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
```

### 4.案例的使用


> appId以及相关的key我们都从服务端获取

#### 4.1 客户端使用说明
 1. 将`AndroidManifest.xml` 的包名修改为申请应用的包名
 2. 将应用中的`build.gradle`的 `applicationId`修改为申请应用的包名
 3. 测试的时候修改默认的签名key

> 将key复制到项目的根目录(app)中并修改`buildTypes` 配置如下

```
 signingConfigs {
    debug {
            keyAlias '222'
            keyPassword '22@222'
            storeFile file(rootProject.ext.signPath)
            storePassword '222@2222'
        }
    }

```

#### 4.3 参考资料
1. 微信接入：https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=11_1
2. 支付宝接入：https://docs.open.alipay.com/204/105296/
3. 银联接入：https://open.unionpay.com/ajweb/help/file/toDetailPage?id=616&flag=1


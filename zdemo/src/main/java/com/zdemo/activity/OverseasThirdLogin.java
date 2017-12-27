package com.zdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.zdemo.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/**
 * 创建人：郑晓辉
 * 创建日期：2017/12/26 0026
 * 描述：海外第三方登录:facebook,twitter,google等
 */
public class OverseasThirdLogin extends BaseActivity {

    private TextView tv_facebook;
    private Button btn_facebook_share;
    private TextView tv_twitter;
    private Button btn_twitter_share;
    private TextView tv_google;

    private CallbackManager callbackManager;
    private TwitterAuthClient twitterAuthClient;

    @Override
    protected int inflateMainLayoutId() {
        return R.layout.activity_overseas_third_login;
    }

    @Override
    protected void initView(View view) {
        tv_facebook = (TextView) view.findViewById(R.id.tv_facebook);
        btn_facebook_share = (Button) view.findViewById(R.id.btn_facebook_share);
        tv_twitter = (TextView) view.findViewById(R.id.tv_twitter);
        btn_twitter_share = (Button) view.findViewById(R.id.btn_twitter_share);
        tv_google = (TextView) view.findViewById(R.id.tv_google);
    }

    @Override
    protected void setListener() {
        tv_facebook.setOnClickListener(this);
        btn_facebook_share.setOnClickListener(this);
        tv_twitter.setOnClickListener(this);
        btn_twitter_share.setOnClickListener(this);
        tv_google.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        //Facebook
        getKeyHash(context);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(com.facebook.login.LoginResult loginResult) {
                Toast.makeText(context, "facebook login success", Toast.LENGTH_SHORT).show();
                AccessToken accessToken = loginResult.getAccessToken();
                Log.d("zheng", "userId=" + accessToken.getUserId());
                Log.d("zheng", "token" + accessToken.getToken());
//                getLoginInfo(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(context, "facebook login cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(context, "facebook login error", Toast.LENGTH_SHORT).show();
                Log.d("zheng", "Facebook error=" + error.toString());
            }
        });

        //twitter
        twitterAuthClient = new TwitterAuthClient();

    }

    /**
     * 获取SHA签名（KeyHash），如Facebook需要的
     * 注意运行的时候，app需要正式的签名，日志打印出来后复制到facebook官网后台填写
     *
     * @param context
     */
    public void getKeyHash(Context context) {
        try {
            PackageInfo info = null;
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest messageDigest = null;
                messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                String keyHash = Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT);
                Log.d("zheng", "KeyHash:" + keyHash);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //    /**
//     * facebook获取登录信息
//     *
//     * @param accessToken
//     */
//    public void getLoginInfo(AccessToken accessToken) {
//        // TODO：拿到userId和token，传给游戏服务器校验
//        Log.d("zheng", "userId=" + accessToken.getUserId());
//        Log.d("zheng", "token" + accessToken.getToken());
//
//        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                if (object != null) {
//                    String id = object.optString("id");   //比如:1565455221565
//                    String name = object.optString("name");  //比如：Zhang San
//                    String gender = object.optString("gender");  //性别：比如 male （男）  female （女）
//                    String email = object.optString("email");  //邮箱：比如：56236545@qq.com
//                    //获取用户头像
//                    JSONObject picture = object.optJSONObject("picture");
//                    JSONObject data = picture.optJSONObject("data");
//                    String url = data.optString("url");
//                    //获取地域信息
//                    String locale = object.optString("locale");   //zh_CN 代表中文简体
//                    Log.d("zheng", object.toString());
//                    Toast.makeText(context, "" + object.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link,gender,birthday,email,picture,locale,updated_time,timezone,age_range,first_name,last_name");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);//facebook
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);//twitter
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_facebook:
                LoginManager.getInstance().logInWithReadPermissions((Activity) context, Arrays.asList("public_profile", "user_friends"));
                break;
            case R.id.btn_facebook_share:
                ShareLinkContent.Builder shareLinkContentBuilder = new ShareLinkContent.Builder();
                shareLinkContentBuilder.setContentUrl(Uri.parse("www.baidu.com"));
                ShareLinkContent shareLinkContent = shareLinkContentBuilder.build();
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareDialog.show(this, shareLinkContent);
                }
                break;
            case R.id.tv_twitter:
                twitterAuthClient.authorize(this, new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        Toast.makeText(context,"twitter login success",Toast.LENGTH_SHORT).show();
                        String authToken = String.valueOf(result.data.getAuthToken());
                        String userId = result.data.getUserId()+"";
                        Log.d("zheng", "userId=" + userId);
                        Log.d("zheng", "token" + authToken);
                        if (authToken !=null && userId != null){
                            //处理登录后的逻辑

                        }
                    }

                    @Override
                    public void failure(TwitterException e) {
                        Toast.makeText(context,"twitter login fail",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_twitter_share:
                try {
//                    Uri imageUri = FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID + ".file_provider",new File("/path/to/image"));
                    TweetComposer.Builder builder = new TweetComposer.Builder(this).url(new URL("https://www.google.com/"))
                            .text("just setting up my Twitter Kit.");
//                            .image(imageUri);
//                            .image(Uri.parse(""));
                    builder.show();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_google:

                break;
        }
    }
}

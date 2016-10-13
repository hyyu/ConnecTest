package com.adanibo.connectest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.facebook.share.widget.ShareDialog.Mode;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final        int     TWITTER_LOGIN_CODE = 140;

    private static final        String  TWITTER_KEY = "oumYXrXNC9CjIkdoUT6mYXTuT";
    private static final        String  TWITTER_SECRET = "Ua6WkK6uyuVUVm7REsgryyLBPt7icBKq5Qu09n1KwGItf4d5QW";

    private TwitterLoginButton  twitterBtn;

    private CallbackManager     callbackManager;

    private ShareContent        content;
    private LoginResult         fb_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);


        LoginButton         facebookBtn;

        facebookBtn = (LoginButton) findViewById(R.id.facebook_button);
        facebookBtn.setReadPermissions("email");

        callbackManager = CallbackManager.Factory.create();

        facebookBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        fb_token = loginResult;
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException error) {
                    }
                });

        content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();

        twitterBtn = (TwitterLoginButton) findViewById(R.id.twitter_button);

        twitterBtn.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
            }

            @Override
            public void failure(TwitterException exception) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TWITTER_LOGIN_CODE:
                twitterBtn.onActivityResult(requestCode, resultCode, data);

            default:
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public void fb_share(View v) {
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(content, Mode.AUTOMATIC);
    }

    public void tweet_it(View v) {
        String sdpath = Environment.getExternalStorageDirectory().toString();
        File img_to_tweet = new File(sdpath + "/DCIM/fabric.png");
        Uri myImageUri = Uri.fromFile(img_to_tweet);
        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text("just setting up my Fabric.")
                .image(myImageUri);
        builder.show();
    }

}

package com.adanibo.connectest;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final        int     FACEBOOK_REQUEST_CODE = 64206;
    private static final        int     TWITTER_REQUEST_CODE = 140;

    private static final        String  TWITTER_KEY = "oumYXrXNC9CjIkdoUT6mYXTuT";
    private static final        String  TWITTER_SECRET = "Ua6WkK6uyuVUVm7REsgryyLBPt7icBKq5Qu09n1KwGItf4d5QW";

    private TwitterLoginButton  twitterBtn;
    private LoginButton         facebookBtn;

    private CallbackManager     callbackManager;

    private LoginResult         fb_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        twitterBtn = (TwitterLoginButton) findViewById(R.id.twitter_button);

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

        // Pass the activity result to the login button.
    switch (requestCode) {
        case TWITTER_REQUEST_CODE:
            twitterBtn.onActivityResult(requestCode, resultCode, data);

        default:
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    }

}

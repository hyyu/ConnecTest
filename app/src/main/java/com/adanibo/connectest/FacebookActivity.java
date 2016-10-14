package com.adanibo.connectest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.facebook.share.widget.ShareDialog.Mode;

import java.util.Arrays;

public class FacebookActivity extends Activity {

    private static final int    FACEBOOK_REQUEST_CODE = 64206;

    private CallbackManager     mCallbackManager;
    private ShareContent        mShareLinkContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplicationContext(), loginResult.toString(), Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(FacebookActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        setContentView(R.layout.activity_facebook);

        LinearLayout fbLogScn = (LinearLayout) findViewById(R.id.fb_button);
        fbLogScn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(FacebookActivity.this, Arrays.asList("public_profile", "user_friends"));
            }
        });

        LinearLayout fbShareScn = (LinearLayout) findViewById(R.id.fb_share);
        fbShareScn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShareLinkContent = new ShareLinkContent.Builder()
                        .setContentTitle("Test share API Facebook")
                        .setContentDescription("Test de l'appli FacebookSample")
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();
                ShareDialog shareDialog = new ShareDialog(FacebookActivity.this);
                shareDialog.show(mShareLinkContent, Mode.AUTOMATIC);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FACEBOOK_REQUEST_CODE:
                mCallbackManager.onActivityResult(requestCode, resultCode, data);

            default:
                break;
        }
    }

}

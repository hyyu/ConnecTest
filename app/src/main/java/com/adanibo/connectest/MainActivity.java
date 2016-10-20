package com.adanibo.connectest;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        LinearLayout fbScn = (LinearLayout) findViewById(R.id.activity_facebook);
        fbScn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FacebookActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout twtScn = (LinearLayout) findViewById(R.id.activity_twitter);
        twtScn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TwitterActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout istgScn = (LinearLayout) findViewById(R.id.activity_istg);
        istgScn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IstgActivity.class);
                startActivity(intent);
            }
        });

    }

}

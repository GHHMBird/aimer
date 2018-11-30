package com.buyint.mergerbot.UIs.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.VideoView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.loginAndRegister.activity.LoginActivity;
import com.buyint.mergerbot.UIs.loginAndRegister.activity.RegisterLoginActivity;
import com.buyint.mergerbot.UIs.setting.LanguageActivity;
import com.buyint.mergerbot.Utility.PermissionUtils;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.bus.annotation.BusReceiver;

import java.io.File;

public class SplashActivity extends BaseActivity {

    public static int MY_PERMISSIONS_REQUEST = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        splashTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //检查应用必备权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] strings = PermissionUtils.hasPermission(/*Manifest.permission.ACCESS_COARSE_LOCATION,*/ Manifest.permission.READ_PHONE_STATE);
            if (strings.length > 0) {
                ActivityCompat.requestPermissions(this, strings, MY_PERMISSIONS_REQUEST);
            }
        }

       initView();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        initView();
    }

    public void initView(){
        findViewById(R.id.activity_splash_language).setOnClickListener(v -> {
            Intent intent = new Intent(SplashActivity.this, LanguageActivity.class);
            startActivity(intent);
        });
        VideoView vv = findViewById(R.id.activity_splash_vv);
        vv.setVideoPath(Uri.parse(getString(R.string.videoBasePath) + getPackageName() + File.separator + R.raw.welcome).toString());
        vv.start();
        vv.setOnPreparedListener(mp -> {
            mp.setVolume(0, 0);
            mp.start();
            mp.setLooping(true);
        });
        vv.setOnCompletionListener(mp -> vv.start());

        View viewLogin = findViewById(R.id.splash_view5_login);
        View viewRegister = findViewById(R.id.splash_view5_register);
        viewLogin.setOnClickListener(v -> startActivityForResult(LoginActivity.class, 10001));
        viewRegister.setOnClickListener(v -> startActivityForResult(RegisterLoginActivity.class, 10000));

        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.rotate_anim1);
        findViewById(R.id.activity_splash_iv1).setAnimation(a1);
        findViewById(R.id.activity_splash_iv1).startAnimation(a1);

        Animation a2 = AnimationUtils.loadAnimation(this, R.anim.rotate_anim2);
        findViewById(R.id.activity_splash_iv2).setAnimation(a2);
        findViewById(R.id.activity_splash_iv2).startAnimation(a2);

    }

    @BusReceiver
    public void StringEvent(String event) {
        if (event.equals(getString(R.string.buyint_language_change))) {
            recreate();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == 10085) {
            finish();
        } else if (requestCode == 10001 && resultCode == 10086) {
            finish();
        }
    }

    public void startActivityForResult(Class<? extends Activity> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_rotate_0_1, R.anim.activity_rotate_1_0);
    }
}

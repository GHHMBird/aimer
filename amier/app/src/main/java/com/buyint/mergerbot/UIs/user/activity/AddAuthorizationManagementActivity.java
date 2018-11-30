package com.buyint.mergerbot.UIs.user.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.user.fragment.SetUserIconFragment;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.dto.AddSecretaryRequest;
import com.buyint.mergerbot.dto.StringResponse;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.interfaces.IUserIconUpdate;
import com.buyint.mergerbot.presenter.MyDetailPresenter;
import com.buyint.mergerbot.view.SelectableBackgroundView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAuthorizationManagementActivity extends BaseActivity implements IUserIconUpdate {

    private int CROP_PICTURE = 30;
    private File finalPic;
    private Uri cropImageUri;
    private Uri uri;
    private MyDetailPresenter presenter;
    private ImageView ivIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        presenter = new MyDetailPresenter(this);

        setTitleWhiteAndTextBlank();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_authorization_management);

        findViewById(R.id.toolbar_white_top).setVisibility(View.GONE);
        findViewById(R.id.toolbar_white_right_add_rl).setVisibility(View.GONE);
        findViewById(R.id.toolbar_white_right_search_rl).setVisibility(View.GONE);
        findViewById(R.id.toolbar_white_back).setOnClickListener(v -> onBackPressed());
        ((TextView) findViewById(R.id.toolbar_white_title)).setText(getString(R.string.mine_buyint_power));

        ivIcon = findViewById(R.id.activity_add_authorization_management_iv);

        EditText etName = findViewById(R.id.activity_add_authorization_management_name);
        EditText etCompany = findViewById(R.id.activity_add_authorization_management_company);
        EditText etPosition = findViewById(R.id.activity_add_authorization_management_position);
        EditText etPhone = findViewById(R.id.activity_add_authorization_management_phone);
        EditText etEmail = findViewById(R.id.activity_add_authorization_management_email);
        EditText etPassword = findViewById(R.id.activity_add_authorization_management_password);

        RelativeLayout rlIv = findViewById(R.id.activity_add_authorization_management_iv_rl);

        SelectableBackgroundView sbv = findViewById(R.id.activity_add_authorization_management_button);
        sbv.setListener(() -> {
            if (!TextUtils.isEmpty(etName.getText().toString().trim()) && !TextUtils.isEmpty(etCompany.getText().toString().trim()) && !TextUtils.isEmpty(etPosition.getText().toString().trim()) && !TextUtils.isEmpty(etPhone.getText().toString().trim()) && !TextUtils.isEmpty(etEmail.getText().toString().trim()) && !TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                showLoadingFragment(R.id.activity_add_authorization_management_fl);
                AddSecretaryRequest request = new AddSecretaryRequest();
                request.name = etName.getText().toString().trim();
                request.company = etCompany.getText().toString().trim();
                request.title = etPosition.getText().toString().trim();
                request.phone = etPhone.getText().toString().trim();
                request.password = etPassword.getText().toString().trim();
                request.email = etEmail.getText().toString().trim();
                HttpHelper.addSecretary(request).subscribe(b -> {
                    if (b) {
                        showToast(getString(R.string.add_success));
                        setResult(102);
                        finish();
                    }
                    removeLoadingFragment();
                }, throwable -> {
                    removeLoadingFragment();
                    showThrowable(throwable);
                });
            } else {
                showToast("请填写完整信息");
            }
        });

        rlIv.setOnClickListener(v -> {
            RxPermissions rxPermissions = new RxPermissions(AddAuthorizationManagementActivity.this);
            SetUserIconFragment iconFragment = new SetUserIconFragment(new SetUserIconFragment.SetUserIconFragmentListener() {
                @Override
                public void photoClick() {
                    //检查应用必备权限
                    rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(granted -> {
                                if (granted) {
                                    getPhoto();
                                } else {
                                    showToast(getString(R.string.permission_denied));
                                }
                            });
                }

                @Override
                public void pictureClick() {
                    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(granted -> {
                                if (granted) {
                                    getPicture();
                                } else {
                                    showToast(getString(R.string.permission_denied));
                                }
                            });
                }
            });
            iconFragment.show(getSupportFragmentManager(), "SetUserIconFragment");
        });
    }


    public void startPhotoZoom(Uri uri) {
        finalPic = new File(getExternalCacheDir(), "crop_image.jpg");
        try {
            if (finalPic.exists()) {
                finalPic.delete();
            }
            finalPic.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        cropImageUri = Uri.fromFile(finalPic);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        intent.putExtra("return-data", false);
        intent.putExtra("circleCrop", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(data.getData());
        } else if (requestCode == 10001 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(uri);
        } else if (requestCode == CROP_PICTURE && resultCode == Activity.RESULT_OK) {
            presenter.userIconUpdate(finalPic);
        }
    }

    private void getPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10000);
    }

    private void getPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int version = Build.VERSION.SDK_INT;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String filename = sdf.format(new Date());
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            File file = new File(Environment.getExternalStorageDirectory(), filename + ".jpg");
            if (version < 24) {
                uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                uri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
        }
        startActivityForResult(intent, 10001);
    }

    @Override
    public void userIconUpdateSuccess(StringResponse response) {
        String data = response.getData();
        Glide.with(this).load(data).placeholder(R.mipmap.default_user).into(ivIcon);
    }

    @Override
    public void userIconUpdateFail(Throwable throwable) {
        showThrowable(throwable);
    }
}
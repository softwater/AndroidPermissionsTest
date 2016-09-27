package com.zjq.androidpermissionstest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private Intent mCallPhoneIntent;
  private CameraFragment mCameraFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mCallPhoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:99999999"));

    findViewById(R.id.call).setOnClickListener(this);

    // 测试Fragment里使用
    mCameraFragment = new CameraFragment();
    getSupportFragmentManager().beginTransaction().add(R.id.container, mCameraFragment).commit();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.call:
        PermissionUtil.startActivityWithPermisssions(this, mCallPhoneIntent,
            Manifest.permission.CALL_PHONE, PermissionUtil.RequestPermissionCode.CALL, null);
        break;
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    // super.onRequestPermissionsResult()这句并不能让Fragment回调，所以手动回调
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    mCameraFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
      case PermissionUtil.RequestPermissionCode.CALL: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          startActivity(mCallPhoneIntent);
        } else {
          Toast.makeText(this, "用户取消授权", Toast.LENGTH_LONG).show();
        }
      }
    }
  }
}

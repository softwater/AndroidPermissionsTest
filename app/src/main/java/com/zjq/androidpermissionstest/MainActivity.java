package com.zjq.androidpermissionstest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.call).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.call:
        if (PermissionUtil.checkSelfPermission(this, Manifest.permission.CALL_PHONE)) {
          startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:99999999")));
        } else {
          if (PermissionUtil.shouldShowRequestPermissionRationale(this,
              Manifest.permission.CALL_PHONE)) {
            // 请求权限不成功，展示说明
            new AlertDialog.Builder(this).setTitle("说明")
                .setMessage("需要调用拨打电话的权限，以便于可以直接进入通话界面")
                .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    PermissionUtil.requestPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE, PermissionUtil.RequestPermissionCode.CALL);
                  }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                  @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                  }
                })
                .show();
            return;
          }
          PermissionUtil.requestPermission(this, Manifest.permission.CALL_PHONE,
              PermissionUtil.RequestPermissionCode.CALL);
        }
        break;
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case PermissionUtil.RequestPermissionCode.CALL: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          try {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:99999999")));
          } catch (SecurityException e) {
            e.printStackTrace();
          }
        } else {
          Toast.makeText(this, "用户取消授权", Toast.LENGTH_LONG).show();
        }
      }
    }
  }
}

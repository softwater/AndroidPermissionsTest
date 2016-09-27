package com.zjq.androidpermissionstest;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * 自Android 6.0起需要动态获取权限的工具类
 * 需要添加<code>compile 'com.android.support:support-v4:24.2.1'</code>依赖
 * <p>
 * 在Activity里实现<code>onRequestPermissionsResult</code>方法：
 * </p>
 * Created by softwater on 16/9/8.
 * Modified by softwater on 16/9/8.
 */
public class PermissionUtil {
  // ==================onRequestPermissionsResult========================
/*
@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
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
 */
  // ==================onRequestPermissionsResult========================

  /**
   * 请求权限的请求码
   */
  public static class RequestPermissionCode {
    public static final int CAMERA = 0x900;
    public static final int CALL = 0x910;
  }

  /**
   * 检测是否授权
   *
   * @param context Context
   * @param permission {@link Manifest.permission}中的常量
   * @return true，已经授权
   */
  public static boolean checkSelfPermission(Context context, String permission) {
    return ContextCompat.checkSelfPermission(context, permission)
        == PackageManager.PERMISSION_GRANTED;
  }

  /**
   * 是否需要展示说明
   *
   * @param activity Activity
   * @param permission {@link Manifest.permission}中的常量
   * @return true 是
   */
  public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
    return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
  }

  /**
   * 请求权限
   *
   * @param activity Activity
   * @param permission {@link Manifest.permission}中的常量
   * @param requestCode 请求 code
   * @return 请求权限是否成功
   */
  public static void requestPermission(Activity activity, String permission, int requestCode) {
    // Permission has not been granted yet. Request it directly.
    ActivityCompat.requestPermissions(activity, new String[] { permission }, requestCode);
  }

  /**
   * 启动需要权限的activity
   *
   * @param activity Activity
   * @param intent Intent
   * @param permission {@link Manifest.permission}字段
   * @param requestPermissionCode 请求权限的code
   * @param dialog {@link Dialog}
   */
  public static void startActivityWithPermisssions(@NonNull final Activity activity,
      @NonNull final Intent intent, @NonNull final String permission,
      @NonNull final int requestPermissionCode, @Nullable Dialog dialog) {
    if (checkSelfPermission(activity, permission)) {
      activity.startActivity(intent);
    } else {
      if (shouldShowRequestPermissionRationale(activity, permission)) {
        if (dialog == null) {
          new AlertDialog.Builder(activity).setTitle("说明")
              .setMessage("需要 " + permission + " 权限，以获得更好的体验。")
              .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
                  requestPermission(activity, permission, requestPermissionCode);
                }
              })
              .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
                }
              })
              .show();
        } else {
          dialog.show();
        }
      } else {
        requestPermission(activity, permission, requestPermissionCode);
      }
    }
  }

  /**
   * 启动需要权限的activity，并且返回数据
   *
   * @param activity Activity
   * @param intent Intent
   * @param requestActivityCode 打开activity的请求码
   * @param permission {@link Manifest.permission}字段
   * @param requestPermissionCode {@link RequestPermissionCode}字段，权限请求码
   * @param dialog {@link Dialog}
   */
  public static void startActivityForResultWithPermisssions(@NonNull final Activity activity,
      @NonNull final Intent intent, @NonNull final int requestActivityCode,
      @NonNull final String permission, @NonNull final int requestPermissionCode,
      @Nullable Dialog dialog) {
    if (checkSelfPermission(activity, permission)) {
      activity.startActivityForResult(intent, requestActivityCode);
    } else {
      if (shouldShowRequestPermissionRationale(activity, permission)) {
        if (dialog == null) {
          new AlertDialog.Builder(activity).setTitle("说明")
              .setMessage("需要 " + permission + " 权限，以获得更好的体验。")
              .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
                  requestPermission(activity, permission, requestPermissionCode);
                }
              })
              .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
                }
              })
              .show();
        } else {
          dialog.show();
        }
      } else {
        requestPermission(activity, permission, requestPermissionCode);
      }
    }
  }
}

package com.zjq.androidpermissionstest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

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
/*
@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
 @NonNull int[] grantResults) {
 super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 if (requestCode == PermissionUtil.RequestPermissionCode.CAMERA && permissions[0].equals(
 Manifest.permission.CAMERA) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
 startActionImageCapture();
 }
 }
 */

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
}
